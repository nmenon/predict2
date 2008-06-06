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

/**
   This I Have Special Thanks to Tell to
   Bruce Eckel
   This class basically deleted the Required Set of Files
   From a Path given
  */
public class FileDeleter
{

    FileDeleter (String Path, String Filter) throws Exception
    {
        System.out.println ("Path=" + Path + " Filter=" + Filter);
        File path = new File (Path);
        File F;
         String[] list;

        if (Path == null)
             list = path.list ();
        else
             list = path.list (new DirFilter (Path, Filter));
//      System.out.println ("Length=" + list.length);
        for (int i = 0; i < list.length; i++)
          {
//      System.out.println ("Deleting" + list[i]);
              F = new File (list[i]);
              F.delete ();
          }
    }


    public static void main (String[]args) throws Exception
    {
        FileDeleter H = new FileDeleter (".", "txt");
    }
}

class DirFilter implements FilenameFilter
{
    String afn;
    String Filter;

     DirFilter (String afn, String Filter)
    {
        // System.out.println ("Filer=" + afn);
        this.afn = afn;
        this.Filter = Filter;
    }


    public boolean accept (File dir, String name)
    {
        // Strip path information:
        String f = new File (name).getName ();

        if (f.endsWith (Filter))
          {
              return f.indexOf (afn) != -1;
          }
        else
            return (false);
    }
}
