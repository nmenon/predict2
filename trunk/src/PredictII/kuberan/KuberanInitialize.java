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

//Source file: Y:/E-Cash-Project/TestBed/development/kuberan/KuberanInitialize.java

/* This Is a Copy Lefted Software
(CL) 2001 Spetember
By Nishanth 'Lazarus' Menon
Calicut Regional Engineering College
Calicut */

package kuberan;

import org.logi.crypto.*;
import org.logi.crypto.sign.*;
import org.logi.crypto.keys.*;
import org.logi.crypto.modes.*;

import java.lang.*;
import java.util.*;
import java.net.*;
import org.logi.crypto.*;
import utils.misc.*;
import utils.crypto.*;
import messages.tools.*;
import messages.*;
import GUI.*;

/**
 * @author 
 * @version 
 * Startup Probs are handles here
 */
public class KuberanInitialize
{

    public int myValidatorPort;
    public int myDepositorPort;
    public String BrahmaIP;
    public KeyPair MyKeyPair;
    public CipherKey MyPublicKey;
    public CipherKey MyPrivateKey;
    public int MyPort;
    public static Vector Users;
    public logger theLogger;
    public String MasterServerID;
    public String Denomination;
    public double MinimumAccountBalance;

    public KuberanDataBaseAccess db;

    public W_DisplayForm baba;

  /** Config specific info*/
    public String configurationFileName;
    public int MaxTimeOut;


    // DataBase Related
    public static String DataBaseConnectionString;
    public static String DataBaseUserName;
    public static String DataBaseUserPassword;

  /**
    * Creates a KeyPair for thte Use during this Session
    */
    public void setMyKeyPair (int Size) throws Exception
    {
        logit ("creating New KeyPair of Size.." + Size);
        MyKeyPair = RSAKey.createKeys (Size);
        MyPublicKey = (CipherKey) MyKeyPair.getPublic ();
        MyPrivateKey = (CipherKey) MyKeyPair.getPrivate ();
        // KeyPair myRSAKey = RSAKey.createKeys (256); //Test Dosage only!!

        logit ("KeyPair =" + MyKeyPair);
    }
    public logger getLog ()
    {
        return theLogger;
    }

    // ConfigFileName
    public String getconfigurationFileName ()
    {
        return configurationFileName;
    }

    public void setconfigurationFileName (String InpVal)
    {
        logit ("Setting Configuration File as " + InpVal);
        configurationFileName = InpVal;
    }

    /** Access Function */
    public int getMaxTimeOut ()
    {
        return MaxTimeOut;
    }

    /**Access function */
    public void setMaxTimeOut (int i)
    {
        MaxTimeOut = i;
    }


   /** MinimumBalanceAmount Access fns*/
    public double getMinimumBalanceAmount ()
    {
        return MinimumAccountBalance;
    }

   /** MinimumBalanceAmount Access fns*/
    public void setMinimumBalanceAmount (double a)
    {
        MinimumAccountBalance = a;
    }

    /**DataBase Access String*/
    public String getDataBaseConnectionString ()
    {
        return DataBaseConnectionString;
    }

    /**DataBase UserName */
    public String getDataBaseUserName ()
    {
        return DataBaseUserName;
    }

    /**DataBase User Password*/
    public String getDataBaseUserPassword ()
    {
        return DataBaseUserPassword;
    }

    public String getMyPublicKey ()
    {
        String g = null;

        try
        {
            g = MyPublicKey.toString ();
        }
        catch (Exception e)
        {
            return null;
        }
        return g;
    }

    public String getMyPrivateKey ()
    {
        String g = null;

        try
        {
            g = MyPrivateKey.toString ();
        }
        catch (Exception e)
        {
            return null;
        }
        return g;
    }

    public static void addUser (HandleUserRequests Client)
    {
        System.out.println ("Adding User" + Client);
        try
        {
            Users.addElement (Client);
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
        System.out.println ("asasdkajsdlksadaljdlakjdlasjdladalkd");
        if (Users == null)
            System.out.println ("Vector Still null");
        System.out.println ("Size=" + Users.size ());
    }

    public Vector getUsers ()
    {
        return Users;
    }

    public void deleteUser (HandleUserRequests Client)
    {
        if (Client != null)
          {
              if (Client.isDone ())
                  System.out.println (Client.getConnectionInfo ());;

              if (Users != null)
                  Users.remove (Client);
          }
    }
    public KeyPair getMyKeyPair ()
    {
        return MyKeyPair;
    }

  /** This is For setting the Port ID */
    public void setMyPort (int port)
    {
        MyPort = port;
    }

    public int getMyPort ()
    {
        return MyPort;
    }

    public void logit (String mesg)
    {
        if (mesg == null)
            return;
        try
        {
            theLogger.writelog (mesg);
        }
        catch (Exception e)
        {
        }
    }

  /**
   * Okay So We Need a Constructor So here it is..
   */
    public KuberanInitialize (String ConfFile) throws Exception
    {
        if (ConfFile == null)
            ConfFile = "conf/kuberan.conf";
        ParseConfigFile a = new ParseConfigFile (ConfFile, false);
         setconfigurationFileName (ConfFile);
         Crypto.initRandom ();

        Hashtable t = a.getList ();

        // Log File
        logger log;
        if (t.containsKey ("LOGFILENAME"))
             log = new logger ((String) t.get ("LOGFILENAME"));
        else
             log = new logger ("test.log");
         log.start ();

        // Minimum Balance Amount Allowed
        if (t.containsKey ("MINIMUMBALANCEAMOUNT"))
          {
              Double h = new Double ((String) t.get ("MINIMUMBALANCEAMOUNT"));
               MinimumAccountBalance = h.doubleValue ();
          }
        else
             MinimumAccountBalance = 0;

        // Master Server Validation port
        if (t.containsKey ("MASTERSERVERVALIDATORPORT"))
          {
              Integer h = new Integer ((String) t.get ("MASTERSERVERVALIDATORPORT"));

              myValidatorPort = h.intValue ();
          }
        else
            myValidatorPort = 7575;

        // Master Server Deposit port
        if (t.containsKey ("MASTERSERVERDEPOSITORPORT"))
          {
              Integer h = new Integer ((String) t.get ("MASTERSERVERDEPOSITORPORT"));

              myDepositorPort = h.intValue ();
          }
        else
            myDepositorPort = 7578;

        // MyMasterServerIP
        if (t.containsKey ("MASTERSERVERIP"))
            BrahmaIP = ((String) t.get ("MASTERSERVERIP"));
        else
            BrahmaIP = ("mir");

        // MyMasterServerID
        if (t.containsKey ("MASTERSERVERID"))
            MasterServerID = ((String) t.get ("MASTERSERVERID"));
        else
            MasterServerID = "MIR";


        // MyMonetaryDenomination
        if (t.containsKey ("DENOMINATION"))
            Denomination = ((String) t.get ("DENOMINATION"));
        else
            Denomination = "Rs";

        // MyPort
        if (t.containsKey ("MYPORT"))
          {
              Integer h = new Integer ((String) t.get ("MYPORT"));

              setMyPort (h.intValue ());
          }
        else
            setMyPort (7532);

        // Max Time out
        if (t.containsKey ("MAXTIMEOUT"))
          {
              Integer h = new Integer ((String) t.get ("MAXTIMEOUT"));

              MaxTimeOut = h.intValue ();
          }
        else
            MaxTimeOut = 7000;


        // My Key Size
        int BitSize;

        if (t.containsKey ("MYKEYSIZE"))
          {
              Integer h = new Integer ((String) t.get ("MYKEYSIZE"));

              BitSize = h.intValue ();
          }
        else
            BitSize = 256;

        // Data Base UserName-
        if (t.containsKey ("DATABASEUSERNAME"))
            DataBaseUserName = ((String) t.get ("DATABASEUSERNAME"));
        else
            DataBaseUserName = "kuberan";

        // Data Base User Password.. Better Make this secure
        if (t.containsKey ("DATABASEUSERPASSWORD"))
            DataBaseUserPassword = ((String) t.get ("DATABASEUSERPASSWORD"));
        else
            DataBaseUserPassword = "kuberan123";

        // DataBase Connection String This Being Independent allows us to have
        // any DB working behind Scenes anywhee
        if (t.containsKey ("DATABASECONNECTIONSTRING"))
            DataBaseConnectionString = ((String) t.get ("DATABASECONNECTIONSTRING"));
        else
            DataBaseConnectionString = "jdbc:oracle:thin:@skylab:1521:predict";

        // DataBaseUserName = "kuberan";
        // DataBaseUserPassword = "kuberan123";
        // DataBaseConnectionString = "jdbc:oracle:thin:@skylab:1521:predict";
        // myValidatorPort = 7575;
        // myDepositorPort = 7578;
        // BrahmaIP = "tma-3";
        // MasterServerID = "TMA3";
        // Denomination = "Rs";
        Users = new Vector (100);
        theLogger = log;
        System.out.println ("Creating Key");
        setMyKeyPair (BitSize);
        // setMyPort (7532);

        // Now Set the Db Connectivity

        db = new KuberanDataBaseAccess (this);

        // Start GUI related Nonsense

        Users = new Vector ();
        baba = new W_DisplayForm (Users);
    }
}
