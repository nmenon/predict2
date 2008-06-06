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

package kuberan;

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
    public CipherKey UserPublicKey;

    public SecureReader UserReader;
    public InSecureReader MasterReader;
    public InSecureWriter MasterWriter;
    public SecureWriter UserWriter;
    public logger theLogger;
    public KuberanInitialize theKuberanInitialize;
    public String IDENTIFIER;
    public Socket MasterSocket;

    public String ConnectionInfo;

    public networkStats OtherStat;
    private String AbortReason;

    public String getAbortReason ()
    {
        return AbortReason;
    }

    public User ()
    {
        OtherStat = null;
    }
    // UserReader is ready???
    public boolean UOk ()
    {

        System.out.println ("----------------UOK reached" + UserReader);
        if (UserReader == null)
            return false;
        // try
        // {
        // System.out.println ("USerReader=" + UserReader);
        // UserReader.suspend();
        // }
        // catch (Exception e)
        // {
        // e.printStackTrace ();
        // }
        // System.out.println ("---------UserReader Alive or not--------" +
        // UserReader.isAlive ());
        while (!UserReader.isReady ()) ;
        // System.out.println ("--------------********--UOK reached");

        if (UserReader.getTRAN_ABRT ())
            AbortReason = UserReader.getABORT_REASON ();

        return (!UserReader.getTRAN_ABRT () && UserReader.isAlive () && UserWriter.isAlive ());
    }

    public boolean MOk ()
    {
        System.out.println ("--------------********--MOK reached" + MasterReader);
        if (MasterReader == null)
            return false;       // Call me? Why?
        System.out.println ("--------------********--MOK reached Entry State=" +
                            MasterReader.isReady ());

        // while (!MasterReader.isReady ()) ;
        System.out.println ("--------------********--MOK reached" + MasterReader + "is Ready");

        if (MasterReader.getTRAN_ABRT ())
            AbortReason = MasterReader.getABORT_REASON ();
        System.out.println ("--------------********--MOK reached" + MasterReader.isAlive ());

        return (!MasterReader.getTRAN_ABRT () && MasterReader.isAlive ()
                && MasterWriter.isAlive ());
    }


    // Reader is ready?
    public void sendUserAbort (String Reason) throws Exception
    {
        MasterWriter.setABORT_REASON (Reason);
    }
    public void UsendRevTran ()
    {
        if (UOk () && MOk ())
            return;             // hey Why did Ya call me eh?
        if (MOk ())
          {
              MasterWriter.setABORT_REASON ("US_DEAD");
          }
        else
            UserWriter.setABORT_REASON ("MAS_DEAD");
    }
    public void MsendRevTran ()
    {
        if (UOk () && MOk ())
            return;             // I should Not have been Called - I quit in
        // huff and puff
        if (UOk ())
          {
              MasterWriter.setABORT_REASON ("MAS_DEAD");
          }
        else
            UserWriter.setABORT_REASON ("US_DEAD");
    }
    public boolean checkSp (boolean d)
    {
        System.out.println ("-----------------CheckSp has Started Sending to Master?" + d);
        if (d)
            return (MOk () && UOk ());
        return UOk ();
        // boolean k = UOk ();
        // System.out.println ("-----------------CheckSp has Ended");
        // return (k);
    }

 /**
   * The General Request Sender. Now I have not modified this as this is for future work
   * Send in a request with two secure writers in and the message created. this will send it
   * as long as the other is working
   */
    public void sendGenRQ (Message K, SecureWriter Wri,
                           /* SecureWriter OtherWri, */ boolean Master) throws Exception
    {

        try
        {
            System.out.println ("Sending Message " + K.getType ());
            theLogger.writelog ("<Message><SND>" + Wri.getID () + "." +
                                K.getType () + ":" + K);
            if (checkSp (Master))
              {
                  // Wri.setPriority (MAX_PRIORITY);
                  while (!Wri.isReady ()) ;
                  Wri.writeLine (K.getType (), K.toString ());
                  // Wri.setPriority (NORM_PRIORITY);
              }
            else
              {
                  if (Master)
                      MsendRevTran ();
                  else
                      UsendRevTran ();
                  throw (new Exception ("One Connection died"));
              }
        }
        catch (IOException e)
        {
            // OtherWri.setABORT_REASON (Wri.getID () + "_DEAD");
            throw (new Exception (Wri.getID () + "Died"));
        }
        System.out.println ("Completed Transmission");
        // Avoid all other Error Messsages I have purposefully avoided the
        // Catching other Exceptions
    }                           // end of class general Sender

 /** The General Request reader. Okay So this has to deal with a lot of stuff
   * Chief among them is that the clients may screw up
   * So Among other things we block that
   */
    public Message getGenRQ (Message K, SecureReader Re,
                             /* SecureWriter OtherWri, */
                             boolean Master) throws Exception
    {
        SuperMessage su = null;
         System.out.println ("----------GetGenRQ is Up");
         try
        {
            System.out.println ("--------------Listening for Message " + K.getType ());
            if (checkSp (Master))
              {
                  // Re.setPriority (MAX_PRIORITY);
                  System.out.println ("--------------------Is Re Ready for Data Transfer????");
                  while (!Re.isReady ()) ;  // Wait for the Reader to be ready
                  // for reading
                  System.out.println ("----------Reader " + Re + " a Message");
                  while (checkSp (Master))
                    {
                        su = Re.readLine (false);
                        if (su != null)
                            break;
                    }

                  System.out.println ("----------Got a Message" + su);

                  // Re.setPriority (NORM_PRIORITY);
              }
            if (!checkSp (Master))
              {
                  System.out.println ("--------------Something is not right");
                  if (Master)
                      UsendRevTran ();
                  else
                      MsendRevTran ();  // I am trying to send the message to
                  // Ya...
                  throw (new Exception ("One Connection died/Aborted Transmission"));
              }
            System.out.println ("---------------Hello+" + su);
            if (!su.verifyType (K.getType ()))
              {

                  System.out.
                      println
                      ("---------------------OOOOOOOOOOPPPPPPPPPPPPSSSInvalid message...");
                  throw (new Exception ("Inavlid Message send " + su.getMESSAGE_NAME ()));
              }
            System.out.println ("--------------------Gotchya herer");
            theLogger.writelog ("<Message><RCPT>" + Re.getID () + "." +
                                K.getType () + ":" + su);
            System.out.println ("--------------------Gotchya laksdalsdaskldlsjlkdlajsd");
            K.SetValues (su.getMessage ());

            // Message a = new REQ_REP (su.getMessage ());
        }
        catch (IOException e)
        {
            // OtherWri.setABORT_REASON (Re.getID () + "_DEAD");
            System.out.println ("---------------------OOOOOOOOOOPPPPPPPPPPPPSSS...");
            e.printStackTrace ();
            throw (new Exception (Re.getID () + "died"));
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
        System.out.println ("-----------------------Finished" + K);
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

   /** We need to quit So We Close the connections as neatly as possible
   */
    private void closeSeR (InSecureReader s) throws Exception
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
    private void closeSeW (InSecureWriter s) throws Exception
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

 /** The General Request reader. Okay So this has to deal with a lot of stuff
   * Chief among them is that the clients may screw up
   * So Among other things we block that
   */
    public SuperMessage getStupid (SecureReader Re,
                                   /* SecureWriter OtherWri, */
                                   boolean Master) throws Exception
    {
        SuperMessage su = null;
         System.out.println ("----------Something is Up");
         try
        {
            System.out.println ("--------------Listening for Message ");
            if (checkSp (Master))
              {
                  // Re.setPriority (MAX_PRIORITY);
                  System.out.println ("--------------------Is Re Ready for Data Transfer????");
                  while (!Re.isReady ()) ;  // Wait for the Reader to be ready
                  // for reading
                  System.out.println ("----------Reader " + Re + " a Message");
                  su = Re.readLine (true);

                  System.out.println ("----------Got a Message" + su);
                  // Re.setPriority (NORM_PRIORITY);
              }
            else
              {
                  System.out.println ("--------------Something is not right");
                  if (Master)
                      UsendRevTran ();
                  else
                      MsendRevTran ();
                  throw (new Exception ("One Connection died"));
              }
            System.out.println ("---------------Hello+" + su);
            if (su == null)
                throw (new Exception ("SuperMessage Recieved is NULLL.. Why???"));
            // Message a = new REQ_REP (su.getMessage ());
        }
        catch (IOException e)
        {
            // OtherWri.setABORT_REASON (Re.getID () + "_DEAD");
            System.out.println ("---------------------OOOOOOOOOOPPPPPPPPPPPPSSS...");
            e.printStackTrace ();
            throw (new Exception (Re.getID () + "died"));
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
        System.out.println ("-----------------------Finished" + su);
        return (su);
    }                           // End of get Request general

   /** We need to quit So We Close the connections as neatly as possible*/
    public void closeAllChildThreads ()
    {
        System.out.println ("Picking up the Threads of Disaster...");
        try
        {
            this.sleep (10000);
        }
        catch (Exception e)
        {
            System.out.println ("Could Not Sleep" + e);
        }

      /** Close the DataStreams*/

        try
        {
            Thread.sleep (1000);    // Sleep for Some Time and let all settle
            // down
        }
        catch (Exception e)
        {
            // Ignore this
        }
        try
        {
            closeSeR (MasterReader);
        }
        catch (Exception e)
        {
            // Ignore this
        }
        try
        {
            closeSeW (MasterWriter);
        }
        catch (Exception e)
        {
            // Ignore this
        }
        try
        {
            closeSeR (UserReader);
        }
        catch (Exception e)
        {
            // Ignore this
        }
        try
        {
            closeSeW (UserWriter);
        }
        catch (Exception e)
        {                       // Ignore
            // Ignore this
        }
        try
        {
            MasterSocket.shutdownInput ();
            MasterSocket.shutdownOutput ();
            MasterSocket.close ();
        }
        catch (Exception e)
        {
        }

    /**Clean Up Code*/

        try
        {
            System.out.println ("I Die Gracefully");
            // UserSocket.close ();
        }
        catch (Exception e)
        {                       // Ignore it
            // Ignore this
        }
    }
}                               // End of Class User
