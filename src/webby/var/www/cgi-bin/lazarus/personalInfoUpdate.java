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
import utils.crypto.*;

class personalInfoUpdate
{

    public personalInfoUpdate (String ServerIp, int ServerPort, String UserName,
                               String PassPhrase, String Address,
                               String MailId) throws Exception
    {
        Socket theClientSocket = new Socket (ServerIp, ServerPort);

        DataInputStream i = new DataInputStream (theClientSocket.getInputStream ());
        DataOutputStream o = new DataOutputStream (theClientSocket.getOutputStream ());
         PassPhrase = PassPhrase.trim ();
        if (PassPhrase.length () < 6)
          {
              System.out.println ("Too small password");
              System.exit (1);
          }
        PassPhrase = hash.hash (PassPhrase);
         System.out.println ("Update PERSONALINFORMATION set ADDRESS='" + Address +
                             "', mailID='" + MailId + "' where USERNAME='" + UserName + "'");
        String SQL_Query =
            ("Update PERSONALINFORMATION set ADDRESS='" + Address + "', mailID='" + MailId +
             "' where USERNAME='" + UserName + "'");

         o.writeInt (1);
         o.writeUTF (SQL_Query);
         o.writeUTF ("");
         o.writeUTF ("");
         o.writeUTF ("");
         o.writeUTF ("");
        int length = i.readInt ();
        for (int t = 0; t < length; t++)
          {
              String g = i.readUTF ();
          }
        theClientSocket.shutdownInput ();

        theClientSocket.shutdownOutput ();
        theClientSocket.close ();
        theClientSocket = new Socket (ServerIp, ServerPort);

        i = new DataInputStream (theClientSocket.getInputStream ());
        o = new DataOutputStream (theClientSocket.getOutputStream ());
        System.out.println ("Update PASSWORD set PASSPHRASE='" + PassPhrase +
                            "' where USERNAME='" + UserName + "'");
        SQL_Query =
            ("Update PASSWORD set PASSPHRASE='" + PassPhrase + "' where USERNAME='" +
             UserName + "'");

        o.writeInt (1);
        o.writeUTF (SQL_Query);
        o.writeUTF ("");
        o.writeUTF ("");
        o.writeUTF ("");
        o.writeUTF ("");
        length = i.readInt ();
        for (int t = 0; t < length; t++)
          {
              String g = i.readUTF ();
          }
        theClientSocket.shutdownInput ();
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
            System.out.println ("argument=" + args[0] + "  " + args[1] + "  " + args[2] + " " +
                                args[3]);
            personalInfoUpdate a =
                new personalInfoUpdate (ServerIp, ServerPort, args[0], args[1], args[2],
                                        args[3]);
        }
        catch (Exception e)
        {
            e.printStackTrace ();
            System.exit (1);
        }
        System.exit (0);
    }
}
