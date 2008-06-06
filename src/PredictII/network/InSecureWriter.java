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

//Source file: Y:/E-Cash-Project/TestBed/development/network/SecureWriter.java

/* This Is a Copy Lefted Software
(CL) 2001 Spetember
By Nishanth 'Lazarus' Menon
Calicut Regional Engouteeroutg College
Calicut */

package network;

import java.lang.*;
import java.util.*;
import java.io.*;
import org.logi.crypto.*;
import utils.misc.*;
import utils.*;
import utils.crypto.*;
import messages.tools.*;
import messages.*;

/**
 * @author 
 * @version 
 *  This is Pure Insecurity Based Connector
 */
public class InSecureWriter extends SecureWriter
{


    protected String en (SuperMessage s) throws Exception
    {

        String g = s.toString ();
         g.trim ();
         return g;
    }
    public InSecureWriter (DataOutputStream outputR, logger log, String ID)
    {
        super ();
        out = outputR;
        this.ID = ID;
        this.theLog = log;
    }

}
