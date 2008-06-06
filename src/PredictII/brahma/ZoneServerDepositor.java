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

//Source file: Y:/E-Cash-Project/TestBed/development/brahma/ZoneServerDepositor.java

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
 */
public class ZoneServerDepositor extends User
{
    public CSH_VAL_MS theCSH_VAL_MS;
    public VAL_REP_MS theVAL_REP_MS;
    public SuperMessage theSuperMessage;
    public BrahmaInitialize Init;
    public long RTT;
    public long RWT;
    public long TimeStamp;


    /********************************************************
     **********Start of Message handling Code  *************
     ********************************************************
     */

    /** 
     * This handles the User authentication part
     */
    private ID_REP getID_CHK () throws Exception
    {
        System.out.println ("-------------ZONESERVER DEPOSITOR__________ Waiting for IDCHK");
        MyState = "Waiting For ID_CHK";
        ID_CHK a = new ID_CHK ();
         a = (ID_CHK) getGenRQ (a, UserReader, UserWriter);
         MyState = "Got ID_CHK=" + a;
         System.out.println ("-------------ZONESERVER DEPOSITOR__________ Got ID_CHK" + a);
        // System.out.println("-------------ZONESERVER DEPOSITOR__________ ");
        Boolean RESULT;
        Long RWT = new Long (Init.getRWT ());
         this.RWT = Init.getRWT ();
        if (a == null)
          {
              // theLogger.logit("Invalid Cash Format
              // from"+UserReader.getID());
              // throw (new Exception ("Invalid Cash Token Format..."));
              RESULT = new Boolean (false);
          }
        else
          {
              RESULT = new Boolean (true);  // *************THIS NEEDS TO BE
              // REPLACED BY THE DATABASE
              // SEARCHER


              /* Generating a Reply */
              /* Check ID Is to Be done here */
              MyState = "Searching the DataBase..";
              if (Init.db.searchID (a.getIDENTIFIER ()))
                {               // We Found a Match in the Identifier
                    // Send a Transaction Abort
                    MyState = "Found Existing ID...";
                    RESULT = new Boolean (false);
                }
              else
                {
                    // Insert the Identifier
                    MyState =
                        "Identifier " + a.getIDENTIFIER () +
                        " is Okay - Inserting into DataBase";
                    Init.db.insertID (a.getIDENTIFIER (), a.getAMOUNT (), a.getRTT (), RWT,
                                      a.getTIMESTAMP ());
                    this.RTT = a.getRTT ().longValue ();
                    this.TimeStamp = a.getTIMESTAMP ().longValue ();
                }
          }

        ID_REP set = new ID_REP (RESULT, RWT);

        System.out.
            println ("-------------ZONESERVER DEPOSITOR__________ IDCHK_Created ID_REP=" +
                     set);

        return set;
    }



    /** Handles the sending of the Identifier Reply that contains the ID_CHK  result*/
    private void sendID_REP (ID_REP a) throws Exception
    {
        // ID_REP a = new ID_REP (new Boolean (RESULT));
        MyState = "Sending ID_REP=" + a;
        sendGenRQ (a, UserWriter, UserWriter);
    }

    /** This handles the Status check of the cash token reply*/
    private Boolean getSTAT_CHK () throws Exception
    {
        STAT_CHK a = new STAT_CHK ();
         MyState = "Waiting For STAT_CHK";
         a = (STAT_CHK) getGenRQ (a, UserReader, UserWriter);
         MyState = "Got STAT_CHK=" + a;
        Boolean RESULT = new Boolean (true);
        if (a == null)
          {
              // theLogger.logit("Invalid Cash Format
              // from"+UserReader.getID());
              // throw (new Exception ("Invalid Cash Token Format..."));
              return (new Boolean (false)); // Invalid Cash format
          }


        // We Have to Now insert our Information into the DataBase

        String IDENTIFIER = a.getIDENTIFIER ();

        MyState = "INserting Into DataBase the STAT_CHK";
        Init.db.insertSTAT_CHK (a.getRAK (), a.getK2 (), IDENTIFIER);
        /* Generating a Reply */
        /* Check ID Is to Be done here */
        // We Wait for the DataBase to Be ready for the Data
        // TIme Being - I have to do time checking here
        boolean dona = false;
        boolean jona = (TimeStamp > System.currentTimeMillis ());
        long timeStart =
            (jona) ? TimeStamp - System.currentTimeMillis () : System.currentTimeMillis () -
            TimeStamp;
        Vector di = Init.db.searchKeys ();  // we have to wait a computation of 
                                            // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // the hash time a factor of the
        // number of keys present
        int oldHashtime = Init.getHashComputationTime () * di.size ();
        double waitTime = ((RTT + Init.getRWT ()) / 2) + 1000 + oldHashtime;    // Acceptable 
                                                                                // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // Wait 
        // Time 
        // ??

        String[]z = null;
        while (true)
          {
              if (z != null)
                {
                    dona = true;
                    break;
                    // Confirm the RAK
                }
              int newHashtime = Init.getHashComputationTime () * di.size ();
              long timeNow =
                  (jona) ? TimeStamp -
                  System.currentTimeMillis () : System.currentTimeMillis () - TimeStamp;
              di = Init.db.searchKeys ();   // we have to wait a computation of 
                                            // 
              // 
              // 
              // 
              // 
              // 
              // 
              // 
              // 
              // 
              // 
              // 
              // 
              // 
              // 
              // 
              // 
              // 
              // 
              // 
              // 
              // 
              // 
              // 
              // 
              // 
              // 
              // 
              // 
              // 
              // 
              // 
              // 
              // 
              // the hash time a factor of the
              // number of keys present

              waitTime = waitTime - oldHashtime + newHashtime;  // Acceptable
              // Wait Time ??
              oldHashtime = newHashtime;

              double countDown = waitTime - (timeNow - timeStart);

              MyState =
                  "Waiting For the RAK to Come into the DataBase...Counting down.Milli Seconds Left="
                  + countDown;
              System.out.println ("Waiting for RAK: Depositor:Ms Left=" + countDown);

              z = Init.db.getStat (IDENTIFIER); // search the db for
              // the rak

              if (countDown <= 0)
                {
                    MyState = "TimeOut... No More Waiting..";
                    System.out.println ("Time OUT!!!");
                    Init.db.deleteID (IDENTIFIER);  // Delete Identifier Since
                    // the RAK for the cash token has not
                    // reached us
                    if (z == null)
                        RESULT = new Boolean (false);
                    break;
                }
          }
        // Delete the Id from the Database
        // Leave this to the Scavenger

        // Finished the Check for the ID
        return RESULT;
    }

    /** Handles the Status Reply Message*/
    private void sendSTAT_REP (Boolean RESULT) throws Exception
    {
        STAT_REP a = new STAT_REP (RESULT);
         MyState = "Sending STAT_REP=" + a;
         sendGenRQ (a, UserWriter, UserWriter);
    }

    /********************************************************
     **********End of Message Handling Code    *************
     ********************************************************
     */

    /** The Constructor*/
    public ZoneServerDepositor (Socket s, BrahmaInitialize Init, logger theLogger)
        throws Exception
    {
        super (s, theLogger);
        this.Init = Init;
        // Init.addDepositor(this);
        MyState = "Depositor Is Starting Up";
    }


    /** the Cleaning Up routine that promises a Clean EXIT */
    public void cleanup () throws Exception
    {
        System.out.println ("Shutting Down Connection" + getConnectInfo ());
        MyState = "Shutting Down..";
        try
        {
            super.closeAllChildThreads ();
        }
        catch (Exception e)
        {
        }
    }

   /** Overloading the thread that results in this code running in parallel*/
    public void run ()
    {
        System.out.println ("DEPOSITOR IS RUNNING--------------");
        try
        {
            while (!done)
              {
                  // Handle the Messages Here

               /********************************************************
                *************Message Send Recieve Section *************
                ********************************************************
                */
                  ID_REP a = getID_CHK ();

                  sendID_REP (a);
                  Boolean RESULT = getSTAT_CHK ();

                  sendSTAT_REP (RESULT);
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
        MyState = "I am Dead";
        System.out.println ("DEPOSITOR IS DEAD--------------");
        try
        {
            Init.deleteDepositor (this);
        }
        catch (Exception e)
        {
        }
    }
}
