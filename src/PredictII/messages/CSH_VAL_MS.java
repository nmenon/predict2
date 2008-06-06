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

//Source file: Y:/E-Cash-Project/TestBed/development/messages/CSH_VAL_MS.java

/* This Is a Copy Lefted Software
(CL) 2001 Spetember
By Nishanth 'Lazarus' Menon
Calicut Regional Engineering College
Calicut */

package messages;

import java.lang.*;
import java.util.*;
import org.logi.crypto.*;
import utils.misc.*;
import utils.crypto.*;
import messages.tools.*;
import messages.*;

/**
 * @author 
 * @version 
 * This Message is from
 * MasterServer_Merchant -> MasterServer_Citizen
 * This message basically sends the validation request to the Masterserver
 * 
 * K1, Hash of (K1+K2)
 */
public class CSH_VAL_MS implements Message
{
    private String K1;
    private String HASH_K1_K2;
    public parseMessage theParseMessage;

   /**
    * @return 
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A046A0260
    */
    public CSH_VAL_MS ()
    {
        K1 = null;
        HASH_K1_K2 = null;
        theParseMessage = new parseMessage ();
    }
    public CSH_VAL_MS (String inputString) throws Exception
    {

        theParseMessage = new parseMessage ();
        if (inputString == null)
            throw (new Exception ("null Parameter passed"));
        // System.out.println (inputString);
        String[] a;
        a = theParseMessage.parse (inputString);
        if (a.length != 2)
            throw (new
                   Exception (inputString + " contains " + a.length +
                              " Parameters While I need 2"));
        K1 = a[0];
        HASH_K1_K2 = a[1];
    }
    public CSH_VAL_MS (String K, String H) throws Exception
    {
        if (K == null || H == null)
            throw (new Exception ("Null Parameter passed K=" + K + " H=" + H));
        K1 = K;
        HASH_K1_K2 = H;
        theParseMessage = new parseMessage ();

    }

   /**
    * @return String
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A16800127
    */
    public String getK1 ()
    {
        return K1;
    }

   /**
    * @return String
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A168B01C3
    */
    public String getHASH_K1_K2 ()
    {
        return HASH_K1_K2;
    }

   /**
    * @return String
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A17BF02AC
    */
    public String getType ()
    {
        return ("CSH_VAL_MS");
    }
    public String toString ()
    {
        if (K1 == null || HASH_K1_K2 == null)
            return null;
        Vector D = new Vector ();

        D.add (K1);
        D.add (HASH_K1_K2);
        String G;

        try
        {
            G = theParseMessage.generate (D);
        }
        catch (Exception e)
        {
            return null;
        }
        return (G);
    }

   /**
    * @param inputString
    * @return boolean
    * @exception Exception
    * @author 
    * @version 
    * @roseuid 3B9A22160348
    */
    public boolean SetValues (String inputString) throws Exception
    {

        if (inputString == null)
            return false;
        String[] a = theParseMessage.parse (inputString);
        if (a.length != 2)
            return false;
        K1 = a[0];
        HASH_K1_K2 = a[1];
        return true;
    }
    public static void main (String[]args) throws Exception
    {
        // Reads the Message as a single string delimited with :::
        if (args.length < 1)
          {
              System.out.println (" Arguments not enough");
              System.exit (1);
          }
        CSH_VAL_MS Jada = new CSH_VAL_MS ();

        System.out.println ("The result of set values is " + Jada.SetValues (args[0]));
        Long a = new Long (System.currentTimeMillis ());

        System.out.println ("The Values are \n K1=" + Jada.getK1 () +
                            "\n HASH_K1_K2=" + Jada.getHASH_K1_K2 ());

        Long b = new Long (System.currentTimeMillis ());
        String Ki = Jada.toString ();

        System.out.println ("Got" + Ki);
        CSH_VAL_MS Jada1 = new CSH_VAL_MS (Ki);

        System.out.println ("The Values are \n K1=" + Jada1.getK1 () +
                            "\n HASH_K1_K2=" + Jada1.getHASH_K1_K2 ());
        Long c = new Long (System.currentTimeMillis ());
        CSH_VAL_MS Jada21 =
            new CSH_VAL_MS (c.toString () + "_" + b.toString (), a.toString ());
        System.out.println ("The Values are \n K1=" + Jada21.getK1 () +
                            "\n HASH_K1_K2=" + Jada21.getHASH_K1_K2 ());
        System.out.println (Jada21 + "\n" + Jada1 + "\n" + Jada);
    }
}
