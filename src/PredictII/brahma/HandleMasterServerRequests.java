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

//Source file: Y:/E-Cash-Project/TestBed/development/brahma/HandleMasterServerRequests.java

/* This Is a Copy Lefted Software
(CL) 2001 Spetember
By Nishanth 'Lazarus' Menon
Calicut Regional Engineering College
Calicut */

package brahma;

import java.lang.*;
import java.net.*;
import org.logi.crypto.*;
import utils.misc.*;
import utils.crypto.*;
import messages.tools.*;
import messages.*;

/**
 * @author 
 * @version 
 * Master Server Requests are handled here
 */
public class HandleMasterServerRequests extends Thread
{
    private ServerSocket mySocket;
    private logger theLogger;
    private BrahmaInitialize Init;
    public boolean done;
    public HandleMasterServerRequests (BrahmaInitialize Init,
                                       logger theLogger) throws Exception
    {
        try
        {
            System.out.println ("Master Server Request Server Listening to " +
                                Init.myRemoteRequestPort);
            theLogger.writelog ("Master Server Request Server Listening to " +
                                Init.myRemoteRequestPort);
            mySocket = new ServerSocket (Init.myRemoteRequestPort);
        }
        catch (Exception e)
        {
            System.out.println ("Unable to Connect to the Master ServerPort" +
                                Init.myRemoteRequestPort);
            throw (new
                   Exception ("Unable to Connect to the Master ServerPort" +
                              Init.myRemoteRequestPort));
        }
        this.Init = Init;

        this.theLogger = theLogger;
        done = false;
    }

    public void run ()
    {
        while (!done)
          {
              try
              {
                  System.out.
                      println ("Master Server Request Server Listening for Clients at " +
                               Init.myRemoteRequestPort);
                  Socket s;

                  try
                  {
                      s = mySocket.accept ();
                  }
                  catch (Exception e)
                  {
                      theLogger.
                          logit ("MasterServer Request Server unable to accept due to -" + e);
                      e.printStackTrace ();
                      return;
                  }
                  RemoteMasterServerValidator theRemoteMasterServerValidator =
                      new RemoteMasterServerValidator (s, Init, theLogger);
                  Init.addRemoteRequest (theRemoteMasterServerValidator);
                  theRemoteMasterServerValidator.start ();
              }
              catch (Exception e1)
              {

                  // Handle Exception
              }
          }                     // End of While
    }                           // End of the The Thread runner
}
