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

//Source file: Y:/E-Cash-Project/TestBed/development/GetKeys/GetMyKey.java

package GetKeys;

import utils.misc.*;
import utils.crypto.*;

//package org.logi.crypto.test;
import org.logi.crypto.*;
import org.logi.crypto.sign.*;
import org.logi.crypto.keys.*;
import org.logi.crypto.modes.*;


import java.util.*;
import java.io.*;

/**
 * @author 
 * @version 
 * I am Supposed to find out about my Keypair and make sure that it is valid!
 */
public class GetMyKey
{
    private String myPassword;
    private String KeyHashAlgo;
    private String PasswordHashAlgo;
    private String KeyEncryptionAlgo;
    private static KeyPair MyKey;
    public StringFileRead theStringFileRead;
    public decrypt theDecrypt;
    public encrypt theEncrypt;
    public hash theHash;
    // public MyKeyInTheFile theMyKeyInTheFile;

    public GetMyKey ()
    {
    }

    public static Key createKeys (String keyType, String initiator) throws Exception
    {

        KeyPair kp = null;
         byte[] byteinit = StringOpt.StringToByte (initiator, initiator.length ());


        if (keyType.equals ("DES"))
          {
              return (new DESKey (byteinit));

          }
        else if (keyType.equals ("TriDES"))
          {
              return (new TriDESKey (byteinit));

          }
        else if (keyType.equals ("Blowfish"))
          {
              return (new BlowfishKey (byteinit));

          }
        else
          {
              return null;
          }
    }

   /**
    * @param MyFileName
    * @param MyPassword
    * @param HashAlgoPassword
    * @param HashAlgoKey
    * @param EncAlgoKey
    * @return KeyPair
    * @exception Exception
    * @author 
    * @version 
    * YEah!!!
    * Ow OW OW I am To Get My own Client Info-
    * 1. I have to Check for the password
    * 2. If Okay then I get my Key
    * 3. make Sure every thing is okay
    * @roseuid 3B8BF0B103AB
    */
    GetMyKey (String MyFileName, String MyPassword,
              String HashAlgoPassword, String HashAlgoKey, String EncAlgoKey) throws Exception
    {

        PrintWriter details = new PrintWriter (System.out, true);
         System.out.println ("Getting My Key from File:" + MyFileName);

         theStringFileRead = new StringFileRead (MyFileName);

        // Reading the Two Strings to the Following
        String myP = theStringFileRead.readln ();
        String myK = theStringFileRead.readRestString ();
        /* { StringFileWrite f= new StringFileWrite("t2"); byte
         * b[]=StringOpt.StringToByte(myK); for (int i=0;i<b.length;i++)
         * f.println(" "+b[i]); f.close(); } */
         System.out.println ("The Key Length=" + myK.length ());

         theStringFileRead.close ();

        // File Ops Finished - Now the Difficult Part

         theHash = new hash ();
         theEncrypt = new encrypt ();
         theDecrypt = new decrypt ();

         myPassword = theHash.hash (MyPassword, HashAlgoPassword);

        // Check if the Passwords Match
        if (!myP.toString ().equals (myPassword.toString ()))
             throw (new Exception ("Sorry Passwords Do Not Match" + myP + myPassword));

        // Lets Get Started with the Key Decryption part
         System.out.println ("The Key HashAlgo=" + HashAlgoKey +
                             "Encryption Algo=" + EncAlgoKey);
        String Jango = theHash.hash (MyPassword, HashAlgoKey);
         System.out.println ("myPassword=" + myPassword + "HashAlgo=" + HashAlgoKey);
        Key k = createKeys (EncAlgoKey, Jango);
         System.out.println (" The Jango=" + Jango +
                             "\n\n Key Used to decrypt=" + k.toString ());

        // System.exit(0);
        if (k == null)
             throw (new
                    Exception
                    ("Invalid Key Encryption Algorithm-Use Blowfish,TriDES,DES.Used -"
                     + EncAlgoKey));

        String myKeyString = theDecrypt.decrypt (myK, k.toString (), "OFB");

         System.out.println ("Key=" + myKeyString);

        // Now Lets Check the Key for Validity

        KeyPair myKey = (KeyPair) Crypto.fromString (myKeyString);

        SignatureKey pri = (SignatureKey) myKey.getPrivate ();
        SignatureKey pub = (SignatureKey) myKey.getPublic ();

        // Test Encryption Decryption
        boolean ok;
         try
        {
            byte[]buffer = new byte[1024];
            java.util.Random rand = new java.util.Random ();
            rand.nextBytes (buffer);
            Fingerprint fp = Fingerprint.create (buffer, "SHA1");
             details.println ("Good signature:");
            Signature s1 = pri.sign (fp);
             s1.prettyPrint (details);
             details.println ();
            boolean m1 = pub.verify (s1, fp);
             details.println ("Signature verified: " + (m1 ? "Yes" : "No"));

             details.println ("Bogus signature:");
             byte[] bogus = s1.getBytes ();
             bogus[3] ^= 5;
            Signature s2 = new Signature ("SHA1", bogus);
             s2.prettyPrint (details);
             details.println ();
            boolean m2 = pub.verify (s2, fp);
             details.println ("Signature verified: " + (m2 ? "Yes" : "No"));

             ok = m1 && !m2;
        }
        catch (Exception e)
        {
            throw (new Exception (" Invalid Key!!"));

        }
        if (ok)
            MyKey = myKey;
    }

    public static KeyPair getKey ()
    {
        return (MyKey);
    }

    public static void main (String[]args) throws Exception
    {

        String FileName = "mytestfile.key";
        String MyPassword = "Hello World";
        String PasswordHashAlgo = "SHA1";
        String KeyHashAlgo = "MD5";
        String EncKeyAlgo = "Blowfish";

//   public GetMyKey (String MyFileName, String MyPassword,
//                 String HashAlgoPassword, String HashAlgoKey,
//                 String EncAlgoKey) throws Exception   

        GetMyKey j = new GetMyKey (FileName, MyPassword, PasswordHashAlgo, KeyHashAlgo,
                                   EncKeyAlgo);
        KeyPair MyK = j.getKey ();

         System.out.println ("Key Retrieved =" + (MyK == null ? null : MyK.toString ()));
    }

}
