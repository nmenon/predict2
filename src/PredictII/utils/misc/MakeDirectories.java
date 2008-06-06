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

//: MakeDirectories.java
// Demonstrates the use of the File class to
// create directories and manipulate files.
import java.io.*;

public class MakeDirectories
{
    private final static String usage =
        "Usage:MakeDirectories path1 ...\n" +
        "Creates each path\n" +
        "Usage:MakeDirectories -d path1 ...\n" +
        "Deletes each path\n" +
        "Usage:MakeDirectories -r path1 path2\n" + "Renames from path1 to path2\n";
    private static void usage ()
    {
        System.err.println (usage);
        System.exit (1);
    }
    private static void fileData (File f)
    {
        System.out.println ("Absolute path: " + f.getAbsolutePath () +
                            "\n Can read: " + f.canRead () +
                            "\n Can write: " + f.canWrite () +
                            "\n getName: " + f.getName () +
                            "\n getParent: " + f.getParent () +
                            "\n getPath: " + f.getPath () +
                            "\n length: " + f.length () +
                            "\n lastModified: " + f.lastModified ());
        if (f.isFile ())
            System.out.println ("it's a file");
        else if (f.isDirectory ())
            System.out.println ("it's a directory");
    }
    public static void main (String[]args)
    {
        if (args.length < 1)
            usage ();
        if (args[0].equals ("-r"))
          {
              if (args.length != 3)
                  usage ();
              File old = new File (args[1]), rname = new File (args[2]);

              old.renameTo (rname);
              fileData (old);
              fileData (rname);
              return;           // Exit main
          }
        int count = 0;
        boolean del = false;

        if (args[0].equals ("-d"))
          {
              count++;
              del = true;
          }
        for (; count < args.length; count++)
          {
              File f = new File (args[count]);

              if (f.exists ())
                {
                    System.out.println (f + " exists");
                    if (del)
                      {
                          System.out.println ("deleting..." + f);
                          f.delete ();
                      }
                }
              else
                {               // Doesn't exist
                    if (!del)
                      {
                          f.mkdirs ();
                          System.out.println ("created " + f);
                      }
                }
              fileData (f);
          }
    }
}                               // /:~ 
