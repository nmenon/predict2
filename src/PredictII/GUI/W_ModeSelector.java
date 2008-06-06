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

/** Wrapper Class for the ModeSelector.
   * THis is the Wrapper for the ModeSelector
   * @version 1.01
   */
public class W_ModeSelector extends ModeSelector
{
    public W_ModeSelector () throws Exception
    {

        super (new javax.swing.JFrame (), true);
        setSize (710, 420);
        setLocation (200, 200);
        show ();
    }

    /** returns String
     * Returns the Mode of the User
     */
    public String getResult ()
    {
        return Result;
    }

    public String getMode ()
    {
        return Result;
    }

    /** Testing Purpose only
    public static void main (String[]args) throws Exception
    {
        W_ModeSelector a = new W_ModeSelector ();
         System.out.println ("The Slected Mode is " + a.getResult ());
         System.exit (0);
   }
   */
}
