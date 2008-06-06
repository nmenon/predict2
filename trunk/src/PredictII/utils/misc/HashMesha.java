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

//Source file: Y:/E-Cash-Project/TestBed/development/utils/misc/HashMesha.java

package utils.misc;

import java.util.*;

/**
 * @author 
 * @version 
 * 
 * The Hashtable extension to the Some extra
 */
public class HashMesha extends Hashtable
{

    public HashMesha ()
    {
        super ();
    }

   /**
    * @param initialCapacity
    * @return 
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B90FD3A031F
    */
    public HashMesha (int initialCapacity)
    {
        super (initialCapacity);
    }

   /**
    * @param Key
    * @return void
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B90FD7D000E
    */
    public void delete (String Key)
    {
        Object t = super.remove ((Key == null) ? Key : Key.trim ());
    }

   /**
    * @param Key
    * @return Object
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B90FDE40021
    */
    public Object get (String Key)
    {
        return (super.get (((Key == null) ? Key : Key.trim ())));
    }

   /**
    * @param Key
    * @return boolean
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B90FE2F0097
    */
    public boolean isthere (String Key)
    {
        return (super.contains ((Key == null) ? Key : Key.trim ()));
    }

   /**
    * @param Key
    * @param member
    * @return boolean
    * @exception Exception
    * @author 
    * @version 
    * @roseuid 3B90FE680251
    */
    public boolean put (String Key, Object member) throws Exception
    {
        if (Key == null)
            throw (new Exception ("Attempt of putting in a null key!!!"));
        Key = Key.trim ();

        if (!super.containsKey (Key))
          {
              super.put (Key, member);
              return (true);
          }
        else
             return (false);
    }

   /**
    * @return void
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B90FEAA01DE
    */
    protected void finalizer ()
    {
        super.clear ();
    }
}
