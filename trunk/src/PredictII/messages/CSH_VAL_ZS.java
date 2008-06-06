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

//Source file: Y:/E-Cash-Project/TestBed/development/messages/CSH_VAL_ZS.java

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
 * ZoneServer_Merchant -> MasterServer_Merchant
 * This Message is to Validate the cash token
 * 
 * K1, Hash of (K1+K2), IDENTIFIER
 */
public class CSH_VAL_ZS implements Message
{
    private String K1;
    private String HASH_K1_K2;
    private String IDENTIFIER;
    public parseMessage theParseMessage;

   /**
    * @return 
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A04650173
    */
    public CSH_VAL_ZS ()
    {
        theParseMessage = new parseMessage ();
        K1 = null;
        HASH_K1_K2 = null;
        IDENTIFIER = null;
    }
    public CSH_VAL_ZS (String inputString) throws Exception
    {

        theParseMessage = new parseMessage ();
        if (inputString == null)
            throw (new Exception ("null Parameter passed"));
        // System.out.println (inputString);
        String[] a;
        a = theParseMessage.parse (inputString);
        if (a.length != 3)
            throw (new
                   Exception (inputString + " contains " + a.length +
                              " Parameters While I need 3"));
        K1 = a[0];
        HASH_K1_K2 = a[1];
        IDENTIFIER = a[2];
    }
    public CSH_VAL_ZS (String K, String H, String I) throws Exception
    {
        if (K == null || H == null || I == null)
            throw (new Exception ("Null Parameter passed K=" + K + " H=" + H + " I=" + I));
        K1 = K;
        HASH_K1_K2 = H;
        IDENTIFIER = I;
        theParseMessage = new parseMessage ();
    }

   /**
    * @return String
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A16550049
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
    * @roseuid 3B9A1662032C
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
    * @roseuid 3B9A16700304
    */
    public String getIDENTIFIER ()
    {
        return IDENTIFIER;
    }

   /**
    * @return String
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A17C100CE
    */
    public String getType ()
    {
        return ("CSH_VAL_ZS");
    }
    public String toString ()
    {
        if (IDENTIFIER == null || HASH_K1_K2 == null || K1 == null)
            return null;

        Vector D = new Vector ();

        D.add (K1);
        D.add (HASH_K1_K2);
        D.add (IDENTIFIER);
        String d;

        try
        {
            d = theParseMessage.generate (D);
        }
        catch (Exception e)
        {
            return null;
        }
        return d;
    }

   /**
    * @param inputString
    * @return boolean
    * @exception Exception
    * @author 
    * @version 
    * @roseuid 3B9A22180189
    */
    public boolean SetValues (String inputString) throws Exception
    {

        if (inputString == null)
            return false;
        String[] a = theParseMessage.parse (inputString);
        if (a.length != 3)
            return false;
        K1 = a[0];
        HASH_K1_K2 = a[1];
        IDENTIFIER = a[2];
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
        CSH_VAL_ZS Jada = new CSH_VAL_ZS ();

        System.out.println ("The result of set values is " + Jada.SetValues (args[0]));
        Long a = new Long (System.currentTimeMillis ());

        System.out.println ("The Values are \n K1=" + Jada.getK1 () +
                            "\n HASH_K1_K2=" + Jada.getHASH_K1_K2 () +
                            "\n IDENTIFIER=" + Jada.getIDENTIFIER ());

        Long b = new Long (System.currentTimeMillis ());
        String Ki = Jada.toString ();

        System.out.println ("Got" + Ki);
        CSH_VAL_ZS Jada1 = new CSH_VAL_ZS (Ki);

        System.out.println ("The Values are \n K1=" + Jada1.getK1 () +
                            "\n HASH_K1_K2=" + Jada1.getHASH_K1_K2 () +
                            "\n IDENTIFIER=" + Jada1.getIDENTIFIER ());
        Long c = new Long (System.currentTimeMillis ());
        CSH_VAL_ZS Jada21 = new CSH_VAL_ZS (c.toString (), b.toString (), a.toString ());

        System.out.println ("The Values are \n K1=" + Jada21.getK1 () +
                            "\n HASH_K1_K2=" + Jada21.getHASH_K1_K2 () +
                            "\n IDENTIFIER=" + Jada21.getIDENTIFIER ());
        System.out.println (Jada21 + "\n" + Jada1 + "\n" + Jada);
    }
}
