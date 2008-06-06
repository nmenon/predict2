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

//Source file: Y:/E-Cash-Project/TestBed/development/utils/misc/logger.java

/* This Is a Copy Lefted Software
(CL) 2001 Spetember
By Nishanth 'Lazarus' Menon
Calicut Regional Engineering College
Calicut */

package utils.misc;

import java.lang.*;
import java.util.*;
import java.io.*;
import org.logi.crypto.*;
import utils.misc.*;
import utils.crypto.*;
import messages.tools.*;
import messages.*;

/**
 * @author 
 * @version 
 * This prints information into a log file
 */
public class logger extends Thread
{
    private Vector CaptainsLog;

  /** the Log File Handle*/
    private StringFileWrite logFile;
    private boolean ravan;

    private String createMesg (String Message) throws Exception
    {
        Date d = new Date (System.currentTimeMillis ());
        String s = "[" + d.toString () + "] " + Message;
         return s;
        // logFile.println (s);

    }
    public void writelog (String Message) throws Exception
    {
        CaptainsLog.addElement (createMesg (Message));
    }

    public void close () throws Exception
    {
        writelog ("Stopped Log...");
        ravan = true;
    }

    public logger (String FileName) throws Exception
    {
        CaptainsLog = new Vector ();
        logFile = new StringFileWrite (FileName, true);
        writelog ("Started Log...");
        ravan = false;
    }

  /**
   *  This is a dangerous peice of code
   * Writes to log and IGNORES any errors 
   *  DO NOT USE THIS
   */
    public void logit (String g)
    {
        try
        {
            CaptainsLog.addElement (createMesg (g));
        }
        catch (Exception e)
        {
        }
    }
    private String topOne ()
    {
        String towrite = (String) CaptainsLog.elementAt (0);

        CaptainsLog.remove (towrite);
        return towrite;
    }
    public void run ()
    {
        boolean okay = true;

        while (okay && !ravan)
          {
              if (CaptainsLog != null)
                  if (CaptainsLog.size () != 0)
                      try
                    {
                        logFile.println (topOne ());
                    }
              catch (Exception e)
              {
                  okay = false;
              }
          }
        if (!okay)
            System.err.
                println ("LogFile In problem- Dumping the Rest of the Info to Screen err");
        if (CaptainsLog != null)
            while (!ravan)
                if (CaptainsLog.size () != 0)
                  {
                      System.err.println ("Captain's Log:" + topOne ());
                  }
        if (okay)
          {
              try
              {
                  if (CaptainsLog != null)
                      while (CaptainsLog.size () != 0)
                          logFile.println (topOne ());
              }
              catch (Exception e)
              {
                  if (CaptainsLog != null)
                      while (CaptainsLog.size () != 0)
                          System.err.println ("Captain's Log:" + topOne ());
              }
          }
        else
          {
              if (CaptainsLog != null)
                  while (CaptainsLog.size () != 0)
                      System.err.println ("Captain's Log:" + topOne ());
          }
    }

    public static void main (String[]args) throws Exception
    {
        logger s = new logger ("test.log");
         s.start ();
         s.writelog ("Hello this is world");
         s.writelog ("Poda Man");
         s.close ();
    }
}
