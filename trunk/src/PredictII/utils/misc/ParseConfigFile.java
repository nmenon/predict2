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

package utils.misc;

import java.io.*;
import java.util.*;
import messages.tools.*;

/**
 * The Configuration File Reader.
 * Creates a Name Value Pair in a Vector
 * This Reads the file and parses the file and closes it.
 * May or maynot throw Exception
 * Default Delimiter for the Configuration file Name Value Pair is '='
 * Configuration file may contains comments too Default coment definer is '#'
 * @version 1.002 
 * @author Nishanth 'Lazarus' Menon
 */
public class ParseConfigFile extends StringFileRead
{
    Hashtable d;
    String ConfFileName;
    boolean strict;
    String Delimiter = "=";
    char Comment = '#';

    int lineNum = 1;

    /** This Generates the Hash Table that is retrieved. Called by the Constructor*/
    private void generateList () throws Exception
    {
        String Line = readln ();
        parseMessage ra = new parseMessage ();
        while (Line != null)
          {
              // Parse the Data into Name Value Pair
              Line = Line.trim ();
              if (!(Line.length () == 0))
                  if (!(Line.charAt (0) == Comment))
                    {
                        String[]g = ra.parse (Line, Delimiter);
                        if (g.length == 0)
                          {
                              String Err =
                                  "File " + ConfFileName + ": Line Number " + lineNum +
                                  " Has a null value";
                              if (strict)
                                   throw (new Exception (Err));
                              else
                                {
                                    System.err.println (Err +
                                                        " Lax Checking ..Ignoring This Line...");
                                    lineNum++;
                                    Line = readln ();
                                    continue;
                                }
                          }
                        if (g.length != 2)
                          {
                              String Err =
                                  "File " + ConfFileName +
                                  ": Invalid number of Parameters in Line number " + lineNum;
                              if (strict)
                                  throw (new Exception (Err));
                              else
                                {
                                    System.err.println (Err +
                                                        " Lax Checking ..Ignoring This Line...");
                                    lineNum++;
                                    Line = readln ();
                                    continue;
                                }
                          }
                        String Name = g[0].trim ();
                        String Value = g[1].trim ();

                        // System.out.println ("Name Value in Line=" + lineNum
                        // + "NAME====" +
                        // Name + " VALUE=====" + Value);
                        if (d.containsKey (Name))
                          {
                              String Err =
                                  "File " + ConfFileName + ": Redefinition of " + Name +
                                  " in line " + lineNum;

                              if (strict)
                                  throw (new Exception (Err));
                              else
                                {
                                    System.err.println (Err +
                                                        " Lax Checking ..Ignoring This Line...");
                                    lineNum++;
                                    Line = readln ();
                                    continue;
                                }
                          }
                        d.put (Name, Value);
                    }
              lineNum++;
              Line = readln ();
          }
    }

    /** Retrieve the Set of Name Value Pairs Found in the File as a Hashtable*/
    public Hashtable getList ()
    {
        return d;
    }

    /** The Constructor.
     * This Reads the file and parses the file and closes it.
     * May or maynot throw Exception
     * Default Delimiter for the Configuration file Name Value Pair is '='
     * Configuration file may contains comments too Default coment definer is '#'
     * @param FileName Type - String Must be the Filename of the Configuration file
     * @param Strict Type - boolean  This tells us whether to throw Exception on error in the Value Parsed.
     * @version 1.002 
     * @author Nishanth 'Lazarus' Menon
     */
    public ParseConfigFile (String FileName, boolean Strict) throws Exception
    {
        super (FileName);
        ConfFileName = FileName;
        d = new Hashtable ();
        strict = Strict;
        generateList ();
        close ();
    }

    /** This is Basically a tester. Use it with a config file name as argument*/
    public static void main (String[]args) throws Exception
    {
        ParseConfigFile a = new ParseConfigFile (args[0], true);
        Hashtable g = a.getList ();
        Enumeration e = g.keys ();
        while (e.hasMoreElements ())
          {
              String Name = e.nextElement ().toString ();
              String Value = (String) g.get (Name);
               System.out.println ("Name=" + Name + "Value=" + Value);
          }
    }
}
