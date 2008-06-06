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

//Source file: Y:/E-Cash-Project/TestBed/development/messages/STAT_REP.java

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
 * MasterServer_Citizen -> ZoneServer_Citizen
 * This Message is basically the result of the checkup.
 * 
 * RESULT
 */
public class STAT_REP implements Message
{
    private Boolean RESULT;
    public parseMessage theParseMessage;

   /**
    * @return 
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A047D0389
    */
    public STAT_REP ()
    {
        RESULT = null;
        theParseMessage = new parseMessage ();
    }

    public STAT_REP (String Ip) throws Exception
    {
        theParseMessage = new parseMessage ();
        if (Ip == null)
            throw (new Exception ("Null PAramater Passed"));
        String[] g = theParseMessage.parse (Ip);
        if (g.length != 1)
            throw (new Exception (Ip + " Caontains " + g.length + " But I need 1"));
        if (!(g[0].equals ("0") || g[0].equals ("1")))
            throw (new Exception (g[0] + " got instead of 1/0"));
        RESULT = new Boolean ((g[0].equals ("1")) ? "true" : "false");
    }
    public STAT_REP (Boolean R) throws Exception
    {
        if (R == null)
            throw (new Exception ("Null Parameter passed"));
        RESULT = R;
        theParseMessage = new parseMessage ();
    }

   /**
    * @return Boolean
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A16290190
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
    * @roseuid 3B9A17CF00BA
    */
    public String getType ()
    {
        return ("STAT_REP");
    }
    public String toString ()
    {
        if (RESULT == null)
            return null;
        return ((RESULT.booleanValue ())? "1" : "0");
    }

   /**
    * @param inputString
    * @return Boolean
    * @exception Exception
    * @author 
    * @version 
    * @roseuid 3B9A221D006D
    */
    public boolean SetValues (String Ip) throws Exception
    {
        if (Ip == null)
            return false;
        // throw (new Exception ("Null PAramater Passed"));
        String[] g = theParseMessage.parse (Ip);
        if (g.length != 1)
            return false;
        // throw (new Exception (Ip + " Caontains " + g.length + " But I need
        // 1"));
        if (!(g[0].equals ("0") || g[0].equals ("1")))
            return false;
        // throw (new Exception (g[0] + " got instead of 1/0"));
        RESULT = new Boolean ((g[0].equals ("1")) ? "true" : "false");
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
        STAT_REP Jada = new STAT_REP ();

        System.out.println ("The result of set values is " + Jada.SetValues (args[0]));
        System.out.println ("The Values are \n RESULT=" + Jada.getRESULT ());

        String Ki = Jada.toString ();

        System.out.println ("Got" + Ki);

        STAT_REP Jada1 = new STAT_REP (Ki);

        System.out.println ("The Values are \n RESULT=" + Jada1.getRESULT ());
        Boolean s = new Boolean ("true");
        STAT_REP Jada21 = new STAT_REP (s);

        System.out.println ("The Values are \n RESULT=" + Jada21.getRESULT ());
        System.out.println (Jada21 + "\n" + Jada1 + "\n" + Jada);
    }
}
