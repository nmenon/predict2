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

//Source file: Y:/E-Cash-Project/TestBed/development/kuchelan/Initializer.java

/* This Is a Copy Lefted Software
(CL) 2001 Spetember
By Nishanth 'Lazarus' Menon
Calicut Regional Engineering College
Calicut */

package kuchelan;


import org.logi.crypto.*;
import org.logi.crypto.sign.*;
import org.logi.crypto.keys.*;
import org.logi.crypto.modes.*;

import java.util.*;
import java.io.*;
import java.net.*;
import java.lang.*;

import utils.crypto.*;
import utils.misc.*;
import messages.tools.*;
import messages.*;
import network.*;
import GUI.*;

/**
 * The Initializer has to initialize the variables of the System
 * Values are to be read from the configuration file and this has to be used for the further 
 * handling of code
 * @author Nishanth Menon
 * @version 1
 */
public class Initializer
{

  /** the Pass phrase of the user */
    private String UserName;
    private String PassPhrase;
    private String PassPhraseHashAlgo;

  /** Config specific info*/
    private String configurationFileName;

  /** The Network info */
    private String ZoneServerIP;
    private int ZoneServerPort;
    private String CitizenIP;
    private int CitizenPort;
    private int MyCitizenPort;

    private logger log;
    private String Mode;

    private static KeyPair MyKeyPair;

    private Float Amount;
    public int MaxTimeOut;

  /** The Set of AccessMethords */

  /**
    public get (){
    	return ;
    }

    public void set (InpVal){
    	  =InpVal;
    }
    */
    private void logit (String g)
    {
        try
        {
            log.writelog (g);
        }
        catch (Exception e)
        {
        }
    }

    public logger getLog ()
    {
        return log;
    }

    /** The Access Function for MaxTimeOut*/
    public int getMaxTimeOut ()
    {
        return MaxTimeOut;
    }

   /** The Access Function for the Max Time out*/
    public void setMaxTimeOut (int InpVal)
    {
        MaxTimeOut = InpVal;
    }

   /** Access fns for Amount*/
    public void setAmount (Float Amt)
    {
        Amount = Amt;
    }

    public Float getAmount ()
    {
        return Amount;
    }

   /** Access Fn for UserName*/
    public void setUserName (String User)
    {
        System.out.println ("Setting UserName as" + User);
        UserName = User;
    }

    public String getUserName ()
    {
        return UserName;
    }
    // PassPhrase
    public String getPassPhrase ()
    {
        return PassPhrase;
    }

    public void setPassPhrase (String Pa)
    {
        PassPhrase = Pa;
    }

    // PublicKey
    public static KeyPair getMyKeyPair ()
    {
        return MyKeyPair;
    }

    public void setMyKeyPair (int Size) throws Exception
    {
        logit ("creating New KeyPair of Size.." + Size);
        MyKeyPair = RSAKey.createKeys (Size);
        // KeyPair myRSAKey = RSAKey.createKeys (256); //Test Dosage only!!

        logit ("KeyPair =" + MyKeyPair);
    }

    // PassPhraseAlgo
    public String getPassPhraseHashAlgo ()
    {
        return PassPhraseHashAlgo;
    }
    public void setPassPhraseHashAlgo (String InpVal)
    {
        logit ("Setting Password Hash Algorithm  as " + InpVal);
        PassPhraseHashAlgo = InpVal;
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

    // ZoneServerIp
    public String getZoneServerIP ()
    {
        return ZoneServerIP;
    }

    public void setZoneServerIP (String InpVal)
    {
        logit ("Setting ZoneServerIP as  " + InpVal);
        ZoneServerIP = InpVal;
    }

    // ZoenServerPort
    public int getZoneServerPort ()
    {
        return ZoneServerPort;
    }
    public void setZoneServerPort (int InpVal)
    {
        logit ("Setting ZoneServerPort as  " + InpVal);
        ZoneServerPort = InpVal;
    }
    public String getCitizenIP ()
    {
        return CitizenIP;
    }

    public void setCitizenIP (String InpVal)
    {
        logit ("Setting CitizenIP as  " + InpVal);
        CitizenIP = InpVal;
    }
    public int getCitizenPort ()
    {
        return CitizenPort;
    }

    public void setCitizenPort (int InpVal)
    {
        logit ("Setting CitizenPort as  " + InpVal);
        CitizenPort = InpVal;
    }

    // MyServerPort- Citizen Port
    public int getMyCitizenPort ()
    {
        return MyCitizenPort;
    }
    public void setMyCitizenPort (int InpVal)
    {
        logit ("Setting Citizen Port as  " + InpVal);
        MyCitizenPort = InpVal;
    }

    public String getMode ()
    {
        return Mode;
    }

    public void setMode (String InpVal)
    {
        logit ("Going into Mode " + InpVal);
        Mode = InpVal;
    }

  /** 
   * This is Constructor is being Used for testing purpose only
   */
    public Initializer (String ConfFile) throws Exception
    {
        // This is test Entries meant for the testing of this of the stuff.
        Crypto.initRandom ();
        if (ConfFile == null)
            ConfFile = "conf/kuchelan.conf";
        ParseConfigFile a = new ParseConfigFile (ConfFile, false);
         setconfigurationFileName (ConfFile);
        Hashtable t = a.getList ();
        // Log File
        if (t.containsKey ("LOGFILENAME"))
             log = new logger ((String) t.get ("LOGFILENAME"));
        else
             log = new logger ("test.log");
         log.start ();
        // Username
        if (t.containsKey ("USERNAME"))
             UserName = (String) t.get ("USERNAME");
        else
             UserName = "guest";
        // PASSPHRASE
        if (t.containsKey ("PASSPHRASE"))
             setPassPhrase ((String) t.get ("PASSPHRASE"));
        else
             setPassPhrase ("PRotocol Enhancement for DIgital Cash Transaction II");
        // PASSPHRASE Hash Algo
        if (t.containsKey ("PASSPHRASEHASHALGO"))
             setPassPhraseHashAlgo ((String) t.get ("PASSPHRASEHASHALGO"));
        else
             setPassPhraseHashAlgo ("SHA1");
        // ZoneServerIp
        if (t.containsKey ("ZONESERVERIP"))
             setZoneServerIP ((String) t.get ("ZONESERVERIP"));
        else
             setZoneServerIP ("mir");
        // ZoneServerPort
        if (t.containsKey ("ZONESERVERPORT"))
          {
              Integer h = new Integer ((String) t.get ("ZONESERVERPORT"));
               setZoneServerPort (h.intValue ());
          }
        else
             setZoneServerPort (7532);

        // MaxTimeOut
        if (t.containsKey ("MAXTIMEOUT"))
          {
              Integer h = new Integer ((String) t.get ("MAXTIMEOUT"));

              setMaxTimeOut (h.intValue ());
          }
        else
            setMaxTimeOut (7000);
        // CitizenPort
        if (t.containsKey ("CITIZENPORT"))
          {
              Integer h = new Integer ((String) t.get ("CITIZENPORT"));

              setCitizenPort (h.intValue ());
              setMyCitizenPort (h.intValue ());
          }
        else
            setCitizenPort (7321);
        // CitizenPort
        if (t.containsKey ("CITIZENIP"))
            setCitizenIP ((String) t.get ("CITIZENIP"));
        else
            setCitizenIP ("mir");

        int BitSize;

        // KeySize
        if (t.containsKey ("KEYSIZE"))
          {
              Integer h = new Integer ((String) t.get ("KEYSIZE"));

              BitSize = h.intValue ();
          }
        else
            BitSize = 256;

/*        String g = "Citizen";

        try
        {
            setMyKeyPair (BitSize); // test dosage :->
            ServerSocket s = new ServerSocket (getMyCitizenPort (), 1,
                                               InetAddress.getByName ("mir"));

            s.close ();
        }
        catch (Exception e)
        {
            g = "Merchant";
        }*/

        try
        {
            setMyKeyPair (BitSize); // test dosage :->
        }
        catch (Exception e)
        {
            System.err.println ("Sorry - Unable to create Key - reason -" + e);
            e.printStackTrace ();
            System.exit (1);
        }
        setAmount (null);
        // Start the GUI Interfaces
        W_SecurityEntryForm aa = new W_SecurityEntryForm (getPassPhraseHashAlgo ());

        setUserName (aa.getUserName ());
        setPassPhrase (aa.getPassPhrase ());
        W_ModeSelector ab = new W_ModeSelector ();
        String g = ab.getMode ();

        if (g.equals ("Merchant"))
          {
              W_MerchantDataEntryForm ac = new W_MerchantDataEntryForm ();

              setCitizenIP (ac.getCitizenIP ());
              if (ac.getCitizenPort () != 0)
                  setCitizenPort (ac.getCitizenPort ());
              setAmount (ac.getAmount ());
          }

        System.out.println ("Mode=" + g);
        setMode (g);
    }
    public static void main (String[]args) throws Exception
    {
        Initializer s = new Initializer (args[0]);
    }
}
