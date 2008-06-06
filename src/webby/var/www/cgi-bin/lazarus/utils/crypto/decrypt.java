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

//Source file: Y:/E-Cash-Project/TestBed/CryptoGraphy/Crypt/development/decrypt.java

//MyPackagees
package utils.crypto;

import org.logi.crypto.*;
import org.logi.crypto.sign.*;
import org.logi.crypto.keys.*;
import org.logi.crypto.modes.*;

import java.util.*;
import java.io.*;
import java.lang.*;

import utils.crypto.*;
import utils.misc.*;

/**
 * @author 
 * @version 
 * I am to decrypt the contents of any String based on a KeyString / Key given to me based on any Algo -
 * BlowFish,TripleDES,RSA are some I support.
 */
public class decrypt
{
    private static StringOpt theStringOpt;

   /**
    * @param msg
    * @return void
    * @exception 
    * @author 
    * @version
    * @roseuid 3B8BD4A30173
    */
    public static void help (Object msg)
    {
        System.err.println (msg);
        System.err.println ();
        System.err.println ("Use: java decrypt <key> [mode]");
        System.err.println ();
        System.err.println ("<key> is a CDS for a CipherKey object and mode");
        System.err.println ("may be one of ECB, CBC, OFB or CFB. Default is CFB.");
        System.err.println ();
        System.err.println ("Note that ECB and CBC modes have block sizes greater than");
        System.err.
            println ("one byte and garbage may be appended to the encryptedtext before");
        System.err.println ("decrypting. Use OFB to avoid this.");
        System.err.println ();
        System.err.println ("Standard input will be read, decrypted with the key");
        System.err.println ("in the cosen mode and written to standard output.");
        System.err.println ();
        System.err.println ("Try something like BlowfishKey(123454321) for the key.");
        System.err.println ();
        System.err.println ("You can combine this program with the decrypt program with:");
        System.err.println ("   java decrypt 'BlowfishKey(123454321)' OFB |");
        System.err.println ("   java decrypt 'BlowfishKey(123454321)' OFB");
        System.err.println ("on one line at a unix-like command-line");
        System.exit (1);
    }

    public static String decrypt (String Message, String Keys, String mode)
    {
        CipherKey K;

        try
        {
            K = (CipherKey) Crypto.fromString (Keys);
        }
        catch (Exception e)
        {
            return null;
        }
        return (dec (Message, K, mode));
    }
    public static String decrypt (String Message, CipherKey Keys, String mode)
    {
        return (dec (Message, Keys, mode));
    }

   /**
   /**
    * @param Message
    * @param Keys
    * @param mode
    * @return String
    * @exception 
    * @author 
    * @version
    * @roseuid 3B8BD4A3017F
    */
    public static String dec (String Message, CipherKey Keys, String mode)
    {
        Crypto.initRandom ();
        String PlainString = "";

        try
        {
            if (mode == null || Keys == null)
                help ("No key given on command line");


            // Either the cast or the parsing may fail.
            CipherKey key = Keys;


            // Depending on the value of mode we create an decryptSession
            // object to decrypt data with the key.
            DecryptSession decryptSession = null;

            int pbs = key.plainBlockSize ();
            int cbs = key.cipherBlockSize ();
            int size = 8 * 1024;
            int numBlock = (size + pbs / 2) / pbs;

            size = numBlock * pbs;

            byte[]plain = new byte[size];
            byte[]cipher = new byte[numBlock * cbs];

            // System.out.println ("Decryption Mode:" + mode);

            if (mode.equals ("ECB"))
                decryptSession = new DecryptECB (key);
            else if (mode.equals ("CBC"))
                decryptSession = new DecryptCBC (key);
            else if (mode.equals ("OFB"))
                decryptSession = new DecryptOFB (key, numBlock * cbs);
            else if (mode.equals ("CFB"))
                decryptSession = new DecryptCFB (key);
            else
                help ("Unknown mode");

            // Read encryptedtext from System.in until it runs out, decrypt it
            // with the decrypt session (and thereby the key) and write to the
            // output stream.
            // System.out.println ("Decryption Key:" + decryptSession.getKey () 
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
            // +
            // "Plain Key SIze" +
            // decryptSession.plainBlockSize ());




            byte[]encryptedtext = theStringOpt.StringToByte (Message, Message.length ());


            byte[]plaintext = decryptSession.decrypt (encryptedtext, 0, encryptedtext.length);

            PlainString = theStringOpt.byteToString (plaintext);
            // flush buffered bytes from the decrypt session. This may append
            // garbage to the encryptedtext in ECB and CBC modes.



        }
        catch (Exception e)
        {
            e.printStackTrace ();
            help (e);
        }
        return (PlainString);
    }
}

/* 
decrypt.StringToByte(String,int){
   }
 */

/* 
decrypt.byteToString(byte[]){
   }
 */
