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

//Source file: Y:/E-Cash-Project/TestBed/development/utils/misc/Hash_Name_IP_And_Key.java

package utils.misc;

import org.logi.crypto.keys.CipherKey;

/**
 * @author 
 * @version 
 * This Hash table Contains the File Name where the Public Key is Stored +The IP+ name of the Client Computer
 */
public class Hash_Name_IP_And_Key
{
    public String IP;
    public String Name;
    public CipherKey Key;


    public Hash_Name_IP_And_Key ()
    {
        IP = null;
        Name = null;
        Key = null;
    }

   /**
    * @return String
    * @exception 
    * @author 
    * @version 
    * @roseuid 3B90E44C00E8
    */
    public String toString ()
    {
        return (IP + "\n" + Name + "\n" + Key);
    }
}
