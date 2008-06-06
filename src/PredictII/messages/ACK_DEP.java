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

//Source file: Y:/E-Cash-Project/TestBed/development/messages/ACK_DEP.java

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
 * ZoneServer_Merchant -> Merchant
 * And this is tomake sure that the both the Ack and the Validate message are needed
 * inorder that the identifier is validated
 * 
 * (IDENTIFIER)(K1+K2) , K2, IDENTIFIER
 */
public class ACK_DEP extends AckMaster implements Message
{
    public parseMessage theParseMessage;

   /**
    * @return 
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A043B0370
    */
    public ACK_DEP ()
    {
        super ();
    }

    public ACK_DEP (String IpString) throws Exception
    {
        super (IpString);
    }
    public ACK_DEP (String R, String K, String I) throws Exception
    {
        super (R, K, I);
    }

   /**
    * @return String
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B9A17C702EA
    */
    public String getType ()
    {
        return ("ACK_DEP");
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
    * @roseuid 3B9A222A0261
    */
    public boolean SetValues (String inputString) throws Exception
    {
        return (super.SetValues (inputString));
    }
    public static void main (String[]args) throws Exception
    {
        try
        {
            ACK_DEP n = new ACK_DEP (args[0]);
             System.out.println ("RAK=" + n.getRAK () + "\nK2=" + n.getK2 () +
                                 "\n IDENTIFIER=" + n.getIDENTIFIER ());
            ACK_DEP m = new ACK_DEP ("aaaa", "bbbb", "cccc");
             System.out.println ("RAK=" + m.getRAK () + "\nK2=" + m.getK2 () +
                                 "\n IDENTIFIER=" + m.getIDENTIFIER ());

        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
    }
}
