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

//Source file: Y:/E-Cash-Project/TestBed/development/messages/ID_REP.java

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
 * The Message from
 * MasterServer_Citizen  -> ZoneServer_Citizen
 * That provides the result =0/1 to the ZS Citizen
 * 
 * RESULT, RWT
 */
public class ID_REP implements Message
{
    private Boolean RESULT;
    private Long RWT;
    public parseMessage theParseMessage;

   /**
    * @return 
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A042301FA
    */
    public ID_REP ()
    {
        RESULT = null;
        RWT = null;
        theParseMessage = new parseMessage ();
    }
    public ID_REP (Boolean R, Long RW) throws Exception
    {
        if (R == null || RW == null)
            throw (new Exception ("null parameter Passed RES=" + R + " RWT=" + RW));
        theParseMessage = new parseMessage ();
        RESULT = R;
        RWT = RW;
    }
    public ID_REP (String Ip) throws Exception
    {
        if (Ip == null)
            throw (new Exception ("Null Paramater Passed"));
        theParseMessage = new parseMessage ();

        String[] s = theParseMessage.parse (Ip);

        if (s.length != 2)
            throw (new Exception (Ip + " Contains " + s.length + " Params while I need 2"));
        if (!(s[0].equals ("0") || s[0].equals ("1")))
            throw (new Exception ("Boolean value accpets only 1/0 - gave" + s[0]));
        RESULT = new Boolean ((s[0].equals ("1")) ? "true" : "false");
        RWT = new Long (s[1]);

    }

   /**
    * @return Boolean
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A14900250
    */
    public Boolean getRESULT ()
    {
        return RESULT;
    }

   /**
    * @return Long
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A14B801F3
    */
    public Long getRWT ()
    {
        return RWT;
    }

   /**
    * @return String
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A17CD0310
    */
    public String getType ()
    {
        return ("ID_REP");
    }
    public String toString ()
    {
        if (RESULT == null || RWT == null)
            return null;
        String s = (RESULT.booleanValue ())? "1" : "0";

        Vector D = new Vector ();

        D.add (s);
        D.add (RWT);
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
    * @roseuid 3B9A22250187
    */
    public boolean SetValues (String Ip) throws Exception
    {
        if (Ip == null)
            return false;

        String[] s = theParseMessage.parse (Ip);

        if (s.length != 2)
            return false;
        if (!(s[0].equals ("0") || s[0].equals ("1")))
            return false;
        RESULT = new Boolean ((s[0].equals ("1")) ? "true" : "false");
        RWT = new Long (s[1]);
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
        ID_REP Jada = new ID_REP ();

        System.out.println ("The result of set values is " + Jada.SetValues (args[0]));
        Long a = new Long (System.currentTimeMillis ());

        System.out.println ("The Values are \n RESULT=" + Jada.getRESULT () +
                            "\n RWT=" + Jada.getRWT ());

        Long b = new Long (System.currentTimeMillis ());
        String Ki = Jada.toString ();

        System.out.println ("Got" + Ki);
        ID_REP Jada1 = new ID_REP (Ki);

        System.out.println ("The Values are \n RESULT=" + Jada1.getRESULT () +
                            "\n RWT=" + Jada1.getRWT ());
        Long c = new Long (System.currentTimeMillis ());
        Boolean p = new Boolean ("true");
        ID_REP Jada21 = new ID_REP (p, c);

        System.out.println ("The Values are \n RESULT=" + Jada21.getRESULT () +
                            "\n RWT=" + Jada21.getRWT ());
        System.out.println (Jada21 + "\n" + Jada1 + "\n" + Jada);
    }
}
