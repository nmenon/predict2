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

//Source file: Y:/E-Cash-Project/TestBed/development/messages/AUTH_USER.java

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
 * THis Authenticates using the Message Info of the PassPhrase
 * 
 * PASSPHRASE
 */
public class AUTH_USER implements Message
{
    private String PASSPHRASE;
    private String UserName;
    public parseMessage theParseMessage;

    public AUTH_USER ()
    {
        // System.out.println ("##########Init NUllCalled");
        theParseMessage = new parseMessage ();
        PASSPHRASE = null;
        UserName = null;
    }
    public AUTH_USER (String inputString) throws Exception
    {
        theParseMessage = new parseMessage ();
        // System.out.println ("##########Init " + inputString + " Called");
        if (inputString == null)
            throw (new Exception ("Null pArameter Passed"));
        // return false;
        String[] k = theParseMessage.parse (inputString);
        if (k.length != 2)
            throw (new Exception (inputString + " has " + k.length + " while I need only 1"));
        UserName = k[0];
        PASSPHRASE = k[1];
    }

    public AUTH_USER (String UserName, String PassPhrase) throws Exception
    {
        // System.out.println ("##########Init " + UserName + PassPhrase + "
        // Called");
        if (UserName == null || PassPhrase == null)
            throw (new
                   Exception ("Null parameter Username=" + UserName +
                              " PassPhrase=" + PassPhrase));
        this.UserName = UserName;
        PASSPHRASE = PassPhrase;
        theParseMessage = new parseMessage ();
    }

   /**
    * @return String
    * @exception 
    * @author 
    * @version 
    * @roseuid 3BA259B300C8
    */
    public String getPASSPHRASE ()
    {
        return PASSPHRASE;
    }

    public String getUserName ()
    {
        return UserName;
    }

   /**
    * @param PASS
    * @return void
    * @exception 
    * @author 
    * @version 
    * @roseuid 3BA259D00305
    */
    public void setPASSPHRASE (String PASS)
    {
        PASSPHRASE = PASS;
    }

    public void setUserName (String User)
    {
        UserName = User;
    }

   /**
    * @return String
    * @exception 
    * @author 
    * @version 
    * @roseuid 3BA25A67006D
    */
    public String getType ()
    {
        return ("AUTH_USER");
    }
    public String toString ()
    {
        // System.out.println ("##########So toString Called");
        if (UserName == null || PASSPHRASE == null)
            return null;
        Vector D = new Vector ();

        D.add (UserName);
        D.add (PASSPHRASE);
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
    * @roseuid 3BA25A670081
    */
    public boolean SetValues (String inputString) throws Exception
    {
        if (inputString == null)
            // throw (new Exception ("Null pArameter Passed"));
            return false;
        String[] k = theParseMessage.parse (inputString);
        if (k.length != 2)
            return false;
        UserName = k[0];
        PASSPHRASE = k[1];
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
        AUTH_USER Jada = new AUTH_USER ();

        System.out.println ("The result of set values is " + Jada.SetValues (args[0]));
        System.out.println ("The Values are \n UserName=" +
                            Jada.getUserName () + "\n PassPhrase=" + Jada.getPASSPHRASE ());

        String Ki = Jada.toString ();

        System.out.println ("Got" + Ki);

        AUTH_USER Jada1 = new AUTH_USER (Ki);

        System.out.println ("The Values are \n UserName=" +
                            Jada1.getUserName () + "\n PassPhrase=" + Jada1.getPASSPHRASE ());
        AUTH_USER Jada21 = new AUTH_USER ("Nishanth", "awsieqwj89712jkhasd78234jkas");

        System.out.println ("The Values are \n UserName=" +
                            Jada21.getUserName () + "\n PassPhrase=" +
                            Jada21.getPASSPHRASE ());
        System.out.println (Jada21 + "\n" + Jada1 + "\n" + Jada);
    }
}
