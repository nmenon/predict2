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

//Source file: Y:/E-Cash-Project/TestBed/development/messages/CSH_TRANS.java

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
 * ZoneServer_Citizen -> Citizen
 * To Give the cash token
 * 
 * RESULT, IDENTIFIER, AMOUNT, RTT, RWT, TIMESTAMP
 */
public class CSH_TRANS extends CashMaster implements Message
{
    private Boolean RESULT;
    public parseMessage theParseMessage;

   /**
    * @return 
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A042903B2
    */
    public CSH_TRANS ()
    {
        super ();
        theParseMessage = new parseMessage ();
        RESULT = null;
    }
    public CSH_TRANS (String inputString) throws Exception
    {
        String J = inputString;
         theParseMessage = new parseMessage ();
        String K[] = theParseMessage.parse (J);
        if (K.length < 2)
             throw (new Exception ("Not Enough Arguments-" + inputString));
        if (!(K[0].equals ("0") || K[0].equals ("1")))
            // return false;
             throw (new Exception ("Expected 1/0 got " + K[0] + " in Message " + inputString));
        Vector D = new Vector ();
        for (int i = 1; i < K.length; i++)
             D.add (K[i]);
         J = theParseMessage.generate (D);
        // super.CashMaster(J);
        if (!(super.SetValues (J)))
             throw (new Exception ("Message Not ProperDear"));;
         RESULT = new Boolean ((K[0].equals ("1")) ? "true" : "False");

    }
    public CSH_TRANS (Boolean R, String I, Float A, Long RT, Long RW, Long T) throws Exception
    {
        super (I, A, RT, RW, T);
        if (R == null)
            throw (new Exception ("Boolean turned out t be null after all"));
        RESULT = R;
    }

   /**
    * @return boolean
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A157902E1
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
    * @roseuid 3B9A35990020
    */
    public String getType ()
    {
        return ("CSH_TRANS");
    }
    public String toString ()
    {
        String J = super.toString ();

        // System.out.println("ToString="+J);
        theParseMessage = new parseMessage ();
        String K[];

        try
        {
            K = theParseMessage.parse (J);
        }
        catch (Exception e)
        {

            return null;
        }
        // for (int i=0; i<K.length;i++)
        // System.out.println("=="+K[i]+"==");
        Vector D = new Vector ();
        String Re = (RESULT.booleanValue ())? "1" : "0";

        D.add (Re);
        for (int i = 0; i < K.length; i++)
            D.add (K[i]);
        try
        {
            J = theParseMessage.generate (D);
        }
        catch (Exception e)
        {
            return null;
        }
        // System.out.println("J="+J);
        return (J);
    }

   /**
    * @param inputString
    * @return boolean
    * @exception Exception
    * @author 
    * @version 
    * @roseuid 3B9A3599003E
    */
    public boolean SetValues (String inputString) throws Exception
    {
        String J = inputString;
        String K[] = theParseMessage.parse (J);

        // for (int i=0; i<K.length;i++)
        // System.out.println("=="+K[i]+"==");
        if (K.length < 2)
             return false;
        if (!(K[0].equals ("0") || K[0].equals ("1")))
             return false;
        // throw (new
        // Exception ("Expected 1/0 got " + K[0] + " in Message " +
        // inputString));
        // System.out.println ("Helloo");
        Vector D = new Vector ();
        for (int i = 1; i < K.length; i++)
             D.add (K[i]);
         try
        {
            J = theParseMessage.generate (D);
            // System.out.println ("---" + J + "----");
        }
        catch (Exception e)
        {
            return false;
        }
        boolean s = super.SetValues (J);

        if (s)
            RESULT = new Boolean ((K[0].equals ("1")) ? "true" : "False");
        // System.out.println("Setting values-"+K[0]+" Gives="+RESULT);
        return (s);
    }

    public static void main (String[]args) throws Exception
    {
        CSH_TRANS Jada = new CSH_TRANS ();
         System.out.println ("The result of set values is " + Jada.SetValues (args[0]));
        // Reads the Message as a single string delimited with :::
        if (args.length < 1)
          {
              System.out.println (" Arguments not enough");
              System.exit (1);
          }
        Long a = new Long (System.currentTimeMillis ());

        System.out.println ("The Values are \n RESULT=" +
                            Jada.getRESULT () + " \nIDENTIFIER=" +
                            Jada.getIDENTIFIER () + "\n AMOUNT=" +
                            Jada.getAMOUNT () + "\n RTT=" + Jada.getRTT () +
                            "\n RWT=" + Jada.getRWT () + "\n TimeStamp=" +
                            Jada.getTIMESTAMP ());
        Long b = new Long (System.currentTimeMillis ());
        String Ki = Jada.toString ();

        System.out.println ("Got" + Ki);
        CSH_TRANS Jada1 = new CSH_TRANS (Ki);

        System.out.println ("The Values are \n RESULT=" +
                            Jada1.getRESULT () + " \nIDENTIFIER=" +
                            Jada1.getAMOUNT () + "\n RTT=" + Jada1.getRTT () +
                            "\n RWT=" + Jada1.getRWT () + "\n TimeStamp=" +
                            Jada1.getTIMESTAMP ());
        Long c = new Long (System.currentTimeMillis ());
        Float f = new Float (1231231098868997698769823.1231231);
        Boolean D = new Boolean ("false");
        CSH_TRANS Jada21 = new CSH_TRANS (D, "Helo dear", f, a, b, c);

        System.out.println ("The Values are \n RESULT=" +
                            Jada21.getRESULT () + " \nIDENTIFIER=" +
                            Jada21.getAMOUNT () + "\n RTT=" + Jada21.getRTT () +
                            "\n RWT=" + Jada21.getRWT () + "\n TimeStamp=" +
                            Jada21.getTIMESTAMP ());
        System.out.println (Jada21 + "\n" + Jada1 + "\n" + Jada);
    }
}
