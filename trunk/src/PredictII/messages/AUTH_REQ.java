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

//Source file: Y:/E-Cash-Project/TestBed/development/messages/AUTH_REQ.java

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
 * The Authentication Request
 * Is Usually done In a INsecure Mode- So We may have only the public Key being send
 * 
 * PUBLIC_KEY
 */
public class AUTH_REQ implements Message
{
    private String Public_key;
    public parseMessage theParseMessage;

    public AUTH_REQ ()
    {
    }
    public AUTH_REQ (String Ip) throws Exception
    {
        theParseMessage = new parseMessage ();
        String[] a = theParseMessage.parse (Ip);
        if (a.length != 1)
            throw (new
                   Exception (Ip + " Contains " + Ip.length () + " While I need 1 to work"));
        Public_key = a[0];
        if (Public_key == null)
            throw (new Exception ("Null Value in Input Public_Key=" + Public_key));
    }

   /**
    * @return String
    * @exception 
    * @author 
    * @version 
    * @roseuid 3BA25A5501C6
    */
    public String getType ()
    {
        return ("AUTH_REQ");
    }
    public String toString ()
    {
        if (Public_key == null)
            return null;
        Vector D = new Vector ();

        D.add (Public_key);
        String s;

        try
        {
            s = theParseMessage.generate (D);
        }
        catch (Exception e)
        {
            return (null);
        }
        return (s);

    }

   /**
    * @param inputString
    * @return boolean
    * @exception Exception
    * @author 
    * @version 
    * @roseuid 3BA25A5501E4
    */
    public boolean SetValues (String Ip) throws Exception
    {
        theParseMessage = new parseMessage ();
        String[] a = theParseMessage.parse (Ip);
        if (a.length != 1)
            return false;
        Public_key = a[0];
        if (Public_key == null)
            return false;
        return true;
    }

   /**
    * @return String
    * @exception 
    * @author 
    * @version 
    * @roseuid 3BA25D4501DB
    */
    public String getPublic_Key ()
    {
        return Public_key;
    }
    public static void main (String[]args) throws Exception
    {
        // Reads the Message as a single string delimited with :::
        if (args.length < 1)
          {
              System.out.println (" Arguments not enough");
              System.exit (1);
          }
        AUTH_REQ Jada = new AUTH_REQ ();

        System.out.println ("The result of set values is " + Jada.SetValues (args[0]));
        System.out.println ("The Values are \n IDENTIFIER=" + Jada.getPublic_Key ());

        String Ki = Jada.toString ();

        System.out.println ("Got" + Ki);

        AUTH_REQ Jada1 = new AUTH_REQ (Ki);

        System.out.println ("The Values are \n Public_Key=" + Jada1.getPublic_Key ());
        AUTH_REQ Jada21 = new AUTH_REQ ("awsieqwj89712jkhasd78234jkas");

        System.out.println ("The Values are \n Public_Key=" + Jada21.getPublic_Key ());
        System.out.println (Jada21 + "\n" + Jada1 + "\n" + Jada);
    }
}
