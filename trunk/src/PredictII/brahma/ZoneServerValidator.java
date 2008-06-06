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

//Source file: Y:/E-Cash-Project/TestBed/development/brahma/ZoneServerValidator.java

/* This Is a Copy Lefted Software
(CL) 2001 Spetember
By Nishanth 'Lazarus' Menon
Calicut Regional Engineering College
Calicut */

package brahma;

import messages.CSH_VAL_MS;
import messages.VAL_REP_MS;
import messages.SuperMessage;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.net.*;
import org.logi.crypto.*;
import utils.misc.*;
import utils.crypto.*;
import messages.tools.*;
import messages.*;
import network.*;

/**
 * @author 
 * @version 
 */
public class ZoneServerValidator extends User
{
    public SuperMessage theSuperMessage;
    public String RemoteMasterServer;
    public BrahmaInitialize Init;
    public String IDENTIFIER;
    public InSecureReader MasterReader;
    public InSecureWriter MasterWriter;
    public Socket MasterSocket;
    public String MasterServerID;

    String MasterIp;
    int MasterPort;

    private void AnalyseMaster (String a) throws Exception
    {
        MyState = "Analysing " + a;
        CSH_IDENTIFIER b = new CSH_IDENTIFIER (a);
         System.out.println ("Analyse Master ----The Cash Identifier is" + a);

        String MasterIp = Init.getMasterServerIP (b.getMasterServerID ());
        int MasterPort = Init.myRemoteRequestPort;

         System.out.println ("=================Connecting to " + MasterIp + ": " + MasterPort);
         MyState = "Connecting to " + MasterIp + ": " + MasterPort + ".........";

         MasterSocket = new Socket (MasterIp, MasterPort);

        DataInputStream i = new DataInputStream (MasterSocket.getInputStream ());
        DataOutputStream o = new DataOutputStream (MasterSocket.getOutputStream ());

         MasterReader = new InSecureReader (i, theLogger, "MSR_R");
         MasterWriter = new InSecureWriter (o, theLogger, "MSR_W");
         MasterReader.start ();
         MasterWriter.start ();
         MyState = "Connected to " + MasterIp + ": " + MasterPort;
    }

    /********************************************************
     **********Start of Message handling Code  *************
     ********************************************************
     */

     /** Get the CSH_VAL_ZS request from zone server*/
    private CSH_VAL_MS getCSH_VAL_ZS () throws Exception
    {
        MyState = "Waiting For CSH_VAL_ZS";
        CSH_VAL_ZS a = new CSH_VAL_ZS ();
         System.out.
            println ("****************ZONESERVERVALIDATOR.java -Waiting for the CSH_VAL_ZS");
         a = (CSH_VAL_ZS) getGenRQ (a, UserReader, UserWriter);
         MyState = "Recieved CSH_VAL_ZS=" + a;
         System.out.println ("****************ZONESERVERVALIDATOR.java -");
        Boolean RESULT = new Boolean (true);
         IDENTIFIER = a.getIDENTIFIER ();
        if (a == null)
          {
              // theLogger.logit("Invalid Cash Format
              // from"+UserReader.getID());
              throw (new Exception ("Invalid Cash Token Format..."));
          }
        AnalyseMaster (a.getIDENTIFIER ());
         return (new CSH_VAL_MS (a.getK1 (), a.getHASH_K1_K2 ()));
    }

     /** Send the CSH_VAL_MS Request to master server*/
    private void sendCSH_VAL_MS (CSH_VAL_MS a) throws Exception
    {
        System.out.println ("****************ZONESERVERVALIDATOR.java -SENDING CSH_VAL_MS" +
                            a);
        MyState = "Sending to MasterServer " + MasterIp + ":" + MasterPort + " " + a;
        // CSH_VAL_MS a = new CSH_VAL_MS ();
        sendGenRQ (a, MasterWriter, UserWriter);
    }

     /** GET the VAL_REP_MS Request from Master Server*/
    private boolean getVAL_REP_MS () throws Exception
    {
        System.out.
            println ("****************ZONESERVERVALIDATOR.java -WAITING FOR VAL_REP_MS");
        MyState =
            "Waiting from  MasterServer " + MasterIp + ":" + MasterPort + " for VAL_REP_MS";
        VAL_REP_MS a = new VAL_REP_MS ();
         a = (VAL_REP_MS) getGenRQ (a, MasterReader, UserWriter);
         MyState = "Got from Master Server " + MasterIp + ":" + MasterPort + " " + a;
        Boolean RESULT = new Boolean (true);
        if (a == null)
          {
              MyState = "CashToken Failed";
              // theLogger.logit("Invalid Cash Format
              // from"+UserReader.getID());
              System.out.println ("Invalid cash token format");
              throw (new Exception ("Invalid Cash Token Format..."));
          }
        if (a.getIDENTIFIER () == null)
          {
              System.out.println ("Identifier null");
              MyState = "Null Identifier recieved";
              return false;
          }
        if (a.getIDENTIFIER ().trim ().length () != 0)
          {
              System.out.println ("Successfull Identifier Withdrawal");
              MyState = "IDENTIFIER- Successfull";
              return true;
          }
        System.out.
            println ("Unsuccessful Attempt at validating cash token..Cash token size=0");
        MyState = "Unsuccessful attempt at withdrawal of cash token cash token size=0";
        return false;
    }


     /** Send the VAL_REP_ZS Request to zone server*/
    private void sendVAL_REP_ZS (VAL_REP_ZS a) throws Exception
    {
        System.out.println ("****************ZONESERVERVALIDATOR.java -SENDING VAL_REP_ZS");
        MyState = "Sending VAL_REP_ZS=" + a;
        sendGenRQ (a, UserWriter, UserWriter);
    }


    /********************************************************
     **********End of Message Handling Code    *************
     ********************************************************
     */

    /** The Constructor*/
    public ZoneServerValidator (Socket s, BrahmaInitialize Init,
                                logger theLogger) throws Exception
    {
        super (s, theLogger);
        this.Init = Init;
        // Init.addValidator(this);
        MyState = "Starting Up Validator...";
        System.out.println ("*********************Starting Up Validator for " +
                            getConnectInfo ());
    }


    /** the Cleaning Up routine that promises a Clean EXIT */
    public void cleanup () throws Exception
    {
        System.out.println ("Shutting Down Connection" + getConnectInfo ());
        MyState = "Shutting Down....";
        try
        {
            super.closeAllChildThreads ();
        }
        catch (Exception e)
        {
        }
        try
        {
            MasterSocket.close ();
        }
        catch (Exception e)
        {
        }
        MyState = "Closed Connections";
    }

   /** Overloading the thread that results in this code running in parallel*/
    public void run ()
    {
        System.out.println ("***************ZoneServerValidato.java Is RUNNING");
        MyState = "Validator is Running...";
        try
        {
            while (!done)
              {
                  // Handle the Messages Here

               /********************************************************
                *************Message Send Recieve Section *************
                ********************************************************
                */
                  CSH_VAL_MS a = getCSH_VAL_ZS ();

                  System.out.println ("**************Got " + a);
                  sendCSH_VAL_MS (a);
                  Boolean RESULT = new Boolean (getVAL_REP_MS ());

                  sendVAL_REP_ZS (new VAL_REP_ZS (RESULT));

                  MyState = "Completed Transactions...";
                  System.out.println ("**************Completed ");
                  // while (true) ;
                  done = true;

               /********************************************************
                *************END OF Message Send Recieve Section *******
                ********************************************************
                */
              }                 // End of While
        }
        catch (Exception e)
        {
            e.printStackTrace ();
            // Error Handling Code 
        }
        try
        {
            cleanup ();
        }
        catch (Exception e)
        {
            // Ignore this stuf
        }
        System.out.println ("***************ZoneServerValidato.java Is DEAD");
        MyState = " I am Dead";
        try
        {
            Init.deleteValidator (this);
        }
        catch (Exception e)
        {
        }
    }
}
