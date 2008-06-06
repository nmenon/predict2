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

//Source file: Y:/E-Cash-Project/TestBed/development/kuchelan/networking/ServerThread.java

/* This Is a Copy Lefted Software
(CL) 2001 Spetember
By Nishanth 'Lazarus' Menon
Calicut Regional Engineering College
Calicut */

package kuchelan.networking;

import network.SecureReader;
import network.SecureWriter;
import network.InSecureWriter;
import network.InSecureReader;
import messages.AUTH_REQ;
import messages.AUTH_REP;
import messages.REQ_AMT;
import messages.REQ_REP;
import messages.AUTH_USER;
import messages.AUTH_RES;
import messages.REQ_WIT;
import messages.CSH_TRANS;
import messages.CSH_FWD;
import messages.ACK_WIT;
import messages.TRAN_COMP;
import messages.TRAN_ABRT;
import messages.SuperMessage;
import java.lang.*;
import org.logi.crypto.*;
import utils.misc.*;
import utils.crypto.*;
import messages.tools.*;
import messages.*;

/**
 * @author 
 * @version 
 * This is the Actual Citizen Thread that handles a request by the Remote Merchant
 */
public class ServerThread extends Thread
{
    public SecureReader theSecureReader[];
    public SecureWriter theSecureWriter[];
    public InSecureWriter theInSecureWriter[];
    public InSecureReader theInSecureReader[];
    public AUTH_REQ theAUTH_REQ;
    public AUTH_REP theAUTH_REP;
    public REQ_AMT theREQ_AMT;
    public REQ_REP theREQ_REP;
    public AUTH_USER theAUTH_USER;
    public AUTH_RES theAUTH_RES;
    public REQ_WIT theREQ_WIT;
    public CSH_TRANS theCSH_TRANS;
    public CSH_FWD theCSH_FWD;
    public ACK_WIT theACK_WIT;
    public TRAN_COMP theTRAN_COMP;
    public TRAN_ABRT theTRAN_ABRT;
    public SuperMessage theSuperMessage;

    public ServerThread ()
    {
    }
}
