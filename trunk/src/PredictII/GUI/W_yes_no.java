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

package GUI;

/**
 * Wrapper Class for the Yes_no class.
 * This class has the Job of wrapping the yes_no class so that the Size is proper.
 */
public class W_yes_no extends yes_no
{

    /** Use this  Method to get the information displayed and the result got.
     * @param Messsage The Message that will Be Asked to the User
     * @return boolean Say true is The User said Yes and false if the User Said no
     * @version 1.01
     */
    public W_yes_no (String Message) throws Exception
    {
        super (new java.awt.Frame (), true, Message);

        setSize (400, 340);
        setLocation (200, 300);
        Sta ();
        // SomeIdiot.resize(false);
        show ();
        System.out.println ("You Selected " + getResult ());
        dispose ();
    }

    /** Test Code*/
    public static void main (String[]args) throws Exception
    {
        W_yes_no a = new W_yes_no ("Are U dead?");
         System.out.println ("Are U dead?" + a.getResult ());
         System.exit (0);
    }
}
