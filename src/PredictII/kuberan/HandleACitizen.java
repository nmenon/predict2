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
 * handle the Request of Any one Citizen
 */
public class HandleACitizen extends User
{
    public String MyState;
    private REQ_WIT CashRequest;
    private String UserName;
    private double Amount;
    private double RemainderMoney;
    private long TimeStamp;
    private String Identifier_Used;

    /** Testif the User May withdraw this amount of cash from his account*/
    private boolean checkWithdrawability (String UserName, Float Amount,
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
        if ((MoneyIn - MoneyOut) < Init.getMinimumBalanceAmount ())
             return false;
         this.Amount = MoneyOut;
         RemainderMoney = MoneyIn - MoneyOut;
         return true;
    }

    /** Commiting the Transaction*/
    private void commitTransaction () throws Exception
    {
        // Update the Personal Information
        theKuberanInitialize.db.UpdatePersonalInformationEntry (UserName,
                                                                (float) RemainderMoney);

        // Enter into the Transaction Information
        theKuberanInitialize.db.AddTransactionEntry (UserName, false, (float) Amount,
                                                     ConnectionInfo, TimeStamp,
                                                     Identifier_Used);
    }

   /** This Creates the Cash token after making sure that it is the proper format*/
    private String PiggyBack (String IDENTIFIER) throws Exception
    {
        parseMessage a = new parseMessage ();
         System.out.println ("+++++++HandleACitizen+++++PiggyBack" + IDENTIFIER);
         String[] b = a.parseIdentifier (IDENTIFIER);
        if (b.length != 1)
             throw (new Exception ("+++++++HandleACitizen+++++Invalid Identifier"));
        CSH_IDENTIFIER h = new CSH_IDENTIFIER (theKuberanInitialize.Denomination,
                                               theKuberanInitialize.MasterServerID,
                                               new Long (IDENTIFIER));
         return (h.toString ());
    }

    public String toString ()
    {
        return (ConnectionInfo + " :[C]: " + MyState);
    }

  /****************************************************************************************
    *************START OF THE MESSAGES HANDLING SECTION***********************************
    ***************************************************************************************
    */

    /** Send the Identifier for Verification to the Master Server*/
    private void sendID_CHK (String IDENTIFIER, Float AMOUNT, Long RTT,
                             Long TIMESTAMP) throws Exception
    {
        this.IDENTIFIER = PiggyBack (IDENTIFIER);
        System.out.println ("+++++++HandleACitizen+++++Sending ID_CHK to MasterServer");
        ID_CHK a = new ID_CHK (this.IDENTIFIER, AMOUNT, RTT, TIMESTAMP);
         MyState = "Sending ID_CHK " + a;
         sendGenRQ (a, MasterWriter, true);
    }

    /** Get the ID Reply from the Master Server*/
    private Long getID_REP () throws Exception
    {
        ID_REP a = new ID_REP ();
        // System.out.println ("*****************Brillianto...");
         MyState = "Waiting for the ID_REP..";
         a = (ID_REP) getGenRQ (a, MasterReader, true);
         System.out.println ("+++++++HandleACitizen+++++Got Reply for the Cash request" + a);
         MyState = "Got ID_REP " + a;
        if (a == null)
          {
              System.out.println ("+++++++HandleACitizen+++++Yeah something happened");
              throw (new Exception ("Reply is Invalid"));
          }
        if (!a.getRESULT ().booleanValue ())
             throw (new
                    Exception ("The Master Server Did Not Agree to DEposit for Us " +
                               IDENTIFIER + " For " + ConnectionInfo));
        this.Identifier_Used = IDENTIFIER;
        return a.getRWT ();
    }

    /**Send the Cash token to the USer*/
    private void sendCSH_TRANS (Boolean RESULT, String Id, Float AMOUNT, Long RTT, Long RWT,
                                Long TIMSTAMP) throws Exception
    {
        CSH_TRANS a = new CSH_TRANS (RESULT, Id, AMOUNT, RTT, RWT, TIMSTAMP);
         MyState = "Sending CSH_TRANS " + a;
         System.out.println ("+++++++HandleACitizen+++++Sending Cash token" + a);
         TimeStamp = TIMSTAMP.longValue ();
         sendGenRQ (a, UserWriter, false);
    }

    /** Get the Acknowledgement of the wihdrawal from the client*/
    private STAT_CHK getACK_WIT () throws Exception
    {
        ACK_WIT a = new ACK_WIT ();
        // System.out.println ("*****************Brillianto...");
         MyState = "Waiting for ACK_WIT";
         a = (ACK_WIT) getGenRQ (a, UserReader, false);
         MyState = "Got Message ACK_WIT " + a;
         System.out.
            println ("+++++++HandleACitizen+++++Got Acknowledgement for the Cash Transmission"
                     + a);
        if (a == null)
             throw (new Exception ("Reply is Invalid"));
        if (!a.getIDENTIFIER ().equals (IDENTIFIER))
             throw (new
                    Exception
                    ("+++++++HandleACitizen+++++I Got an Acknowledgement for a Cash token I did not send"));
         return (new STAT_CHK (a.getRAK (), a.getK2 (), a.getIDENTIFIER ()));
    }

    /** Send Stat request to the Master Server*/
    private void sendSTAT_CHK (STAT_CHK a) throws Exception
    {
        System.out.println ("+++++++HandleACitizen+++++Sending Cash Status Check " + a);
        MyState = "Sending STAT_CHK " + a;
        sendGenRQ (a, MasterWriter, true);
    }

    /** Get the Status reply from the Master Server*/
    private TRAN_COMP getSTAT_REP () throws Exception
    {
        STAT_REP a = new STAT_REP ();
         MyState = "Waiting for STAT_REP....";
        // System.out.println ("*****************Brillianto...");
         a = (STAT_REP) getGenRQ (a, MasterReader, true);
         MyState = "Got STAT_REP " + a;
        if (a == null)
             throw (new Exception ("Invalid Reply- while looking for Stat_rep"));
        if (a.getRESULT ().booleanValue ())
            // CommitTransaction
             commitTransaction ();
         return (new TRAN_COMP (a.getRESULT ().toString ()));
    }

    /** Send Successful completion info to the User*/
    private void sendTRAN_COMP (TRAN_COMP a) throws Exception
    {
        MyState = "Sending TRANS_COMP " + a;
        sendGenRQ (a, UserWriter, false);
    }



  /****************************************************************************************
    ************* END  OF THE MESSAGES HANDLING SECTION***********************************
    ***************************************************************************************
    */
    public HandleACitizen (SecureReader UserReaderA, SecureWriter UserWriterA,
                           KuberanInitialize theKuberanInitializeA, logger theLoggerA,
                           String ConnectionInfoA, REQ_WIT aA,
                           String UserName) throws Exception
    {
        System.out.println ("+++++++HandleACitizen+++++Created a New Instance");
        this.UserName = UserName;
        this.UserReader = UserReaderA;
        this.UserWriter = UserWriterA;
        this.theLogger = theLoggerA;
        this.theKuberanInitialize = theKuberanInitializeA;
        this.ConnectionInfo = ConnectionInfoA;
        CashRequest = aA;
        if (!(checkWithdrawability (UserName, aA.getAMOUNT (), theKuberanInitialize)))
          {
              UserWriter.setABORT_REASON ("MONEY_LESS");
              return;
          }
        System.out.
            println ("+++++++HandleACitizen+++++Opening Connection to the Depositor Socket" +
                     theKuberanInitialize.BrahmaIP + ":" +
                     theKuberanInitialize.myDepositorPort);
        MasterSocket =
            new Socket (theKuberanInitialize.BrahmaIP, theKuberanInitialize.myDepositorPort);
        MasterSocket.setSoTimeout (theKuberanInitialize.getMaxTimeOut ());
        System.out.
            println ("+++++++HandleACitizen+++++Connection Established to the Depositor Socket"
                     + theKuberanInitialize.BrahmaIP + ":" +
                     theKuberanInitialize.myDepositorPort);
        DataInputStream i = new DataInputStream (MasterSocket.getInputStream ());
        DataOutputStream o = new DataOutputStream (MasterSocket.getOutputStream ());

        MasterReader = new InSecureReader (i, theLogger, "MAS_R_D");
        MasterWriter = new InSecureWriter (o, theLogger, "MAS_W_D");
        MasterReader.start ();
        MasterWriter.start ();
        System.out.
            println ("+++++++HandleACitizen+++++ready for Continuing Transaction of Citizen");
    }                           // End of Constructor
    public void run ()
    {
        try
        {
            System.out.println ("Handle A Citizen Is Runnin+++++++++++++++++++");

  /****************************************************************************************
    *************START OF THE MESSAGES HANDLING SECTION***********************************
    ***************************************************************************************
    */
            System.out.println ("+++++++HandleACitizen+++++Sending the ID_CHK");
            Long TIMESTAMP = new Long (System.currentTimeMillis ());

            sendID_CHK (CashRequest.getIDENTIFIER (), CashRequest.getAMOUNT (),
                        CashRequest.getRTT (), TIMESTAMP);
            System.out.println ("+++++++HandleACitizen+++++Getting the ID_REP");
            Long RWT = getID_REP ();

            System.out.println ("+++++++HandleACitizen+++++Sending the CSH_TRANS");
            sendCSH_TRANS (new Boolean (true), IDENTIFIER,
                           CashRequest.getAMOUNT (), CashRequest.getRTT (), RWT, TIMESTAMP);
            System.out.println ("Waiting For ID_CHK");
            System.out.println ("+++++++HandleACitizen+++++Sending the ID_CHK");
            STAT_CHK b = getACK_WIT ();

            sendSTAT_CHK (b);
            TRAN_COMP c = getSTAT_REP ();

            sendTRAN_COMP (c);

            // while (true) ;
            MyState = "Closing Connection to Master Server";
            MasterSocket.close ();

  /****************************************************************************************
    ************* END  OF THE MESSAGES HANDLING SECTION***********************************
    ***************************************************************************************
    */
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
        try                     // Clean Up
        {
            closeAllChildThreads ();
        }
        catch (Exception e)
        {
        }
        System.out.println ("Handle A Citizen Is Dead+++++++++++++++++++");
        MyState = "I Am Dead";
    }
}                               // End Of class
