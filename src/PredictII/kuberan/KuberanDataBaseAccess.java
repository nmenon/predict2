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


/*
 * This is the DataBase Access to the DataBase for the Kuberan UseCase
 */

// You need to import the java.sql package to use JDBC
package kuberan;

import kuberan.*;
import utils.*;
import utils.misc.*;
import messages.*;
import java.sql.*;
import java.util.*;

/** This class is Meant for the Kuberan Use case's Data base access.
 * Though it Uses Oracle Connection String,
 * The porting should be easy as to juct changing the connection String
 */
class KuberanDataBaseAccess
{
    // The Password Handlers
    Connection DataConn_AddPasswordEntry;
    Connection DataConn_DeletePasswordEntry;
    Connection DataConn_UpdatePasswordEntry;
    Connection DataConn_GetPasswordEntry;

    // The Transaction Handlers
    Connection DataConn_AddTransactionEntry;
    Connection DataConn_DeleteTransactionEntry;
    Connection DataConn_GetTransactionEntry;
    Connection DataConn_GetTransactionEntryCond;

    // The Personal Information Handlers
    Connection DataConn_AddPersonalInformationEntry;
    Connection DataConn_DeletePersonalInformationEntry;
    Connection DataConn_UpdatePersonalInformationEntry;
    Connection DataConn_GetPersonalInformationEntry;

    // The Statements for the Password Entry Handlers
    PreparedStatement Stmt_AddPasswordEntry;
    PreparedStatement Stmt_DeletePasswordEntry;
    PreparedStatement Stmt_UpdatePasswordEntry;
    PreparedStatement Stmt_GetPasswordEntry;

    // The Statements for the Transaction Handlers
    PreparedStatement Stmt_AddTransactionEntry;
    PreparedStatement Stmt_DeleteTransactionEntry;
    PreparedStatement Stmt_GetTransactionEntry;
    PreparedStatement Stmt_GetTransactionEntryCond;

    // The Statements for the Personal Information Handlers
    PreparedStatement Stmt_AddPersonalInformationEntry;
    PreparedStatement Stmt_DeletePersonalInformationEntry;
    PreparedStatement Stmt_UpdatePersonalInformationEntry;
    PreparedStatement Stmt_GetPersonalInformationEntry;

    public KuberanDataBaseAccess (KuberanInitialize Init) throws Exception
    {

        if (Init == null)
            throw (new Exception ("Initialiser is Null"));
        String DataString = Init.getDataBaseConnectionString ();
        String User = Init.getDataBaseUserName ();
        String Password = Init.getDataBaseUserPassword ();

        if (DataString == null || User == null || Password == null)
             throw (new
                    Exception (" Null in the Parameters Passed;DataString=" + DataString +
                               " UserName=" + User + " Password=" + Password));

         System.out.
            println
            ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ___DataBase____registering Driver____");
         DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver ());

         System.out.
            println
            ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ___DataBase____Creating Connection  1");
         DataConn_AddPasswordEntry = DriverManager.getConnection (DataString, User, Password);
         System.out.
            println
            ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ___DataBase____Creating Connection  2");
         DataConn_DeletePasswordEntry =
            DriverManager.getConnection (DataString, User, Password);
         System.out.
            println
            ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ___DataBase____Creating Connection  3");
         DataConn_UpdatePasswordEntry =
            DriverManager.getConnection (DataString, User, Password);
         System.out.
            println
            ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ___DataBase____Creating Connection  4");
         DataConn_GetPasswordEntry = DriverManager.getConnection (DataString, User, Password);
         System.out.
            println
            ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ___DataBase____Creating Connection  5");
         DataConn_AddTransactionEntry =
            DriverManager.getConnection (DataString, User, Password);
         System.out.
            println
            ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ___DataBase____Creating Connection  6");
         DataConn_GetTransactionEntry =
            DriverManager.getConnection (DataString, User, Password);
         System.out.
            println
            ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ___DataBase____Creating Connection  7");
         DataConn_DeleteTransactionEntry =
            DriverManager.getConnection (DataString, User, Password);
         System.out.
            println
            ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ___DataBase____Creating Connection  8");
         DataConn_GetTransactionEntryCond =
            DriverManager.getConnection (DataString, User, Password);
         System.out.
            println
            ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ___DataBase____Creating Connection  9");
         DataConn_AddPersonalInformationEntry =
            DriverManager.getConnection (DataString, User, Password);
         System.out.
            println
            ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ___DataBase____Creating Connection  10");
         DataConn_DeletePersonalInformationEntry =
            DriverManager.getConnection (DataString, User, Password);
         System.out.
            println
            ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ___DataBase____Creating Connection  11");
         DataConn_UpdatePersonalInformationEntry =
            DriverManager.getConnection (DataString, User, Password);
         System.out.
            println
            ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ___DataBase____Creating Connection  12");
         DataConn_GetPersonalInformationEntry =
            DriverManager.getConnection (DataString, User, Password);

         System.out.
            println
            ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ___DataBase____Connections Established. preparing Statements..");
        // Passwoprd Table modification
         Stmt_AddPasswordEntry =
            DataConn_AddPasswordEntry.
            prepareStatement
            ("Insert into PASSWORD (USERNAME,PASSPHRASE,FROZEN) values (?,?,?)");
         Stmt_DeletePasswordEntry =
            DataConn_DeletePasswordEntry.
            prepareStatement ("Delete from PASSWORD where USERNAME=?");
         Stmt_UpdatePasswordEntry =
            DataConn_UpdatePasswordEntry.
            prepareStatement ("Update PASSWORD set PASSPHRASE=?, FROZEN=? where USERNAME=?");
         Stmt_GetPasswordEntry =
            DataConn_GetPasswordEntry.
            prepareStatement ("Select PASSPHRASE,FROZEN from PASSWORD where USERNAME=?");
        // Transaction Entry Modification
         Stmt_AddTransactionEntry =
            DataConn_AddTransactionEntry.
            prepareStatement
            ("Insert into TRANSACTION (USERNAME,CREDIT_DEBIT,AMOUNT,CONNECTED_FROM_IP,TIMSTAMP,IDENTIFIER_USED) values (?,?,?,?,?,?)");
         Stmt_DeleteTransactionEntry =
            DataConn_DeleteTransactionEntry.
            prepareStatement ("Delete from TRANSACTION where USERNAME=?");
         Stmt_GetTransactionEntry =
            DataConn_GetTransactionEntry.
            prepareStatement
            ("select CREDIT_DEBIT,AMOUNT,CONNECTED_FROM_IP,TIMSTAMP,IDENTIFIER_USED from TRANSACTION where USERNAME=?");
         Stmt_GetTransactionEntryCond =
            DataConn_GetTransactionEntryCond.prepareStatement
            ("select CREDIT_DEBIT,AMOUNT,CONNECTED_FROM_IP,TIMSTAMP,IDENTIFIER_USED from TRANSACTION where ?");
        // Personal Information Modification
         Stmt_AddPersonalInformationEntry =
            DataConn_AddPersonalInformationEntry.
            prepareStatement
            ("Insert into PERSONALINFORMATION (USERNAME,MONEYBALANCE) values (?,?)");
         Stmt_DeletePersonalInformationEntry =
            DataConn_DeletePersonalInformationEntry.
            prepareStatement ("Delete from PERSONALINFORMATION where USERNAME=?");
         Stmt_UpdatePersonalInformationEntry =
            DataConn_UpdatePersonalInformationEntry.
            prepareStatement
            ("Update PERSONALINFORMATION set MONEYBALANCE=? where USERNAME=?");
         Stmt_GetPersonalInformationEntry =
            DataConn_GetPersonalInformationEntry.
            prepareStatement
            ("Select MONEYBALANCE  from PERSONALINFORMATION where USERNAME=?");
         System.out.
            println
            ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ___DataBase____Statement Preperation Complete");

    }

    /** Closing a Connection */
    private void quietClose (Connection c)
    {
        try
        {
            c.close ();
        }
        catch (Exception e)
        {
            // Ignore this
        }
    }

    /** Close a Prepared Statement*/
    private void quietClose (PreparedStatement s)
    {
        try
        {
            s.close ();
        }
        catch (Exception e)
        {
            // Ignore this
        }
    }
    private String convBoolean (boolean isFrozen)
    {
        return ((isFrozen) ? "t" : "f");
    }
//***************************Start Of Access Methods

            /** Access method for the AddPassword Entry.
             * ("Insert into PASSWORD (USERNAME,PASSPHRASE,FROZEN) values (?,?,?)")
             */
    synchronized void AddPasswordEntry (String UserName, String PassPhrase,
                                        boolean isFrozen) throws Exception
    {
        System.out.println ("adding To Pssword-" + UserName + "PassPhrase=" + PassPhrase +
                            "is Frozen=" + isFrozen);
        if (UserName == null || PassPhrase == null)
            throw (new
                   Exception ("Null value in Argument UserName=" + UserName + "Pass Phrase=" +
                              PassPhrase));
        // See if the User was present
        Vector d = GetPasswordEntry (UserName);
        if (d.size () != 0)
             throw (new Exception ("Entry for UserName=" + UserName + " Already Present"));
         Stmt_AddPasswordEntry.setString (1, UserName);
         Stmt_AddPasswordEntry.setString (2, PassPhrase);
         Stmt_AddPasswordEntry.setString (3, convBoolean (isFrozen));
         Stmt_AddPasswordEntry.execute ();
    }

            /** Access method for the DeletePassword Entry.
             * ("Delete from PASSWORD where USERNAME=?");
             */
    synchronized void DeletePasswordEntry (String UserName) throws Exception
    {
        System.out.println ("deleting Password Entry-" + UserName);
        if (UserName == null)
            throw (new Exception ("Null value in the Parameter UserName=" + UserName));
        Stmt_DeletePasswordEntry.setString (1, UserName);
        Stmt_DeletePasswordEntry.execute ();
    }

            /** Access method for the UpdatePassword Entry.
             * ("Update PASSWORD set PASSPHRASE=?, FROZEN=? where USERNAME=?");
             */
    synchronized void UpdatePasswordEntry (String UserName, String PassPhrase,
                                           boolean isFrozen) throws Exception
    {
        System.out.println ("Update Password- " + UserName + " PassPhrase" + PassPhrase +
                            "Is Frozn=" + isFrozen);
        if (UserName == null || PassPhrase == null)
            throw (new
                   Exception ("Null value in Argument UserName=" + UserName + "Pass Phrase=" +
                              PassPhrase));
        Stmt_UpdatePasswordEntry.setString (1, PassPhrase);
        Stmt_UpdatePasswordEntry.setString (2, convBoolean (isFrozen));
        Stmt_UpdatePasswordEntry.setString (3, UserName);
        Stmt_UpdatePasswordEntry.execute ();
    }

            /** Access method for the GetPassword Entry.
             * ("Select PASSPHRASE,FROZEN from PASSWORD where USERNAME=?");
             */
    synchronized Vector GetPasswordEntry (String UserName) throws Exception
    {
        System.out.println ("Searching Password for" + UserName);
        if (UserName == null)
            throw (new Exception ("Null value in the Parameter UserName=" + UserName));
        Stmt_GetPasswordEntry.setString (1, UserName);
        ResultSet rset = Stmt_GetPasswordEntry.executeQuery ();
        Vector d = new Vector ();
        while (rset.next ())
          {
              String g = rset.getString (1);
              String s = rset.getString (2);
               d.add (g);
               d.add (s);
          }
        return d;

    }

            /** Access method for the AddTransactionEntry Entry.
             * ("Insert into TRANSACTION (USERNAME,CREDIT_DEBIT,AMOUNT,CONNECTED_FROM_IP,TIMSTAMP,IDENTIFIER_USED) values (?,?,?,?,?,?)");
             */
    synchronized void AddTransactionEntry (String UserName, boolean isCredit, float AMOUNT,
                                           String Connected_From_Ip, long TIMSTAMP,
                                           String IDENTIFIER_USED) throws Exception
    {
        System.out.println ("Transaction Entry-" + UserName + "isCredit=" + isCredit +
                            " Amount=" + AMOUNT + "Connected_From_Ip=" + Connected_From_Ip +
                            " TimeStamp=" + TIMSTAMP + "IDENTIFIER_USED=" + IDENTIFIER_USED);
        if (UserName == null || Connected_From_Ip == null || IDENTIFIER_USED == null)
            throw (new
                   Exception ("Null in Parameters UserName=" + UserName +
                              " Connected_From_Ip=" + Connected_From_Ip + "IDENTIFIER_USED =" +
                              IDENTIFIER_USED));
        Stmt_AddTransactionEntry.setString (1, UserName);
        Stmt_AddTransactionEntry.setString (2, convBoolean (isCredit));
        Stmt_AddTransactionEntry.setDouble (3, AMOUNT);
        Stmt_AddTransactionEntry.setString (4, Connected_From_Ip);
        Stmt_AddTransactionEntry.setLong (5, TIMSTAMP);
        Stmt_AddTransactionEntry.setString (6, IDENTIFIER_USED);
        Stmt_AddTransactionEntry.execute ();

    }

            /** Access method for the DeleteTransaction Entry.
             * ("Delete from TRANSACTION where USERNAME=?");
             */
    synchronized void DeleteTransactionEntry (String UserName) throws Exception
    {
        System.out.println ("deleting Transaction Entry-" + UserName);
        if (UserName == null)
            throw (new Exception ("Null value in the Parameter UserName=" + UserName));
        Stmt_DeleteTransactionEntry.setString (1, UserName);
        Stmt_DeleteTransactionEntry.execute ();
    }


            /** Access method for the GetTransactionEntry Entry.
             * ("select CREDIT_DEBIT,AMOUNT,CONNECTED_FROM_IP,TIMSTAMP,IDENTIFIER_USED from TRANSACTION where USERNAME=?");
             */
    synchronized Vector GetTransactionEntry (String UserName) throws Exception
    {
        System.out.println ("Searching Transaction for" + UserName);
        if (UserName == null)
            throw (new Exception ("Null value in the Parameter UserName =" + UserName));
        Stmt_GetTransactionEntry.setString (1, UserName);
        ResultSet rset = Stmt_GetTransactionEntry.executeQuery ();
        Vector d = new Vector ();
        while (rset.next ())
          {
              d.add (rset.getString (1));
              d.add (rset.getString (2));
              d.add (rset.getString (3));
              d.add (rset.getString (4));
              d.add (rset.getString (5));
          }
        return d;

    }

            /** Access method for the GetTransactionEntryCond .
             * ("select CREDIT_DEBIT,AMOUNT,CONNECTED_FROM_IP,TIMSTAMP,IDENTIFIER_USED from TRANSACTION where ?");
             */
    synchronized Vector GetTransactionEntryCond (String Condition) throws Exception
    {
        System.out.println ("Searching TransactionConditional for" + Condition);
        if (Condition == null)
            throw (new Exception ("Null value in the Parameter UserName=" + Condition));
        Stmt_GetTransactionEntryCond.setString (1, Condition);
        ResultSet rset = Stmt_GetTransactionEntryCond.executeQuery ();
        Vector d = new Vector ();
        while (rset.next ())
          {
              d.add (rset.getString (1));
              d.add (rset.getString (2));
              d.add (rset.getString (3));
              d.add (rset.getString (4));
              d.add (rset.getString (5));
              d.add (rset.getString (6));
          }
        return d;

    }

            /** Access method for the AddPersonalInformationEntry .
             * ("Insert into PERSONALINFORMATION (USERNAME,MONEYBALANCE) values (?,?)");
             */
    synchronized void AddPersonalInformationEntry (String UserName,
                                                   float MoneyBalance) throws Exception
    {
        System.out.println ("Adding to PersonalInformation " + UserName + "MoneyBalance=" +
                            MoneyBalance);
        if (UserName == null)
            throw (new Exception ("Null value in the Parameter UserName=" + UserName));

        // See if the User was present
        Vector d = GetPersonalInformationEntry (UserName);
        if (d.size () != 0)
             throw (new Exception ("Entry for UserName=" + UserName + " Already Present"));
         Stmt_AddPersonalInformationEntry.setString (1, UserName);
         Stmt_AddPersonalInformationEntry.setDouble (2, MoneyBalance);
         Stmt_AddPersonalInformationEntry.execute ();
    }

            /** Access method for the DeletePersonalInformationEntry .
             * ("Delete from PERSONALINFORMATION where USERNAME=?");
             */
    synchronized void DeletePersonalInformationEntry (String UserName) throws Exception
    {
        System.out.println ("deleting PersonalInformation Entry-" + UserName);
        if (UserName == null)
            throw (new Exception ("Null value in the Parameter UserName=" + UserName));
        Stmt_DeletePersonalInformationEntry.setString (1, UserName);
        Stmt_DeletePersonalInformationEntry.execute ();
    }

            /** Access method for the UpdatePersonalInformationEntry .
             * ("Update PERSONALINFORMATION set MONEYBALANCE=? where USERNAME=?");
             */
    synchronized void UpdatePersonalInformationEntry (String UserName,
                                                      float MoneyBalance) throws Exception
    {
        System.out.println ("Updating to PersonalInformation " + UserName + "MoneyBalance=" +
                            MoneyBalance);
        if (UserName == null)
            throw (new Exception ("Null value in the Parameter UserName=" + UserName));
        Stmt_UpdatePersonalInformationEntry.setDouble (1, MoneyBalance);
        Stmt_UpdatePersonalInformationEntry.setString (2, UserName);
        Stmt_UpdatePersonalInformationEntry.execute ();
    }

            /** Access method for the GetPersonalInformationEntry .
             * ("Select MONEYBALANCE  from PERSONALINFORMATION where USERNAME=?");
             */
    synchronized Vector GetPersonalInformationEntry (String UserName) throws Exception
    {
        System.out.println ("Searching PersonalInformation for" + UserName);
        if (UserName == null)
            throw (new Exception ("Null value in the Parameter UserName=" + UserName));
        Stmt_GetPersonalInformationEntry.setString (1, UserName);
        Stmt_GetPersonalInformationEntry.setString (1, UserName);
        ResultSet rset = Stmt_GetPersonalInformationEntry.executeQuery ();
        Vector d = new Vector ();
        while (rset.next ())
          {
              String s = rset.getString (1);
               d.add (s);
          }
        return d;

    }
//***************************End of Access Methods

    /** The Basic Closer of all connections */
    public void close ()
    {
        // Close the Statements First
        quietClose (Stmt_AddPasswordEntry);
        quietClose (Stmt_AddTransactionEntry);
        quietClose (Stmt_AddPersonalInformationEntry);
        quietClose (Stmt_DeletePasswordEntry);
        quietClose (Stmt_DeletePersonalInformationEntry);
        quietClose (Stmt_UpdatePasswordEntry);
        quietClose (Stmt_UpdatePersonalInformationEntry);
        quietClose (Stmt_GetPasswordEntry);
        quietClose (Stmt_DeleteTransactionEntry);
        quietClose (Stmt_GetTransactionEntry);
        quietClose (Stmt_GetTransactionEntryCond);
        quietClose (Stmt_GetPersonalInformationEntry);
        // Close the Connections Next
        quietClose (DataConn_AddPasswordEntry);
        quietClose (DataConn_DeletePasswordEntry);
        quietClose (DataConn_UpdatePasswordEntry);
        quietClose (DataConn_GetPasswordEntry);
        quietClose (DataConn_AddTransactionEntry);
        quietClose (DataConn_DeleteTransactionEntry);
        quietClose (DataConn_GetTransactionEntry);
        quietClose (DataConn_GetTransactionEntryCond);
        quietClose (DataConn_AddPersonalInformationEntry);
        quietClose (DataConn_DeletePersonalInformationEntry);
        quietClose (DataConn_UpdatePersonalInformationEntry);
        quietClose (DataConn_GetPersonalInformationEntry);
    }


    /** This is testing Code*/
    public static void main (String args[]) throws Exception
    {
        logger theLog = new logger ("D.log");
        KuberanInitialize j = new KuberanInitialize ("conf/kuberantest.conf");
        KuberanDataBaseAccess a = new KuberanDataBaseAccess (j);
        // Insert an User
         a.AddPasswordEntry ("Nishanth", "pioda", true);    // Okay
        Vector d = a.GetPasswordEntry ("Nishanth");
        for (int k = 0; k < d.size (); k++)
             System.out.println (d.elementAt (k));  // Okay
         a.UpdatePasswordEntry ("Nishanth", "poda", false); // Okay
         d = a.GetPasswordEntry ("Nishanth");
        for (int k = 0; k < d.size (); k++)
             System.out.println (d.elementAt (k));  // Okay Password Test
        long timeStamp = System.currentTimeMillis ();
         a.AddTransactionEntry ("Nishanth", true, (float) 10.22, "192.168.10.100", timeStamp, "Mir;;Rs;;123123123123123");  // Okay
         d = a.GetTransactionEntry ("Nishanth");
        for (int k = 0; k < d.size (); k++)
             System.out.println (d.elementAt (k));  // Okay for Now
         a.DeleteTransactionEntry ("Nishanth"); // Okay 

        // Now the Personal Info Stuff
         a.AddPersonalInformationEntry ("Nishanth", (float) 10.2323);
         d = a.GetPersonalInformationEntry ("Nishanth");
        for (int k = 0; k < d.size (); k++)
             System.out.println (d.elementAt (k));
        float aa = (float) 1231312321.312312312;
         System.out.println ("Me put in " + aa);
         a.UpdatePersonalInformationEntry ("Nishanth", aa);
         d = a.GetPersonalInformationEntry ("Nishanth");
        for (int k = 0; k < d.size (); k++)
             System.out.println (d.elementAt (k));
         a.DeletePersonalInformationEntry ("Nishanth");


         a.DeletePasswordEntry ("Nishanth");    // Okay

         a.close ();
         System.exit (0);
    }
}
