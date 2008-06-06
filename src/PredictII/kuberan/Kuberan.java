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

//Source file: Y:/E-Cash-Project/TestBed/development/kuberan/Kuberan.java

/* This Is a Copy Lefted Software
(CL) 2001 Spetember
By Nishanth 'Lazarus' Menon
Calicut Regional Engineering College
Calicut */

package kuberan;

import utils.misc.logger;
import java.lang.*;
import java.net.*;
import java.util.*;
import org.logi.crypto.*;
import utils.misc.*;
import utils.crypto.*;
import messages.tools.*;
import messages.*;

/**
 * @author 
 * @version 
 * This Is the Zone Server Software Main Head
 */
public class Kuberan
{

    public HandleUserRequests theHandleUserRequests;
    public logger theLogger;
    public KuberanInitialize theKuberanInitialize;
    public logger theLog;
    public ServerSocket Me;
    public Socket Client;
    public Kuberan (String ConfFile)
    {
        Crypto.initRandom ();
        try
        {
            theKuberanInitialize = new KuberanInitialize (ConfFile);
        }
        catch (Exception e)
        {

    /** Handler
    */ e.printStackTrace ();
            System.err.println ("Unable to Startup...." + e + "\n Exiting");
            return;
        }
        theLog = theKuberanInitialize.getLog ();
        try
        {
            Me = new ServerSocket (theKuberanInitialize.getMyPort ());
        }
        catch (Exception e)
        {

       /** handle Any errors here*/
            theLog.logit ("Unable to Start Up Server - Exception " + e);
            e.printStackTrace ();
            return;
        }
        System.out.println ("Ready for Users");

    /** Start up any Nonsense code here */

    /** I Have to Start a Viewer thread here */
        // Now Loop till End of World
        while (true)
          {
              System.out.println ("Listening for More Users on Port :" +
                                  theKuberanInitialize.getMyPort ());
              try
              {
                  Client = Me.accept ();
                  Client.setSoTimeout (theKuberanInitialize.getMaxTimeOut ());
                  System.out.println ("Got Client: " + Client);
                  theHandleUserRequests =
                      new HandleUserRequests (Client, theKuberanInitialize, theLog);
                  theHandleUserRequests.start ();
                  theKuberanInitialize.addUser (theHandleUserRequests);
                  Vector s = theKuberanInitialize.getUsers ();

                  if (s != null)
                      for (int i = 0; i < s.size (); i++)
                        {
                            HandleUserRequests a = (HandleUserRequests) s.elementAt (i);

                            System.out.println ("Connection " + i + " :" + a.isDone () +
                                                "::" + a.getConnectionInfo ());
                        }
              }
              catch (Exception e)
              {

      /** Write handler Code here
	  */
              }
          }                     // End of Infinite while

    }

    public static void main (String[]args) throws Exception
    {
        try
        {
            Kuberan k = new Kuberan (args[0]);
        }
        catch (Exception e)
        {
        }
        System.exit (0);
    }
}
