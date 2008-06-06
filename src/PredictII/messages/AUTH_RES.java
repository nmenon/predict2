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

//Source file: Y:/E-Cash-Project/TestBed/development/messages/AUTH_RES.java

/* This Is a Copy Lefted Software
(CL) 2001 Spetember
By Nishanth 'Lazarus' Menon
Calicut Regional Engineering College
Calicut */

package messages;

import messages.tools.parseMessage;
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
 * Replies with the Result
 * 
 * RESULT
 */
public class AUTH_RES implements Message
{
    private Boolean RESULT;
    public parseMessage theParseMessage;

    public AUTH_RES ()
    {
        RESULT = null;
        theParseMessage = new parseMessage ();
    }

    public AUTH_RES (String Ip) throws Exception
    {
        if (Ip == null)
            throw (new Exception ("Null Paramater Passed"));
        theParseMessage = new parseMessage ();

        String[] s = theParseMessage.parse (Ip);

        if (s.length != 1)
            throw (new Exception (Ip + " Contains " + s.length + " Params while I need 2"));
        if (!(s[0].equals ("0") || s[0].equals ("1")))
            throw (new Exception ("Boolean value accpets only 1/0 - gave" + s[0]));
        RESULT = new Boolean ((s[0].equals ("1")) ? "true" : "false");

    }

   /**
    * @return Boolean
    * @exception 
    * @author 
    * @version 
    * @roseuid 3BA25A3001EB
    */
    public Boolean getRESULT ()
    {
        return RESULT;
    }

    public AUTH_RES (Boolean R) throws Exception
    {
        if (R == null)
            throw (new Exception ("null parameter Passed RES=" + R));
        theParseMessage = new parseMessage ();
        RESULT = R;
    }

   /**
    * @return String
    * @exception 
    * @author 
    * @version 
    * @roseuid 3BA25A68023B
    */
    public String getType ()
    {
        return ("AUTH_RES");
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
    * @return boolean
    * @exception Exception
    * @author 
    * @version 
    * @roseuid 3BA25A680259
    */
    public boolean SetValues (String Ip) throws Exception
    {
        if (Ip == null)
            return false;

        String[] s = theParseMessage.parse (Ip);

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
        AUTH_RES Jada = new AUTH_RES ();

        System.out.println ("The result of set values is " + Jada.SetValues (args[0]));
        Long a = new Long (System.currentTimeMillis ());

        System.out.println ("The Values are \n RESULT=" + Jada.getRESULT ());

        Long b = new Long (System.currentTimeMillis ());
        String Ki = Jada.toString ();

        System.out.println ("Got" + Ki);
        AUTH_RES Jada1 = new AUTH_RES (Ki);

        System.out.println ("The Values are \n RESULT=" + Jada1.getRESULT ());
        Long c = new Long (System.currentTimeMillis ());
        Boolean p = new Boolean ("true");
        AUTH_RES Jada21 = new AUTH_RES (p);

        System.out.println ("The Values are \n RESULT=" + Jada21.getRESULT ());
        System.out.println (Jada21 + "\n" + Jada1 + "\n" + Jada);
    }
}
