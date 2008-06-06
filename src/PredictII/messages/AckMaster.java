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

//Source file: Y:/E-Cash-Project/TestBed/development/messages/AckMaster.java

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
 * Citizen -> ZoneServer_Citizen
 * This message gives the zoneserver the acknowledgement message recieved.
 * 
 * (IDENTIFIER)(K1+K2) , K2, IDENTIFIER
 */
public class AckMaster implements Message
{
    private String RAK;
    private String K2;
    private String IDENTIFIER;
    public parseMessage theParseMessage;

    public AckMaster ()
    {
        theParseMessage = new parseMessage ();
        RAK = null;
        K2 = null;
        IDENTIFIER = null;
    }

  /** 
    * The Better Constructor
    *
    */
    public AckMaster (String R, String K, String I) throws Exception
    {
        if (R == null || K == null || I == null)
            throw (new Exception ("NullValue in Input R=" + R + " K=" + K + "I=" + I));
        RAK = R;
        K2 = K;
        IDENTIFIER = I;
        theParseMessage = new parseMessage ();
    }

   /**
    * @return String
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A15B702E0
    */
    public String getRAK ()
    {
        return RAK;
    }

   /**
    * @return String
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A15BD0072
    */
    public String getK2 ()
    {
        return K2;
    }

   /**
    * @return String
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A15C5004B
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
    * @roseuid 3B9A17C202BA
    */
    public String getType ()
    {
        return ("AckMaster");
    }
    public String toString ()
    {
        Vector D = new Vector ();

        D.add (RAK);
        D.add (K2);
        D.add (IDENTIFIER);
        String s = "";

        try
        {
            s = theParseMessage.generate (D);
        }
        catch (Exception e)
        {
            return (null);
        }
        return s;
    }

   /**
    * @param IpString
    * @return boolean
    * @exception Exception
    * @author 
    * @version 
    * @roseuid 3B9A221903E3
    */
    public boolean SetValues (String IpString) throws Exception
    {
        theParseMessage = new parseMessage ();
        String[] a = theParseMessage.parse (IpString);
        if (a.length != 3)
            return false;
        RAK = a[0];
        K2 = a[1];
        IDENTIFIER = a[2];
        return true;
    }

   /**
    * @param IpString
    * @return 
    * @exception Exception
    * @author 
    * @version 
    * @roseuid 3B9A31FA0095
    */
    public AckMaster (String IpString) throws Exception
    {
        theParseMessage = new parseMessage ();
        String[] a = theParseMessage.parse (IpString);
        if (a.length != 3)
            throw (new Exception (IpString + " Contains " + a.length + " Values != 3"));
        RAK = a[0];
        K2 = a[1];
        IDENTIFIER = a[2];
    }

    public static void main (String[]args) throws Exception
    {
        try
        {
            AckMaster n = new AckMaster (args[0]);
             System.out.println ("RAK=" + n.getRAK () + "\nK2=" + n.getK2 () +
                                 "\n IDENTIFIER=" + n.getIDENTIFIER ());
            AckMaster m = new AckMaster ("aaaa", "bbbb", "cccc");
             System.out.println ("RAK=" + m.getRAK () + "\nK2=" + m.getK2 () +
                                 "\n IDENTIFIER=" + m.getIDENTIFIER ());

        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
    }
}
