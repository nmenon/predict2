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

//MyPackages
package utils.misc;

import java.io.*;
import java.lang.*;
import java.util.*;

/**
 * @author 
 * @version 
 * This Class Handles all the String Ops to a file.
 * 
 * I Have designed this class so that it will form the basis for all the file activities
 * 
 * Basic Writing to Files
 */
public class StringFileWrite
{
    private static RandomAccessFile Fr;

    public StringFileWrite ()
    {
    }

   /**
    * @param FileName
    * @param Append
    * @return void
    * @exception Exception
    * @author 
    * @version 
    * This Sets the File to writable mode.
    * @roseuid 3B90AB5E019F
    */
    private static void writeme (String FileName, boolean Append) throws Exception
    {

        File f = new File (FileName);
        long FileLength;

        // Test for readability and existance
        if (!f.exists ())
          {
              if (!f.createNewFile ())
                  throw (new
                         Exception ("File Not Present And Unable to Create File :" +
                                    FileName));
          }
        else if (!Append)
          {
              if (!f.delete ())
                  throw (new Exception ("Unable to delete :" + FileName));
              if (!f.createNewFile ())
                  throw (new Exception ("Unable to Create File:" + FileName));
          }

        // So we have the file to write and is size 0 :-)
        if (!f.canWrite ())
            throw (new Exception ("Unable to Write to File :" + FileName));

        Fr = new RandomAccessFile (FileName, "rw");
        if (Append)
            Fr.seek (Fr.length ());
    }

   /**
    * @return void
    * @exception 
    * @author 
    * @version 
    * The Destructor Function
    * @roseuid 3B90AD2B02A7
    */
    protected void finalize ()
    {
        // This Gets called when the object get collected by garbage. So If the 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // calling function has
        // already called close lets ignore any errors
        try
        {
            Fr.close ();
        }
        catch (Exception e)
        {
        }
    }

   /**
    * @param s
    * @return void
    * @exception Exception
    * @author 
    * @version 
    * Print String With No EOL Character at end. Does NOT remove EOL character
    * @roseuid 3B90ABF30172
    */
    public static void print (String s) throws Exception
    {
        // create Byte line if the EOL not present add it. then Write to the
        // File
        Fr.writeBytes (s);
    }

   /**
    * @param s
    * @return void
    * @exception Exception
    * @author 
    * @version 
    * Print the String with one EOL char at end
    * @roseuid 3B90AC2F0092
    */
    public static void println (String s) throws Exception
    {
        // create Byte line if the EOL not present add it. then Write to the
        // File
        Fr.writeBytes ((s.endsWith ("\n")) ? s : s + "\n");
    }

   /**
    * @param b
    * @return void
    * @exception Exception
    * @author 
    * @version 
    * We Might (Might) have a need to Write a byte array as such into the file. This method allows that
    * @roseuid 3B90AC9E02B8
    */
    public static void printByteArray (byte[]b) throws Exception
    {
        Fr.write (b);
    }

   /**
    * @param b
    * @return void
    * @exception Exception
    * @author 
    * @version 
    * Writes a byte to the File
    * @roseuid 3B90ACC60139
    */
    public static void write (int b) throws Exception
    {
        Fr.write (b);
    }

   /**
    * @return void
    * @exception Exception
    * @author 
    * @version 
    * Closes the File From Further I/O operations
    * @roseuid 3B90AD0300C8
    */
    public static void close () throws Exception
    {
        // Unshamedly Admission:
        // I have no trust of Java destructors!!

        Fr.close ();
    }

   /**
    * @param filename
    * @return void
    * @exception Exception
    * @author 
    * @version 
    * Constructor
    * @roseuid 3B90AD5D0281
    */
    public StringFileWrite (String filename) throws Exception
    {
        writeme (filename, false);
    }

   /**
    * @param filename
    * @param Append
    * @return void
    * @exception Exception
    * @author 
    * @version 
    * Constructor With Append Option
    * @roseuid 3B90ADA001FB
    */
    public StringFileWrite (String filename, boolean Append) throws Exception
    {
        writeme (filename, Append);
    }
}
