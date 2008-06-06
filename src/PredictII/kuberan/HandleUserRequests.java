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

//Source file: Y:/E-Cash-Project/TestBed/development/kuberan/HandleUserRequests.java

/* This Is a Copy Lefted Software
(CL) 2001 Spetember
By Nishanth 'Lazarus' Menon
Calicut Regional Engineering College
Calicut */

package kuberan;

import org.logi.crypto.*;
import org.logi.crypto.sign.*;
import org.logi.crypto.keys.*;
import org.logi.crypto.modes.*;

import java.lang.*;
import java.net.*;
import java.util.*;
import java.io.*;
import org.logi.crypto.*;
import utils.misc.*;
import utils.*;
import utils.crypto.*;
import messages.tools.*;
import messages.*;
import network.*;

/**
 * @author 
 * @version 
 * Many Users might connect So we need this
 */
public class HandleUserRequests extends User
{

    private Socket UserSocket;
    private KuberanInitialize theKuberanInitialize;

    private network.Authenticator theAuthenticator;
    private String MyState;
    public HandleAuthentication theHandleAuthentication;
    private boolean done;
    public HandleACitizen theHandleACitizen;
    public HandleAMerchant theHandleAMerchant;
    public String UserName;


    public HandleUserRequests (Socket Client,
                               KuberanInitialize theKuberanInitialize, logger theLogger)
    {
        // theKuberanInitialize.addUser (this);
        UserSocket = Client;
        ConnectionInfo =
            Client.getInetAddress ().toString () + ":" + Client.getPort () + "-" +
            Client.getLocalPort ();
        this.theKuberanInitialize = theKuberanInitialize;
        this.theLogger = theLogger;
        MyPrivateKey = (CipherKey) theKuberanInitialize.getMyKeyPair ().getPrivate ();
        MyPublicKey = (CipherKey) theKuberanInitialize.getMyKeyPair ().getPublic ();
        done = false;
        theLogger.logit ("Connection Established from " + Client);

    }

    public String getConnectionInfo ()
    {
        return ConnectionInfo;
    }

    public boolean isDone ()
    {
        return done;
    }

    public String toString ()
    {
        if (!(theHandleACitizen == null))
            return theHandleACitizen.toString ();
        if (!(theHandleAMerchant == null))
            return theHandleAMerchant.toString ();
        return (ConnectionInfo + ":-" + MyState);
    }


  /****************************************************************************************
    *************START OF THE MESSAGES HANDLING SECTION***********************************
    ***************************************************************************************
    */

    /** 
     * This handles the User authentication part
     */
    private boolean getAUTH_USER () throws Exception
    {
        AUTH_USER a = new AUTH_USER ();
        boolean set = true;
        // System.out.println ("*****************Brillianto...");
         a = (AUTH_USER) getGenRQ (a, UserReader, false);
         System.out.println ("Got AUthentication " + a);
         MyState = "Got AUthentication " + a;
        {
            // The DataBase Query

            Vector d = theKuberanInitialize.db.GetPasswordEntry (a.getUserName ().trim ());
             System.out.println ("GOt Password Table");
            for (int i = 0; i < d.size (); i++)
                 System.out.println (d.elementAt (i));
            if (d.size () == 0)
              {
                  // Failiure of login No UserID
                  set = false;
                  System.err.println ("No Login ID entry for UserName=" + a.getUserName ());
              }
            else
              {
                  String PassPhrase = ((String) d.elementAt (0)).trim ();

                  if (!a.getPASSPHRASE ().equals (PassPhrase))
                    {
                        // Failiure of Login wrong Passphrase
                        System.err.println ("Invalid PassPhrase-" + a);
                        set = false;
                    }
                  else
                    {
                        String g = ((String) d.elementAt (1)).trim ();

                        System.out.println ("Gada g=" + g);
                        if (!((String) d.elementAt (1)).trim ().equals ("f"))
                          {
                              // Failiure of Login - Frozen Login
                              System.err.println ("Frozen Login-" + a);
                              set = false;
                          }
                    }
              }

        }                       // End of Database Search
        /* 
         * * // System.out.println ("CHK1"); if * (!a.getUserName ().equals
         * ("Nishanth")) set * = false; * * // System.out.println *
         * ("******************Brilliant"); // * System.out.println ("CHK2");
         * if * (!a.getPASSPHRASE ().equals ("Poda")) set = * false; //
         * System.out.println * ("******************Brilliant"); // *
         * System.out.println ("CHK3"); */
        if (!set)
          {
              theLogger.logit ("AUTHENTICATION FAILIURE USERNAME=" + a.getUserName () +
                               " From " + getConnectionInfo ());
              UserWriter.setABORT_REASON ("AUTH_USER_FAIL");
              MyState = "Authentication Fail";
              throw (new Exception ("Authentication Failiure from" + getConnectionInfo ()));
          }
        theLogger.logit (a.toString ());
        UserName = a.getUserName ();

        // System.out.println ("Okay Shall we proceeed--------------");
        return set;
    }

    /** Handles the sending of the authentication result*/
    private void sendAUTH_RES (boolean RESULT) throws Exception
    {
        MyState = "Sending AUTH_RES = " + RESULT;
        AUTH_RES a = new AUTH_RES (new Boolean (RESULT));
         sendGenRQ (a, UserWriter, false);
    }

  /****************************************************************************************
    ************* END  OF THE MESSAGES HANDLING SECTION***********************************
    ***************************************************************************************
    */

    public void closeAllChildThreads ()
    {
        MyState = "Closing Connection...";
        try
        {
            this.sleep (10000);
        }
        catch (Exception e)
        {
            System.out.println ("Could Not Sleep" + e);
        }
        try
        {
            super.closeAllChildThreads ();
        }
        catch (Exception e)
        {                       // Ignore this
        }
        try
        {
            theLogger.writelog ("Closing connection to .." + getConnectionInfo ());
            System.out.println ("Closed Connection to ..." + getConnectionInfo ());
            // UserSocket.close ();
        }
        catch (Exception e)
        {                       // Ignore it
        }
        theKuberanInitialize.deleteUser (this);
    }

  /** The Thread in Motion */
    public void run ()
    {
        done = false;

        System.out.println ("So We are on the Authentication Path for " + UserSocket);

    /** Step 1. Authentication Dear */
        try
        {
            System.out.println ("Time Initialization:-" + UserSocket);
            MyState = "TimeInitialization Started";
            DataInputStream i = new DataInputStream (UserSocket.getInputStream ());
            DataOutputStream o = new DataOutputStream (UserSocket.getOutputStream ());
            networkAnalyzerServer ja = new networkAnalyzerServer (i, o, 30);

            System.out.println ("Time Initialization-Completed for" + UserSocket);
            MyState = "TimeInitialization Complete";

            MyState = "Starting Authentication...";
            theAuthenticator =
                new network.Authenticator (UserSocket, theLogger,
                                           theKuberanInitialize.getMyPublicKey ());

            while (!theAuthenticator.isDone ()) ;
            if (!theAuthenticator.isSuccess ())
                throw (new Exception ("Bad Attempt at Authentication"));
            if (theAuthenticator.getMyMessage () == null
                || theAuthenticator.getRTT () == 0 || theAuthenticator.getRemoteKey () == null)
              {

          /** Handle Authentication Failiure*/
                  theLogger.writelog ("Authentication Failiure" + UserSocket);
                  MyState = "Authentication Failiure";
                  return;
              }
            try
            {
                UserPublicKey =
                    (CipherKey) Crypto.fromString (theAuthenticator.getRemoteKey ());
                theLogger.logit (UserPublicKey.toString ());
            }
            catch (Exception e)
            {
                // Send Transaction Abort to needed user
                theLogger.writelog ("NETWORK AUTHENTICATION FAILIURE FROM " + UserSocket);
                closeAllChildThreads ();
                return;

         /** Wrritte handler */
            }
            theLogger.logit ("Conpleted Authentication - ready for Messages...." + UserSocket);
            System.out.
                println ("Conpleted Authentication - ready for Messages...." + UserSocket);
            // Preparing for action
            UserPublicKey = (CipherKey) Crypto.fromString (theAuthenticator.getRemoteKey ());

    /** Step 2. Create the Secure DataStreams*/
            System.out.println ("MyPublic Key = " + MyPublicKey +
                                "\n My Private Key= " + MyPrivateKey);
            MyState = "Generation of Session Keys Complete";

            UserReader =
                new SecureReader (MyPrivateKey, UserPublicKey,
                                  new DataInputStream (UserSocket.getInputStream ()),
                                  theLogger, "USR_R");
            UserWriter =
                new SecureWriter (MyPrivateKey, UserPublicKey,
                                  new DataOutputStream (UserSocket.
                                                        getOutputStream ()), theLogger,
                                  "USR_W");
            UserReader.start ();
            UserWriter.start ();
            MyState = "Completed Creating Secure DataStreams..";

    /** End of DataStream creations*/
            MyState = "Auth Success.. Waiting for Transaction to Start";
            try
            {                   // / TRyy Data Z

       /**********************************************************************
        *  Step 3 So We are in the bussiness of sending messages...***********
        **********************************************************************
        */
                boolean result = getAUTH_USER ();   // Get Request for

                // Authentication
                sendAUTH_RES (result);  // Send the Authentication result

                SuperMessage su = getStupid (UserReader, false);

                if (su.getMESSAGE_NAME ().equals ("REQ_WIT"))
                  {
                      System.out.println (this + " Is Handling a Citizen");
                      MyState = "Detected a Citizen";
                      theHandleACitizen =
                          new HandleACitizen (UserReader, UserWriter, theKuberanInitialize,
                                              theLogger, ConnectionInfo,
                                              new REQ_WIT (su.getMessage ()), UserName);
                      try
                      {
                          theHandleACitizen.start ();
                      }
                      catch (Exception e)
                      {
                          e.printStackTrace ();
                          System.exit (0);
                      }
                      try
                      {
                          theHandleACitizen.join ();
                      }
                      catch (Exception e)
                      {
                      }
                      System.out.println ("Completed the Citizen Handler");
                      // Start UserThread for Citizen
                      // Wait for it to die
                  }
                else if (su.getMESSAGE_NAME ().equals ("CSH_DEP"))
                  {
                      MyState = "Detected a Merchant";
                      System.out.println (this + " Is Handling a Merchant");
                      theHandleAMerchant =
                          new HandleAMerchant (UserReader, UserWriter, theKuberanInitialize,
                                               theLogger, ConnectionInfo,
                                               new CSH_DEP (su.getMessage ()), UserName);
                      try
                      {
                          theHandleAMerchant.start ();
                      }
                      catch (Exception e)
                      {
                          e.printStackTrace ();
                          System.exit (0);
                      }
                      try
                      {
                          theHandleAMerchant.join ();
                      }
                      catch (Exception e)
                      {
                      }
                      System.out.println ("Completed the Merchant Handler");
                      // Start UserThread for Merchant
                      // Start the User Thread for the Merchant
                      // Wait for it to die
                  }
                else
                  {
                      // Stupid of him to send us a bogus transmission
                      throw new Exception ("Invalid Message");
                  }

       /**********************************************************************
        **********So We are done   bussiness of sending messages...***********
        **********************************************************************
        */
            }
            catch (Exception e)
            {
                /* Write handler */
                e.printStackTrace ();
                closeAllChildThreads ();
                return;
            }                   // End of try data Z
        }
        catch (Exception e)
        {
            e.printStackTrace ();
            closeAllChildThreads ();
            return;

  /** Write Handler Code here */
        }
        System.out.println ("Completed Execution");
        try
        {
            closeAllChildThreads ();
            UserSocket.shutdownInput ();
            UserSocket.shutdownOutput ();
            UserSocket.close ();    // Close the Client Socket
        }
        catch (Exception e)
        {
            // Ignore
        }

        done = true;
        MyState = "Dead Connection";
        theKuberanInitialize.deleteUser (this);
    }
}
