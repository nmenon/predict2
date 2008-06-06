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

//Source file: Y:/E-Cash-Project/TestBed/development/messages/TRAN_COMP.java

/* This Is a Copy Lefted Software
(CL) 2001 Spetember
By Nishanth 'Lazarus' Menon
Calicut Regional Engineering College
Calicut */

package messages;

import java.lang.*;
import org.logi.crypto.*;
import utils.misc.*;
import utils.crypto.*;
import messages.tools.*;
import messages.*;

/**
 * @author 
 * @version 
 * This Message is Either from
 * ZoneServer_Merchant -> Merchant
 * OR
 * ZoneServer_Citizen -> Citizen
 * This Message is send when both the Citizen and the <Merchant Zone Servers have completed transaction
 * 
 * IDENTIFIER
 */
public class TRAN_COMP implements Message
{
    private String IDENTIFIER;
    public parseMessage theParseMessage;

   /**
    * @return 
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A048A02D5
    */
    public TRAN_COMP ()
    {
        IDENTIFIER = null;
        theParseMessage = new parseMessage ();
    }

    public TRAN_COMP (String inputString) throws Exception
    {
        theParseMessage = new parseMessage ();
        if (inputString == null)
            throw (new Exception ("Null pArameter Passed"));
        // return false;
        String[] k = theParseMessage.parse (inputString);
        if (k.length != 1)
            throw (new Exception (inputString + " has " + k.length + " while I need only 1"));
        IDENTIFIER = k[0];
    }

    public String getIDENTIFIER ()
    {
        return IDENTIFIER;
    }

   /**
    * @return String
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A17BB0166
    */
    public String getType ()
    {
        return ("TRAN_COMP");
    }
    public String toString ()
    {
        return IDENTIFIER;
    }

   /**
    * @param inputString
    * @return boolean
    * @exception Exception
    * @author 
    * @version 
    * @roseuid 3B9A221200FE
    */
    public boolean SetValues (String inputString) throws Exception
    {
        if (inputString == null)
            // throw (new Exception ("Null pArameter Passed"));
            return false;
        String[] k = theParseMessage.parse (inputString);
        if (k.length != 1)
            return false;
        IDENTIFIER = k[0];
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
        TRAN_COMP Jada = new TRAN_COMP ();

        System.out.println ("The result of set values is " + Jada.SetValues (args[0]));
        System.out.println ("The Values are \n IDENTIFIER=" + Jada.getIDENTIFIER ());

        String Ki = Jada.toString ();

        System.out.println ("Got" + Ki);

        TRAN_COMP Jada1 = new TRAN_COMP (Ki);

        System.out.println ("The Values are \n IDENTIFIER=" + Jada1.getIDENTIFIER ());
        TRAN_COMP Jada21 = new TRAN_COMP ("awsieqwj89712jkhasd78234jkas");

        System.out.println ("The Values are \n IDENTIFIER=" + Jada21.getIDENTIFIER ());
        System.out.println (Jada21 + "\n" + Jada1 + "\n" + Jada);
    }
}
