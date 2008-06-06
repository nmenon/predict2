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

//Source file: Y:/E-Cash-Project/TestBed/development/kuchelan/Kuchelan.java

/* This Is a Copy Lefted Software
(CL) 2001 Spetember
By Nishanth 'Lazarus' Menon
Calicut Regional Engineering College
Calicut */

package kuchelan;

import utils.misc.logger;
import java.lang.*;
import java.net.*;
import org.logi.crypto.*;
import utils.misc.*;
import utils.crypto.*;
import messages.tools.*;
import messages.*;

/**
 * @author 
 * @version 
 * This Class is the Controlling class for the rest of the implementation of Kuchelan
 */
public class Kuchelan
{
    protected Citizen theCitizen;
    protected Merchant theMerchant;
    protected logger theLogger;
    protected Initializer theInitializer;

    private ServerSocket citizen;
    private Socket merchant;
    private Socket zoneServer;

  /** The network Part*/
    private void cleanup ()
    {
        try
        {
            Thread.sleep (10000);
        }
        catch (Exception e)
        {
            System.out.println ("Could not sleep.." + e);
        }
        if (theLogger != null)
            try
          {
              theLogger.writelog ("Kuchelan is Shutting Down...");
          }
        catch (Exception e)
        {                       // Ignore
        }
        if (citizen != null)
            try
          {
              citizen.close ();
          }
        catch (Exception e)
        {                       // Ignore
        }
        if (merchant != null)
            try
          {
              merchant.close ();
          }
        catch (Exception e)
        {                       // Ignore
        }
        if (theLogger != null)
            try
          {
              theLogger.close ();
              theLogger.join ();
          }
        catch (Exception e)
        {                       // Ignore
        }
    }
    public Kuchelan (String confFile) throws Exception
    {

        theInitializer = new Initializer (confFile);
        theLogger = theInitializer.getLog ();

   /** Check for the possibility  of the system running in the 
     * the citizen mode or the Merchant mode
     */
        if (theInitializer.getMode ().equals ("Merchant"))
          {
              System.out.println ("Connecting to " + theInitializer.getCitizenIP () + ":" +
                                  theInitializer.getCitizenPort ());
              merchant =
                  new Socket (theInitializer.getCitizenIP (),
                              theInitializer.getCitizenPort ());
              // Set the Max Time out
              merchant.setSoTimeout (theInitializer.getMaxTimeOut ());

    /** Connect to the Zone Server
     */
              try
              {
                  zoneServer =
                      new Socket (theInitializer.getZoneServerIP (),
                                  theInitializer.getZoneServerPort ());
                  zoneServer.setSoTimeout (theInitializer.getMaxTimeOut ());
              }
              catch (Exception e)
              {
                  /* Write Handler Code here */
                  theLogger.
                      writelog ("Unable to connect to Zone Server-" + theInitializer.
                                getZoneServerIP () + ":" +
                                theInitializer.getZoneServerPort () + " Exception " + e);
                  return;
              }
              theLogger.writelog ("Merchant connected to..." + merchant);
              theMerchant = new Merchant (theInitializer, theLogger, merchant, zoneServer);
          }
        else
            // The Citizen
          {
              System.out.println ("Waiting for Merchants on port " +
                                  theInitializer.getMyCitizenPort ());
              citizen = new ServerSocket (theInitializer.getMyCitizenPort ());
              // We need to handle only one merchant at a time right?
              Socket s = citizen.accept ();

              s.setSoTimeout (theInitializer.getMaxTimeOut ());

    /** Connect to the Zone Server
     */
              try
              {
                  zoneServer =
                      new Socket (theInitializer.getZoneServerIP (),
                                  theInitializer.getZoneServerPort ());
                  zoneServer.setSoTimeout (theInitializer.getMaxTimeOut ());

              }
              catch (Exception e)
              {
                  /* Write Handler Code here */
                  theLogger.
                      writelog ("Unable to connect to Zone Server-" + theInitializer.
                                getZoneServerIP () + ":" +
                                theInitializer.getZoneServerPort () + " Exception " + e);
                  return;
              }
              theLogger.writelog ("Citizen  connected to..." + s);
              theCitizen = new Citizen (theInitializer, theLogger, s, zoneServer);

          }
        System.out.println ("Clean Exit.......");
        cleanup ();
        System.exit (0);
    }
    public static void main (String[]args) throws Exception
    {
        try
        {
            Kuchelan g = new Kuchelan (args[0]);
        }
        catch (Exception e)
        {
            System.out.println ("Caught Error in Main");
            e.printStackTrace ();
        }
        System.exit (0);
    }
}
