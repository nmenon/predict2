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
 * This is the DataBase Access to the DataBase for the Brahma UseCase
 */

// You need to import the java.sql package to use JDBC
package brahma;
import brahma.*;
import utils.*;
import utils.misc.*;
import messages.*;
import java.sql.*;
import java.util.*;

/** This class is Meant for the Brahma Use case's Data base access.
 * Though it Uses Oracle Connection String,
 * The porting should be easy as to juct changing the connection String
 */
class DataBaseAccess
{
    Connection DataConn_DeleteID;
    Connection DataConn_SearchID;
    Connection DataConn_SearchID_1;
    Connection DataConn_SearchAllID;
    Connection DataConn_InsertID;
    Connection DataConn_InsertSTAT_CHK;
    Connection DataConn_InsertRAK;
    Connection DataConn_GetStat_Result;

    PreparedStatement Stmt_DeleteID;
    PreparedStatement Stmt_SearchID;
    PreparedStatement Stmt_SearchID_1;
    PreparedStatement Stmt_SearchAllID;
    PreparedStatement Stmt_InsertID;
    PreparedStatement Stmt_InsertSTAT_CHK;
    PreparedStatement Stmt_InsertRAK;
    PreparedStatement Stmt_GetStat_Result_1;

    public DataBaseAccess (BrahmaInitialize Init) throws Exception
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
         DataConn_DeleteID = DriverManager.getConnection (DataString, User, Password);
         System.out.
            println
            ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ___DataBase____Creating Connection  2");
         DataConn_SearchAllID = DriverManager.getConnection (DataString, User, Password);
         System.out.
            println
            ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ___DataBase____Creating Connection  3");
         DataConn_SearchID = DriverManager.getConnection (DataString, User, Password);
         System.out.
            println
            ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ___DataBase____Creating Connection  4");
         DataConn_SearchID_1 = DriverManager.getConnection (DataString, User, Password);
         System.out.
            println
            ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ___DataBase____Creating Connection  5");
         DataConn_InsertID = DriverManager.getConnection (DataString, User, Password);
         System.out.
            println
            ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ___DataBase____Creating Connection  6");
         DataConn_InsertSTAT_CHK = DriverManager.getConnection (DataString, User, Password);
         System.out.
            println
            ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ___DataBase____Creating Connection  7");
         DataConn_InsertRAK = DriverManager.getConnection (DataString, User, Password);
         System.out.
            println
            ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ___DataBase____Creating Connection  8");
         DataConn_GetStat_Result = DriverManager.getConnection (DataString, User, Password);
         System.out.
            println
            ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ___DataBase____Connections Established");

         Stmt_InsertID =
            DataConn_InsertID.
            prepareStatement
            ("insert into IDENTIFIER_INFORMATION (IDENTIFIER,AMOUNT,RTT,RWT,TIMESTAMP) values (?,?,?,?,?)");
         Stmt_SearchAllID =
            DataConn_SearchID.
            prepareStatement
            ("select IDENTIFIER,RTT,RWT,TIMESTAMP  from IDENTIFIER_INFORMATION ");
        // ("select
        // IDENTIFIER,AMOUNT,RTT,RWT,TIMESTAMP,K1,K2,HASH_K1_K2,SETVAL_CHK,SETSTAT_CHK 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // 
        // from IDENTIFIER_INFORMATION ");
         Stmt_SearchID =
            DataConn_SearchID.
            prepareStatement
            ("select IDENTIFIER from IDENTIFIER_INFORMATION where IDENTIFIER=?");
         Stmt_SearchID_1 =
            DataConn_SearchID_1.
            prepareStatement
            ("select IDENTIFIER,K2,RAK from IDENTIFIER_INFORMATION where SETSTAT_CHK='t'");
         Stmt_DeleteID =
            DataConn_DeleteID.
            prepareStatement ("delete from IDENTIFIER_INFORMATION where IDENTIFIER=?");
        // prepareStatement ("delete from IDENTIFIER_INFORMATION ");
         Stmt_InsertSTAT_CHK =
            DataConn_InsertSTAT_CHK.
            prepareStatement
            ("update  IDENTIFIER_INFORMATION set RAK=?,K2=?,SETSTAT_CHK='t' where IDENTIFIER=?");
         Stmt_InsertRAK =
            DataConn_InsertRAK.
            prepareStatement
            ("update  IDENTIFIER_INFORMATION set HASH_K1_K2=?,K1=?,SETVAL_CHK='t' where IDENTIFIER=?");
         Stmt_GetStat_Result_1 =
            DataConn_GetStat_Result.
            prepareStatement
            ("select AMOUNT,RTT,RWT,TIMESTAMP,RAK,K1,K2,HASH_K1_K2 from IDENTIFIER_INFORMATION where IDENTIFIER=? AND SETVAL_CHK='t' AND SETSTAT_CHK='t'");

    }
    public void close ()
    {
        try
        {
            Stmt_InsertID.close ();
        }
        catch (Exception e)
        {
        }
        try
        {
            Stmt_SearchAllID.close ();
        }
        catch (Exception e)
        {
        }
        try
        {
            Stmt_SearchID.close ();
        }
        catch (Exception e)
        {
        }
        try
        {
            Stmt_SearchID_1.close ();
        }
        catch (Exception e)
        {
        }
        try
        {
            Stmt_DeleteID.close ();
        }
        catch (Exception e)
        {
        }
        try
        {
            Stmt_InsertSTAT_CHK.close ();
        }
        catch (Exception e)
        {
        }
        try
        {
            Stmt_InsertRAK.close ();
        }
        catch (Exception e)
        {
        }
        try
        {
            Stmt_GetStat_Result_1.close ();
        }
        catch (Exception e)
        {
        }
        try
        {
            DataConn_DeleteID.close ();
        }
        catch (Exception e)
        {
        }
        try
        {
            DataConn_SearchAllID.close ();
        }
        catch (Exception e)
        {
        }
        try
        {
            DataConn_SearchID.close ();
        }
        catch (Exception e)
        {
        }
        try
        {
            DataConn_SearchID_1.close ();
        }
        catch (Exception e)
        {
        }
        try
        {
            DataConn_InsertID.close ();
        }
        catch (Exception e)
        {
        }
        try
        {
            DataConn_InsertSTAT_CHK.close ();
        }
        catch (Exception e)
        {
        }
        try
        {
            DataConn_InsertRAK.close ();
        }
        catch (Exception e)
        {
        }
        try
        {
            DataConn_GetStat_Result.close ();
        }
        catch (Exception e)
        {
        }
    }

    synchronized void insertID (String IDENTIFIER, Float AMOUNT, Long RTT, Long RWT,
                                Long TIMESTAMP) throws Exception
    {
        System.out.println ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQSending Insert req" +
                            IDENTIFIER + AMOUNT + RTT + RWT + TIMESTAMP);
        if (IDENTIFIER == null || AMOUNT == null || RTT == null || RWT == null
            || TIMESTAMP == null)
            throw (new
                   Exception (" Null Value in Argument" + IDENTIFIER + " AMOUNt=" + AMOUNT +
                              "RTT=" + RTT + " RWT=" + RWT + "TIMSTAMP=" + TIMESTAMP));
        Stmt_InsertID.setString (1, IDENTIFIER);
        Stmt_InsertID.setFloat (2, AMOUNT.floatValue ());
        Stmt_InsertID.setLong (3, RTT.longValue ());
        Stmt_InsertID.setLong (4, RWT.longValue ());
        Stmt_InsertID.setLong (5, TIMESTAMP.longValue ());
        Stmt_InsertID.execute ();
    }

    synchronized Vector searchKeys () throws Exception
    {
        System.out.println ("QQQQQQQQQQQQQQQQQQQQQSearching for KEYS");
        ResultSet s = Stmt_SearchID_1.executeQuery ();
        Vector d = new Vector ();
        while (s.next ())
          {
              String g = s.getString (1);
              String k = s.getString (2);
              String f = s.getString (3);
               System.out.println ("QQQQQQQQQQQQQQQQQQQQQData Base --Got -" + g + " -" + k +
                                   "-" + f);
               d.add (g);
               d.add (k);
               d.add (f);
          }
        return d;
    }

            /** Search the Entire DataBase .
             * ("select IDENTIFIER,RTT,RWT,TIMESTAMP  from IDENTIFIER_INFORMATION ");
             */
    synchronized Vector searchAllIDs () throws Exception
    {
        System.out.println ("QQQQQQQQQQQQQQQQQQQQQSearching for All IDs..");
        ResultSet s = Stmt_SearchAllID.executeQuery ();
        Vector d = new Vector ();
        while (s.next ())
          {
              d.add (s.getString (1));
              d.add (s.getString (2));
              d.add (s.getString (3));
              d.add (s.getString (4));
              d.add ("----------------------------------------------");
          }
        return d;
    }
    synchronized boolean searchID (String IDENTIFIER) throws Exception
    {
        System.out.println ("QQQQQQQQQQQQQQQQQQQQQData Base --Searching for " + IDENTIFIER);
        if (IDENTIFIER == null)
            throw (new Exception ("QQQQQQQQQQQQQQNull Value in the parameters"));
        Stmt_SearchID.setString (1, IDENTIFIER);
        ResultSet rset = Stmt_SearchID.executeQuery ();
        boolean s = false;
        while (rset.next ())
             s = true;
         return s;
    }
    synchronized void insertSTAT_CHK (String RAK, String K2,
                                      String IDENTIFIER) throws Exception
    {
        System.out.
            println ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQSending Stat_chk req" +
                     IDENTIFIER + RAK + K2);
        if (IDENTIFIER == null || K2 == null || RAK == null)
            throw (new Exception ("Null in Arguments " + RAK + K2 + IDENTIFIER));
        Stmt_InsertSTAT_CHK.setString (1, RAK);
        Stmt_InsertSTAT_CHK.setString (2, K2);
        Stmt_InsertSTAT_CHK.setString (3, IDENTIFIER);
        Stmt_InsertSTAT_CHK.execute ();
    }


    synchronized void insertRAK (String HASH_K1_K2, String K1,
                                 String IDENTIFIER) throws Exception
    {
        System.out.
            println ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQSending RAK Update Req" +
                     HASH_K1_K2 + K1 + IDENTIFIER);
        if (IDENTIFIER == null || K1 == null || HASH_K1_K2 == null)
            throw (new Exception ("Null in Arguments " + HASH_K1_K2 + K1 + IDENTIFIER));

        Stmt_InsertRAK.setString (1, HASH_K1_K2);
        Stmt_InsertRAK.setString (2, K1);
        Stmt_InsertRAK.setString (3, IDENTIFIER);
        Stmt_InsertRAK.execute ();
    }

    synchronized void deleteID (String IDENTIFIER) throws Exception
    {
        System.out.println ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQDeleteing" +
                            IDENTIFIER);
        if (IDENTIFIER == null)
            throw (new Exception ("Null In Argument-" + IDENTIFIER));
        Stmt_DeleteID.setString (1, IDENTIFIER);
        Stmt_DeleteID.execute ();
    }

   /** This makes the Status Test returns null if the setSTAT_CHK or the setSTAT*/
    synchronized String[] getStat (String IDENTIFIER) throws Exception
    {
        Stmt_GetStat_Result_1.setString (1, IDENTIFIER);
        ResultSet rset = Stmt_GetStat_Result_1.executeQuery ();
        if (rset == null)
             return null;
        if (!rset.next ())
             return null;

         String[] S = new String[8];
        int i = 1;
        while (i < 9)
          {
              S[i - 1] = rset.getString (i);
              System.out.println ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQGetString-" +
                                  i + " " + S[i - 1]);
              i++;
          }
        if (rset.next ())
             throw (new Exception ("Got Another Row with the Same Identifier init"));

        return S;

    }

    /** This is testing Code*/
    public static void main (String args[]) throws Exception
    {
        logger theLog = new logger ("D.log");
        BrahmaInitialize j = new BrahmaInitialize (null);
        DataBaseAccess a = new DataBaseAccess (j);
        String IDENTIFIER = "Dollar;;Mir;;" + System.currentTimeMillis ();
        Float AMOUNT = new Float (12312.123123);
        Long RTT = new Long (System.currentTimeMillis ());
        Long RWT = new Long (System.currentTimeMillis ());
        Long TIMESTAMP = new Long (System.currentTimeMillis ());
         System.out.
            println ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQresult of Searching for " +
                     IDENTIFIER + " is " + a.searchID (IDENTIFIER));
        long S1 = System.currentTimeMillis ();
         a.insertID (IDENTIFIER, AMOUNT, RTT, RWT, TIMESTAMP);
         System.out.
            println ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQresult of Searching for " +
                     IDENTIFIER + " is " + a.searchID (IDENTIFIER));
        long S2 = System.currentTimeMillis ();
        long d1 = S2 - S1;
         a.insertSTAT_CHK ("HASh____asdasdasdasd", "BlowFishKey(\"a12312313", IDENTIFIER);
        long S3 = System.currentTimeMillis ();
         String[] g = a.getStat (IDENTIFIER);
        if (g == null)
             System.out.
                println
                ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQOkay So G was Null We Wait");
        else
          {
              for (int i = 0; i < g.length; i++)
                  System.out.println ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQGot at G=" +
                                      i + "=" + g[i]);
          }
        long d2 = S3 - S2;
        Vector s12 = a.searchKeys ();

        for (int i = 0; i < s12.size (); i++)
            System.out.println ("----" + s12.elementAt (i));
        a.insertRAK ("RAK__121231231231313131232131231231", "12318931028301283012)",
                     IDENTIFIER);
        long S4 = System.currentTimeMillis ();
        long d3 = S4 - S3;

        String[]g1 = a.getStat (IDENTIFIER);
        if (g1 == null)
            System.out.
                println
                ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQOkay So G was Null We Wait");
        else
          {
              for (int i = 0; i < g1.length; i++)
                  System.out.println ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQGot at G=" +
                                      i + "=" + g1[i]);
          }
        // a.deleteID (IDENTIFIER);
        System.out.
            println ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQresult of Searching for " +
                     IDENTIFIER + " is " + a.searchID (IDENTIFIER));
        long S5 = System.currentTimeMillis ();
        long d4 = S5 - S4;
        Vector s = a.searchKeys ();

        for (int i = 0; i < s12.size (); i++)
            System.out.println ("----" + s12.elementAt (i));

        a.close ();
        System.out.println ("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQT1=" + d1 + "T1=" +
                            d2 + "T3=" + d3 + "T4=" + d4);
    }
}
