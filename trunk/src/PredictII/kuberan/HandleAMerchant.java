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

//Source file: Y:/E-Cash-Project/TestBed/development/kuberan/HandleACtizen.java

/* This Is a Copy Lefted Software
(CL) 2001 Spetember
By Nishanth 'Lazarus' Menon
Calicut Regional Engineering College
Calicut */

package kuberan;

import kuberan.ZS_Database.CommitTransaction;
import java.lang.*;
import java.io.*;
import java.util.*;
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
 * handle the Request of Any one Merchant
 */
public class HandleAMerchant extends User
{
    private CSH_DEP CashRequest;
    GenerateRAK Baja;
    String MyState;
    private String UserName;
    private double Amount;
    private double RemainderMoney;
    private long TimeStamp;
    private String Identifier_Used;

   /** PUblic to String*/
    public String toString ()
    {
        return (ConnectionInfo + " :[M]: " + MyState);
    }

    /** Testif the User May withdraw this amount of cash from his account*/
    private boolean checkDepositability (String UserName, Float Amount,
                                         KuberanInitialize Init) throws Exception
    {
        MyState = "Checking the DataBase for the User Withdrawability";
        Vector d = Init.db.GetPersonalInformationEntry (UserName);
        if (d.size () == 0)
             throw (new
                    Exception (UserName + " Has No Entry in the Personal Information Table"));
        Double a = new Double ((String) d.elementAt (0));
        double MoneyIn = a.doubleValue ();
        double MoneyOut = (double) Amount.floatValue ();

        // if ((MoneyIn-MoneyOut)<Init.getMinimumBalanceAmount()) //MaxAmount??
        // return false;
         this.Amount = MoneyOut;    // Amount Acceptable
         RemainderMoney = MoneyIn + MoneyOut;
         return true;
    }

    /** Commiting the Transaction*/
    private void commitTransaction () throws Exception
    {
        // Update the Personal Information
        theKuberanInitialize.db.UpdatePersonalInformationEntry (UserName,
                                                                (float) RemainderMoney);

        // Enter into the Transaction Information
        theKuberanInitialize.db.AddTransactionEntry (UserName, true, (float) Amount,
                                                     ConnectionInfo, TimeStamp,
                                                     Identifier_Used);
    }

   /** This Creates the Cash token after making sure that it is the proper format*/
    private ACK_DEP GenerateACK_DEP (String IDENTIFIER, String EncType, String Initiator,
                                     String HashType) throws Exception
    {
        System.out.println ("+++GenAck+++ChkPnt1");
        Baja = new GenerateRAK ();
        long d = System.currentTimeMillis ();
         System.out.println ("+++GenAck+++ChkPnt2");
         Baja.setIDENTIFIER (IDENTIFIER);
         System.out.println ("+++GenAck+++ChkPnt3");
         Baja.generate (EncType, HashType);
         System.out.println ("+++GenAck+++ChkPnt4");
        long ei = System.currentTimeMillis () - d;
         System.out.println ("RAK GenTime=" + ei);
         Baja.generate (IDENTIFIER, EncType, HashType);
         try
        {
            System.out.println (Baja.getIDENTIFIER ());
            System.out.println (Baja.getK1 ());
            System.out.println (Baja.getK2 ());
            System.out.println (Baja.getHashK1K2 ());
            System.out.println (Baja.getRAK ());
            // This makes the transactioins takke twice the time at this point
            if (!Baja.check (HashType))
                throw (new
                       Exception
                       ("Internal Error - GenerateACK_DEP, Unable to check the RAK!!!"));
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
        System.out.println ("+++GenAck+++ChkPnt5");

        return (new ACK_DEP (Baja.getRAK (), Baja.getK2 (), IDENTIFIER));

    }


  /****************************************************************************************
    *************START OF THE MESSAGES HANDLING SECTION***********************************
    ***************************************************************************************
    */

    /** Send the Identifier for Verification to the Master Server*/
    private void sendACK_DEP (CSH_DEP a, String EncType, String Initiator,
                              String HashType) throws Exception
    {
        MyState = "Waiting For ACK_DEP...";
        this.TimeStamp = a.getTIMESTAMP ().longValue ();
        this.IDENTIFIER = a.getIDENTIFIER ();
        this.Identifier_Used = a.getIDENTIFIER ();
        MyState = "Got ACK_DEP:-" + a;
        System.out.println ("+++++++HandleAMerchant+++++Generating ACK_DEP to Merchant");
        ACK_DEP b = GenerateACK_DEP (IDENTIFIER, EncType, Initiator, HashType);
         System.out.println ("+++++++HandleAMerchant+++++Sending ACK_DEP to Merchant");
         sendGenRQ (b, UserWriter, false);
    }

    /** Send Validation Request to the Zone Server */
    private void sendCSH_VAL_ZS () throws Exception
    {
        System.out.println ("+++++++HandleAMerchant+++++Sending CSH_VAL_ZS to Master Server");
        CSH_VAL_ZS a = new CSH_VAL_ZS (Baja.getK1 (), Baja.getHashK1K2 (), IDENTIFIER);
         MyState = "Sending CSH_VAL_ZS to Master Server" + a;
         sendGenRQ (a, MasterWriter, true);
    }

    /** Listen to the Master Server for the VAL_REP_ZS*/
    private TRAN_COMP getVAL_REP_ZS () throws Exception
    {
        System.out.
            println ("+++++++HandleAMerchant+++++Getting VAL_REP_ZS  from  Master Server");
        MyState = " Waiting for VAL_REP_ZS from Master Server";
        VAL_REP_ZS s = new VAL_REP_ZS ();
         s = (VAL_REP_ZS) getGenRQ (s, MasterReader, false);
         MyState = "Got Message" + s;
        if (s == null)
          {
              System.out.
                  println
                  ("+++++++HandleAMerchant+++++Yeah something happened a=null GET_VAL_REP_ZS");
              // Write to Zombie- Mebbie we have a network Failiure
              throw (new Exception ("Reply is Invalid"));
          }
        if (!s.getRESULT ().booleanValue ())
          {
              UserWriter.setABORT_REASON ("VAL_FAIL");
              // Log this
          }
        else
          {
              // Commit the Transaction
          }
        return (new TRAN_COMP (s.getRESULT ().toString ()));
    }


    /** Get the Status reply from the Master Server*/
    /* 
     * private TRAN_COMP getSTAT_REP () throws Exception { STAT_REP a = new
     * STAT_REP (); System.out. println
     * ("+++++++++++++++++HandleAMerchant.java++++++++++Getting stat_Rep"); a = 
     * (STAT_REP) getGenRQ (a, MasterReader, false); if (a == null) throw (new
     * Exception ("Invalid Reply- while looking for Stat_rep"));
     * System.out.println ("*****************Brillianto..." + a.getRESULT ());
     * return (new TRAN_COMP (a.getRESULT ().toString ())); } */

    /** Send Successful completion info to the User*/
    private void sendTRAN_COMP (TRAN_COMP a) throws Exception
    {
        System.out.println ("+++++++++++++++++HandleAMerchant.java++++++++++Sending TRANCOMP");
        commitTransaction ();
        MyState = "Sending TRAN_COMP" + a;
        sendGenRQ (a, UserWriter, false);
    }



  /****************************************************************************************
    ************* END  OF THE MESSAGES HANDLING SECTION***********************************
    ***************************************************************************************
    */
    public HandleAMerchant (SecureReader UserReaderA, SecureWriter UserWriterA,
                            KuberanInitialize theKuberanInitializeA, logger theLoggerA,
                            String ConnectionInfoA, CSH_DEP aA,
                            String UserName) throws Exception
    {
        System.out.println ("+++++++HandleAMerchant+++++Created a New Instance");
        this.UserName = UserName;
        this.UserReader = UserReaderA;
        this.UserWriter = UserWriterA;
        this.theLogger = theLoggerA;
        this.theKuberanInitialize = theKuberanInitializeA;
        this.ConnectionInfo = ConnectionInfoA;
        CashRequest = aA;
        if (!(checkDepositability (UserName, aA.getAMOUNT (), theKuberanInitialize)))
          {
              UserWriter.setABORT_REASON ("MONEY_MORE");
              return;
          }
        System.out.
            println ("+++++++HandleAMerchant+++++Opening Connection to the Validator Socket" +
                     theKuberanInitialize.BrahmaIP + ":" +
                     theKuberanInitialize.myValidatorPort);
        MyState =
            "Starting Connections To MasterServer at" + theKuberanInitialize.BrahmaIP + ":" +
            theKuberanInitialize.myValidatorPort;
        MasterSocket =
            new Socket (theKuberanInitialize.BrahmaIP, theKuberanInitialize.myValidatorPort);
        System.out.
            println
            ("+++++++HandleAMerchant+++++Connection Established to the Validator Socket" +
             theKuberanInitialize.BrahmaIP + ":" + theKuberanInitialize.myValidatorPort);
        DataInputStream i = new DataInputStream (MasterSocket.getInputStream ());
        DataOutputStream o = new DataOutputStream (MasterSocket.getOutputStream ());

        MasterReader = new InSecureReader (i, theLogger, "MAS_R_V");
        MasterWriter = new InSecureWriter (o, theLogger, "MAS_W_V");
        MasterReader.start ();
        MasterWriter.start ();
        MyState = "Connections to Master Server Ready..";
        System.out.
            println
            ("+++++++HandleAMerchant+++++ready for Continuing Transaction of Merchant");
    }                           // End of Constructor
    public void run ()
    {
        try
        {
            System.out.println ("---------------Handle A merchant is Running");
            MyState = "Merchant Handler is running..";

  /****************************************************************************************
    *************START OF THE MESSAGES HANDLING SECTION***********************************
    ***************************************************************************************
    */
            sendACK_DEP (CashRequest, "Blowfish",
                         "PRotocol Enhancement in DIgital Cash Transaction", "SHA1");
            sendCSH_VAL_ZS ();
            TRAN_COMP a = getVAL_REP_ZS ();

            sendTRAN_COMP (a);

            // while (true) ;

  /****************************************************************************************
    ************* END  OF THE MESSAGES HANDLING SECTION***********************************
    ***************************************************************************************
    */
            MasterSocket.close ();
        }
        catch (Exception e)
        {
            System.out.println ("+++++++HandleAMerchant+++++Dying Horrrribbly...");
            e.printStackTrace ();
        }
        try
        {
            closeAllChildThreads ();
        }
        catch (Exception e)
        {
        }
        System.out.println ("---------------Handle A merchant is Dead");
        MyState = "I am Dead";
    }
}                               // End Of class
