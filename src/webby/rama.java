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


/*
 * @(#)Rama.java 1.0 01/09/29
 *
 * You can modify the template of this file in the
 * directory ..\JCreator\Templates\Template_1\Project_Name.java
 *
 * You can also create your own project template by making a new
 * folder in the directory ..\JCreator\Template\. Use the other
 * templates as examples.
 *
 */

import java.sql.*;
import java.util.*;
import java.net.*;
import java.io.*;


/* A Test Class for the Testing of mia ideas...*/
class rama extends Thread
{
    Connection connectionTest;  // the DB Connection - Need to establish that
    // first
    Statement myStatement;
    ServerSocket myServerSocket;
    // The Server Socket - The Spider waits for the lil fly..
    String RowStart = "<TR>\n";
    String RowEnd = "</TR>\n";
    String ColStart = "<TD>\n";
    String ColEnd = "</TD>\n";
    boolean getOut;             // tells me when to get lost..

    synchronized Vector executeSQL (String SQLQuery, int numberOfFields)
    {
        ResultSet rset = null;
        Vector d = new Vector ();
         System.out.println ("Query Recieved..." + SQLQuery + "....length of fileds=" +
                             numberOfFields);
         try
        {

            rset = myStatement.executeQuery (SQLQuery);

            while (rset.next ())
              {
                  d.add (RowStart);
                  for (int i = 1; i <= numberOfFields; i++)
                    {
                        d.add (ColStart);
                        try
                        {
                            d.add (rset.getString (i) + "\t");
                        }
                        catch (Exception e)
                        {
                            // Handle this - Maybe the User gave us wrong
                            // info..
                        }
                        d.add (ColEnd);

                        // set...
                    }           // End of collection of All fields
                  d.add (RowEnd);
              }                 // End of While
        }
        catch (Exception e)
        {
            // Query Execution Failed
            e.printStackTrace ();

            return (new Vector ());
        }
        return d;
    }

    /** The Anonymous class  that handles the server activities*/
    class HandleAClient extends Thread
    {
        Socket myClient;        // We Got A Client and this Handles that part
        DataInputStream in;
        DataOutputStream out;

        public HandleAClient (Socket s) throws Exception
        {
            myClient = s;
            in = new DataInputStream (s.getInputStream ());
            out = new DataOutputStream (s.getOutputStream ());
        }

        public void run ()
        {
            try
            {
                System.out.println ("Processing " + myClient);
                int numberOfFields = in.readInt (); // Read no of fields
                String Query = in.readUTF ();   // read the Query
                 RowStart = in.readUTF ();  // read the row start delimiter
                 RowEnd = in.readUTF ();    // read the row end delimiter
                 ColStart = in.readUTF ();  // read the Col start delimiter
                 ColEnd = in.readUTF ();    // read the col end delimiter
                Vector output = executeSQL (Query, numberOfFields);
                 System.out.println ("Sending output");
                int len = output.size ();
                 out.writeInt (len);
                for (int i = 0; i < len; i++)
                     out.writeUTF ((String) output.elementAt (i));

            }
            catch (Exception e)
            {
                // Jango Man...
                // DB/network error
            }
            try
            {
                myClient.shutdownInput ();
                myClient.shutdownOutput ();
                myClient.close ();
            }
            catch (Exception e)
            {
                // If Any error it was network error...
            }
            System.out.println ("completed Processing" + myClient);

        }
    }                           // End of Anonymous class HandleAClient


      /** Main Constructor*/
    public rama (String DataString, String DBUser, String DBPassword,
                 int ServerPort) throws Exception
    {

        // Loading Probs-....
        // DataString = "jdbc:oracle:thin:@skylab:1521:predict";
        // DataString = "jdbc:odbc:lines";

        // DBUser = "rama";
        // DBPassword = "rama123";
        // Class.forName ("sun.jdbc.odbc.jdbcodbc");
        System.out.println ("Starting up Rama DB proxy...");
        System.out.println ("Connecting to "+DataString);
        DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver ());
        connectionTest = DriverManager.getConnection (DataString, DBUser, DBPassword);
        myStatement = connectionTest.createStatement ();
        // So We Are Up and Running...DB Wise
        // Create the Web.. Connection...
        myServerSocket = new ServerSocket (ServerPort);
    }

    // So We need to set the Server to reside and Wait..
    public void run ()
    {
        try
        {
            while (!getOut)     // Run Till I am told to get out...This is
                // possible only after catching
                // client as the Accept holds and waits.. Best is to throw
                // an Exception...
              {
                  System.out.println ("Listening...for clients on" + myServerSocket);
                  Socket s = myServerSocket.accept ();

                  System.out.println ("Connection from Client:" + s);

                  HandleAClient oneClient = new HandleAClient (s);

                  oneClient.start ();
              }
        }
        catch (Exception e)
        {                       // Ignore
            e.printStackTrace ();
        }
    }

    public static void main (String[]args) throws Exception
    {
        rama a =
            new rama ("jdbc:oracle:thin:@skylab:1521:predict", "kuberan", "kuberan123", 9032);
         a.start ();
    }
}
