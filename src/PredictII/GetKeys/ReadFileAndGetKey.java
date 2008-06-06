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

//Source file: Y:/E-Cash-Project/TestBed/development/GetKeys/ReadFileAndGetKey.java

package GetKeys;

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
 * Given a File this Is Supposed to return the Set IP,Name and Public Key of the Client
 */
public class ReadFileAndGetKey
{
    private static String MyIP;
    private static Key MyKey;
    private static String MyName;
    public static StringFileRead theStringFileRead;


    public ReadFileAndGetKey ()
    {
        MyIP = null;
        MyKey = null;
        MyName = null;
    }


    // The Functions that Return the Values
    public static Key getMyKey ()
    {
        return (MyKey);
    }

    public static String getMyIP ()
    {
        return (MyIP);
    }
    public static String getMyName ()
    {
        return (MyName);
    }

   /**
    * @param IP
    * @return boolean
    * @exception 
    * @author 
    * @version 
    * Hey Other Wise How am I to Be sure whether the File I am reading Contains the Key I want?
    * @roseuid 3B8BFA83003F
    */
    private boolean CheckIP (String IP)
    {
        return ((MyIP == null) ? false : MyIP.equals (IP));
    }

   /**
    * @param FileName
    * @param MyName
    * @return KeyAdapter
    * @exception Exception
    * @author 
    * @version 
    * _______ConStructor
    * I Am Supposed to read the file and return the Info as required.
    * @roseuid 3B8BF14401F4
    */
    ReadFileAndGetKey (String FileName, String MyIP) throws Exception
    {
        // Read From the File
        theStringFileRead = new StringFileRead (FileName);
        String Name = theStringFileRead.readln ().trim ();
        String IP = theStringFileRead.readln ().trim ();
        String publicKey = theStringFileRead.readln ().trim ();
         theStringFileRead.close ();

        // Check for the Validity
        if (!IP.equals (MyIP))
             throw (new
                    Exception ("File " + FileName + "Contains IP=" + IP +
                               " But request was for " + MyIP));
        // Check Conversion of the Key
         MyKey = (Key) (Crypto).fromString (publicKey);
         MyName = Name;
         this.MyIP = IP;

    }
    public static void main (String[]args) throws Exception
    {
        String Filename = "TestMe0.key";
        String Name = "Mycomputer0.srlab";
        String IP = "192.168.10.0";
        ReadFileAndGetKey f = new ReadFileAndGetKey (Filename, IP);
         System.out.println ("Name=" + f.getMyName () + " IP=" + f.getMyIP () +
                             "Key=" + f.getMyKey ().toString ());

    }
}
