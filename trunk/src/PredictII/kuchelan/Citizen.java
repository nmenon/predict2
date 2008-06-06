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

//Source file: Y:/E-Cash-Project/TestBed/development/kuchelan/Citizen.java

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

import kuchelan.networking.ServerThread;
import java.lang.*;
import java.net.*;
import java.io.*;
import org.logi.crypto.*;
import utils.misc.*;
import utils.crypto.*;
import messages.tools.*;
import messages.*;
import network.*;
import GUI.*;

/**
 * @author Nishanth "Lazarus" Menon <A Href="http://code.google.com/p/predict2/"> Click here to mail </a>
 * @version 1
 * The Citizen Manages Communications with other Merchant and Zone Servers
 */
public class Citizen extends User
{
    private AUTH_REQ theAUTH_REQ;
    private AUTH_REP theAUTH_REP;
    private Socket ZoneSever;
    private networkAnalyzerClient ZoneDat;
    private networkAnalyzerClient MerDat;

    public ServerThread theServerThread;

    private Float AMOUNT;
    private network.Authenticator MerAuth;
    private network.Authenticate ZoneServerAuth;



  /****************************************************************************************
    *************START OF THE MESSAGES HANDLING SECTION***********************************
    ***************************************************************************************
    */

   /**
    * Collect the MERchant Amount Request
    */
    private void getREQ_AMT () throws Exception
    {
        System.out.println ("Waiting for REQ_AMT");

        REQ_AMT a = new REQ_AMT ();
         a = (REQ_AMT) getGenRQ (a, MerchantReader, ZoneServerWriter, true);

         AMOUNT = a.getAMOUNT ();
    }

  /**
   * Send a Req Reply for the AMOUNT REquest
   */
    private void sendREQ_REP () throws Exception
    {
        try
        {
            System.out.println ("Sending REQ_REP");
            if (AMOUNT == null)
                throw (new Exception ("AMount=null"));
            REQ_REP a = new REQ_REP (AMOUNT.toString ());
            if (a == null)
                 throw (new Exception ("Authentcation Failed"));
             sendGenRQ (a, MerchantWriter, ZoneServerWriter, true);
        }
        catch (Exception e)
        {
            throw (e);
        }
    }

  /** Send AUTH USER FROM CITIZEN TO ZONESERVER*/
    private void sendAUTH_USER (String UserName, String Passphrase) throws Exception
    {
        AUTH_USER a = new AUTH_USER (UserName, Passphrase);
         sendGenRQ (a, ZoneServerWriter, MerchantWriter, true);

    }

  /** Get the User Authentication Result from the Zone Server*/
    private boolean getAUTH_RES () throws Exception
    {
        AUTH_RES a = new AUTH_RES ();
         a = (AUTH_RES) getGenRQ (a, ZoneServerReader, MerchantWriter, true);
        if (a == null)
             throw (new Exception ("Authentication Failiure"));
         return true;
    }

    /** Create Cash IDENTIFIER*/
    private String genIDENTIFIER (long Rtt) throws Exception
    {
        // Generate a Random Number and Convert it into String 
        java.util.Random m = new java.util.Random (Rtt);
        Integer I = new Integer ((int) (System.currentTimeMillis () % 500000)); // Maximum 
                                                                                // 
        // 
        // 
        // 
        // 
        // 
        // 
        // Value 
        // of 
        // 
        // 
        // the Int value
         return (I.toString ());
    }

    /** Create RTT*/
    private long genRTT () throws Exception
    {
        // Create the RTT out of the ZoneStat and Merstat
        double a = MerDat.getOneWayTime ();
        double b = ZoneDat.getOneWayTime ();
        double RTT = (a + b) * 2;
         return ((long) RTT);
    }

   /** Send Request for Withdrawal of cash token*/
    private void sendREQ_WIT (String IDENTIFIER, Float Amount, Long Rtt) throws Exception
    {
        REQ_WIT cashReq = new REQ_WIT (IDENTIFIER, Amount, Rtt);
         sendGenRQ (cashReq, ZoneServerWriter, MerchantWriter, true);
    }

   /** Get the Cash token*/
    private CSH_FWD getCSH_TRANS () throws Exception
    {
        CSH_TRANS a = new CSH_TRANS ();
         a = (CSH_TRANS) getGenRQ (a, ZoneServerReader, MerchantWriter, true);
        if (a == null)
             throw (new Exception ("Unable to Retrieve the Cash Token"));
        if (!a.getRESULT ().booleanValue ())
             throw (new Exception ("Cash Request Rejected"));
        // set the identifier
         return (new
                 CSH_FWD (a.getIDENTIFIER (), a.getAMOUNT (), a.getRTT (), a.getRWT (),
                          a.getTIMESTAMP ()));
    }

  /** Forward the Cash token to the Merchant*/
    private void sendCSH_FWD (CSH_FWD a) throws Exception
    {
        sendGenRQ (a, MerchantWriter, ZoneServerWriter, true);

    }

  /** Get the Acknowledgement from the Merchant*/
    private ACK_WIT getACK_FWD () throws Exception
    {
        System.out.println ("Listening for the ACK_FWD from Merchant");
        ACK_FWD a = new ACK_FWD ();
         a = (ACK_FWD) getGenRQ (a, MerchantReader, ZoneServerWriter, true);
        if (a == null)
             throw (new Exception ("Unable to Retrieve the Cash Token"));
        // check the identifier
         return (new ACK_WIT (a.getRAK (), a.getK2 (), a.getIDENTIFIER ()));

    }

    /** Send the Withdrawal Acknowledgement to the ZoneServer*/
    private void sendACK_WIT (ACK_WIT a) throws Exception
    {
        System.out.println ("Sending ACK_WIT to the Zone Server");
        sendGenRQ (a, ZoneServerWriter, MerchantWriter, true);
    }

    /** Wait For the Transaction Complete Message*/
    private boolean getTRAN_COMP () throws Exception
    {
        System.out.println ("Listening for the TRAN_COMP from the Zone Server");
        TRAN_COMP a = new TRAN_COMP ();
         a = (TRAN_COMP) getGenRQ (a, ZoneServerReader, MerchantWriter, true);
        if (a == null)
             return false;
        // Check the Identifier Value...
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

  /** 
   * The Constructor
   * 
   */
    public Citizen (Initializer theInitializer, logger theLogger,
                    Socket citizen, Socket zoneServer) throws Exception
    {


        MyPrivateKey = (CipherKey) Initializer.getMyKeyPair ().getPrivate ();
        MyPublicKey = (CipherKey) Initializer.getMyKeyPair ().getPublic ();
        this.theLogger = theLogger;
        {
            W_yes_no ab =
                new W_yes_no ("Merchant Request from -" +
                              citizen.getInetAddress ().toString () + " Agree?");
            if (!ab.getResult ())
                 System.exit (1);
        }

    /** Authenticate the Client*/
        try
        {
            System.out.println ("Time Initialization...to my zone server");
            DataInputStream i = new DataInputStream (zoneServer.getInputStream ());
            DataOutputStream o = new DataOutputStream (zoneServer.getOutputStream ());

            ZoneDat = new networkAnalyzerClient (i, o, 30);
            System.out.println ("Time Initialization...to merchant zone server");
            i = new DataInputStream (citizen.getInputStream ());
            o = new DataOutputStream (citizen.getOutputStream ());
            MerDat = new networkAnalyzerClient (i, o, 30);

            System.out.
                println ("Initiating Authentication for sockets \nCitizen=" +
                         citizen + "\nZoneServer=" + zoneServer);
            MerAuth = new network.Authenticator (citizen, theLogger, MyPublicKey.toString ());

            System.out.println ("Starting ZoneServer Auth");
            ZoneServerAuth =
                new network.Authenticate (zoneServer, theLogger, MyPublicKey.toString ());
            // Set Stats
            ZoneStat = ZoneServerAuth.getStats ();
            OtherStat = MerAuth.getStats ();
            System.out.
                println
                ("Connection Established with ZoneServer and Merchant\nMechant =" +
                 citizen + "Stats=" + OtherStat + "\n ZoneServer=" + zoneServer +
                 "Stats=" + ZoneStat);
            while (!MerAuth.isDone ()) ;    // Wait for the Request to be done 
            while (!ZoneServerAuth.isDone ()) ; // Wait for connection from
            if (!MerAuth.isSuccess ())
                throw (new Exception ("Invalid trial of authentication to Merchant"));
            if (!ZoneServerAuth.isSuccess ())
                throw (new Exception ("Invalid trial of authentication to ZoneServer"));
            // ZoneServer

        }
        catch (Exception e)
        {
            /* handle Authentication Failiure */
            e.printStackTrace ();
            this.destroy ();
        }

        if (MerAuth.getMyMessage () == null || MerAuth.getRTT () == 0
            || MerAuth.getRemoteKey () == null
            || ZoneServerAuth.getMyMessage () == null
            || ZoneServerAuth.getRTT () == 0 || ZoneServerAuth.getRemoteKey () == null)
          {

          /** Handle Authentication Failiure*/
              theLogger.writelog ("Authentication Failiure");
              return;
          }
        MerchantPublicKey = (CipherKey) Crypto.fromString (MerAuth.getRemoteKey ());
        ZoneServerPublicKey = (CipherKey) Crypto.fromString (ZoneServerAuth.getRemoteKey ());


        System.out.println ("Merchant PublicKey=" + MerchantPublicKey +
                            "RTT=" + MerAuth.getRTT () +
                            "\n Zone ServerPublic Key=" + ZoneServerPublicKey +
                            " RTT =" + ZoneServerAuth.getRTT ());

    /** Create the Secure DataStreams*/
        System.out.println ("MyPublic Key = " + MyPublicKey +
                            "\n My Private Key= " + MyPrivateKey);

        MerchantReader =
            new SecureReader (MyPrivateKey, MerchantPublicKey,
                              new DataInputStream (citizen.getInputStream ()),
                              theLogger, "MER_R");
        MerchantWriter =
            new SecureWriter (MyPrivateKey, MerchantPublicKey,
                              new DataOutputStream (citizen.getOutputStream ()),
                              theLogger, "MER_W");
        ZoneServerReader =
            new SecureReader (MyPrivateKey, ZoneServerPublicKey,
                              new DataInputStream (zoneServer.getInputStream ()),
                              theLogger, "ZONE_R");
        ZoneServerWriter =
            new SecureWriter (MyPrivateKey, ZoneServerPublicKey,
                              new DataOutputStream (zoneServer.getOutputStream ()),
                              theLogger, "ZONE_W");
        MerchantReader.start ();
        MerchantWriter.start ();
        ZoneServerReader.start ();
        ZoneServerWriter.start ();

    /** End of DataStream creations*/
        try
        {

       /** We start sending the messages here
        * If We have a problem then we quit elegantly
        */
            getREQ_AMT ();
            // Process the Request
            // Asj the USer for Response
            W_yes_no aa =
                new W_yes_no ("The Merchant has Requested for Amount " + AMOUNT +
                              " Grant Permission?");
            if (!aa.getResult ())
                System.exit (1);
            sendREQ_REP ();
            sendAUTH_USER (theInitializer.getUserName (), theInitializer.getPassPhrase ());
            /* Generate Cash Info */
            long RTT = genRTT ();

            IDENTIFIER = genIDENTIFIER (RTT);
            sendREQ_WIT (IDENTIFIER, AMOUNT, new Long (RTT));
            System.out.println (" The Finale result of Authentication is " + getAUTH_RES ());
            CSH_FWD a = getCSH_TRANS ();

            System.out.println ("cash Recieved=" + a);
            sendCSH_FWD (a);
            ACK_WIT b = getACK_FWD ();

            sendACK_WIT (b);
            System.out.println ("RESULTOF THE TRANSACTION IS " + getTRAN_COMP ());
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
                  e.printStackTrace ();
                  theLogger.logit ("I Die Miserably due to-" + e);
              }
            // Exit code
        }
        try
        {
            closeAllChildThreads ();
        }
        catch (Exception e)
        {                       // Ignore it
        }
        W_TransactionResult aaaaa = new W_TransactionResult (false);
    }
}
