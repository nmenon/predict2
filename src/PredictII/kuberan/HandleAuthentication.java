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

//Source file: Y:/E-Cash-Project/TestBed/development/kuberan/HandleAuthentication.java

/* This Is a Copy Lefted Software
(CL) 2001 Spetember
By Nishanth 'Lazarus' Menon
Calicut Regional Engineering College
Calicut */

package kuberan;

import network.InSecureWriter;
import network.InSecureReader;
import kuberan.ZS_Database.CheckUserDB;
import messages.AUTH_REQ;
import messages.AUTH_REP;
import messages.AUTH_USER;
import messages.AUTH_RES;
import messages.TRAN_ABRT;
import messages.REQ_WIT;
import messages.ACK_WIT;
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
 * Junkies Love to attack our system. We Kick them out here
 */
public class HandleAuthentication
{
    public InSecureWriter theInSecureWriter;
    public InSecureReader theInSecureReader;
    public HandleAMerchant theHandleAMerchant;
    public HandleACitizen theHandleACitizen;
    public CheckUserDB theCheckUserDB;
    public AUTH_REQ theAUTH_REQ;
    public AUTH_REP theAUTH_REP;
    public AUTH_USER theAUTH_USER;
    public AUTH_RES theAUTH_RES;
    public TRAN_ABRT theTRAN_ABRT;
    public REQ_WIT theREQ_WIT;
    public ACK_WIT theACK_WIT;
    public SuperMessage theSuperMessage;

    public HandleAuthentication ()
    {
    }
}
