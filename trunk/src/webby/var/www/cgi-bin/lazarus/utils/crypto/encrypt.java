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

//Source file: Y:/E-Cash-Project/TestBed/CryptoGraphy/Crypt/development/encrypt.java
//MyPackages
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
 *  I am to Encrypt the contents of any String based on a KeyString / Key given to me based on any Algo -
 * BlowFish,TripleDES,RSA are some I support.
 */
public class encrypt
{
    private static StringOpt theStringOpt;

   /**
    * @param msg
    * @return void
    * @exception 
    * @author 
    * @version
    * @roseuid 3B8BD4A3039A
    */
    public static void help (Object msg)
    {
        System.err.println (msg);
        System.err.println ();
        System.err.println ("Use: java encrypt <key> [mode]");
        System.err.println ();
        System.err.println ("<key> is a CDS for a CipherKey object and mode");
        System.err.println ("may be one of ECB, CBC, OFB or CFB. Default is CFB.");
        System.err.println ();
        System.err.println ("Note that ECB and CBC modes have block sizes greater than");
        System.err.println ("one byte and garbage may be appended to the plaintext before");
        System.err.println ("encrypting. Use OFB to avoid this.");
        System.err.println ();
        System.err.println ("Standard input will be read, encrypted with the key");
        System.err.println ("in the cosen mode and written to standard output.");
        System.err.println ();
        System.err.println ("Try something like BlowfishKey(123454321) for the key.");
        System.err.println ();
        System.err.println ("You can combine this program with the decrypt program with:");
        System.err.println ("   java encrypt 'BlowfishKey(123454321)' OFB |");
        System.err.println ("   java decrypt 'BlowfishKey(123454321)' OFB");
        System.err.println ("on one line at a unix-like command-line");
        System.exit (1);
    }

    public static String encrypt (String Message, String Keys, String mode)
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
        return (en (Message, K, mode));
    }
    public static String encrypt (String Message, CipherKey Keys, String mode)
    {
        return (en (Message, Keys, mode));
    }

   /**
    * @param Message
    * @param Keys
    * @param mode
    * @return String
    * @exception 
    * @author 
    * @version
    * @roseuid 3B8BD4A303A1
    */
    public static String en (String Message, CipherKey Keys, String mode)
    {
        Crypto.initRandom ();
        String CipherString = "";

        try
        {
            if (mode == null || Keys == null)
                help ("No key given on command line");

            // key is parsed from the stnig in arg[0] and cast to CipherKey.
            // Either the cast or the parsing may fail.
            CipherKey key = Keys;



            // Depending on the value of mode we create an EncryptSession
            // object to encrypt data with the key.
            EncryptSession encryptSession = null;
            int pbs = key.plainBlockSize ();
            int cbs = key.cipherBlockSize ();
            int size = 8 * 1024;
            int numBlock = (size + pbs / 2) / pbs;

            size = numBlock * pbs;

            byte[]plain = new byte[size];
            byte[]cipher = new byte[numBlock * cbs];

            // System.out.println ("Encryption Mode:" + mode);
            if (mode.equals ("ECB"))
                encryptSession = new EncryptECB (key);
            else if (mode.equals ("CBC"))
                encryptSession = new EncryptCBC (key);
            else if (mode.equals ("OFB"))
                encryptSession = new EncryptOFB (key, size);
            else if (mode.equals ("CFB"))
                encryptSession = new EncryptCFB (key);
            else
                help ("Unknown mode");
            // System.out.println ("Encryption Key:" + encryptSession.getKey () 
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
            // "Plain Key SIze" + numBlock * cbs);
            // Read plaintext from System.in until it runs out, encrypt it
            // with the encrypt session (and thereby the key) and write to the
            // output stream.

            if (Message.length () > size)
                throw (new
                       Exception ("Encryption: Message Length =" + Message.length () +
                                  " Max encryption Possible=" + size));

            byte[]plaintext = theStringOpt.StringToByte (Message, size);


            byte[]ciphertext = encryptSession.encrypt (plaintext, 0, size);

            CipherString = theStringOpt.byteToString (ciphertext);
            // System.out.println (" Size of the cipher " + ciphertext.length +
//                        " Length of text" + CipherString.length ());
            // flush buffered bytes from the encrypt session. This may append
            // garbage to the plaintext in ECB and CBC modes.
        }
        catch (Exception e)
        {
            help (e);
        }
        return (CipherString);
    }
    public static void encrypt ()
    {
    }
}
