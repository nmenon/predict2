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

//Source file: Y:/E-Cash-Project/TestBed/development/messages/TRAN_ABRT.java

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
 * This Message may be created by Either the 
 * 
 * Zone Server or the Citizen / Merchant
 * 
 * ABORT_REASON
 */
public class TRAN_ABRT implements Message
{
    private String ABORT_REASON;
    public parseMessage theParseMessage;

   /**
    * @return 
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A049603C1
    */
    public TRAN_ABRT ()
    {
        ABORT_REASON = null;
        theParseMessage = new parseMessage ();
    }

    public TRAN_ABRT (String inputString) throws Exception
    {
        theParseMessage = new parseMessage ();
        if (inputString == null)
            throw (new Exception ("Null pArameter Passed"));
        // return false;
        String[] k = theParseMessage.parse (inputString);
        if (k.length != 1)
            throw (new Exception (inputString + " has " + k.length + " while I need only 1"));
        ABORT_REASON = k[0];
    }

   /**
    * @return String
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A16380246
    */
    public String getABORT_REASON ()
    {
        return ABORT_REASON;
    }

   /**
    * @return String
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A17B80202
    */
    public String getType ()
    {
        return ("TRAN_ABRT");
    }
    public String toString ()
    {
        return ABORT_REASON;
    }

   /**
    * @param inputString
    * @return boolean
    * @exception Exception
    * @author 
    * @version 
    * @roseuid 3B9A220F00EF
    */
    public boolean SetValues (String inputString) throws Exception
    {
        if (inputString == null)
            // throw (new Exception ("Null pArameter Passed"));
            return false;
        String[] k = theParseMessage.parse (inputString);
        if (k.length != 1)
            return false;
        ABORT_REASON = k[0];
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
        TRAN_ABRT Jada = new TRAN_ABRT ();

        System.out.println ("The result of set values is " + Jada.SetValues (args[0]));
        System.out.println ("The Values are \n REASON=" + Jada.getABORT_REASON ());

        String Ki = Jada.toString ();

        System.out.println ("Got" + Ki);

        TRAN_ABRT Jada1 = new TRAN_ABRT (Ki);

        System.out.println ("The Values are \n REASON=" + Jada1.getABORT_REASON ());
        TRAN_ABRT Jada21 = new TRAN_ABRT ("Dead Server");

        System.out.println ("The Values are \n REASON=" + Jada21.getABORT_REASON ());
        System.out.println (Jada21 + "\n" + Jada1 + "\n" + Jada);
    }
}
