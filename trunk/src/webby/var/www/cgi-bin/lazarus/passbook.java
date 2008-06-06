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

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;
import utils.misc.*;

/**Output required is 
<DATE>
<CONNECTION FROM IP>
<CREDIT>
<DEBIT>
<BALANCE>
*/
class passbook
{

    public passbook (String ServerIp, int ServerPort, String UserName) throws Exception
    {
        Socket theClientSocket = new Socket (ServerIp, ServerPort);

        DataInputStream i = new DataInputStream (theClientSocket.getInputStream ());
        DataOutputStream o = new DataOutputStream (theClientSocket.getOutputStream ());

        String SQL_Query =
            "select TIMSTAMP,CONNECTED_FROM_IP,CREDIT_DEBIT,AMOUNT from TRANSACTION where UserName='"
            + UserName + "'";

         o.writeInt (4);
         o.writeUTF (SQL_Query);
         o.writeUTF ("");
         o.writeUTF ("");
         o.writeUTF ("");
         o.writeUTF ("");
        int length = i.readInt ();
        String inphrase = null;
        Vector d = new Vector ();
        for (int t = 0; t < length; t++)
          {
              String g = i.readUTF ();
              // System.out.println (g);
              if (g.trim ().length () != 0)
                {
                    inphrase = g.trim ();
                    // System.out.println (inphrase);
                    d.add (inphrase);
                }
          }
        int t = 0;
        Double Balance = new Double (0);

        while (t < d.size ())
          {
              System.out.println ("       <TR>");
              System.out.println ("            <TD align=niddle> <FONT face=Arialsize=1><B>");
              Long timeStamp = new Long ((String) d.elementAt (t));
              String connected_from_IP = (String) d.elementAt (t + 1);
              String Credit_debit = (String) d.elementAt (t + 2);
              Double AMOUNT = new Double ((String) d.elementAt (t + 3));
              Date thatDay = new Date (timeStamp.longValue ());

              System.out.println ("\t\t\t\t\t" +
                                  DateFormat.getDateTimeInstance (DateFormat.MEDIUM,
                                                                  DateFormat.LONG).
                                  format (thatDay));
              System.out.println ("            </B></FONT></TD>");
              System.out.println ("            <TD align=niddle> <FONT face=Arialsize=1><B>");
              System.out.println ("\t\t\t\t\t" + connected_from_IP);
              System.out.println ("            </B></FONT></TD>");

              System.out.println ("            <TD align=niddle> <FONT face=Arialsize=1><B>");
              if (Credit_debit.trim ().equals ("t"))
                {
                    System.out.println ("\t\t\t\t\t" + AMOUNT);
                    System.out.println ("            </B></FONT></TD>");
                    System.out.
                        println ("            <TD align=niddle> <FONT face=Arialsize=1><B>");
                    System.out.
                        println ("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                    System.out.println ("            </B></FONT></TD>");
                    Balance = new Double (Balance.doubleValue () + AMOUNT.doubleValue ());
                }
              else
                {
                    System.out.println ("            </B></FONT></TD>");
                    System.out.
                        println ("            <TD align=niddle> <FONT face=Arialsize=1><B>");
                    System.out.println ("\t\t\t\t\t" + AMOUNT);
                    System.out.println ("            </B></FONT></TD>");
                    Balance = new Double (Balance.doubleValue () - AMOUNT.doubleValue ());
                }
              System.out.println ("            <TD align=niddle> <FONT face=Arialsize=1><B>");
              System.out.println ("\t\t\t\t\t" + Balance);
              System.out.println ("            </B></FONT></TD>");

              System.out.println ("       </TR>");


              t += 4;

          }
        theClientSocket.shutdownInput ();

        theClientSocket.shutdownOutput ();
        theClientSocket.shutdownOutput ();
        theClientSocket.close ();
        System.exit (0);        // All Okay
    }
    public static void main (String[]args) throws Exception
    {
        ParseConfigFile as = new ParseConfigFile ("server.conf", false);
        Hashtable t = as.getList ();
        int ServerPort;
        String ServerIp;

        // data String
        if (t.containsKey ("SERVERIP"))
          {
              ServerIp = ((String) t.get ("SERVERIP"));
          }
        else
             ServerIp = "127.0.0.1";

        // Server Port 
        if (t.containsKey ("SERVERPORT"))
          {
              Integer h = new Integer ((String) t.get ("SERVERPORT"));

              ServerPort = h.intValue ();
          }
        else
            ServerPort = 9032;
        try
        {
            passbook a = new passbook (ServerIp, ServerPort, args[0]);
        }
        catch (Exception e)
        {
            e.printStackTrace ();
            System.exit (1);
        }
        System.exit (0);
    }
}
