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

//Source file: Y:/E-Cash-Project/TestBed/development/messages/VAL_REP_ZS.java

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
 * MasterServer_Citizen -> ZoneServer_Merchant
 * This Message is the reply of the validation request from the Zone server
 * 
 * RESULT
 */
public class VAL_REP_ZS implements Message
{
    private Boolean RESULT;
    public parseMessage theParseMessage;

   /**
    * @return 
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A04840087
    */
    public VAL_REP_ZS ()
    {
        RESULT = null;
        theParseMessage = new parseMessage ();

    }

    public VAL_REP_ZS (Boolean R) throws Exception
    {
        if (R == null)
            throw (new Exception ("null parameter Passed RES=" + R));
        theParseMessage = new parseMessage ();
        RESULT = R;
    }

    public VAL_REP_ZS (String Ip) throws Exception
    {
        if (Ip == null)
            throw (new Exception ("Null Paramater Passed"));
        theParseMessage = new parseMessage ();

        String[] s = theParseMessage.parse (Ip);

        if (s.length != 1)
            throw (new Exception (Ip + " Contains " + s.length + " Params while I need 1"));
        if (!(s[0].equals ("0") || s[0].equals ("1")))
            throw (new Exception ("Boolean value accpets only 1/0 - gave" + s[0]));
        RESULT = new Boolean ((s[0].equals ("1")) ? "true" : "false");
    }

   /**
    * @return Boolean
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A16BB033E
    */
    public Boolean getRESULT ()
    {
        return RESULT;
    }

   /**
    * @return String
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A17BC0302
    */
    public String getType ()
    {
        return ("VAL_REP_ZS");
    }
    public String toString ()
    {
        if (RESULT == null)
            return null;
        String s = (RESULT.booleanValue ())? "1" : "0";

        Vector D = new Vector ();

        D.add (s);
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
    * @return Boolean
    * @exception Exception
    * @author 
    * @version 
    * @roseuid 3B9A221302F4
    */
    public boolean SetValues (String Ip) throws Exception
    {
        if (Ip == null)
            return false;

        String[] s = theParseMessage.parse (Ip);
        // System.out.println(Ip+" Length"+s.length);
        if (s.length != 1)
            return false;
        if (!(s[0].equals ("0") || s[0].equals ("1")))
            return false;
        RESULT = new Boolean ((s[0].equals ("1")) ? "true" : "false");
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
        VAL_REP_ZS Jada = new VAL_REP_ZS ();

        System.out.println ("The result of set values is " + Jada.SetValues (args[0]));
        System.out.println ("The Values are \n RESULT=" + Jada.getRESULT ());

        String Ki = Jada.toString ();

        System.out.println ("Got" + Ki);
        VAL_REP_ZS Jada1 = new VAL_REP_ZS (Ki);

        System.out.println ("The Values are \n RESULT=" + Jada1.getRESULT ());
        Boolean p = new Boolean ("true");
        VAL_REP_ZS Jada21 = new VAL_REP_ZS (p);

        System.out.println ("The Values are \n RESULT=" + Jada21.getRESULT ());
        System.out.println (Jada21 + "\n" + Jada1 + "\n" + Jada);
    }
}
