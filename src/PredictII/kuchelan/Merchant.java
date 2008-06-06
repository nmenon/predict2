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

//Source file: Y:/E-Cash-Project/TestBed/development/kuchelan/Merchant.java

/* This Is a Copy Lefted Software
(CL) 2001 Spetember
By Nishanth 'Lazarus' Menon
Calicut Regional Engineering College
Calicut */

package kuchelan;

import org.logi.crypto.*;
import org.logi.crypto.sign.*;
import org.logi.crypto.keys.*;
import org.logi.crypto.modes.*;
import kuchelan.networking.ClientThread;
import java.lang.*;
import java.io.*;
import java.net.*;
import org.logi.crypto.*;
import utils.misc.*;
import utils.crypto.*;
import messages.tools.*;
import messages.*;
import network.*;
import GUI.*;


/**
 * @author 
 * @version 
 * The Merchant Manages Communications with the Zone Servers and the Citizen
 */
public class Merchant extends User
{
    private AUTH_REQ theAUTH_REQ;
    private AUTH_REP theAUTH_REP;

    private Socket ZoneSever;


    public ClientThread theClientThread;

    public Float AMOUNT;

    private network.Authenticate CitiAuth;
    private network.Authenticate ZoneServerAuth;

  /****************************************************************************************
    *************START OF THE MESSAGES HANDLING SECTION***********************************
    ***************************************************************************************
    */

    /** Send Request for money  to the Citizen*/
    private void sendREQ_AMT () throws Exception
    {
        try
        {
            System.out.println ("***************Merchant.java*****Sending  REQ_AMT");
            if (AMOUNT == null)
                throw (new Exception ("AMount=null"));

            REQ_AMT a = new REQ_AMT (AMOUNT.toString ());
             System.out.println ("REQAMT=" + a);
             sendGenRQ (a, CitizenWriter, ZoneServerWriter, false);
        }
        catch (Exception e)
        {
            throw (e);
        }                       // End of New Code
    }

  /** Get the Request of Money reply from the Citizen*/
    private void getREQ_REP () throws Exception
    {
        System.out.println ("***************Merchant.java*****Waiting for REQ_REP");
        REQ_REP a = new REQ_REP ();
         a = (REQ_REP) getGenRQ (a, CitizenReader, ZoneServerWriter, false);
        if (!a.getAMOUNT ().equals (AMOUNT))
             throw (new
                    Exception ("Citizen Agreed for an Amount not mine " +
                               a.getAMOUNT () + " MyAMt=" + AMOUNT));
    }

  /** Send AUTH USER FROM Me TO ZONESERVER*/
    private void sendAUTH_USER (String UserName, String Passphrase) throws Exception
    {
        AUTH_USER a = new AUTH_USER (UserName, Passphrase);
         sendGenRQ (a, ZoneServerWriter, CitizenWriter, false);

    }

  /** Get the User Authentication Result from the Zone Server*/
    private boolean getAUTH_RES () throws Exception
    {
        AUTH_RES a = new AUTH_RES ();
         a = (AUTH_RES) getGenRQ (a, ZoneServerReader, CitizenWriter, false);
        if (a == null)
             throw (new Exception ("Authentication Failiure"));
         return true;
    }

    /** Get the Cash Token From the User*/
    private CSH_DEP getCSH_FWD () throws Exception
    {
        CSH_FWD a = new CSH_FWD ();
         a = (CSH_FWD) getGenRQ (a, CitizenReader, ZoneServerWriter, false);
        if (a == null)
             throw (new Exception ("Authentication Failiure"));
        if (!a.getAMOUNT ().equals (AMOUNT))
             throw (new
                    Exception ("Amount Send to Us is not the Same AMount that We Requested"));
         IDENTIFIER = a.getIDENTIFIER ();

         return (new
                 CSH_DEP (a.getIDENTIFIER (), a.getAMOUNT (), a.getRTT (), a.getRWT (),
                          a.getTIMESTAMP ()));
    }

    /** Send the Deposit to the zone Server*/
    private void sendCSH_DEP (CSH_DEP a) throws Exception
    {
        sendGenRQ (a, ZoneServerWriter, CitizenWriter, false);
    }

   /** Get the Acknowledgement from ZoneServer*/
    private ACK_FWD getACK_DEP () throws Exception
    {
        ACK_DEP a = new ACK_DEP ();
         a = (ACK_DEP) getGenRQ (a, ZoneServerReader, CitizenWriter, false);
        if (a == null)
             throw (new Exception ("Zone Serever Rejected My cash token "));
        if (!a.getIDENTIFIER ().equals (IDENTIFIER))
             throw (new
                    Exception
                    ("Identifier Send is not the Identifier for which the reply came"));
         return (new ACK_FWD (a.getRAK (), a.getK2 (), a.getIDENTIFIER ()));
    }

   /** Send the Acnowledment to the Citizen*/
    private void sendACK_FWD (ACK_FWD a) throws Exception
    {
        sendGenRQ (a, CitizenWriter, ZoneServerWriter, false);
    }

   /** Wait for the Transaction Complete Message from the Zone Server*/
    private boolean getTRAN_COMP () throws Exception
    {
        TRAN_COMP r = new TRAN_COMP ();
        TRAN_COMP a = (TRAN_COMP) getGenRQ (r, ZoneServerReader, CitizenWriter, false);
         System.out.println ("a=" + a);
        if (a == null)
             throw (new Exception ("Transaction was Rejected by the Zone Server"));
        if (!a.getIDENTIFIER ().equals ("true"))
             return false;
         System.out.println ("TRAN_COMP_RESULT=" + a.getIDENTIFIER ());
         return true;
    }

  /****************************************************************************************
    ************* END  OF THE MESSAGES HANDLING SECTION***********************************
    ***************************************************************************************
    */

  /** The Thread in Motion */

    public Merchant (Initializer theInitializer, logger theLogger,
                     Socket merchant, Socket zoneServer) throws Exception
    {
        AMOUNT = theInitializer.getAmount ();
        MyPrivateKey = (CipherKey) Initializer.getMyKeyPair ().getPrivate ();
        MyPublicKey = (CipherKey) Initializer.getMyKeyPair ().getPublic ();
        this.theLogger = theLogger;

    /** Authenticate the remote client*/
        try
        {
            System.out.
                println
                ("***************Merchant.java*****Time Initialization...Between citizen and my zoneServer");
            DataInputStream i = new DataInputStream (merchant.getInputStream ());
            DataOutputStream o = new DataOutputStream (merchant.getOutputStream ());
            DataInputStream i1 = new DataInputStream (zoneServer.getInputStream ());
            DataOutputStream o1 = new DataOutputStream (zoneServer.getOutputStream ());
            // This is a security flaw - but i have no iddea how to repair this
            networkAnalyzerForwarder ja = new networkAnalyzerForwarder (i, o, i1, o1, 30);
            // May be I 
            // can 
            // analyse 
            // this 
            // later

             System.out.
                println
                ("***************Merchant.java*****Initiating Authentication for sockets \nMerchant="
                 + merchant + "\nZoneServer=" + zoneServer);
             CitiAuth =
                new network.Authenticate (merchant, theLogger, MyPublicKey.toString ());
             System.out.println ("***************Merchant.java*****Starting ZoneServer Auth");
             ZoneServerAuth =
                new network.Authenticate (zoneServer, theLogger, MyPublicKey.toString ());
            // Set Stats
             ZoneStat = ZoneServerAuth.getStats ();
             OtherStat = CitiAuth.getStats ();
             System.out.
                println
                ("Connection Established with ZoneServer and Citizen \nCitizen =" +
                 merchant + "Stats=" + OtherStat + "\n ZoneServer=" + zoneServer +
                 "Stats=" + ZoneStat);
            while (!CitiAuth.isDone ()) ;   /* Wait for the Request to be done */
            while (!ZoneServerAuth.isDone ()) ; // Wait for connection from
            if (!CitiAuth.isSuccess ())
                 throw (new Exception ("Invalid trial of authentication to Merchant"));
            if (!ZoneServerAuth.isSuccess ())
                 throw (new Exception ("Invalid trial of authentication to ZoneServer"));
            // ZoneServer
        }
        catch (Exception e)
        {
            /* handle Authentication Failiure */
            W_TransactionResult a = new W_TransactionResult (false);
        }

        if (CitiAuth.getMyMessage () == null || CitiAuth.getRTT () == 0
            || CitiAuth.getRemoteKey () == null
            || ZoneServerAuth.getMyMessage () == null
            || ZoneServerAuth.getRTT () == 0 || ZoneServerAuth.getRemoteKey () == null)
          {
              W_TransactionResult a = new W_TransactionResult (false);

   /** Handle Authentication Failiure*/
          }
        CitizenPublicKey = (CipherKey) Crypto.fromString (CitiAuth.getRemoteKey ());
        ZoneServerPublicKey = (CipherKey) Crypto.fromString (ZoneServerAuth.getRemoteKey ());
        System.out.println ("***************Merchant.java*****Citizen Public Key=" +
                            CitizenPublicKey + " RTT= " + CitiAuth.getRTT () +
                            "\n Zone Server Public Key=" + ZoneServerPublicKey + " RTT =" +
                            ZoneServerAuth.getRTT ());

    /** Create the Secure DataStreams*/
        System.out.println ("***************Merchant.java*****MyPublic Key = " + MyPublicKey +
                            "\n My Private Key= " + MyPrivateKey);
        CitizenReader =
            new SecureReader (MyPrivateKey, CitizenPublicKey,
                              new DataInputStream (merchant.getInputStream ()),
                              theLogger, "CIT_R");
        CitizenWriter =
            new SecureWriter (MyPrivateKey, CitizenPublicKey,
                              new DataOutputStream (merchant.
                                                    getOutputStream ()), theLogger, "CIT_W");
        ZoneServerReader =
            new SecureReader (MyPrivateKey, ZoneServerPublicKey,
                              new DataInputStream (zoneServer.
                                                   getInputStream ()), theLogger, "ZONE_R");
        ZoneServerWriter =
            new SecureWriter (MyPrivateKey, ZoneServerPublicKey,
                              new DataOutputStream (zoneServer.
                                                    getOutputStream ()), theLogger, "ZONE_W");
        CitizenReader.start ();
        CitizenWriter.start ();
        ZoneServerReader.start ();
        ZoneServerWriter.start ();

    /** End of DataStream Creations - they are up and running*/

    /** Now We are Ready to Send and recieve messages*/
        try
        {
            sendREQ_AMT ();
            getREQ_REP ();
            sendAUTH_USER (theInitializer.getUserName (), theInitializer.getPassPhrase ());
            System.out.
                println
                ("***************Merchant.java***** The Finale result of Authentication is " +
                 getAUTH_RES ());
            CSH_DEP a = getCSH_FWD ();

            System.out.
                println ("***************Merchant.java*****Cash that is to be deposited is " +
                         a);

            System.out.
                println
                ("***************Merchant.java*****Depositing Cash with the ZoneServer");
            sendCSH_DEP (a);
            System.out.
                println
                ("***************Merchant.java*****Waiting For an Acknowledgement From the ZoneServer");
            ACK_FWD b = getACK_DEP ();

            System.out.println ("***************Merchant.java*****Got Acknowledgement" + b);
            sendACK_FWD (b);
            boolean c = getTRAN_COMP ();

            System.out.
                println ("***************Merchant.java*****RESULT OF THE TRANSACTION IS-" + c);
            W_TransactionResult aaa = new W_TransactionResult (true);
        }
        catch (Exception e)
        {
            if (getAbortReason () != null)
              {
                  System.out.println ("TRANSACTION ABORTED DUE TO....." + getAbortReason () /* +" 
                                                                                             * I 
                                                                                             * was 
                                                                                             * "+MyState */ );
                  theLogger.logit ("ABORTED TRANSACTION Reason=" + getAbortReason ());
              }
            else
              {
                  // System.out.println ("Transaction is Being Aborted.. in
                  // State"+MyState);
                  e.printStackTrace ();
                  theLogger.logit ("I Die Miserably due to-" + e);
              }
        }
        // and try gracefully to quit here
        try
        {
            closeAllChildThreads ();
            zoneServer.shutdownInput ();
            zoneServer.shutdownOutput ();
            zoneServer.close ();
        }
        catch (Exception e)
        {
        }
        W_TransactionResult aaa = new W_TransactionResult (false);
    }                           // Run
}                               // End of Class
