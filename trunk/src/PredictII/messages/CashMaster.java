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

//Source file: Y:/E-Cash-Project/TestBed/development/messages/CashMaster.java

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
 */
public class CashMaster implements Message
{
    private String IDENTIFIER;
    private Float AMOUNT;
    private Long RTT;
    private Long RWT;
    private Long TIMESTAMP;
    public parseMessage theParseMessage;

    public CashMaster ()
    {
        theParseMessage = new parseMessage ();
        IDENTIFIER = null;
        AMOUNT = null;
        RTT = null;
        RWT = null;
        TIMESTAMP = null;
    }

  /**
   *
   * The Better Constructor
   */
    public CashMaster (String I, Float A, Long RT, Long RW, Long T) throws Exception
    {
        if (I == null || A == null || RT == null || RW == null || T == null)
            throw (new
                   Exception ("Null Value in Input IDENTIFIER=" + I + "\n AMOUNT=" +
                              A + "\n RTT=" + RT + "\n RWT=" + RW + "TIMESTAMP=" + T));
        IDENTIFIER = I;
        AMOUNT = A;
        RTT = RT;
        RWT = RW;
        TIMESTAMP = T;
        theParseMessage = new parseMessage ();
    }

  /**
   *
   */
    public CashMaster (String Ip) throws Exception
    {
        theParseMessage = new parseMessage ();
        String[] a = theParseMessage.parse (Ip);
        if (a.length != 5)
            throw (new
                   Exception (Ip + " Contains " + Ip.length () + " While I need 5 to work"));
        IDENTIFIER = a[0];
        AMOUNT = new Float (a[1]);
        RTT = new Long (a[2]);
        RWT = new Long (a[3]);
        TIMESTAMP = new Long (a[4]);

        if (IDENTIFIER == null || AMOUNT == null || RTT == null || RWT == null
            || TIMESTAMP == null)
            throw (new
                   Exception ("Null Value in Input IDENTIFIER=" + IDENTIFIER +
                              "\n AMOUNT=" + AMOUNT + "\n RTT=" + RTT + "\n RWT=" +
                              RWT + "TIMESTAMP=" + TIMESTAMP));
    }

   /**
    * @return String
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A2ADD01AF
    */
    public String getIDENTIFIER ()
    {
        return IDENTIFIER;
    }

   /**
    * @return Float
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A2AE6032E
    */
    public Float getAMOUNT ()
    {
        return AMOUNT;
    }

   /**
    * @return Long
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A2AEF036D
    */
    public Long getRTT ()
    {
        return RTT;
    }

   /**
    * @return Long
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A2AF6036D
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
    * @roseuid 3B9A2B12018D
    */
    public Long getTIMESTAMP ()
    {
        return TIMESTAMP;
    }

   /**
    * @return String
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A32C80041
    */
    public String getType ()
    {
        return ("CashMaster");
    }
    public String toString ()
    {
        if (IDENTIFIER == null || AMOUNT == null || RTT == null || RWT == null
            || TIMESTAMP == null)
            return null;
        Vector D = new Vector ();

        D.add (IDENTIFIER);
        D.add (AMOUNT);
        D.add (RTT);
        D.add (RWT);
        D.add (TIMESTAMP);
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
    * @roseuid 3B9A32C80069
    */
    public boolean SetValues (String Ip) throws Exception
    {
        theParseMessage = new parseMessage ();
        String[] a = theParseMessage.parse (Ip);
        if (a.length != 5)
            return false;
        IDENTIFIER = a[0];
        AMOUNT = new Float (a[1]);
        RTT = new Long (a[2]);
        RWT = new Long (a[3]);
        TIMESTAMP = new Long (a[4]);

        if (IDENTIFIER == null || AMOUNT == null || RTT == null || RWT == null
            || TIMESTAMP == null)
            return false;
        return true;
    }

    public static void main (String[]args) throws Exception
    {
        CashMaster Jada = new CashMaster ();
         Jada.SetValues (args[0]);
        // Reads the Message as a single string delimited with :::
        if (args.length < 1)
          {
              System.out.println (" Arguments not enough");
              System.exit (1);
          }
        Long a = new Long (System.currentTimeMillis ());

        System.out.println ("The Values are \nIDENTIFIER=" +
                            Jada.getIDENTIFIER () + "\n AMOUNT=" +
                            Jada.getAMOUNT () + "\n RTT=" + Jada.getRTT () +
                            "\n RWT=" + Jada.getRWT () + "\n TimeStamp=" +
                            Jada.getTIMESTAMP ());
        Long b = new Long (System.currentTimeMillis ());
        CashMaster Jada1 = new CashMaster (Jada.toString ());

        System.out.println ("The Values are \nIDENTIFIER=" +
                            Jada1.getIDENTIFIER () + "\n AMOUNT=" +
                            Jada1.getAMOUNT () + "\n RTT=" +
                            Jada1.getRTT () + "\n RWT=" + Jada1.getRWT () +
                            "\n TimeStamp=" + Jada1.getTIMESTAMP ());
        Long c = new Long (System.currentTimeMillis ());
        Float f = new Float (1231231098868997698769823.1231231);
        CashMaster Jada21 = new CashMaster ("Helo dear", f, a, b, c);

        System.out.println ("The Values are \nIDENTIFIER=" +
                            Jada21.getIDENTIFIER () + "\n AMOUNT=" +
                            Jada21.getAMOUNT () + "\n RTT=" +
                            Jada21.getRTT () + "\n RWT=" +
                            Jada21.getRWT () + "\n TimeStamp=" + Jada21.getTIMESTAMP ());
    }
}
