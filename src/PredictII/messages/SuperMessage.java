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

//Source file: Y:/E-Cash-Project/TestBed/development/messages/SuperMessage.java

/* This Is a Copy Lefted Software
(CL) 2001 Spetember
By Nishanth 'Lazarus' Menon
Calicut Regional Engineering College
Calicut */

package messages;

import messages.tools.parseMessage;
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
 * This is the Super Message containing the Message Name and the Message
 * 
 * MESSAGE_NAME
 * Message
 */
public class SuperMessage implements Message
{
    private String MESSAGE_NAME;
    private String Message;
    private Float GenTime;
    private Float LastTime;
    public parseMessage theParseMessage;

    public SuperMessage ()
    {
        MESSAGE_NAME = null;
        theParseMessage = new parseMessage ();
        Message = null;
        LastTime = null;
        GenTime = new Float (System.currentTimeMillis ());
    }

    public SuperMessage (String Messg_Name, String Messg) throws Exception
    {
        theParseMessage = new parseMessage ();
        if (Messg_Name == null || Messg == null)
            throw (new
                   Exception ("Null Parameter Passed MsgNam=" + Messg_Name +
                              " Messg=" + Messg));
        MESSAGE_NAME = Messg_Name;
        Message = Messg;
        LastTime = null;
        GenTime = new Float (System.currentTimeMillis ());
    }

    public SuperMessage (String inputString) throws Exception
    {
        theParseMessage = new parseMessage ();
        if (inputString == null)
            throw (new Exception ("Null Parameter Passed"));
        // return false;
        String[] k = theParseMessage.parseSuper (inputString);
        if (k.length != 2)
            throw (new Exception (inputString + " has " + k.length + " while I need only 2"));
        MESSAGE_NAME = k[0];
        Message = k[1];
        LastTime = null;
        GenTime = new Float (System.currentTimeMillis ());
    }

    public boolean verifyType (String MEssage_Name) throws Exception
    {
        // System.out.println ("Reached CHK1");
        if (MESSAGE_NAME == null)
            return false;
        // System.out.println ("Reached CHK2");
        if (MESSAGE_NAME.equals (MEssage_Name))
            return true;
        // System.out.println ("Reached CHK3");
        return false;
    }

   /**
    * @return String
    * @exception 
    * @author 
    * @version 
    * @roseuid 3BA273CD022F
    */
    public String getMESSAGE_NAME ()
    {
        return MESSAGE_NAME;
    }

    public String getMessage ()
    {
        return Message;
    }

   /**
    * @return String
    * @exception 
    * @author 
    * @version 
    * @roseuid 3BA274240375
    */
    public String getType ()
    {
        return ("SuperMessage");
    }
    public String toString ()
    {
        if (MESSAGE_NAME == null || Message == null)
            return null;
        Vector D = new Vector ();

        D.add (MESSAGE_NAME);
        D.add (Message);
        String d;

        try
        {
            d = theParseMessage.generateSuper (D);
        }
        catch (Exception e)
        {
            return null;
        }
        return (d);
    }

   /**
    * @param inputString
    * @return boolean
    * @exception Exception
    * @author 
    * @version 
    * @roseuid 3BA2742403BB
    */
    public boolean SetValues (String inputString) throws Exception
    {
        if (inputString == null)
            // throw (new Exception ("Null pArameter Passed"));
            return false;
        String[] k = theParseMessage.parseSuper (inputString);
        if (k.length != 2)
            return false;
        MESSAGE_NAME = k[0];
        Message = k[1];
        return true;
    }

    public void setLastTime (Float f)
    {
        LastTime = f;
    }

    public void setLastTime (float f)
    {
        LastTime = new Float (f);
    }


    public Float getLastTime ()
    {
        return LastTime;
    }

  /** 
   * The Access Function Used for testing purpose
   *
   */
    public static void main (String[]args) throws Exception
    {
        // Reads the Message as a single string delimited with :::
        if (args.length < 1)
          {
              System.out.println (" Arguments not enough");
              System.exit (1);
          }
        SuperMessage Jada = new SuperMessage ();

        System.out.println ("The result of set values is " + Jada.SetValues (args[0]));
        System.out.println ("The Values are \n MESSAGE_NAME=" +
                            Jada.getMESSAGE_NAME () + "\n Message=" + Jada.getMessage ());
        String Ki = Jada.toString ();

        System.out.println ("Got" + Ki);
        SuperMessage Jada1 = new SuperMessage (Ki);

        System.out.println ("The Values are \n MESSAGE_NAME=" + Jada1.getMESSAGE_NAME ());
        SuperMessage Jada21 = new SuperMessage ("123123123", "hello");

        System.out.println ("The Values are \n MESSAGE_NAME=" + Jada21.getMESSAGE_NAME ());
        System.out.println ("Last " + Jada21 + "\n" + Jada1 + "\n" + Jada);
    }
}
