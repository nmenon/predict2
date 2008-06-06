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

//Source file: Y:/E-Cash-Project/TestBed/development/messages/VAL_REP_MS.java

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
 * This Message is from
 * MasterServer_Citizen -> MasterServer_Merchant
 * This Gives the result of the cash token validification
 * 
 * 
 * IDENTIFIER, AMOUNT, RTT, RWT, TIMESTAMP
 */
public class VAL_REP_MS extends CashMaster implements Message
{
    public parseMessage theParseMessage;

   /**
    * @return 
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A04700291
    */
    public VAL_REP_MS ()
    {
        super ();
    }

    public VAL_REP_MS (String I, Float A, Long RT, Long RW, Long T) throws Exception
    {
        super (I, A, RT, RW, T);
    }
    public VAL_REP_MS (String Ip) throws Exception
    {
        super (Ip);
    }

   /**
    * @return String
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A35940177
    */
    public String getType ()
    {
        return ("VAL_REP_MS");
    }
    public String toString ()
    {
        return (super.toString ());
    }

   /**
    * @param inputString
    * @return boolean
    * @exception Exception
    * @author 
    * @version 
    * @roseuid 3B9A359401B3
    */
    public boolean SetValues (String inputString) throws Exception
    {
        return (super.SetValues (inputString));
    }
    public static void main (String[]args) throws Exception
    {
        // Reads the Message as a single string delimited with :::
        if (args.length < 1)
          {
              System.out.println (" Arguments not enough");
              System.exit (1);
          }
        VAL_REP_MS Jada = new VAL_REP_MS ();

        Jada.SetValues (args[0]);
        Long a = new Long (System.currentTimeMillis ());

        System.out.println ("The Values are \nIDENTIFIER=" +
                            Jada.getIDENTIFIER () + "\n AMOUNT=" +
                            Jada.getAMOUNT () + "\n RTT=" + Jada.getRTT () +
                            "\n RWT=" + Jada.getRWT () + "\n TimeStamp=" +
                            Jada.getTIMESTAMP ());
        Long b = new Long (System.currentTimeMillis ());
        VAL_REP_MS Jada1 = new VAL_REP_MS (Jada.toString ());

        System.out.println ("The Values are \nIDENTIFIER=" +
                            Jada1.getIDENTIFIER () + "\n AMOUNT=" +
                            Jada1.getAMOUNT () + "\n RTT=" + Jada1.getRTT () +
                            "\n RWT=" + Jada1.getRWT () + "\n TimeStamp=" +
                            Jada1.getTIMESTAMP ());
        Long c = new Long (System.currentTimeMillis ());
        Float f = new Float (1231231098868997698769823.1231231);
        VAL_REP_MS Jada21 = new VAL_REP_MS ("Helo dear", f, a, b, c);

        System.out.println ("The Values are \nIDENTIFIER=" +
                            Jada21.getIDENTIFIER () + "\n AMOUNT=" +
                            Jada21.getAMOUNT () + "\n RTT=" + Jada21.getRTT () +
                            "\n RWT=" + Jada21.getRWT () + "\n TimeStamp=" +
                            Jada21.getTIMESTAMP ());
        System.out.println (Jada21 + "\n" + Jada1 + "\n" + Jada);
    }
}
