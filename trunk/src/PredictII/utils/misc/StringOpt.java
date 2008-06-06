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

//Source file: Y:/E-Cash-Project/TestBed/CryptoGraphy/Crypt/development/StringOpt.java

//MyPackagees
package utils.misc;

import java.util.*;
import java.io.*;
import java.lang.*;


import utils.misc.*;

/**
 * @author 
 * @version
 */
public class StringOpt
{

   /**
    * @param a
    * @param Size
    * @return byte[]
    * @exception Exception
    * @author 
    * @version 
    *      
    *      This Converts the String Input to an Array of byte
    *      
    *      Convertion Algo Works like this
    *      
    *      Each Character is converted into its byte Equivalent
    *      and split into the byte array
    * @roseuid 3B8DEEC602FE
    */
    public static byte[] StringToByte (String a, int Size) throws Exception
    {
        int i;
        byte b[] = new byte[Size];
        String s = a;

        // Check for the Block Size and the String Size
        if (Size < a.length ())
            // a = s.substring (0, Size);
             throw (new
                    Exception ("Block Size Less than String Length " + Size +
                               " < " + a.length () + "."));
        // Convert this to byte
        for (i = 0; i < a.length (); i++)
             b[i] = (byte) a.charAt (i);

        while (i < Size)
             b[i++] = 0;
         return b;
    }
    public static byte[] StringToByte (String a) throws Exception
    {
        return (StringToByte (a, a.length ()));
    }

   /**
    * @param b
    * @return String
    * @exception Exception
    * @author 
    * @version 
    *     
    *    This Converts a Array of byte - byte[] to String
    *    
    *     Convertion Algo Works like this
    *     
    *     Each byte is converted into its Character Equivalent
    *     and concatentated
    * @roseuid 3B8DEECD00CD
    */
    public static String byteToString (byte[]b) throws Exception
    {
        String c = "";

         try
        {
            // Convert Byte Array back to string
            for (int i = 0; i < b.length; i++)
                c = c.concat (String.valueOf ((char) b[i]));
            return c;
        }
        catch (Exception e)
        {
            throw (new Exception (e.toString ()));
        }
    }

   /**
    * @return void
    * @exception 
    * @author 
    * @version 
    * The Dumb Constructor
    * @roseuid 3B8DF17A01AE
    */
    public StringOpt ()
    {
    }
}
