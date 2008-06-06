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

package GUI;
import java.lang.*;
import java.util.*;
import java.text.*;

public class W_BrahmaDisplayForm extends Thread
{
    BrahmaDisplayForm theBrahmaDisplayForm;
    Vector theVectorWithdraw;
    Vector theVectorDeposit;
    Vector theVectorRemote;
    anony a;
    public static Vector dam;

    class anony extends Thread
    {
        public anony ()
        {
        }
        public void run ()
        {
            // System.out.println ("Starting up anny");
            theBrahmaDisplayForm.show ();
        }
    }
    public W_BrahmaDisplayForm (Vector ZoneWithdraw, Vector ZoneDeposit,
                                Vector RemoteValidate) throws Exception
    {
        // System.out.println ("Constructor Called");
        if (ZoneWithdraw == null || ZoneDeposit == null || RemoteValidate == null)
            throw (new Exception ("Null Vector Supplied"));

        theBrahmaDisplayForm = new BrahmaDisplayForm (new javax.swing.JFrame (), true);
        theVectorWithdraw = ZoneWithdraw;
        theVectorDeposit = ZoneDeposit;
        theVectorRemote = RemoteValidate;
        a = new anony ();
        a.start ();
        start ();
    }
    public void run ()
    {
        // System.out.println ("Runner is Running..");
        try
        {
            String ToBrahmaDisplay = null;
            Date myDate = new Date ();
            String myString = null;
            Long l;

            while (true)
              {
                  l = new Long (System.currentTimeMillis ());
                  myDate.setTime (l.longValue ());
                  myString =
                      DateFormat.getDateTimeInstance (DateFormat.FULL,
                                                      DateFormat.FULL).format (myDate);

                  ToBrahmaDisplay =
                      myString.trim () + ":" + l.toString () +
                      ":\nInformation of the Connections are...";

                  for (int i = 0; i < theVectorWithdraw.size (); i++)
                      ToBrahmaDisplay =
                          ToBrahmaDisplay + "\n" + theVectorWithdraw.elementAt (i).toString ();
                  // System.out.println ("Withdraw BrahmaDisplay
                  // MessageMessage=" +
                  // ToBrahmaDisplay + "asasdasd" +
                  // theVectorWithdraw.size ());
                  theBrahmaDisplayForm.setTextWithdraw (ToBrahmaDisplay);

                  l = new Long (System.currentTimeMillis ());
                  myDate.setTime (l.longValue ());
                  myString =
                      DateFormat.getDateTimeInstance (DateFormat.FULL,
                                                      DateFormat.FULL).format (myDate);

                  ToBrahmaDisplay =
                      myString.trim () + ":" + l.toString () +
                      ":\nInformation of the Connections are...";

                  for (int i = 0; i < theVectorDeposit.size (); i++)
                      ToBrahmaDisplay =
                          ToBrahmaDisplay + "\n" + theVectorDeposit.elementAt (i).toString ();
                  // System.out.println ("Deposit BrahmaDisplay
                  // MessageMessage=" +
                  // ToBrahmaDisplay + "asasdasd" + theVectorDeposit.size ());
                  theBrahmaDisplayForm.setTextDeposit (ToBrahmaDisplay);

                  l = new Long (System.currentTimeMillis ());
                  myDate.setTime (l.longValue ());
                  myString =
                      DateFormat.getDateTimeInstance (DateFormat.FULL,
                                                      DateFormat.FULL).format (myDate);

                  ToBrahmaDisplay =
                      myString.trim () + ":" + l.toString () +
                      ":\nInformation of the Connections are...";

                  for (int i = 0; i < theVectorRemote.size (); i++)
                      ToBrahmaDisplay =
                          ToBrahmaDisplay + "\n" + theVectorRemote.elementAt (i).toString ();
                  // System.out.println ("Remtote BrahmaDisplay
                  // MessageMessage=" +
                  // ToBrahmaDisplay + "asasdasd" + theVectorRemote.size ());
                  theBrahmaDisplayForm.setTextRemote (ToBrahmaDisplay);

                  sleep (500);
              }
        }
        catch (Exception e)
        {
            // Ignore
            e.printStackTrace ();
        }
    }

    public static void main (String args[]) throws Exception
    {
        dam = new Vector ();
        for (int i = 0; i < args.length; i++)
          {
              String s = args[i];
               dam.add (s);
          }
        // System.out.println ("Length=" + dam.size ());
        W_BrahmaDisplayForm a = new W_BrahmaDisplayForm (dam, dam, dam);

        while (true) ;

    }
}
