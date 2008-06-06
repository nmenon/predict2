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


/* This Is a Copy Lefted Software
(CL) 2001 Spetember
By Nishanth 'Lazarus' Menon
Calicut Regional Engineering College
Calicut */


package network;

import java.lang.*;
import java.io.*;
import java.util.*;
import java.net.*;

/**
  * The Network Analyser Server works with the Client.
  * This is basiically meant for the sending a specified umber of packets and analysing the network
  * Info at the end of the transmission.
  *
  */
public class networkAnalyzerServer extends networkAnalyzer
{

    long CurrT = System.currentTimeMillis ();
    private static long histime;
    private static long mytime;
    private static long mytimeD;


    public networkAnalyzerServer (DataInputStream in, DataOutputStream out,
                                  int numberofSends) throws Exception
    {
        int count = 0;
        int c = in.readInt ();  // << READ Number of reads
        // System.out.println ("TOLD TO READ " + c);
        if (c != numberofSends)
             throw (new
                    Exception
                    ("Client Requests more number of transmits than what i was told to do"
                     + c + " " + numberofSends));


        // Start the Stuff...
         mytimeD = System.currentTimeMillis ();
         CurrT = System.currentTimeMillis ();
        while (count < numberofSends + 1)
          {
              // sleep (10000);
              histime = in.readLong (); // <<<<<Reading Data
              mytimeD = System.currentTimeMillis ();
              // System.out.println (count + ": Time is :" +
              // System.currentTimeMillis ());
              addTime (mytime, mytimeD, histime);   // *****Adding Time
              mytime = System.currentTimeMillis ();
              // sleep (10000);
              out.writeLong (mytime);   // >>>>>>Sending Data
              count++;
          }                     // End of While
        analyse ();

    }
    public static void main (String[]args) throws Exception
    {
        ServerSocket s = new ServerSocket (8909);
        Socket cli = s.accept ();
        DataOutputStream out = new DataOutputStream (cli.getOutputStream ());
        DataInputStream in = new DataInputStream (cli.getInputStream ());
        // The Number 30 is excellent for most Statistical reasons...
        networkAnalyzerServer a = new networkAnalyzerServer (in, out, 30);
        // System.out.println ("RESULT= OneWayTIME=" + a.getOneWayTime () +
        // "\n Differenceintim=" + a.getDifferenceInTime ());
         cli.close ();
    }
}                               // End of networkAnalyzerServer
