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

//Source file: Y:/E-Cash-Project/TestBed/development/brahma/BrahmaInitialize.java

/* This Is a Copy Lefted Software
(CL) 2001 Spetember
By Nishanth 'Lazarus' Menon
Calicut Regional Engineering College
Calicut */

package brahma;

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
 * @author Nishanth 'Lazarus' Menon
 * @version 1.01
 * Initialize the Brahma
 */
public class BrahmaInitialize
{

    public static Vector Validators;
    public static Vector Depositors;
    public static Vector RemoteRequests;
    public long RWT;
    public int myValidatorPort;
    public int myDepositorPort;
    public int myRemoteRequestPort;
    public String MasterServerID;
    public logger theLogger;
    public DataBaseAccess db;
    private Hashtable MasterServerInfo;

    // GUI related
    private W_BrahmaDisplayForm theForm;

    // DataBase Related
    public static String DataBaseConnectionString;
    public static String DataBaseUserName;
    public static String DataBaseUserPassword;

    public int HashComputationTime;
    public int MaxTimeOut;

  /** Config specific info*/
    public String configurationFileName;

   /** Access Function*/
    public int getMaxTimeOut ()
    {
        return MaxTimeOut;
    }

   /** Access Function*/
    public void setMaxTimeOut (int t)
    {
        MaxTimeOut = t;
    }

   /** Access Function*/
    public int getHashComputationTime ()
    {
        return HashComputationTime;
    }

   /** Access Function*/
    public void setHashComputationTime (int t)
    {
        HashComputationTime = t;
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

   /** Testing logger*/
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
    public logger getLog ()
    {
        return theLogger;
    }

    /** Access Fns**/
    public long getRWT ()
    {
        return RWT;
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

    /** Remove from validator*/
    public void deleteValidator (Object o) throws Exception
    {
        if (Validators == null)
            throw (new Exception ("Validator is null and trying to delete one object"));
        Validators.remove (o);
    }

    /** Remove from Depositor*/
    public void deleteDepositor (Object o) throws Exception
    {
        if (Depositors == null)
            throw (new Exception ("Depositor is null and trying to delete one object"));
        Depositors.remove (o);
    }

    /** Remove from RemoteRequest*/
    public void deleteRemoteRequest (Object o) throws Exception
    {
        if (RemoteRequests == null)
            throw (new Exception ("RemoteRequest is null and trying to delete one object"));
        RemoteRequests.remove (o);
    }

    /** Add into validator*/
    public void addValidator (Object o) throws Exception
    {
        if (Validators == null)
            throw (new Exception ("Validator is null and trying to add one object"));
        Validators.add (o);
    }

    /** Add into Depositor*/
    public void addDepositor (Object o) throws Exception
    {
        if (Depositors == null)
            throw (new Exception ("Depositor is null and trying to add one object"));
        Depositors.add (o);
    }

    /** Add into RemoteRequest*/
    public void addRemoteRequest (Object o) throws Exception
    {
        if (RemoteRequests == null)
            throw (new Exception ("RemoteRequest is null and trying to add one object"));
        RemoteRequests.add (o);
    }

    public String getMasterServerIP (String MID) throws Exception
    {
        if (MasterServerInfo.containsKey (MID))
            return ((String) MasterServerInfo.get (MID));
        else
            throw (new Exception ("Unknown Master Server ID Supplied" + MID));
    }


   /** The Constructor
   */
    public BrahmaInitialize (String ConfFile) throws Exception
    {
        // Loading The Variables
        if (ConfFile == null)
            ConfFile = "conf/brahma.conf";
        ParseConfigFile a = new ParseConfigFile (ConfFile, false);
         setconfigurationFileName (ConfFile);

        Hashtable t = a.getList ();
        // Log File
        logger theLog;
        if (t.containsKey ("LOGFILENAME"))
             theLog = new logger ((String) t.get ("LOGFILENAME"));
        else
             theLog = new logger ("test.log");
         theLog.start ();
        // MyMasterServerID
        if (t.containsKey ("MASTERSERVERID"))
             MasterServerID = ((String) t.get ("MASTERSERVERID"));
        else
             MasterServerID = "MIR";

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
            myDepositorPort = 7602;


        // RemoteRequest Port
        if (t.containsKey ("REMOTEREQUESTPORT"))
          {
              Integer h = new Integer ((String) t.get ("REMOTEREQUESTPORT"));

              myRemoteRequestPort = h.intValue ();
          }
        else
            myRemoteRequestPort = 7578;

        // MAXTIMEOUT
        if (t.containsKey ("MAXTIMEOUT"))
          {
              Integer h = new Integer ((String) t.get ("MAXTIMEOUT"));

              MaxTimeOut = h.intValue ();
          }
        else
            MaxTimeOut = 7000;


        // RWT
        if (t.containsKey ("RWT"))
          {
              Integer h = new Integer ((String) t.get ("RWT"));

              RWT = h.intValue ();
          }
        else
            RWT = 7000;

        String MIDInfoFile;

        // Data Base UserName-
        if (t.containsKey ("MIDINFOFILE"))
            MIDInfoFile = ((String) t.get ("MIDINFOFILE"));
        else
            MIDInfoFile = "conf/MID.conf";
        {                       // Handle the MasterServerID Set
            ParseConfigFile y = new ParseConfigFile (MIDInfoFile, false);

            MasterServerInfo = y.getList ();
        }                       // End of Handling the MasterServerId list

        // HashComputation time
        if (t.containsKey ("HASHCOMPUTATIONTIME"))
          {
              Integer h = new Integer ((String) t.get ("HASHCOMPUTATIONTIME"));

              HashComputationTime = h.intValue ();
          }
        else
            HashComputationTime = 78;
        // Data Base UserName-
        if (t.containsKey ("DATABASEUSERNAME"))
            DataBaseUserName = ((String) t.get ("DATABASEUSERNAME"));
        else
            DataBaseUserName = "brahma";

        // Data Base User Password.. Better Make this secure
        if (t.containsKey ("DATABASEUSERPASSWORD"))
            DataBaseUserPassword = ((String) t.get ("DATABASEUSERPASSWORD"));
        else
            DataBaseUserPassword = "brahma123";

        // DataBase Connection String This Being Independent allows us to have
        // any DB working behind Scenes anywhee
        if (t.containsKey ("DATABASECONNECTIONSTRING"))
            DataBaseConnectionString = ((String) t.get ("DATABASECONNECTIONSTRING"));
        else
            DataBaseConnectionString = "jdbc:oracle:thin:@skylab:1521:predict";

        // myValidatorPort = 7575;
        // myDepositorPort = 7578;
        // myRemoteRequestPort = 7602;

        // May Be nice if I wrote a Checking Code to make Sure that no Null
        // values are present

        Validators = new Vector ();
        Depositors = new Vector ();
        RemoteRequests = new Vector ();
        theLogger = theLog;
        // DataBaseUserName = "brahma";
        // DataBaseUserPassword = "brahma123";
        // DataBaseConnectionString = "jdbc:oracle:thin:@skylab:1521:predict";
        // RWT = 100000;
        // End of Loading the Variables
        // This is the last line
        db = new DataBaseAccess (this);

        // The GUI
        theForm = new W_BrahmaDisplayForm (Validators, Depositors, RemoteRequests);
    }
}
