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

public class GenerateRAK extends Crypto
{

    public String IDENTIFIER;
    public String K1;
    public String K2;
    public String HashK1K2;
    public String RAK;
    public static String MODE;

    /** Access Function*/
    public String getIDENTIFIER ()
    {
        return IDENTIFIER;
    }

    /** Access Function*/
    public String getK1 ()
    {
        return K1;
    }

    /** Access Function*/
    public String getK2 ()
    {
        return K2;
    }

    /** Access Function*/
    public String getHashK1K2 ()
    {
        return HashK1K2;
    }

    /** Access Function*/
    public String getRAK ()
    {
        return RAK;
    }

    /** Access Function*/
    public void setIDENTIFIER (String a) throws Exception
    {
        if (a == null)
            throw (new Exception ("Null in Parameter"));
        IDENTIFIER = a;
    }

    /** Access Function*/
    public void setK1 (String a) throws Exception
    {
        if (a == null)
            throw (new Exception ("Null in Parameter"));
        K1 = a;
    }

    /** Access Function*/
    public void setK2 (String a) throws Exception
    {
        if (a == null)
            throw (new Exception ("Null in Parameter"));
        K2 = a;
    }

    /** Access Function*/
    public void setHashK1K2 (String a) throws Exception
    {
        if (a == null)
            throw (new Exception ("Null in Parameter"));
        HashK1K2 = a;
    }

    /** Access Function*/
    public void setRAK (String a) throws Exception
    {
        if (a == null)
            throw (new Exception ("Null in Parameter"));
        RAK = a;
    }

    private static Key createKeys (String keyType, String initiator) throws Exception
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

    /** Encrypting fn for the rest of the world*/
    private String EncHash (CipherKey K1, String IDENTIFIER, String HashAlgo) throws Exception
    {
        encrypt t = new encrypt ();

        // String g = t.encrypt (IDENTIFIER, K1, MODE);

        // StringFileWrite s= new
        // StringFileWrite("a"+System.currentTimeMillis(),false);
        // s.println(g);
        // s.close();

        String H = hash.hash (K1.toString (), HashAlgo);

        // System.out.println("-+EncHashCalled Returns - "+IDENTIFIER+" was
        // Encrypted by and Hashed with"+HashAlgo+" Key used is "+K1+"
        // Gives++--++"+H);

         return (H);

    }

    /** The Algo For the Encryption. if the Values are not set- throws an Exception*/
    public void generate (String EncType, String HashType) throws Exception
    {
        if (EncType == null || HashType == null)
            throw (new Exception ("Null in Parameters"));
        if (IDENTIFIER == null)
            throw (new Exception ("Value Not Set"));
        if (MODE == null)
            MODE = "ECB";
        // encrypt t = new encrypt ();
        // String g = t.encrypt (IDENTIFIER, K, "OFB");
        java.util.Random rand = new java.util.Random ();
        int size = 20;
         byte[] plain = new byte[size];
         rand.nextBytes (plain);
        String Rplain = StringOpt.byteToString (plain);

        CipherKey Kr1 = (CipherKey) createKeys (EncType, Rplain);
         RAK = EncHash (Kr1, IDENTIFIER, HashType); // hash.hash 
        // RAK = "RAK&^***IUIUIKKJJJ***KJKKPP";
        // (g,
        // HashType);
        // System.out.println ("------" + RAK);
         RAK = RAK.trim ();
        // Create Key blocks
        String j = Kr1.toString ();
        int l = (int) j.length () / 2;

         K1 = j.substring (0, l);
         K2 = j.substring (l, j.length ());
         HashK1K2 = hash.hash (j, HashType);
        // System.out.println("++++HASHK1K2="+HashK1K2);
    }
    public void generate (String IDENTIFIER, String EncType, String HashType) throws Exception
    {
        this.IDENTIFIER = IDENTIFIER;
        generate (EncType, HashType);
    }

    /** The Algo For the DEcryption. if the Values are not set- throws an Exception*/
    public boolean check (String HashType) throws Exception
    {
        if (K1 == null || K2 == null || IDENTIFIER == null || HashK1K2 == null || RAK == null)
            throw (new Exception ("Value not Set"));
        if (HashType == null)
            throw (new Exception ("Null in Parameter"));
        // Check Hash
        String j = K1 + K2;
        CipherKey Key = (CipherKey) Crypto.fromString (j);
        String MyHash = hash.hash (j, HashType);
        if (!MyHash.equals (HashK1K2))
             return false;
        // Start the Hashing
        String g = EncHash (Key, IDENTIFIER, HashType); // hash.hash 
        // (g,
        // HashType);
        // System.out.println ("----" + g);
         g = g.trim ();
        if (g.equals (RAK))
             return true;
         return false;
    }
    public boolean check (String IDENTIFIER, String K1, String K2, String RAK,
                          String Hash_k1_k2, String HashType) throws Exception
    {
        this.IDENTIFIER = IDENTIFIER;
        this.K1 = K1;
        this.K2 = K2;
        this.RAK = RAK;
        this.HashK1K2 = Hash_k1_k2;
        return check (HashType);
    }

    /** Constructor*/
    public GenerateRAK ()
    {
        initRandom ();
        IDENTIFIER = null;
        K1 = null;
        K2 = null;
        RAK = null;
        HashK1K2 = null;
    }

    public static void main (String[]args) throws Exception
    {
        if (args.length == 1)
            MODE = args[0];
        GenerateRAK j = new GenerateRAK ();
         j.setIDENTIFIER ("Hello ;;;Wolrd;;;");
        long S1 = System.currentTimeMillis ();
         j.generate ("TriDES", "SHA1");
        long S2 = System.currentTimeMillis ();
        boolean ch = j.check ("SHA1");
        long S3 = System.currentTimeMillis ();
        long k = S2 - S1;
        long m = S3 - S2;
         System.out.println ("Result=" + ch + "Time=" + k + "   " + m);
    }
}
