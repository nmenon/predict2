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

package network;

import java.io.*;

/** 
 * We need to get a through merchant stat.
 * This helps by reading from in and sending to out1 then reading from in1 and sending it to out
 */
public class networkAnalyzerForwarder
{
    public networkAnalyzerForwarder (DataInputStream in, DataOutputStream out,
                                     DataInputStream in1, DataOutputStream out1,
                                     int numberofSends) throws Exception
    {
        int count = 0;
        int countA = in.readInt ();
        // System.out.println ("Read from in " + countA);
        long histime = 0;
         numberofSends++;

         out1.writeInt (countA);
        // System.out.println ("Wrote to out1 " + countA);


        while (count < countA + 1)
          {
              // System.out.println ("Count=" + count);
              histime = in.readLong (); // <<<<<Reading Data from first
              // System.out.println ("Read from in" + histime);
              out1.writeLong (histime); // >>>>>>>Sending DATA to second
              // System.out.println ("Wrote to out1 " + histime);
              histime = in1.readLong ();    // <<<<<Reading Data from second
              // System.out.println ("Read from in1" + histime);
              out.writeLong (histime);  // >>>>>>>Sending DATA to first
              // System.out.println ("Wrote to out " + histime);

              count++;
          }                     // End of While
    }
}
