/*
 * Copyright (C) 2001-2008  Nishanth Menon <menon.nishanth at gmail dot com>
 *
 * Part of the predict two project done during NITC Final year.
 * Predict2: http://code.google.com/p/predict2/
 * NITC: http://web.nitc.ac.in/~cse/
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  US
 */

//Source file: Y:/E-Cash-Project/TestBed/development/brahma/Brahma.java

/* This Is a Copy Lefted Software
(CL) 2001 Spetember
By Nishanth 'Lazarus' Menon
Calicut Regional Engineering College
Calicut */

package brahma;

import utils.misc.logger;
import java.lang.*;
import org.logi.crypto.*;
import utils.misc.*;
import utils.crypto.*;
import messages.tools.*;
import messages.*;

/**
 * @author 
 * @version 
 * The real Brahma Class
 */
public class Brahma
{
    public static logger theLogger;
    public HandleMasterServerRequests theHandleMasterServerRequests;
    public HandleZoneServerDepositRequests theHandleZoneServerDepositRequests;
    public HandleZoneServerValidatorRequests theHandleZoneServerValidatorRequests;
    public BrahmaInitialize theBrahmaInitialize;

    /** The Base Class that creates four threads- one for each validation,depositing and remote verification
    */
    public Brahma (String ConfFile) throws Exception
    {
        try
        {
            theBrahmaInitialize = new BrahmaInitialize (ConfFile);
        }
        catch (Exception e)
        {
            System.err.println ("Unable to Start up Master Server due to Failure-" + e);
            return;
        }
        theLogger = theBrahmaInitialize.getLog ();

        theHandleMasterServerRequests =
            new HandleMasterServerRequests (theBrahmaInitialize, theLogger);
        theHandleZoneServerValidatorRequests =
            new HandleZoneServerValidatorRequests (theBrahmaInitialize, theLogger);
        theHandleZoneServerDepositRequests =
            new HandleZoneServerDepositRequests (theBrahmaInitialize, theLogger);
        theHandleMasterServerRequests.start ();
        theHandleZoneServerValidatorRequests.start ();
        theHandleZoneServerDepositRequests.start ();
        theHandleMasterServerRequests.join ();
        theHandleZoneServerValidatorRequests.join ();
        theHandleZoneServerDepositRequests.join ();
        theLogger.close ();
    }

    /** Startup man... In Case We do not have a initiator*/
    public static void main (String[]args) throws Exception
    {
        if (args.length < 1)
            throw (new Exception ("Argument not enough"));
        try
        {
            Brahma theBrahma = new Brahma (args[0]);
        }
        catch (Exception e)
        {
            System.err.println ("Fatal Error-" + e);
        }
        System.exit (0);
    }
}
