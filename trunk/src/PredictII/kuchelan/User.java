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
import kuchelan.networking.ClientThread;
import java.lang.*;
import java.io.*;
import java.net.*;
import org.logi.crypto.*;
import utils.*;
import utils.misc.*;
import utils.crypto.*;
import messages.tools.*;
import messages.*;
import network.*;


public class User extends Thread
{
    public CipherKey MyPublicKey;
    public CipherKey MyPrivateKey;
    public CipherKey CitizenPublicKey;
    public CipherKey MerchantPublicKey;
    public CipherKey ZoneServerPublicKey;

    public SecureReader CitizenReader;
    public SecureWriter CitizenWriter;
    public SecureReader MerchantReader;
    public SecureWriter MerchantWriter;
    public SecureReader ZoneServerReader;
    public SecureWriter ZoneServerWriter;
    public logger theLogger;
    public String IDENTIFIER;

    public networkStats ZoneStat;
    public networkStats OtherStat;
    private String AbortReason;

    private Initializer Init;

    public String getAbortReason ()
    {
        return AbortReason;
    }

    public User ()
    {
        ZoneStat = null;
        OtherStat = null;
    }
    public boolean COk ()
    {
        if (CitizenReader.getTRAN_ABRT ())
            AbortReason = CitizenReader.getABORT_REASON ();
        return ((!CitizenReader.getTRAN_ABRT () && CitizenReader.isAlive ()
                 && CitizenWriter.isAlive ()));
    }
    public boolean ZOk ()
    {
        if (ZoneServerReader.getTRAN_ABRT ())
            AbortReason = ZoneServerReader.getABORT_REASON ();
        return ((!ZoneServerReader.getTRAN_ABRT () && ZoneServerReader.isAlive ()
                 && ZoneServerWriter.isAlive ()));
    }
    public boolean MOk ()
    {
        // System.out.println("Merchant
        // Reader"+MerchantReader+"MerhchantWriter");
        if (MerchantReader.getTRAN_ABRT ())
            AbortReason = MerchantReader.getABORT_REASON ();    // Janemann Eh
        // Tu dead?
        return ((!MerchantReader.getTRAN_ABRT () && MerchantReader.isAlive ()
                 && MerchantWriter.isAlive ()));
    }
    public void CsendRevTran ()
    {
        if (ZOk () && MOk ())
            return;             // hey Why did Ya call me eh?
        if (ZOk ())
          {
              ZoneServerWriter.setABORT_REASON ("CIT_DEAD");
          }
        else
            MerchantWriter.setABORT_REASON ("ZS_DEAD");
    }
    public void MsendRevTran ()
    {
        if (ZOk () && COk ())
            return;             // I should Not have been Called - I quit in
        // huff and puff
        if (ZOk ())
          {
              ZoneServerWriter.setABORT_REASON ("CIT_DEAD");
          }
        else
            CitizenWriter.setABORT_REASON ("ZS_DEAD");
    }

    public boolean checkSp (boolean Cit)
    {
        if (!Cit)
            return (ZOk () && COk ());
        return (ZOk () && MOk ());
    }

 /**
   * The General Request Sender. Now I have not modified this as this is for future work
   * Send in a request with two secure writers in and the message created. this will send it
   * as long as the other is working
   */
    public void sendGenRQ (Message K, SecureWriter Wri,
                           SecureWriter OtherWri, boolean Cit) throws Exception
    {
        System.out.println ("Send Gen RQ Sending Message" + K);
        try
        {
            System.out.println ("Sending Message " + K.getType ());
            theLogger.writelog ("<Message><SND>" + Wri.getID () + "." +
                                K.getType () + ":" + K);

            if (checkSp (Cit))
              {
                  // Wri.setPriority (MAX_PRIORITY);
                  while (!Wri.isReady ()) ;
                  Wri.writeLine (K.getType (), K.toString ());
                  // Wri.setPriority (NORM_PRIORITY);
              }
            else
              {
                  if (Cit)
                      CsendRevTran ();
                  else
                      MsendRevTran ();
                  throw (new Exception ("One Connection died"));
              }
        }
        catch (IOException e)
        {
            OtherWri.setABORT_REASON (Wri.getID () + "_DEAD");
            throw (new Exception (Wri.getID () + "Died"));
        }
        // Avoid all other Error Messsages I have purposefully avoided the
        // Catching other Exceptions
    }                           // end of class general Sender

 /** The General Request reader. Okay So this has to deal with a lot of stuff
   * Chief among them is that the clients may screw up
   * So Among other things we block that
   */
    public Message getGenRQ (Message K, SecureReader Re,
                             SecureWriter OtherWri, boolean Cit) throws Exception
    {
        SuperMessage su = null;
         try
        {
            System.out.println ("Listening for Message " + K.getType ());
            if (checkSp (Cit))
              {
                  System.out.println ("Checking for Ready of Re" + Re.isReady ());
                  // Re.setPriority (MAX_PRIORITY);
                  while (!Re.isReady ()) ;  // Wait for the Reader to be ready
                  // for reading
                  // The Probs is that the Remote System might Screw up while
                  // We are waiting for reading
                  System.out.println ("reader is ready" + Re.getID ());
                  while (checkSp (Cit)) // Until Somthing Nasty Happens..
                    {
                        su = Re.readLine (true);
                        if (su != null)
                            break;
                        System.out.println ("Me no get data..." + Re.getID ());
                    }
                  System.out.println ("Mia getta Data" + Re.getID ());

                  System.out.println ("Me get Data" + Re.getID () + su);
                  // if (su == null)
                  // return null;
                  // Re.setPriority (NORM_PRIORITY);
              }
            if (su == null)
              {
                  if (Cit)
                      CsendRevTran ();
                  else
                      MsendRevTran ();
                  throw (new Exception ("Connection died/Aborted Transmission"));
              }
            if (!su.verifyType (K.getType ()))
                throw (new Exception ("Inavlid Message send " + su.getMESSAGE_NAME ()));
            theLogger.writelog ("<Message><RCPT>" + Re.getID () + "." +
                                K.getType () + ":" + su);
            K.SetValues (su.getMessage ());
            // Message a = new REQ_REP (su.getMessage ());
        }
        catch (IOException e)
        {
            OtherWri.setABORT_REASON (Re.getID () + "_DEAD");
            throw (new Exception (Re.getID () + "died"));
        }
        return (K);
    }                           // End of get Request general

   /** We need to quit So We Close the connections as neatly as possible
   */
    private void closeSeR (SecureReader s) throws Exception
    {

        if (s != null)
            try
          {
              s.setDone (true);
              s.stop (new StopThreadException ());
              System.out.println ("Killed UserReader-" + s);
          }
        catch (Exception e)
        {                       // Ignore ti
        }

    }
    private void closeSeW (SecureWriter s) throws Exception
    {
        if (s != null)
            try
          {
              s.setDone (true);
              // s.join(10);
              try
              {
                  s.stop (new StopThreadException ());
              }
              catch (Exception e)
              {
              }
              System.out.println ("Killed -" + s);
          }
        catch (Exception e)
        {                       // Ignore ti
        }
    }
    public void closeAllChildThreads ()
    {
        System.out.println ("Picking up the Threads of Disaster...");

      /** Close the DataStreams*/
        try
        {
            closeSeR (ZoneServerReader);
        }
        catch (Exception e)
        {
            // Ignore this
        }
        try
        {
            closeSeW (ZoneServerWriter);
        }
        catch (Exception e)
        {
            // Ignore this
        }
        try
        {
            closeSeR (MerchantReader);
        }
        catch (Exception e)
        {
            // Ignore this
        }
        try
        {
            closeSeW (MerchantWriter);
        }
        catch (Exception e)
        {
            // Ignore this
        }
        try
        {
            closeSeR (CitizenReader);
        }
        catch (Exception e)
        {
            // Ignore this
        }
        try
        {
            closeSeW (CitizenWriter);
        }
        catch (Exception e)
        {
            // Ignore this
        }

    /**Clean Up Code*/

        try
        {
            System.out.println ("I Die Gracefully");
            // UserSocket.close ();
        }
        catch (Exception e)
        {                       // Ignore it
        }
    }
}                               // End of Class User
