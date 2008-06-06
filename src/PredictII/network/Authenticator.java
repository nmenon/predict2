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
public class Authenticator
{

    class serverThread extends Thread
    {
        Socket s;
        String keyEx;
        String OutMessage;
        String IncommingMessage;
        long rtt;
        public networkStats stats;
        private boolean done;
        public String getIncommingMessage ()
        {
            return IncommingMessage;
        }

        public long getrtt ()
        {
            return rtt;
        }
        // ServerThread Constructor
        public serverThread (Socket s, String Key, String Mes)
        {
            this.s = s;
            keyEx = Key;
            IncommingMessage = null;
            rtt = 0;
            OutMessage = Mes;
            done = false;
        }
        public boolean getDone ()
        {
            return done;
        }
        // Making KEXServer
        public InterKeyExServer makeKexSer ()
        {
            if (keyEx.equals ("DH"))
                return new DHKeyExServer (256, "BlowfishKey");
            return null;
        }

        // Sever Runner
        public void run ()
        {
            CipherStreamServer css = null;

            done = false;
            try
            {
                css = new CipherStreamServer (s.getInputStream (),
                                              s.getOutputStream (),
                                              makeKexSer (),
                                              new EncryptOFB (64), new DecryptOFB (64));
                DataInputStream in = new DataInputStream (css.getInputStream ());
                DataOutputStream out = new DataOutputStream (css.getOutputStream ());
                long start = System.currentTimeMillis ();

                IncommingMessage = in.readUTF ();
                out.writeUTF (OutMessage);
                long end = System.currentTimeMillis ();

                rtt = end - start;

   /** Get the time */
                networkAnalyzerServer sona = new networkAnalyzerServer (in, out, 30);

                stats = sona.getStats ();

                // System.out.println (rtt + " End=" + end + " start=" +
                // start);
                success = true;
            }
            catch (Exception e)
            {
                if (!(e instanceof java.io.EOFException))
                  {
                      System.err.println ("S\tDied with an exception" + e);
                      e.printStackTrace ();
                  }
            }                   // end of catch and try
            done = true;

        }                       // End of runner
    }                           // End of Server Thread Class

    public SuperMessage theSuperMessage;
    public networkStats stats;
    private String MyMessage;
    private String RemoteKey;
    private boolean done;
    private boolean success;
    private long RTT;

    public boolean isSuccess ()
    {
        return success;
    }
    public networkStats getStats ()
    {
        return stats;
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
        if (!Ki.verifyType ("AUTH_REQ"))
             throw (new
                    Exception ("Invalid Message type -Expected AUTH_REQ -got" +
                               Ki.getMESSAGE_NAME ()));
        CipherKey k = (CipherKey) Crypto.fromString (Ki.getMessage ());
         RemoteKey = k.toString ();
        // System.out.println("Key===="+k);

    }

    public Authenticator (Socket s, logger theLog, String myPublicKey)
    {
        Crypto.initRandom ();
        serverThread server = null;

        RTT = 0;
        done = false;
        MyMessage = null;
        RemoteKey = null;
        success = false;
        try
        {
            theSuperMessage = new SuperMessage ("AUTH_REP", myPublicKey);
            theLog.logit ("Starting Authentication " + s);
            server = new serverThread (s, "DH", theSuperMessage.toString ());
            server.start ();
            // while (!server.getDone ());
            server.join ();
            stats = server.stats;
            verifySuperMessage (server.getIncommingMessage ());
            MyMessage = server.getIncommingMessage ();
            RTT = server.getrtt ();
            done = true;
        }
        catch (Exception e)
        {
            e.printStackTrace ();

            done = true;
        }
    }                           // Constructor
    public static void main (String[]args) throws Exception
    {
        Crypto.initRandom ();
        logger theLog = new logger ("test.log");
        KeyPair myRSAKey = RSAKey.createKeys (256); // Test Dosage only!!
        CipherKey b = (CipherKey) myRSAKey.getPublic ();
         System.out.println ("My PUblic Key = " + b);

        ServerSocket s = new ServerSocket (9050);
        Socket k = s.accept ();

        Authenticator A = new Authenticator (k, theLog, "asdasda" + b.toString ());
        while (!A.isDone ()) ;
         System.out.println ("RTT=" + A.getRTT () + "\n The Message=" + A.getMyMessage ());
    }
}
