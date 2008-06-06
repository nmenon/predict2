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
import network.*;

/** The Network Analyser will analyse data information that is derived from the info
  */
public class networkAnalyzer extends networkStats
{
    private static Vector d;
    private netInfo next;

    public networkAnalyzer ()
    {
        super ();
        d = new Vector ();
        DifferenceinTime = 0;
        OneWayTime = 0;
    }
    public networkAnalyzer (networkStats s)
    {
        super ();
        DifferenceinTime = s.DifferenceinTime;
        OneWayTime = s.OneWayTime;
        d = new Vector ();
        DifferenceinTime = 0;
        OneWayTime = 0;
    }


  /** Change this is we are changing the network stats
  */
    public networkStats getStats ()
    {
        networkStats s = new networkStats ();

        s.DifferenceinTime = DifferenceinTime;
        s.OneWayTime = OneWayTime;
        return s;
    }

    public void addTime (long mytimeDL, long mytimeD, long histime)
    {
        // System.out.println ("Recieved time=" + mytimeD + " Last recieved
        // time=" +
        // mytimeDL + " Send time=" + histime);
        // double away = mytimeD - CurrT;
        // System.out.println ("Send " + away + " millis after start");
        double oneway = (mytimeD - mytimeDL) / 2;

        next = new netInfo ();
        next.OneWayTime = oneway;
        next.DifferenceTime = histime - (mytimeD - oneway);
        // System.out.println ("OneWay=" + next.OneWayTime + " Diff=" +
        // next.DifferenceTime);
        d.add (next);
        netInfo s = (netInfo) d.lastElement ();

        // System.out.println (" ->s dif=" + s.DifferenceTime + " one=" +
        // s.OneWayTime + "Size of D=" + d.size ());
    }

    public void addstats (networkStats s) throws Exception
    {
        next = new netInfo ();
        next.OneWayTime = s.OneWayTime;
        next.DifferenceTime = s.DifferenceinTime;
    }

    public void analyse () throws Exception
    {
        if (d == null)
            return;
        if (d.size () == 0)
            return;
        double SumofDiff = 0;
        double SumofOneWay = 0;
        int count = 0;
        if (d == null)
             return;
         d.remove (d.firstElement ());  // The First Value inserted is off
        // balance inherantly we ignore it
        // Proper stat tech such as chi square analysis can be done but I am
        // not doing it here
        // Variance needs a statistical approximation of the variance - Above
        // which we
        // Throw an exception and quit and below which we may either take the
        // mean or the Mode (or median)
         count = 0;
        while (d.size () != count)
          {
              netInfo s = (netInfo) d.elementAt (count);
               SumofDiff += s.DifferenceTime;
               SumofOneWay += s.OneWayTime;
              // System.out.println (count + ": Sums - OneWay=" + SumofOneWay +
              // " Diff=" + SumofDiff + " ->s dif=" +
              // s.DifferenceTime + " one=" + s.OneWayTime);
              // d.remove(0);
               count++;
          }
        count--;

        DifferenceinTime = SumofDiff / count;
        OneWayTime = SumofOneWay / count;
    }
}
