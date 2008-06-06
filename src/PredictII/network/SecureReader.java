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

//Source file: Y:/E-Cash-Project/TestBed/development/network/SecureReader.java

/* This Is a Copy Lefted Software
(CL) 2001 Spetember
By Nishanth 'Lazarus' Menon
Calicut Regional Engineering College
Calicut */

package network;

import org.logi.crypto.*;
import org.logi.crypto.sign.*;
import org.logi.crypto.keys.*;
import org.logi.crypto.modes.*;

import messages.SuperMessage;
import java.lang.*;
import java.util.*;
import java.io.*;
import org.logi.crypto.*;
import utils.misc.*;
import utils.*;
import utils.crypto.*;
import messages.tools.*;
import messages.*;

/**
 * @author 
 * @version 
 * RSA Based Writer
 */
public class SecureReader extends Thread
{
    protected DataInputStream in;
    private encrypt theEncrypt;
    private decrypt theDecrypt;
    private CipherKey myKey;
    private CipherKey yourKey;
    private Vector buffer;
    private boolean TRAN_ABRT;
    private String ABORT_REASON;
    private SuperMessage theSuperMessage;
    private StringOpt theStringOpt;
    private boolean done;
    private boolean ready;
    protected String ID;
    protected logger theLog;

    public void setDone (boolean d)
    {
        done = d;
    }

    public boolean getDone ()
    {
        return done;
    }

    public boolean isReady ()
    {
        return ready;
    }
    public String getID ()
    {
        return ID;
    }

    public boolean getTRAN_ABRT ()
    {
        // //System.out.
        // println ("++++++++++++++++++++++++++++++++++++++++++++++++TranABRT
        // REACHED");
        // System.out.println ("TRANABRT=" + TRAN_ABRT);
        return TRAN_ABRT;
    }

    public String getABORT_REASON ()
    {
        return ABORT_REASON;
    }

    public SuperMessage readLine (boolean block) throws Exception
    {
        System.out.print (ID + "readLine" + block);
        if (buffer == null)
            while (buffer == null && !TRAN_ABRT)
                System.out.println ("Buffer is null");
        if (!this.isAlive () && buffer.size () == 0)
          {
              System.out.println ("I Am Throwing the Exception Connection failesd");
              throw (new Exception ("Attempt to read After Connection Failed"));
          }

        if ((buffer.size () == 0) && !block)
          {
              System.out.print ("blockeless datalessretunr");
              return null;
          }
        if (TRAN_ABRT)
          {
              done = true;
              System.out.println ("TRANSACTION ABORTED");
              return null;
          }
        while (buffer.size () == 0)
          {
              if (!this.isAlive () && buffer.size () == 0)
                {
                    System.out.println ("GOTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
                    throw (new Exception ("Attempt to read After Connection Failed"));
                }
          }
        System.out.println ("Got A Message-----");
        SuperMessage s = (SuperMessage) buffer.elementAt (0);

        if (s == null)
          {
              System.out.println ("-----------s=null");
              return null;
          }
        /* System.err.println("----------------------I read the stuff and it is 
         * // null"); // System.out.println("----------------------Giving "+s); 
         */
        buffer.remove (s);
        // System.err.println("----------------------I am Giving "+s);
        return (s);
    }

    protected SuperMessage de (String input) throws Exception
    {

    /** Decrypt with my Key*/
        // String g = theDecrypt.decrypt (input, myKey, "ECB");
        String g = input;
        // ..Change above

    /** Decrypt with your key*/
        // String r = theDecrypt.decrypt (input, yourKey, "ECB");
        // System.out.println (ID + " - Reader Decrypt myKey=" + myKey +
        // " Not Using yourKey=" + yourKey);
        // System.out.println ("INputSTring Length=" + input.length ());
         g = g.trim ();
        SuperMessage su = new SuperMessage (g);
         return su;
    }
    public SecureReader ()
    {
        buffer = new Vector ();
        TRAN_ABRT = false;
        ABORT_REASON = null;
        theStringOpt = new StringOpt ();
        myKey = null;
        yourKey = null;
        done = false;
    }
    public SecureReader (CipherKey MyPrivateKey, CipherKey YourPublicKey,
                         DataInputStream inputR, logger log, String ID)
    {
        buffer = new Vector ();
        TRAN_ABRT = false;
        ABORT_REASON = null;
        theEncrypt = new encrypt ();
        theDecrypt = new decrypt ();
        theStringOpt = new StringOpt ();
        myKey = MyPrivateKey;
        yourKey = YourPublicKey;
        in = inputR;
        this.ID = ID;
        done = false;
        this.theLog = log;
    }

    public void run ()
    {
        System.out.println ("SecureReader " + ID + " is Running");
        ready = false;

        try
        {
            while (!done)
              {
                  System.out.println (ID + "SR__Waiting to read");
                  int length = in.readInt ();

                  System.out.println ("Secure Reader to Read-" + ID + "Length of Data= " +
                                      length);
                  byte[]inByte = new byte[length];
                  in.readFully (inByte);
                  String inString = theStringOpt.byteToString (inByte);

                  // String inString=in.readUTF();

                  // Start of file write
                  // StringFileWrite ra= new
                  // StringFileWrite("sReader"+ID,false);
                  // ra.print(inString);
                  // ra.close();
                  // End of file write

                  SuperMessage su = de (inString);

                  System.out.println (ID + "Secure Read Got Message-" + su);
                  // Testing Purpose only
                  theLog.writelog (ID + "Secure Read Got Message-" + su);

                  if (su.getMESSAGE_NAME ().equals ("TRAN_ABRT"))
                    {
                        theLog.writelog (ID + "Send TRAN_ABRT. REASON-" + su.getMessage ());
                        TRAN_ABRT = true;
                        ABORT_REASON = su.getMessage ();
                    }
                  else
                      buffer.addElement (su);
                  System.out.println ("Added Element" + su + " Length of buffer=" +
                                      buffer.size ());
                  ready = true;
              }
        }
        catch (StopThreadException d)
        {
            // Jada Man How to get this to quit decently is still a matter
            // of
            // thought Mia thinking......
        }
        catch (Exception e)
        {
            theLog.logit ("SecureRead " + ID + " Error-" + e);
            System.err.println ("Error in SecureReader" + ID + " " + e);
            e.printStackTrace ();
        }                       // Ignore it
        theLog.logit ("Closing Read Stream " + ID);
        System.out.println ("SecureRead " + ID + " Quits..");
        ready = true;
        // I have not given done = true as this might cause the following
        // problem-
        // if done = true and is ! isAlive then we have a problem
    }
}
