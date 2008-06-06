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

//Source file: Y:/E-Cash-Project/TestBed/development/messages/REQ_REP.java

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
 * This Is the Reply for the Request Amount from the Citizen -> Merchant
 * 
 * AMOUNT
 */
public class REQ_REP implements Message
{
    private Float AMOUNT;
    public parseMessage theParseMessage;

   /**
    * @return 
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A0408004C
    */
    public REQ_REP ()
    {
        AMOUNT = null;
        theParseMessage = new parseMessage ();
    }

    public REQ_REP (String Ip) throws Exception
    {
        if (Ip == null)
            throw (new Exception ("Null value in parameter AMOUNT"));

        String[] g;
        theParseMessage = new parseMessage ();

        g = theParseMessage.parse (Ip);
        if (g.length != 1)
            throw (new Exception (Ip + " Contains " + g.length + " Params while I need 1"));
        AMOUNT = new Float (g[0]);

    }

    public REQ_REP (Float A) throws Exception
    {
        if (A == null)
            throw (new Exception ("Null value in parameter AMOUNT"));
        AMOUNT = A;
        theParseMessage = new parseMessage ();
    }


   /**
    * @return Float
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A141602EB
    */
    public Float getAMOUNT ()
    {
        return AMOUNT;
    }

   /**
    * @return String
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A17B502C6
    */
    public String getType ()
    {
        return ("REQ_REP");
    }
    public String toString ()
    {
        if (AMOUNT == null)
            return null;
        Vector D = new Vector ();

        D.add (AMOUNT);
        String g;

        try
        {
            g = theParseMessage.generate (D);
        }
        catch (Exception e)
        {
            return null;
        }
        return g;
    }

   /**
    * @param inputString
    * @return boolean
    * @exception Exception
    * @author 
    * @version 
    * @roseuid 3B9A21EB031E
    */
    public boolean SetValues (String Ip) throws Exception
    {
        if (Ip == null)
            return false;
        String[] g;

        try
        {
            g = theParseMessage.parse (Ip);
        }
        catch (Exception e)
        {
            return false;
        }
        if (g.length != 1)
            return false;
        AMOUNT = new Float (g[0]);
        return true;
    }
    public static void main (String[]args) throws Exception
    {
        REQ_REP Jada = new REQ_REP ();
         Jada.SetValues (args[0]);
        // Reads the Message as a single string delimited with :::
        if (args.length < 1)
          {
              System.out.println (" Arguments not enough");
              System.exit (1);
          }
        System.out.println ("The Values are\n AMOUNT=" + Jada.getAMOUNT ());
        REQ_REP Jada1 = new REQ_REP (Jada.toString ());

        System.out.println ("The Values are\n AMOUNT=" + Jada1.getAMOUNT ());
        Float f = new Float (1231231098868997698769823.1231231);
        REQ_REP Jada21 = new REQ_REP (f);

        System.out.println ("The Values are\n AMOUNT=" + Jada21.getAMOUNT ());
        System.out.println (Jada21 + "\n" + Jada1 + "\n" + Jada);
    }
}
