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

//Source file: Y:/E-Cash-Project/TestBed/development/WriteKeys/WriteKeys.java

package WriteKeys;

import utils.crypto.*;
import utils.misc.*;
import org.logi.crypto.*;
import org.logi.crypto.keys.*;

import java.util.*;
import java.lang.*;

/**
 * @author 
 * @version 
 * This Reads the Hash Tables and Writes them into the respective Files
 */
public class DoWriteKeys
{
    private Hash_File_And_IP theHash_File_And_IP;
    private Hash_Name_IP_And_Key theHash_Name_IP_And_Key;
    private WriteMyKey theWriteMyKey;
    public WriteThisKey theWriteThisKey;
    public static HashMesha theHashMesha[];

    public DoWriteKeys ()
    {
    }
    public DoWriteKeys (HashMesha HashFileIP, HashMesha HashNameIPKey,
                        String myPassword, String passwordHashAlgo, KeyPair K,
                        String keyHashAlgo, String keyEncryptionAlgo) throws Exception
    {
        Crypto.initRandom ();

        theHash_File_And_IP = new Hash_File_And_IP ();;


        if ((HashFileIP.get ("my") != null) && (HashNameIPKey.get ("my") != null))
          {
              theWriteMyKey = new WriteMyKey ();
              theHash_File_And_IP = (Hash_File_And_IP) HashFileIP.get ("my");
              String filename = theHash_File_And_IP.FileName;

               theWriteMyKey.SetMyKey (filename, myPassword, passwordHashAlgo,
                                       K.toString (), keyHashAlgo, filename,
                                       keyEncryptionAlgo);
          }

        // the
        // SetMyKey (filename, Pass, PasswordHashAlgo, myRSAKey.toString (),
        // KeyHashAlgo, filename, KeyEncryptionAlgo);

        for (Enumeration e = HashFileIP.keys (); e.hasMoreElements ();)
          {
              String myIP = (String) e.nextElement ();

              System.out.println ("Checking: " + myIP);
              if (myIP != "my")
                {
                    System.out.println ("Starting to write " + myIP);
                    theWriteThisKey = new WriteThisKey ();


                    theHash_File_And_IP = (Hash_File_And_IP) HashFileIP.get (myIP);
                    theHash_Name_IP_And_Key = (Hash_Name_IP_And_Key) HashNameIPKey.get (myIP);

                    theWriteThisKey.SetMyKey (myIP, theHash_Name_IP_And_Key.Name,
                                              theHash_Name_IP_And_Key.Key.toString (),
                                              theHash_File_And_IP.FileName);
                    // System.out.println(e.nextElement());
                }
          }

    }
    public static void main (String[]args) throws Throwable
    {
        Hash_File_And_IP a;
        Hash_Name_IP_And_Key b;
        HashMesha c = new HashMesha ();
        HashMesha d = new HashMesha ();
         Crypto.initRandom ();
        for (int i = 0; i < 10; i++)
          {
              a = new Hash_File_And_IP ();
              a.IP = "192.168.10." + i;
              a.FileName = "TestMe" + i + ".key";

              b = new Hash_Name_IP_And_Key ();
              b.IP = a.IP;
              b.Name = "Mycomputer" + i + ".srlab";

              KeyPair myRSAKey = RSAKey.createKeys (256);   // Test Dosage
              // only!!

               b.Key = (CipherKey) myRSAKey.getPublic ();

               c.put (a.IP, a);
               d.put (b.IP, b);
          }
        a = new Hash_File_And_IP ();

        a.IP = "my";
        a.FileName = "mytestfile.key";

        b = new Hash_Name_IP_And_Key ();
        b.IP = a.IP;
        b.Name = "Mir.srlab";

        KeyPair myRSAKey = RSAKey.createKeys (256); // Test Dosage only!!


        c.put (a.IP, a);
        d.put (b.IP, b);
        DoWriteKeys JoJo = new DoWriteKeys (c, d, "a1b2c3", "MD5", myRSAKey, "SHA1",
                                            "Blowfish");

    }
}
