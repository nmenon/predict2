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

package brahma;
import brahma.*;
import utils.crypto.*;
import java.util.*;

public class listId
{
    public static DataBaseAccess a;
    public static void help ()
    {
        System.err.println ("Usage");
        System.err.println (" listUser");
        System.err.println ("");
        System.err.println ("");
    }
    private static void listit (Vector d)
    {
        for (int i = 0; i < d.size (); i++)
          {
              // try{
              // a.deleteID(d.elementAt(i).toString());
              // }
              // catch (Exception e){
              // System.out.println ("Trying to delete-"+d.elementAt(i)+"
              // exception "+e);
              // }
              System.out.println (d.elementAt (i));
          }
    }
    public static void main (String[]args) throws Exception
    {
        try
        {
            BrahmaInitialize j = new BrahmaInitialize (null);
             a = new DataBaseAccess (j);

             System.out.println ("------------------Password Entries-----------------");
            Vector d = a.searchAllIDs ();

             listit (d);

        }
        catch (Exception e)
        {
            System.err.println ("ERRROR---" + e);
            e.printStackTrace ();
            help ();
        }
        System.exit (0);
    }
}
