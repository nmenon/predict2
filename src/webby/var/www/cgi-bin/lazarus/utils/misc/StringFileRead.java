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

//Import the Junks
import java.lang.*;
import java.io.*;

import utils.misc.*;

/**
 * @author 
 * @version 
 * This Class Handles all the String Ops to a file.
 * 
 * I Have designed this class so that it will form the basis for all the file activities
 * 
 * Basic Reading from a File
 */
public class StringFileRead
{
    private static RandomAccessFile Fr;

    public StringFileRead ()
    {
    }

   /**
    * @param FileName
    * @return void
    * @exception Exception
    * @author 
    * @version 
    * This Enables the file For Reading
    * @roseuid 3B90B07C033E
    */
    private static void readme (String FileName) throws Exception
    {

        File f = new File (FileName);
        long FileLength;

        // Test for readability and existance
        if (!f.exists ())
          {
              throw (new Exception ("File Not Present :" + FileName));
          }

        // So we have the file to read
        if (!f.canRead ())
             throw (new Exception ("Unable to Write to File :" + FileName));

        Fr = new RandomAccessFile (FileName, "r");
    }

   /**
    * @return void
    * @exception 
    * @author 
    * @version 
    * The Desctructor- But U See One Can never trust these from Java- Gets called only when the Garbage collector comes in
    * @roseuid 3B90B0FE039F
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
    * @return String
    * @exception Exception
    * @author 
    * @version 
    * reads an Entire String from the File and returns it
    * @roseuid 3B90B1340180
    */
    public static String readln () throws Exception
    {

        return (Fr.readLine ());
    }

   /**
    * @return int
    * @exception Exception
    * @author 
    * @version 
    * Reads Just one char from the File
    * @roseuid 3B90B2AB0360
    */
    public static int read () throws Exception
    {
        return (Fr.read ());
    }

   /**
    * @return byte[]
    * @exception Exception
    * @author 
    * @version 
    * This Gets the Rest of the File from the Current File Pointer posn to the end of the file
    * into a single byte array
    * @roseuid 3B90B2EE0199
    */
    public static byte[] readRestByteArray () throws Exception
    {
        int rest = (int) Fr.length () - (int) Fr.getFilePointer ();
        byte b[] = new byte[rest];
        if (rest == 0)
             return (null);
         Fr.read (b);
         return (b);
    }

   /**
    * @return String
    * @exception Exception
    * @author 
    * @version 
    * This Gets the Rest of the File from the Current File Pointer posn to the end of the file
    * into a single String
    * @roseuid 3B90B32C0211
    */
    public static String readRestString () throws Exception
    {
        int rest = (int) Fr.length () - (int) Fr.getFilePointer ();
        byte b[] = new byte[rest];
        if (rest == 0)
             return (null);
         Fr.read (b);
         return (StringOpt.byteToString (b));
    }

   /**
    * @return void
    * @exception Exception
    * @author 
    * @version 
    * Close further I/O Ops to the File.
    * 
    * I Hate The Java Garbage collection. So there!
    * @roseuid 3B90B35A01E5
    */
    public static void close () throws Exception
    {
        // Unshamedly Admission:
        // I have no idea of Java destructors!!

        Fr.close ();
    }

   /**
    * @param FileName
    * @return void
    * @exception Exception
    * @author 
    * @version 
    * The Constructor..
    * @roseuid 3B90B37103DC
    */
    public StringFileRead (String FileName) throws Exception
    {
        readme (FileName);
    }
}
