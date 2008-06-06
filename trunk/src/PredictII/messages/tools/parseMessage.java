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

//Source file: Y:/E-Cash-Project/TestBed/development/messages/tools/parseMessage.java

/* This Is a Copy Lefted Software
(CL) 2001 Spetember
By Nishanth 'Lazarus' Menon
Calicut Regional Engineering College
Calicut */

package messages.tools;

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
public class parseMessage
{

    protected String Delimiter = ":::";
    protected String MessageDelimiter = ":::";
    protected String SuperDelimiter = "|||";
    protected String IdentifierDelimiter = ";;";
    public parseMessage ()
    {
    }

    public String generate (Vector listOfObjects) throws Exception
    {
        int d = listOfObjects.size ();
        String retn = "";
        if (d == 0)
             return null;
        for (int i = 0; i < d; i++)
          {
              // System.out.println (i + "List Object" +
              // listOfObjects.elementAt (i).toString ());
              retn = retn.concat (listOfObjects.elementAt (i).toString ());
              // System.out.println ("The Retn=" + retn);
              if (i < d - 1)
                  retn = retn.concat (Delimiter);
          }
        return retn;
    }                           // End of Generate

    public String generateSuper (Vector Mesg) throws Exception
    {
        Delimiter = SuperDelimiter;
        String a = generate (Mesg);
         Delimiter = MessageDelimiter;
         return (a);
    }
    public String generateIdentifier (Vector Mesg) throws Exception
    {
        Delimiter = IdentifierDelimiter;
        String a = generate (Mesg);
         Delimiter = MessageDelimiter;
         return (a);
    }
    public String generate (Vector listOfObjects, String Delim) throws Exception
    {
        int d = listOfObjects.size ();
        String retn = "";
        if (d == 0)
             return null;
        for (int i = 0; i < d; i++)
          {
              // System.out.println (i + "List Object" +
              // listOfObjects.elementAt (i).toString ());
              retn = retn.concat (listOfObjects.elementAt (i).toString ());
              // System.out.println ("The Retn=" + retn);
              if (i < d - 1)
                  retn = retn.concat (Delim);
          }
        return retn;
    }

   /**
    * @param Mesg
    * @return String[]
    * @exception Exception
    * @author 
    * @version 
    * @roseuid 3B9A3779007A
    */
    public String[] parse (String Mesg) throws Exception
    {
        int numberOfFields = 0;
        int lastIndex = 0;
        // System.out.println("InputString="+Mesg);
        if (Mesg == null)
            // {
            // System.out.println ("Mesg=null");
             return null;
        // }
        while (lastIndex >= 0)
          {
              lastIndex =
                  Mesg.indexOf (Delimiter,
                                (lastIndex == 0) ? 0 : lastIndex + Delimiter.length ());
              // System.out.println (lastIndex + "---");

              if (lastIndex > 0)
                  numberOfFields++;

          }                     // End of while
        numberOfFields++;

        // System.out.println ("NumOfFields=" + numberOfFields);
        if (numberOfFields == 0)
          {
              String[]retn = new String[1];
              retn[0] = Mesg;
              return retn;
          }
        String[]retn = new String[numberOfFields];
        numberOfFields = 0;
        lastIndex = 0;
        // System.out.println ("So We have " + retn.length);
        while (lastIndex >= 0)
          {
              int tindex = Mesg.indexOf (Delimiter, lastIndex);

              if (tindex > 0)
                  retn[numberOfFields++] = Mesg.substring (lastIndex, tindex);
              else
                  retn[numberOfFields++] = Mesg.substring (lastIndex, Mesg.length ());
              // System.out.println ("The" + numberOfFields + " " +
              // retn[numberOfFields - 1]);
              lastIndex = (tindex > 0) ? tindex + Delimiter.length () : tindex;
          }                     // End of while

        return retn;
    }
    public String[] parseSuper (String Mesg) throws Exception
    {
        Delimiter = SuperDelimiter;
        String[] a = parse (Mesg);
        Delimiter = MessageDelimiter;
        return (a);
    }
    public String[] parseIdentifier (String Mesg) throws Exception
    {
        Delimiter = IdentifierDelimiter;
        String[] a = parse (Mesg);
        Delimiter = MessageDelimiter;
        return (a);
    }
    public String[] parse (String Mesg, String Delimiter) throws Exception
    {
        int numberOfFields = 0;
        int lastIndex = 0;
        // System.out.println("InputString="+Mesg);
        if (Mesg == null)
            // {
            // System.out.println ("Mesg=null");
             return null;
        // }
        while (lastIndex >= 0)
          {
              lastIndex =
                  Mesg.indexOf (Delimiter,
                                (lastIndex == 0) ? 0 : lastIndex + Delimiter.length ());
              // System.out.println (lastIndex + "---");

              if (lastIndex > 0)
                  numberOfFields++;

          }                     // End of while
        numberOfFields++;

        // System.out.println ("NumOfFields=" + numberOfFields);
        if (numberOfFields == 0)
          {
              String[]retn = new String[1];
              retn[0] = Mesg;
              return retn;
          }
        String[]retn = new String[numberOfFields];
        numberOfFields = 0;
        lastIndex = 0;
        // System.out.println ("So We have " + retn.length);
        while (lastIndex >= 0)
          {
              int tindex = Mesg.indexOf (Delimiter, lastIndex);

              if (tindex > 0)
                  retn[numberOfFields++] = Mesg.substring (lastIndex, tindex);
              else
                  retn[numberOfFields++] = Mesg.substring (lastIndex, Mesg.length ());
              // System.out.println ("The" + numberOfFields + " " +
              // retn[numberOfFields - 1]);
              lastIndex = (tindex > 0) ? tindex + Delimiter.length () : tindex;
          }                     // End of while

        return retn;
    }
    public static void main (String[]args) throws Exception
    {
        try
        {
            if (args.length < 1)
                System.exit (1);
            parseMessage j = new parseMessage ();
            Vector d = new Vector ();
             String[] a = j.parse (args[0]);
            for (int i = 0; i < a.length; i++)
              {
                  System.out.println (i + " parameter is " + a[i]);
                  d.addElement (a[i]);
              }
            System.out.println ("The Generated string is " + j.generate (d));

        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
    }
}
