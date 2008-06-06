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

//Source file: Y:/E-Cash-Project/TestBed/development/brahma/RemoteMasterServerValidator.java

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
public class RemoteMasterServerValidator extends User
{
    public SuperMessage theSuperMessage;
    public BrahmaInitialize Init;
    public String IDENTIFIER;


    public VAL_REP_MS AnalyseThis (CSH_VAL_MS a) throws Exception
    {

        // We need to Anlyse this Info Comming in...
        MyState = "Waiting For RWT/2 Msecs before Accessing the DataBase";
        System.out.println ("Waiting for RWT");
        this.sleep ((int) (Init.getRWT () / 2) - 1000); // The Computation time 
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
        // for the Hasher
        System.out.println ("Analysis Start--------Searching the DataBase for Keys");
        Vector d = Init.db.searchKeys ();
        if (d.size () == 0)
          {
              // We have no keys here and handle this sitn
              MyState = "No Keys Found...";
              System.out.println ("RemoteMasterServerValidator.java---No Keys Found");
              return null;
          }
        System.out.println ("Remote Server Analysis Start--------Search finished.." +
                            d.size () + "found");
         MyState = "Got The Identifier Information - Now Starting the Analysis....";
        int i = 0;
        String IDENTIFIER = null;
        String K2;
        String RAK;
        String K1 = a.getK1 ();
        String Hash = a.getHASH_K1_K2 ().trim ();
        while (i < d.size ())
          {
              // We are going to check the keys
              K2 = (String) d.elementAt (i + 1);
              RAK = (String) d.elementAt (i + 2);
              System.out.println ("Remote Server Analysis----------------" + K1 + K2 + "RAK=" +
                                  RAK + "Hash=" + Hash);
              MyState = "analysing " + K1 + K2 + " RAK=" + RAK + "Hash=" + Hash;
              GenerateRAK Baja = new GenerateRAK ();
              String ID = (String) d.elementAt (i);
              boolean kl = Baja.check (ID, K1, K2, RAK, Hash, "SHA1");
               System.out.println ("Analysis---------Result is " + kl);
               MyState = "Result=" + kl;
              if (kl)
                   IDENTIFIER = ID.trim ();
               i += 3;          // Keep Searching for the last value match
          }
        if (IDENTIFIER == null)
          {
              // No MAtch Handle the Abortion
              System.out.println ("RemoteMasterServerValidator.java---No Match Found");
              MyState = "Identifier Not Found...";
              return null;
          }
        System.out.println ("Analysis Start--------Inserting RAK");
        MyState = "Match Found...Inserting RAK";
        Init.db.insertRAK (Hash, K1, IDENTIFIER);
        String[]g = Init.db.getStat (IDENTIFIER);
        if (g == null)
          {
              // Handle a Bad Sitn...
              System.out.
                  println
                  ("RemoteMasterServerValidator.java---Something Unholy Happend I Found the key and searched for it again and it was NOT Found");
              return null;
          }
        System.out.println ("Analysis Start-------Got the Following Result");
        for (int j = 0; j < g.length; j++)
            System.out.println ("------------" + g[j]);

        // This ought to be there- If Not Some one may have changed the
        // code....
        return (new
                VAL_REP_MS (IDENTIFIER, new Float (g[0]), new Long (g[1]),
                            new Long (g[2]), new Long (g[3])));
    }

    /********************************************************
     **********Start of Message handling Code  *************
     ********************************************************
     */

     /** Get the CSH_VAL_MS request from Masterserver*/
    private CSH_VAL_MS getCSH_VAL_MS () throws Exception
    {
        MyState = "Waiting For CSH_VAL_MS";
        CSH_VAL_MS a = new CSH_VAL_MS ();
         System.out.
            println
            ("=======================**************MasterServerVALIDATOR.java -Waiting for the CSH_VAL_MS");
         a = (CSH_VAL_MS) getGenRQ (a, UserReader, UserWriter);
         System.out.
            println
            ("=======================**************MasterServerVALIDATOR.java -Got CSH_VAL_MS"
             + a);
         MyState = "Got CSH_VAL_MS=" + a;
        if (a == null)
          {
              // theLogger.logit("Invalid Cash Format
              // from"+UserReader.getID());
              throw (new Exception ("Invalid Cash Token Format..."));
          }
        return (a);
    }

     /** Send the VAL_REP_MS Request to the Master Server*/
    private void sendVAL_REP_MS (VAL_REP_MS a) throws Exception
    {
        System.out.
            println
            ("=======================**************MasterServerVALIDATOR.java -Sending VAL_REP_MS"
             + a);
        MyState = "Sending VAL_REP_MS= " + a;
        sendGenRQ (a, UserWriter, UserWriter);
    }

    /********************************************************
     **********End of Message Handling Code    *************
     ********************************************************
     */

    /** The Constructor*/
    public RemoteMasterServerValidator (Socket s, BrahmaInitialize Init,
                                        logger theLogger) throws Exception
    {
        super (s, theLogger);
        this.Init = Init;
        // Init.addRemoteRequest(this);
        System.out.
            println ("=======================*******************Starting Up Validator for " +
                     getConnectInfo ());
        MyState = "Remote Validator is Starting up....";
    }


    /** the Cleaning Up routine that promises a Clean EXIT */
    public void cleanup () throws Exception
    {
        System.out.println ("Shutting Down Connection" + getConnectInfo ());
        MyState = "Shutting Down the Connections..";
        try
        {
            super.closeAllChildThreads ();
        }
        catch (Exception e)
        {
        }
        MyState = "All Connections Are Down...";
    }

   /** Overloading the thread that results in this code running in parallel*/
    public void run ()
    {
        System.out.
            println ("THE REMOTEMASTERSERVERVALIDATOR IS RUNNING1111111111111111111111111111");
        System.out.
            println
            ("=======================*************MasterServerValidato.java Is RUNNING");
        MyState = "Remote Master Server Validator is Running";
        try
        {
            while (!done)
              {
                  // Handle the Messages Here

               /********************************************************
                *************Message Send Recieve Section *************
                ********************************************************
                */
                  CSH_VAL_MS a = getCSH_VAL_MS ();

                  System.out.println ("=======================************Got " + a);
                  VAL_REP_MS b = AnalyseThis (a);

                  sendVAL_REP_MS (b);
                  System.out.
                      println ("=======================************Completed but waiting");
                  // while (true) ;
                  MyState = "Completed the Transactions Expected of Me...";
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
        System.out.
            println ("THE REMOTEMASTERSERVERVALIDATOR IS DEAD1111111111111111111111111111");
        MyState = "I Am Dead...";
        try
        {
            Init.deleteRemoteRequest (this);
        }
        catch (Exception e)
        {
        }
    }
}
