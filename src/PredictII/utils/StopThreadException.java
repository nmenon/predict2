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


package utils;

import java.lang.*;

/** This Excepition is thrown when a thread is to be stopped
  */
public class StopThreadException extends Exception
{
    private String Message;

    /** Access Function*/
    public String getMessage ()
    {
        return Message;
    }

    /** Constructor Function Sets Message as Stop Thread Exception.
    */
    public StopThreadException ()
    {
        super ();
        Message = "Stop Thread Exception";
    }

    /** Constructor that sets message as that i give in Message
    */
    public StopThreadException (String Message)
    {
        this.Message = Message;
    }
}
