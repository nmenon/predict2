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

//Source file: Y:/E-Cash-Project/TestBed/development/GetKeys/GetAllKeys.java

package GetKeys;

import utils.crypto.*;
import utils.misc.*;

//package org.logi.crypto.test;
import org.logi.crypto.*;
import org.logi.crypto.sign.*;
import org.logi.crypto.keys.*;
import org.logi.crypto.modes.*;

import java.util.*;
import java.io.*;
import java.lang.*;

/**
 * @author 
 * @version 
 * I am Supposed to get a hash table that contains the FileNames for each IP and Collect allforms of the Info from these files and create another hash Table containing this Info. Phew!!
 */
public class GetAllKeys
{
    private static Hash_Name_IP_And_Key theHash_Name_IP_And_Key;
    private static Hash_File_And_IP theHash_File_And_IP;
    private static HashMesha HashNameIPKey;
    private static KeyPair MyKeyPair;

    private static GetMyKey theGetMyKey;
    private static ReadFileAndGetKey theReadFileAndGetKey;

    public GetAllKeys ()
    {
        theHash_File_And_IP = null;
        theHash_Name_IP_And_Key = null;
        HashNameIPKey = null;
        MyKeyPair = null;
    }

    public static KeyPair getMyKeyPair ()
    {
        return (MyKeyPair);
    }

    public static HashMesha getHashNameIPKey ()
    {
        return (HashNameIPKey);
    }

   /**
    * @param FileList
    * @param Password
    * @param HashAlgoPassword
    * @param HashAlgoKey
    * @return Hash_Name_IP_And_Key
    * @exception Exception
    * @author 
    * @version 
    * So the Great Constructor
    * All I Have to do is allocate the Jobs to every body and make sure that nothin goes wrong
    * By the By I read a hash table containing the IP and filenames 
    * 
    * then return a hash table full of info if everything goes okay!
    * @roseuid 3B8BEC2A0354
    */
    public GetAllKeys (HashMesha HashFileIP, String Password,
                       String HashAlgoPassword,
                       String HashAlgoKey, String EncAlgoKey) throws Exception
    {
        HashMesha IPNameKey;
        KeyPair MyKey;
         theHash_File_And_IP = new Hash_File_And_IP ();
         HashNameIPKey = new HashMesha ();
        if (HashFileIP.get ("my") != null)
          {
              theHash_File_And_IP = (Hash_File_And_IP) HashFileIP.get ("my");
              // Lets Get my key first
              // GetMyKey(FileName,MyPassword,PasswordHashAlgo,KeyHashAlgo,EncKeyAlgo);
              theGetMyKey =
                  new GetMyKey (theHash_File_And_IP.FileName, Password,
                                HashAlgoPassword, HashAlgoKey, EncAlgoKey);
              MyKey = theGetMyKey.getKey ();
              MyKeyPair = MyKey;
              System.out.println ("Got My Key=" + MyKey.toString ());
          }
        else
          {
              throw (new Exception ("Unable to find file Where I Shall Find my own Key"));
          }


        for (Enumeration e = HashFileIP.keys (); e.hasMoreElements ();)
          {
              String myIP = (String) e.nextElement ();

              System.out.println ("Checking: " + myIP);
              if (myIP != "my")
                {
                    try
                    {
                        theHash_File_And_IP = (Hash_File_And_IP) HashFileIP.get (myIP);

                        // Read the File and get results
                        // new ReadFileAndGetKey(Filename,IP);
                        theReadFileAndGetKey =
                            new ReadFileAndGetKey (theHash_File_And_IP.FileName,
                                                   theHash_File_And_IP.IP);

                        // Add to the HashTable representing the Key List
                        System.out.println ("Got From File" +
                                            theHash_File_And_IP.FileName + "Name=" +
                                            theReadFileAndGetKey.getMyName () +
                                            "\nIP=" + theReadFileAndGetKey.getMyIP () +
                                            "\nKey=" + theReadFileAndGetKey.getMyKey ());
                        theHash_Name_IP_And_Key = new Hash_Name_IP_And_Key ();
                        theHash_Name_IP_And_Key.IP = theReadFileAndGetKey.getMyIP ();
                        theHash_Name_IP_And_Key.Name = theReadFileAndGetKey.getMyName ();
                        theHash_Name_IP_And_Key.Key =
                            (CipherKey) theReadFileAndGetKey.getMyKey ();

                        HashNameIPKey.put (myIP, theHash_Name_IP_And_Key);
                    }
                    catch (Exception e1)
                    {
                    }
                }
          }

    }                           // End of GetAllKeys

    public static void main (String[]args) throws Exception
    {

        Hash_File_And_IP a;
        Hash_Name_IP_And_Key b;
        HashMesha c = new HashMesha ();


         Crypto.initRandom ();
        for (int i = 0; i < 10; i++)
          {
              a = new Hash_File_And_IP ();
              a.IP = "192.168.10." + i;
              a.FileName = "TestMe" + i + ".key";

              c.put (a.IP, a);

          }
        a = new Hash_File_And_IP ();

        a.IP = "my";
        a.FileName = "mytestfile.key";

        c.put (a.IP, a);

        // GetAllKeys (HashMesha HashFileIP, String Password,
        // String HashAlgoPassword,
        // String HashAlgoKey,String EncAlgoKey) throws Exception
        GetAllKeys myGet = new GetAllKeys (c, "Hello World", "SHA1", "MD5", "Blowfish");

        HashMesha g = myGet.getHashNameIPKey ();
        KeyPair MyKey = myGet.getMyKeyPair ();

        System.out.println ("FROM MAIN___________________");
        for (Enumeration e = g.keys (); e.hasMoreElements ();)
          {
              String myIP = (String) e.nextElement ();

              System.out.println ("Checking: " + myIP);
              Hash_Name_IP_And_Key p = new Hash_Name_IP_And_Key ();

              p = (Hash_Name_IP_And_Key) g.get (myIP);
              System.out.println ("Name= " + p.Name + "\n IP=" + p.IP + "\n Key=" +
                                  p.Key.toString ());
          }

        System.out.println ("MyKeyPair is ---" + MyKey.toString ());
    }
}
