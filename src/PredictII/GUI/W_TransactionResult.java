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

/** The Wrapper Class for the Transaction Result.
 * This is the wrapper for the Transaction Resul.
 */

public class W_TransactionResult
{

    public W_TransactionResult (boolean Result)
    {
        TransactionResult a;
        if (!Result)
             a = new TransactionResult (new javax.swing.JFrame (), true,
                                        "images/transaction_failed.jpg");
        else
             a = new TransactionResult (new javax.swing.JFrame (), true,
                                        "images/transaction_success.jpg");
         a.show ();
         System.exit (0);

    }

    public static void main (String[]args) throws Exception
    {

        W_TransactionResult a;

        if (args.length == 0)
             a = new W_TransactionResult (true);
        else
             a = new W_TransactionResult (false);
    }
}
