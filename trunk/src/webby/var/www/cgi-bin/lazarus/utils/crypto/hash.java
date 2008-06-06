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

//Source file: Y:/E-Cash-Project/TestBed/CryptoGraphy/Crypt/development/hash.java

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
 *  I am Supposed to hash according to any standard hashing algo -I support only MD5 and SHA1
 */
public class hash
{
    private static StringOpt theStringOpt;

   /**
    * @param StringToHash
    * @param algorithm
    * @return Fingerprint
    * @exception Exception
    * @author 
    * @version
    * @roseuid 3B8BD4A40194
    */
    private static Fingerprint hashIt (String StringToHash, String algorithm) throws Exception
    {
        HashState state = HashState.create (algorithm);

        int BlockSize = state.blockSize ();
        int lengMessg = StringToHash.length ();
         byte[] mbuf = theStringOpt.StringToByte (StringToHash, lengMessg);
        int i = 0;
        int j = 0;
         byte[] buf = new byte[BlockSize];

        while (i < lengMessg)
          {
              for (j = i; (j < i + BlockSize) && (j < lengMessg); j++)
                  buf[j - i] = mbuf[j];
              i = j;
              // System.out.println ("I=" + i);
              state.update (buf, 0, BlockSize);
          }

        // System.out.println ("Length=" + lengMessg + " last=" + i);

        /* 
         * int n=in.read(buf); while (n!=-1) { state.update(buf,0,n);
         * n=in.read(buf); } */
        return (state.calculate ());
    }

   /**
    * @param StringToHash
    * @return Fingerprint
    * @exception Exception
    * @author 
    * @version
    * @roseuid 3B8BD4A40197
    */
    private static Fingerprint hashIt (String StringToHash) throws Exception
    {
        return (hashIt (StringToHash, "SHA1"));
    }

   /**
    * @param msg
    * @return void
    * @exception 
    * @author 
    * @version
    * @roseuid 3B8BD4A4018A
    */
    public static void help (String msg)
    {
        if (msg != null)
            System.err.println (msg);
        System.err.println ();
        System.err.println ("use: java hash  <algorithm> String");
        System.err.println ("     algorithm ::= MD5|SHA1");
        System.err.println ("The default hash function is SHA1");
        System.err.println ();
        System.exit (1);
    }

   /**
    * @param arg[]
    * @return void
    * @exception Exception
    * @author 
    * @version
    * @roseuid 3B8BD4A40199
    */
    public static void main (String arg[]) throws Exception
    {
        System.out.println (" hi the Hash is " + hash ("Hello there", "MD5"));
    }

   /**
    * @param Message
    * @return String
    * @exception 
    * @author 
    * @version
    * @roseuid 3B8C005A0020
    */
    public static String hash (String Message) throws Exception
    {
        Crypto.initRandom ();

        String Test = "";
         return (hashIt (Message).toString ());
    }

   /**
    * @param Message
    * @param HashAlgo
    * @return String
    * @exception 
    * @author 
    * @version
    * @roseuid 3B8BD4A40189
    */
    public static String hash (String Message, String HashAlgo) throws Exception
    {
        Crypto.initRandom ();

        String Test = "";
         return (hashIt (Message, HashAlgo).toString ());
    }
    public static void hash ()
    {
    }
}
