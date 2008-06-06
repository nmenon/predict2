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


/* This Is a Copy Lefted Software
(CL) 2001 Spetember
By Nishanth 'Lazarus' Menon
Calicut Regional Engineering College
Calicut */

package network;

import org.logi.crypto.*;
import org.logi.crypto.keys.*;
import org.logi.crypto.modes.*;
import org.logi.crypto.protocols.*;
import org.logi.crypto.io.*;

import java.util.Random;
import java.io.*;
import java.net.*;
import java.util.*;

import utils.misc.*;
import utils.crypto.*;
import messages.tools.*;
import messages.*;
import network.*;

/**
 * @author 
 * @version 
 * Okay So this is a Very Insecure Reader!!! Means nothing but that the Messages are being send after DH tech (160bit)
 */
public class Authenticate
{
    class clientThread extends Thread
    {
        Socket mysocket;
        String keyEx;
        String Message;
        String ReadMessage;
        public networkStats stats;
        long rtt;
        boolean done;

        public long getrtt ()
        {
            return rtt;
        }
        public String getReply ()
        {
            return ReadMessage;
        }

        public boolean getDone ()
        {
            return done;
        }
        public clientThread (Socket s, String Ex, String Mess) throws Exception
        {
            mysocket = s;
            keyEx = Ex;
            rtt = 0;
            Message = Mess;
            done = false;
        }

        public InterKeyExClient makeKexCli () throws Exception
        {

            if (keyEx.equals ("DH"))
                return new DHKeyExClient (256, "BlowfishKey");

            return null;
        }                       // End of makeKexCli

        // Client Thread Runner
        public void run ()
        {

            CipherStreamClient csc = null;

            try
            {
                csc =
                    new CipherStreamClient (mysocket.getInputStream (),
                                            mysocket.getOutputStream (),
                                            makeKexCli (), new EncryptOFB (64),
                                            new DecryptOFB (64));
                DataInputStream in = new DataInputStream (csc.getInputStream ());
                DataOutputStream out = new DataOutputStream (csc.getOutputStream ());

                long start = System.currentTimeMillis ();

                // System.out.println ("Writing Message " + Message);
                out.writeUTF (Message);
                ReadMessage = in.readUTF ();
                // System.out.println ("Read Message " + ReadMessage);
                long end = System.currentTimeMillis ();

   /** Get the time */
                networkAnalyzerClient sona = new networkAnalyzerClient (in, out, 30);

                stats = sona.getStats ();
                rtt = end - start;
                success = true;

            }                   // End of try
            catch (Exception e)
            {
                System.out.println (" while analysing... Exception occured-" + e);
                e.printStackTrace ();
            }                   // End of Catching
            done = true;
            return;
            /* try { mysocket.close (); } catch (Exception e3) { } */
        }                       // End of run() - Client Thread
    }


    public SuperMessage theSuperMessage;
    public networkStats stats;
    private String MyMessage;
    private String RemoteKey;
    private boolean done;
    public boolean success;
    private long RTT;

    public networkStats getStats ()
    {
        return stats;
    }
    public boolean isSuccess ()
    {
        return success;
    }
    public boolean isDone ()
    {
        return done;
    }
    public String getMyMessage ()
    {
        return MyMessage;
    }
    public long getRTT ()
    {
        return RTT;
    }
    public String getRemoteKey ()
    {
        return RemoteKey;
    }
    private void verifySuperMessage (String Message) throws Exception
    {
        SuperMessage Ki = new SuperMessage (Message);
        if (!Ki.verifyType ("AUTH_REP"))
             throw (new
                    Exception ("Invalid Message type -Expected AUTH_REQ -got" +
                               Ki.getMESSAGE_NAME ()));
        CipherKey k = (CipherKey) Crypto.fromString (Ki.getMessage ());
         RemoteKey = k.toString ();
    }
    public Authenticate (Socket s, logger theLog, String myPublicKey)
    {
        Crypto.initRandom ();
        clientThread client = null;

        success = false;
        RTT = 0;
        done = false;
        MyMessage = null;
        RemoteKey = null;
        try
        {
            theLog.writelog ("Authenticating" + s);
            theSuperMessage = new SuperMessage ("AUTH_REQ", myPublicKey);
            client = new clientThread (s, "DH", theSuperMessage.toString ());
            client.start ();
            // while (!client.getDone ());
            client.join ();
            stats = client.stats;
            verifySuperMessage (client.getReply ());
            RTT = client.getrtt ();
            MyMessage = client.getReply ();
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
        done = true;
    }                           // Constructor
    public static void main (String[]args) throws Exception
    {
        Crypto.initRandom ();
        KeyPair myRSAKey = RSAKey.createKeys (256); // Test Dosage only!!
        logger g = new logger ("test.log");
        CipherKey b = (CipherKey) myRSAKey.getPublic ();
         System.out.println ("My PUblic Key = " + b);


        Socket k = new Socket (args[0], 9050);
        Authenticate A = new Authenticate (k, g, b.toString () + "asdasdsa");
        while (!A.isDone ()) ;
         System.out.println ("RTT=" + A.getRTT () + "\n The Message=" + A.getMyMessage ());

    }
}
