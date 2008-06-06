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
public class W_DisplayForm extends Thread
{
    DisplayForm theDisplayForm;
    Vector theVector;
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
            theDisplayForm.show ();
        }
    }
    public W_DisplayForm (Vector d) throws Exception
    {
        // System.out.println ("Constructor Called");
        if (d == null)
            throw (new Exception ("Null Vector Supplied"));

        theDisplayForm = new DisplayForm (new javax.swing.JFrame (), true);
        theVector = d;
        a = new anony ();
        a.start ();
        start ();
    }
    public void run ()
    {
        System.out.println ("Runner is Running..");
        try
        {
            String ToDisplay = null;
            Date myDate = new Date ();
            Long l;

            while (true)
              {
                  l = new Long (System.currentTimeMillis ());
                  myDate.setTime (l.longValue ());
                  String myString = DateFormat.getDateTimeInstance (DateFormat.FULL,
                                                                    DateFormat.FULL).
                      format (myDate);
                  ToDisplay =
                      myString.trim () + ":" + l.toString () +
                      ":\nInformation of the Connections are...";
                  for (int i = 0; i < theVector.size (); i++)
                      ToDisplay = ToDisplay + "\n" + theVector.elementAt (i).toString ();
                  // System.out.println ("Display MessageMessage=" + ToDisplay
                  // + "asasdasd" +
                  // theVector.size ());
                  theDisplayForm.setText (ToDisplay);
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
        W_DisplayForm a = new W_DisplayForm (dam);

        while (true) ;

    }
}
