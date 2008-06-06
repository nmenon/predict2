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

/** The Network Alalyser client works along with the network analyser server 
 * Finds the network stats
 */
public class networkAnalyzerClient extends networkAnalyzer
{
    private static long histime;
    private static long mytime;
    private static long mytimeD;
    private static long mytimeX;

    public networkAnalyzerClient (DataInputStream in, DataOutputStream out,
                                  int numberofSends) throws Exception
    {
        int count = 0;
         mytime = 0;
         histime = 0;
         mytimeD = 0;

         out.writeInt (numberofSends);

         mytimeD = System.currentTimeMillis ();

        while (count < numberofSends + 1)
          {
              mytime = System.currentTimeMillis (); // /Time When I Just Send
              // System.out.println (count + ": Time is :" +
              // System.currentTimeMillis ());
              // sleep (1000);
              out.writeLong (mytime);   // >>>>>>>Sending DATA
              // sleep (1000);
              histime = in.readLong (); // <<<<<Reading Data
              mytimeD = System.currentTimeMillis ();

              addTime (mytime, mytimeD, histime);   // *********Adding time

              count++;
          }                     // End of While
        analyse ();
    }
    public static void main (String[]args) throws Exception
    {
        Socket s = new Socket (args[0], 8909);
        DataOutputStream out = new DataOutputStream (s.getOutputStream ());
        DataInputStream in = new DataInputStream (s.getInputStream ());
        // The Number 30 is excellent for most Statistical reasons...
        networkAnalyzerClient a = new networkAnalyzerClient (in, out, 30);
        // System.out.println ("RESULT= OneWayTIME=" + a.getOneWayTime () +
        // "\n Differenceintim=" + a.getDifferenceInTime ());
         s.close ();
    }
}                               // End of networkAnalyzerServer
