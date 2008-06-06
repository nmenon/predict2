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

package brahma;

import java.lang.*;
import java.io.*;
import java.net.*;
import utils.*;
import utils.misc.*;
import utils.crypto.*;
import messages.tools.*;
import messages.*;
import network.*;


public class User extends Thread
{

    public InSecureReader UserReader;
    public InSecureWriter UserWriter;
    public logger theLogger;
    public String ConnectInfo;
    public String MyState;
    public String ConnectionInfo;

    public networkStats ZoneStat;
    public networkStats OtherStat;
    public Socket mySocket;
    private String AbortReason;
    public boolean done;

    public String getAbortReason ()
    {
        return AbortReason;
    }

    public User (Socket mySocket, logger theLogger) throws Exception
    {
        this.mySocket = mySocket;
        this.theLogger = theLogger;
        DataInputStream i;
        DataOutputStream o;
         this.ConnectInfo =
            mySocket.getInetAddress ().toString () + ":" + mySocket.getPort () + "-" +
            mySocket.getLocalPort () + " ";
         MyState = "Creating Data Streams";
         try
        {
            i = new DataInputStream (mySocket.getInputStream ());
            o = new DataOutputStream (mySocket.getOutputStream ());
        }
        catch (Exception e)
        {
            System.out.println ("Unable to create Data Streams....Exiting");
            done = true;
            throw (new Exception ("Unable to CREATE DATA STREAM"));
        }
        UserReader = new InSecureReader (i, theLogger, "USR_R");

        UserWriter = new InSecureWriter (o, theLogger, "USR_W");
        UserReader.start ();
        UserWriter.start ();
        ZoneStat = null;
        OtherStat = null;
        done = false;
    }

    public String getConnectInfo ()
    {
        return ConnectInfo;
    }
    public boolean UOk ()
    {
        System.out.println ("____________USer.java______reached UOK");
        if (UserReader == null || UserWriter == null)
          {
              System.out.println ("____________USer.java______" + UserReader + " " +
                                  UserWriter);
              return false;
          }
        System.out.println ("____________USer.java______reached UOK___UOK");

        if (UserReader.getTRAN_ABRT ())
          {
              MyState = "Transaction Aborted-" + UserReader.getABORT_REASON ();
              AbortReason = UserReader.getABORT_REASON ();
          }
        return ((!UserReader.getTRAN_ABRT () && UserReader.isAlive ()
                 && UserWriter.isAlive ()));
    }
    public void sendRevTran ()
    {
        System.out.println ("____________USer.java__REACHED SEND REVTRAN____" + UserReader +
                            " " + UserWriter);
        if (!UOk ())
          {
              System.out.
                  println
                  ("____________USer.java__REACHED SEND REVTRAN____RevTran__User Okay");
              /* Handler */
          }
    }

 /**
   * The General Request Sender. Now I have not modified this as this is for future work
   * Send in a request with two secure writers in and the message created. this will send it
   * as long as the other is working
   */
    public void sendGenRQ (Message K, InSecureWriter Wri,
                           InSecureWriter OtherWri) throws Exception
    {
        System.out.println ("____________USer.java______reached SendGENRQ");

        try
        {
            System.out.println ("Sending Message " + K.getType ());
            theLogger.writelog ("<Message><SND>" + Wri.getID () + "." +
                                K.getType () + ":" + K);

            if (UOk ())
              {
                  System.out.
                      println ("____________USer.java______reached SendGENRQ - USer Ok");
                  // Wri.setPriority (MAX_PRIORITY);
                  while (!Wri.isReady ()) ;
                  Wri.writeLine (K.getType (), K.toString ());
                  // Wri.setPriority (NORM_PRIORITY);
              }
            else
              {
                  sendRevTran ();
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

    /** To String Converts this to the String Foramt for Display*/
    public String toString ()
    {
        return (ConnectionInfo + ":" + MyState);
    }

 /** The General Request reader. Okay So this has to deal with a lot of stuff
   * Chief among them is that the clients may screw up
   * So Among other things we block that
   */
    public Message getGenRQ (Message K, InSecureReader Re,
                             InSecureWriter OtherWri) throws Exception
    {
        System.out.println ("____________USer.java______reached getGENRQ");
        SuperMessage su = null;
         try
        {
            System.out.println ("____________USer.java______reached getGENRQ__Listening for " +
                                K.getType ());
            if (UOk ())
              {
                  System.out.println ("____________USer.java______reached getGENRQ__UOK OK" +
                                      Re);
                  // Re.setPriority (MAX_PRIORITY);
                  while (!Re.isReady ()) ;  // Wait for the Reader to be ready
                  System.out.println ("____________USer.java______reached getGENRQ__" + Re +
                                      " Is Ready");
                  // for reading
                  while (UOk ())
                    {
                        su = Re.readLine (false);
                        System.out.print ("Waiting..");
                        if (su != null)
                            break;
                    }

                  System.out.println ("____________USer.java______reached getGENRQ__Got" + su);

                  // if (su == null)
                  // return null;
                  // Re.setPriority (NORM_PRIORITY);
              }
            if (!UOk ())
              {
                  sendRevTran ();
                  throw (new Exception ("One Connection died"));
                  // Zone Server Died.. Shall I QUIT???? That is a neat way of
                  // disabling this Zone..
              }

              /**Check For DEL_ID We handle that here.*/
            if (su.verifyType ("DEL_ID"))
              {
                  // Call DeleteID Set of events
                  done = true;
                  throw (new Exception ("DEL_ID Got here"));
              }

              /** Now we check whether the data is what we expect it to be*/
            System.out.println ("WHat is it that we got?" + su);
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
    public void closeAllChildThreads ()
    {
        System.out.println ("Picking up the Threads of Disaster...");
        try
        {
            this.sleep (1000);  // Lets Wait for some time and let probs clear
            // up
        }
        catch (Exception e)
        {
            System.out.println ("Unable to Sleep");
        }

      /** Close the DataStreams*/
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
        {
            // Ignore this
        }

    /**Clean Up Code*/
        try
        {
            mySocket.shutdownInput ();
        }
        catch (Exception e)
        {
        }
        try
        {
            mySocket.shutdownOutput ();
        }
        catch (Exception e)
        {
        }
        try
        {
            mySocket.close ();
        }
        catch (Exception e)
        {
            // Ignore this
        }

        try
        {
            System.out.println ("I Die Gracefully....");
            // UserSocket.close ();
        }
        catch (Exception e)
        {                       // Ignore it
        }
    }
}                               // End of Class User
