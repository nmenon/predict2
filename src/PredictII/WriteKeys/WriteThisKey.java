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

//Source file: Y:/E-Cash-Project/TestBed/development/WriteKeys/WriteThisKey.java

package WriteKeys;

import utils.crypto.*;
import utils.misc.*;
import org.logi.crypto.*;
import org.logi.crypto.keys.*;

/**
 * @author 
 * @version 
 */
public class WriteThisKey
{
    private static String myIP;
    private static String myName;
    private static String myKey;
    private static String myFileName;
    public static StringFileWrite theStringFileWrite;

    public WriteThisKey ()
    {
    }

   /**
    * @param IP
    * @param Name
    * @param Key
    * @param filename
    * @return void
    * @exception Exception
    * @author 
    * @version 
    * @roseuid 3B927AE702D0
    */
    public static void SetMyKey (String IP, String Name, String Key,
                                 String filename) throws Exception
    {
        myName = Name;
        myIP = IP;
        myFileName = filename;
        myKey = Key;
        if ((myName == null))
            throw (new Exception ("Name input is null "));
        if ((myIP == null))
            throw (new Exception ("IP input is null "));
        if ((myFileName == null))
            throw (new Exception ("FileName input is null "));
        if ((myKey == null))
            throw (new Exception ("Key input is null "));

        theStringFileWrite = new StringFileWrite (myFileName);
        theStringFileWrite.println (myName);
        theStringFileWrite.println (myIP);
        theStringFileWrite.println (myKey);
        theStringFileWrite.close ();
    }

    public static void main (String[]args) throws Throwable
    {
        Crypto.initRandom ();
        String filename = "test.key";
        String IP = "192.168.10.1";
        String Name = "Skylab.Srlab";
        Key publicKey;
        KeyPair myRSAKey = RSAKey.createKeys (512); // Test Dosage only!!
         publicKey = myRSAKey.getPublic ();
         SetMyKey (IP, Name, publicKey.toString (), filename);


    }
}

/* 
WriteThisKey.writeMe(){
   }
 */
