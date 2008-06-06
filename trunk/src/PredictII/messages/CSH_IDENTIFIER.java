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


//Source file: Y:/E-Cash-Project/TestBed/development/messages/CSH_IDENTIFIER.java

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
 * MasterServer_Citizen -> MasterServer_Merchant
 * This Gives the result of the cash token validification
 * 
 * 
 * CSH_IDENTIFIER, AMOUNT, RTT, RWT, TIMESTAMP
 */
public class CSH_IDENTIFIER implements Message
{
    public parseMessage theParseMessage;
    private String Denomination;
    private String MasterServerID;
    private Long ID;

   /**
    * @return 
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A04700291
    */
    public CSH_IDENTIFIER ()
    {
        ID = null;
        MasterServerID = null;
        Denomination = null;

        theParseMessage = new parseMessage ();
    }

    public CSH_IDENTIFIER (String D, String MasterServerID, Long T) throws Exception
    {
        if (D == null || MasterServerID == null || T == null)
            throw (new
                   Exception ("Null Value in the Argument Denom=" + D + " MASTERID=" +
                              MasterServerID + " IDEN=" + T));
        Denomination = D;
        this.MasterServerID = MasterServerID;
        ID = T;
        theParseMessage = new parseMessage ();
    }
    public CSH_IDENTIFIER (String Ip) throws Exception
    {
        theParseMessage = new parseMessage ();
        String[] a = theParseMessage.parseIdentifier (Ip);
        if (a.length != 3)
            throw (new
                   Exception (Ip + " Contains " + Ip.length () + " While I need 5 to work"));
        Denomination = a[0];
        MasterServerID = a[1];
        ID = new Long (a[2]);
        if (ID == null || Denomination == null || MasterServerID == null)
            throw (new Exception ("Null Value in Parse Value"));
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
        return ("CSH_IDENTIFIER");
    }
    public Long getID ()
    {
        return ID;
    }

    public String getDenomination ()
    {
        return Denomination;
    }

    public String getMasterServerID ()
    {
        return MasterServerID;
    }
    public String toString ()
    {
        Vector d = new Vector ();

        if (ID == null || Denomination == null || MasterServerID == null)
            return null;

        d.add (Denomination);
        d.add (MasterServerID);
        d.add (ID);
        String s;

        try
        {
            s = theParseMessage.generateIdentifier (d);
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
    * @roseuid 3B9A359401B3
    */
    public boolean SetValues (String Ip) throws Exception
    {
        theParseMessage = new parseMessage ();
        String[] a = theParseMessage.parseIdentifier (Ip);
        if (a.length != 3)
            return false;
        for (int i = 0; i < a.length; i++)
            System.out.println (a[i]);
        Denomination = a[0];
        MasterServerID = a[1];
        ID = new Long (a[2]);
        if (ID == null || Denomination == null || MasterServerID == null)
            return false;
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
        CSH_IDENTIFIER Jada = new CSH_IDENTIFIER ();

        Jada.SetValues (args[0]);
        Long a = new Long (System.currentTimeMillis ());

        System.out.println ("The Values are \nCSH_IDENTIFIER=" +
                            Jada.getDenomination () + "\n MasterServerID=" +
                            Jada.getMasterServerID () + "\n ID=" + Jada.getID ());
        Long b = new Long (System.currentTimeMillis ());
        CSH_IDENTIFIER Jada1 = new CSH_IDENTIFIER (Jada.toString ());

        System.out.println ("The Values are \nCSH_IDENTIFIER=" +
                            Jada1.getDenomination () + "\n MasterServerID=" +
                            Jada1.getMasterServerID () + "\n ID=" + Jada1.getID ());
        Long c = new Long (System.currentTimeMillis ());
        Float f = new Float (1231231098868997698769823.1231231);
        CSH_IDENTIFIER Jada21 = new CSH_IDENTIFIER ("Dollars", "Mir", c);

        System.out.println ("The Values are \nCSH_IDENTIFIER=" +
                            Jada21.getDenomination () + "\n MasterServerID=" +
                            Jada21.getMasterServerID () + "\n ID=" + Jada21.getID ());
        System.out.println (Jada21 + "\n" + Jada1 + "\n" + Jada);
    }
}
