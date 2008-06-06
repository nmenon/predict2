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

package kuberan;
import kuberan.*;
import utils.crypto.*;

public class addUser
{
    public static void help ()
    {
        System.err.println ("Usage");
        System.err.
            println
            (" addUser <UserName>, <UserPassPhrase>, <HashAlgo>,<Money to be deposited in his Account>");
        System.err.println ("");
        System.err.println ("");
    }
    public static void main (String[]args) throws Exception
    {
        if (args.length != 4)
          {
              System.err.println ("Arguments Not enough");
              help ();
              System.exit (1);
          }

        try
        {
            String UserName = args[0].trim ();
            String PassPhrase = args[1].trim ();
            String Hash_Algo = args[2].trim ();
            String Amount = args[3].trim ();
            String Hash_pass = hash.hash (PassPhrase, Hash_Algo);
            KuberanInitialize j = new KuberanInitialize ("conf/kuberantest.conf");
            KuberanDataBaseAccess a = new KuberanDataBaseAccess (j);

            System.out.println ("Adding to Password Table -" + UserName + " Hash=" +
                                Hash_pass);
            a.AddPasswordEntry (UserName, Hash_pass, false);    // Okay
            float b = new Float (Amount).floatValue ();

            System.out.println ("Adding to Personal information-" + UserName + " Amount=" + b);
            a.AddPersonalInformationEntry (UserName, b);
            a.AddTransactionEntry (UserName, true, b, "Deposit Amount",
                                   System.currentTimeMillis (), "DEPOSIT");
            System.out.println ("Completed Successfully");
            a.close ();
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
