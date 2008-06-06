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

//Source file: Y:/E-Cash-Project/TestBed/development/network/SecureWriter.java

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
import utils.*;
import java.util.*;
import java.io.*;
import org.logi.crypto.*;
import utils.misc.*;
import utils.crypto.*;
import messages.tools.*;
import messages.*;

/**
 * @author 
 * @version 
 * Secure RSA Writer
 */
public class SecureWriter extends Thread
{
    protected DataOutputStream out;
    private encrypt theEncrypt;
    private decrypt theDecrypt;
    private CipherKey myKey;
    private CipherKey yourKey;
    private Vector buffer;
    private boolean TRAN_ABRT;
    private String ABORT_REASON;
    public SuperMessage theSuperMessage;
    private StringOpt theStringOpt;
    private boolean done;
    private boolean ready;
    protected String ID;
    protected logger theLog;

    public void setDone (boolean value)
    {
        done = value;
    }

    public boolean getDone ()
    {
        return done;
    }

    public void setABORT_REASON (String Reason)
    {
        theLog.logit (ID + " SWriter TRAN_ABRT SET- Reason " + Reason);
        ABORT_REASON = Reason;
        TRAN_ABRT = true;
    }

    public boolean isReady ()
    {
        return ready;
    }

    public String getID ()
    {
        return ID;
    }
    public boolean writeLine (String MesgType, String messg)
    {
        System.out.println ("Sending Message" + messg);
        if (buffer == null)
            return false;
        try
        {
            theSuperMessage = new SuperMessage (MesgType, messg);
        }
        catch (Exception e)
        {
            return false;
        }
        buffer.addElement (theSuperMessage);
        return true;
    }
    protected String en (SuperMessage s) throws Exception
    {
        String g = s.toString ();
         g.trim ();

    /** Encrypt with my enc key*/
        // String k = theEncrypt.encrypt (g, myKey, "ECB");

    /** Encrypt with Your dec key*/
        // g = theEncrypt.encrypt (g, yourKey, "ECB");
        // Change Above
        // System.out.println (ID + " - Writer Encrypt Key=" + yourKey +
        // " Not Using MyKey=" + myKey);
        // System.out.println ("Output String Length=" + g.length ());
        // Start of File Write
        // StringFileWrite ra= new StringFileWrite("sEncrypt"+ID,false);
        // ra.print(g);
        // ra.close();
        // End of File Write

         return g;
    }
    public SecureWriter ()
    {
        buffer = new Vector ();
        TRAN_ABRT = false;
        ABORT_REASON = null;
        theEncrypt = null;
        theDecrypt = null;
        myKey = null;
        yourKey = null;
        theStringOpt = new StringOpt ();
        done = false;
        ready = false;
    }
    public SecureWriter (CipherKey myPrivateKey, CipherKey yourPublicKey,
                         DataOutputStream outputR, logger Log, String ID)
    {
        buffer = new Vector ();
        TRAN_ABRT = false;
        ABORT_REASON = null;
        theEncrypt = new encrypt ();
        theDecrypt = new decrypt ();
        myKey = myPrivateKey;
        yourKey = yourPublicKey;
        out = outputR;
        theStringOpt = new StringOpt ();
        this.ID = ID;
        this.theLog = Log;
        done = false;
        ready = false;
    }
    public void run ()
    {
        System.out.println ("SecureWriter " + ID + " is Running");
        try
        {
            while (!done)
              {
                  boolean a = false;
                  SuperMessage su = null;

                  ready = true; // I am ready to get written into!!!
                  if (TRAN_ABRT)
                    {
                        su = new SuperMessage ("TRAN_ABRT", ABORT_REASON);
                        a = true;
                        done = true;
                    }
                  else if (buffer.size () > 0)
                    {
                        su = (SuperMessage) buffer.elementAt (0);
                        buffer.remove (su);
                        a = true;
                    }
                  if (a)
                    {
                        System.out.println (" SecureWrite Sending to" + ID + " - " + su);
                        theLog.writelog (ID + " SWrite Sending Message -" + su.toString ());
                        String outString = en (su);

                        // Start of File Write
                        // StringFileWrite ra= new
                        // StringFileWrite("sWriter"+ID,false);
                        // ra.print(outString);
                        // ra.close();
                        // End of File Write

                        // out.writeUTF(outString);
                        int length = outString.length ();

                        // System.out.println ("Length of String=" + length);
                        byte[]b = theStringOpt.StringToByte (outString, length);
                        length = b.length;
                        // System.out.println ("SecureWriter Writing length=" + 
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
                        out.writeInt (length);
                        out.write (b, 0, length);
                    }           // end of if a
              }                 // end of infinite while
        }
        catch (StopThreadException d)
        {                       // Ignore the Stoper
        }
        catch (Exception e)
        {
            theLog.logit (ID + " Secure Writer ERROR-" + e);
            System.out.println ("Error in SecureWriter " + ID + " ..." + e);
            e.printStackTrace ();
        }
        System.out.println ("Secure Write " + ID + " Quits..");
    }
}
