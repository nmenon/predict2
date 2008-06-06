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

//Source file: Y:/E-Cash-Project/TestBed/development/WriteKeys/WriteMyKey.java

package WriteKeys;

import utils.crypto.*;
import utils.misc.*;

//package org.logi.crypto.test;
import org.logi.crypto.*;
import org.logi.crypto.sign.*;
import org.logi.crypto.keys.*;
import org.logi.crypto.modes.*;

/**
 * @author 
 * @version 
 */
public class WriteMyKey
{
    private static String myIP;
    private static KeyPair myKey;
    private static String myFilename;
    private static String HashAlgoForPassword;
    private static String HashAlgoForKey;
    private static String EncAlgoForKey;
    private static String password;
    private static String myKeyString;
    public static hash theHash;
    public static encrypt theEncrypt;
    public static decrypt theDecrypt;
    public static StringFileWrite theStringFileWrite;

    public WriteMyKey ()
    {
    }

   /**
    * @param FileName
    * @return void
    * @exception Exception
    * @author 
    * @version 
    * @roseuid 3B92541A0297
    */
    public static void writemyKey (String FileName) throws Exception
    {
        StringFileWrite f = new StringFileWrite (FileName);


         f.println (password);
         f.print (myKeyString);
         f.close ();
    }

   /**
    * @param keyType
    * @param initiator
    * @return Key
    * @exception Exception
    * @author 
    * @version 
    * @roseuid 3B92547700D7
    */
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
    * @param KeyString
    * @param HashAlgoKey
    * @param FileName
    * @param EncAlgoKey
    * @return void
    * @exception Exception
    * @author 
    * @version 
    * @roseuid 3B9254E000C3
    */
    public static void SetMyKey (String MyFileName, String MyPassword,
                                 String HashAlgoPassword, String KeyString,
                                 String HashAlgoKey, String FileName,
                                 String EncAlgoKey) throws Exception
    {
        String Jango;
        Key k;
         theHash = new hash ();
         theEncrypt = new encrypt ();
         HashAlgoForKey = HashAlgoKey;
         HashAlgoForPassword = HashAlgoPassword;
         EncAlgoForKey = EncAlgoKey;

         System.out.println ("Setting Keys - File:" + MyFileName +
                             "\n\nPassowrd=" + MyPassword +
                             " \n\nPasswordHashAlgo=" + HashAlgoPassword +
                             " \n\nKey String=" + KeyString +
                             " \n\nKey HashAlgo=" + HashAlgoKey +
                             " \n\nKey Encryption Algo=" + EncAlgoKey);

        // First hash and Store The Password

         password = theHash.hash (MyPassword, HashAlgoPassword);

         System.out.println ("hash of Passowrd using " + HashAlgoPassword +
                             " is =" + password);

        // Next generate a Encryption Key based on our Algo Using other Hash
        // for the Key
        // First Creating the Second Hash to serve as the key for the enc algo

         Jango = theHash.hash (MyPassword, HashAlgoKey);
         System.out.println ("The Hash initiator for the Key using " +
                             HashAlgoKey + " is Jango=" + Jango);

         k = createKeys (EncAlgoKey, Jango);

        if (k == null)
             throw (new
                    Exception
                    ("Invalid Key Encryption Algorithm-Use Blowfish,TriDES,DES.Used -"
                     + EncAlgoKey));

         myKeyString = theEncrypt.encrypt (KeyString, k.toString (), "OFB");
        /* { StringFileWrite f= new StringFileWrite("t1"); byte
         * b[]=StringOpt.StringToByte(myKeyString); for (int
         * i=0;i<b.length;i++) f.println(" "+b[i]); f.close(); theDecrypt= new
         * decrypt(); String
         * t=theDecrypt.decrypt(myKeyString,k.toString(),"OFB");
         * System.out.println("Sett:=="+ t+"\n\n\n\n"); } */

         System.out.println (" Key --" + KeyString + " Was Encrypted Using " +
                             k + " resulting in --" + myKeyString.length ());

         writemyKey (FileName);

/*  
	//Put this After this fn to get this working for test purpose
	public static void main (String[]args) throws Exception
  {

    Crypto.initRandom ();
    String Pass = "";
    String filename = "test.key";
    String PasswordHashAlgo = "MD5";
    String KeyHashAlgo = "SHA1";
    String KeyEncryptionAlgo = "TriDES";

    KeyPair myRSAKey = RSAKey.createKeys (512);	//Test Dosage only!!
    //public static void SetMyKey(String MyFileName, String MyPassword, String HashAlgoPassword,
    //String KeyString, String HashAlgoKey, String FileName, String EncAlgoKey) throws Exception 

      SetMyKey (filename, Pass, PasswordHashAlgo, myRSAKey.toString (),
		KeyHashAlgo, filename, KeyEncryptionAlgo);


  }*/
    }
}

/* 
WriteMyKey.main(String[]){
    Crypto.initRandom ();
    String Pass = "";
    String filename = "test.key";
    String PasswordHashAlgo = "MD5";
    String KeyHashAlgo = "SHA1";
    String KeyEncryptionAlgo = "TriDES";
    KeyPair myRSAKey = RSAKey.createKeys (512);	//Test Dosage only!!
    //public static void SetMyKey(String MyFileName, String MyPassword, String HashAlgoPassword,
    //String KeyString, String HashAlgoKey, String FileName, String EncAlgoKey) throws Exception 
      SetMyKey (filename, Pass, PasswordHashAlgo, myRSAKey.toString (),
		KeyHashAlgo, filename, KeyEncryptionAlgo);
 }
 */
