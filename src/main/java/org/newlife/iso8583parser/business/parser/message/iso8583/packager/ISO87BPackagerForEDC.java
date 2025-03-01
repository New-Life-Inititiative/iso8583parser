/*
 * jPOS Project [http://jpos.org]
 * Copyright (C) 2000-2023 jPOS Software SRL
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.newlife.iso8583parser.business.parser.message.iso8583.packager;

import org.newlife.iso8583parser.business.parser.model.ISO8583Property;
import org.jpos.iso.IFB_AMOUNT;
import org.jpos.iso.IFB_BINARY;
import org.jpos.iso.IFB_BITMAP;
import org.jpos.iso.IFB_LLCHAR;
import org.jpos.iso.IFB_LLLBINARY;
import org.jpos.iso.IFB_LLLCHAR;
import org.jpos.iso.IFB_LLNUM;
import org.jpos.iso.IFB_NUMERIC;
import org.jpos.iso.IF_CHAR;
import org.jpos.iso.ISOComponent;
import org.jpos.iso.ISOFieldPackager;
import org.jpos.iso.ISOPackager;
import org.newlife.iso8583parser.business.parser.message.iso8583.packager.ISOBasePackager;

/**
 * ISO 8583 v1987 BINARY Packager
 *
 * @author apr@cs.com.uy
 * @version $Id$
 * @see ISOPackager
 * @see ISOBasePackager
 * @see ISOComponent
 */
public class ISO87BPackagerForEDC extends ISOBasePackager {
    private static final boolean pad = false;
    protected ISOFieldPackager fld[] = {
            /*000*/ new IFB_NUMERIC (  4, "MESSAGE TYPE INDICATOR", true),
            /*001*/ new IFB_BITMAP  ( 16, "BIT MAP"),
            /*002*/ new IFB_LLNUM   ( 19, "PAN - PRIMARY ACCOUNT NUMBER", pad),
            /*003*/ new IFB_NUMERIC (  6, "PROCESSING CODE", true),
            /*004*/ new IFB_NUMERIC ( 12, "AMOUNT, TRANSACTION", true),
            /*005*/ new IFB_NUMERIC ( 12, "AMOUNT, SETTLEMENT", true),
            /*006*/ new IFB_NUMERIC ( 12, "AMOUNT, CARDHOLDER BILLING", true),
            /*007*/ new IFB_NUMERIC ( 10, "TRANSMISSION DATE AND TIME", true),
            /*008*/ new IFB_NUMERIC (  8, "AMOUNT, CARDHOLDER BILLING FEE", true),
            /*009*/ new IFB_NUMERIC (  8, "CONVERSION RATE, SETTLEMENT", true),
            /*010*/ new IFB_NUMERIC (  8, "CONVERSION RATE, CARDHOLDER BILLING", true),
            /*011*/ new IFB_NUMERIC (  6, "SYSTEM TRACE AUDIT NUMBER", true),
            /*012*/ new IFB_NUMERIC (  6, "TIME, LOCAL TRANSACTION", true),
            /*013*/ new IFB_NUMERIC (  4, "DATE, LOCAL TRANSACTION", true),
            /*014*/ new IFB_NUMERIC (  4, "DATE, EXPIRATION", true),
            /*015*/ new IFB_NUMERIC (  4, "DATE, SETTLEMENT", true),
            /*016*/ new IFB_NUMERIC (  4, "DATE, CONVERSION", true),
            /*017*/ new IFB_NUMERIC (  4, "DATE, CAPTURE", true),
            /*018*/ new IFB_NUMERIC (  4, "MERCHANTS TYPE", true),
            /*019*/ new IFB_NUMERIC (  3, "ACQUIRING INSTITUTION COUNTRY CODE", true),
            /*020*/ new IFB_NUMERIC (  3, "PAN EXTENDED COUNTRY CODE", true),
            /*021*/ new IFB_NUMERIC (  3, "FORWARDING INSTITUTION COUNTRY CODE", true),
            /*022*/ new IFB_NUMERIC (  3, "POINT OF SERVICE ENTRY MODE", true),
            /*023*/ new IFB_NUMERIC (  3, "CARD SEQUENCE NUMBER", true),
            /*024*/ new IFB_NUMERIC (  3, "NETWORK INTERNATIONAL IDENTIFIEER", true), // 기존 HLI: 4 자리 <-> EDC ISO8583 Doc Spec 3자리 로 상이함 => Doc 기준 설정
            /*025*/ new IFB_NUMERIC (  2, "POINT OF SERVICE CONDITION CODE", true),
            /*026*/ new IFB_NUMERIC (  2, "POINT OF SERVICE PIN CAPTURE CODE", true),
            /*027*/ new IFB_NUMERIC (  1, "AUTHORIZATION IDENTIFICATION RESP LEN",true),
            /*028*/ new IFB_AMOUNT  (  9, "AMOUNT, TRANSACTION FEE", true),
            /*029*/ new IFB_AMOUNT  (  9, "AMOUNT, SETTLEMENT FEE", true),
            /*030*/ new IFB_AMOUNT  (  9, "AMOUNT, TRANSACTION PROCESSING FEE", true),
            /*031*/ new IFB_AMOUNT  (  9, "AMOUNT, SETTLEMENT PROCESSING FEE", true),
            /*032*/ new IFB_LLNUM   ( 11, "ACQUIRING INSTITUTION IDENT CODE", pad),
            /*033*/ new IFB_LLNUM   ( 11, "FORWARDING INSTITUTION IDENT CODE", pad),
            /*034*/ new IFB_LLCHAR  ( 28, "PAN EXTENDED"),
            /*035*/ new IFB_LLNUM   ( 37, "TRACK 2 DATA", pad),
            /*036*/ new IFB_LLLCHAR (104, "TRACK 3 DATA"),
            /*037*/ new IF_CHAR     ( 12, "RETRIEVAL REFERENCE NUMBER"),
            /*038*/ new IF_CHAR     (  6, "AUTHORIZATION IDENTIFICATION RESPONSE"),
            /*039*/ new IF_CHAR     (  2, "RESPONSE CODE"),
            /*040*/ new IF_CHAR     (  3, "SERVICE RESTRICTION CODE"),
            /*041*/ new IF_CHAR     (  8, "CARD ACCEPTOR TERMINAL IDENTIFICACION"),
            /*042*/ new IF_CHAR     ( 15, "CARD ACCEPTOR IDENTIFICATION CODE" ),
            /*043*/ new IF_CHAR     ( 40, "CARD ACCEPTOR NAME/LOCATION"),
            /*044*/ new IFB_LLCHAR  ( 25, "ADITIONAL RESPONSE DATA"),
            /*045*/ new IFB_LLCHAR  ( 76, "TRACK 1 DATA"),
            /*046*/ new IFB_LLLCHAR (999, "ADITIONAL DATA - ISO"),
            /*047*/ new IFB_LLLCHAR (999, "ADITIONAL DATA - NATIONAL"),
            /*048*/ new IFB_LLLCHAR (999, "ADITIONAL DATA - PRIVATE"),
            /*049*/ new IF_CHAR     (  3, "CURRENCY CODE, TRANSACTION"),
            /*050*/ new IF_CHAR     (  3, "CURRENCY CODE, SETTLEMENT"),
            /*051*/ new IF_CHAR     (  3, "CURRENCY CODE, CARDHOLDER BILLING"   ),
            /*052*/ new IFB_BINARY  (  8, "PIN DATA"   ),
            /*053*/ new IFB_BINARY  (  8, "SECURITY RELATED CONTROL INFORMATION"),
            /*054*/ new IFB_LLLCHAR (120, "ADDITIONAL AMOUNTS"),
            /*055*/ new IFB_LLLCHAR (999, "RESERVED ISO"),
            /*056*/ new IFB_LLLCHAR (999, "RESERVED ISO"),
            /*057*/ new IFB_LLLCHAR (999, "RESERVED NATIONAL"),
            /*058*/ new IFB_LLLCHAR (999, "RESERVED NATIONAL"),
            /*059*/ new IFB_LLLCHAR (999, "RESERVED NATIONAL"),
            /*060*/ new IFB_LLLCHAR (999, "RESERVED PRIVATE"),
            /*061*/ new IFB_LLLCHAR (999, "RESERVED PRIVATE"),
            /*062*/ //new IFB_LLLCHAR (999, "RESERVED PRIVATE"),
            /*062*/ new IFB_LLLBINARY (999, "RESERVED PRIVATE"),
            /*063*/ new IFB_LLLBINARY (999, "RESERVED PRIVATE"),
            /*064*/ new IFB_BINARY  (  8, "MESSAGE AUTHENTICATION CODE FIELD"),
            /*065*/ new IFB_BINARY  (  1, "BITMAP, EXTENDED"),
            /*066*/ new IFB_NUMERIC (  1, "SETTLEMENT CODE", true),
            /*067*/ new IFB_NUMERIC (  2, "EXTENDED PAYMENT CODE", true),
            /*068*/ new IFB_NUMERIC (  3, "RECEIVING INSTITUTION COUNTRY CODE", true),
            /*069*/ new IFB_NUMERIC (  3, "SETTLEMENT INSTITUTION COUNTRY CODE", true),
            /*070*/ new IFB_NUMERIC (  3, "NETWORK MANAGEMENT INFORMATION CODE", true),
            /*071*/ new IFB_NUMERIC (  4, "MESSAGE NUMBER", true),
            /*072*/ new IFB_NUMERIC (  4, "MESSAGE NUMBER LAST", true),
            /*073*/ new IFB_NUMERIC (  6, "DATE ACTION", true),
            /*074*/ new IFB_NUMERIC ( 10, "CREDITS NUMBER", true),
            /*075*/ new IFB_NUMERIC ( 10, "CREDITS REVERSAL NUMBER", true),
            /*076*/ new IFB_NUMERIC ( 10, "DEBITS NUMBER", true),
            /*077*/ new IFB_NUMERIC ( 10, "DEBITS REVERSAL NUMBER", true),
            /*078*/ new IFB_NUMERIC ( 10, "TRANSFER NUMBER", true),
            /*079*/ new IFB_NUMERIC ( 10, "TRANSFER REVERSAL NUMBER", true),
            /*080*/ new IFB_NUMERIC ( 10, "INQUIRIES NUMBER", true),
            /*081*/ new IFB_NUMERIC ( 10, "AUTHORIZATION NUMBER", true),
            /*082*/ new IFB_NUMERIC ( 12, "CREDITS, PROCESSING FEE AMOUNT", true),
            /*083*/ new IFB_NUMERIC ( 12, "CREDITS, TRANSACTION FEE AMOUNT", true),
            /*084*/ new IFB_NUMERIC ( 12, "DEBITS, PROCESSING FEE AMOUNT", true),
            /*085*/ new IFB_NUMERIC ( 12, "DEBITS, TRANSACTION FEE AMOUNT", true),
            /*086*/ new IFB_NUMERIC ( 16, "CREDITS, AMOUNT", true),
            /*087*/ new IFB_NUMERIC ( 16, "CREDITS, REVERSAL AMOUNT", true),
            /*088*/ new IFB_NUMERIC ( 16, "DEBITS, AMOUNT", true),
            /*089*/ new IFB_NUMERIC ( 16, "DEBITS, REVERSAL AMOUNT", true),
            /*090*/ new IFB_NUMERIC ( 42, "ORIGINAL DATA ELEMENTS", true),
            /*091*/ new IF_CHAR     (  1, "FILE UPDATE CODE"),
            /*092*/ new IF_CHAR     (  2, "FILE SECURITY CODE"),
            /*093*/ new IF_CHAR     (  6, "RESPONSE INDICATOR"),
            /*094*/ new IF_CHAR     (  7, "SERVICE INDICATOR"),
            /*095*/ new IF_CHAR     ( 42, "REPLACEMENT AMOUNTS"),
            /*096*/ new IFB_BINARY  ( 16, "MESSAGE SECURITY CODE"),
            /*097*/ new IFB_AMOUNT  ( 17, "AMOUNT, NET SETTLEMENT", pad),
            /*098*/ new IF_CHAR     ( 25, "PAYEE"),
            /*099*/ new IFB_LLNUM   ( 11, "SETTLEMENT INSTITUTION IDENT CODE", pad),
            /*100*/ new IFB_LLNUM   ( 11, "RECEIVING INSTITUTION IDENT CODE", pad),
            /*101*/ new IFB_LLCHAR  ( 17, "FILE NAME"),
            /*102*/ new IFB_LLCHAR  ( 28, "ACCOUNT IDENTIFICATION 1"),
            /*103*/ new IFB_LLCHAR  ( 28, "ACCOUNT IDENTIFICATION 2"),
            /*104*/ new IFB_LLLCHAR (100, "TRANSACTION DESCRIPTION"),
            /*105*/ new IFB_LLLCHAR (999, "RESERVED ISO USE"),
            /*106*/ new IFB_LLLCHAR (999, "RESERVED ISO USE"),
            /*107*/ new IFB_LLLCHAR (999, "RESERVED ISO USE"),
            /*108*/ new IFB_LLLCHAR (999, "RESERVED ISO USE"),
            /*109*/ new IFB_LLLCHAR (999, "RESERVED ISO USE"),
            /*110*/ new IFB_LLLCHAR (999, "RESERVED ISO USE"),
            /*111*/ new IFB_LLLCHAR (999, "RESERVED ISO USE"),
            /*112*/ new IFB_LLLCHAR (999, "RESERVED NATIONAL USE"),
            /*113*/ new IFB_LLLCHAR (999, "RESERVED NATIONAL USE"),
            /*114*/ new IFB_LLLCHAR (999, "RESERVED NATIONAL USE"   ),
            /*115*/ new IFB_LLLCHAR (999, "RESERVED NATIONAL USE"),
            /*116*/ new IFB_LLLCHAR (999, "RESERVED NATIONAL USE"  ),
            /*117*/ new IFB_LLLCHAR (999, "RESERVED NATIONAL USE"),
            /*118*/ new IFB_LLLCHAR (999, "RESERVED NATIONAL USE"),
            /*119*/ new IFB_LLLCHAR (999, "RESERVED NATIONAL USE"),
            /*120*/ new IFB_LLLCHAR (999, "RESERVED PRIVATE USE"),
            /*121*/ new IFB_LLLCHAR (999, "RESERVED PRIVATE USE"),
            /*122*/ new IFB_LLLCHAR (999, "RESERVED PRIVATE USE"),
            /*123*/ new IFB_LLLCHAR (999, "RESERVED PRIVATE USE"),
            /*124*/ new IFB_LLLCHAR (999, "RESERVED PRIVATE USE"),
            /*125*/ new IFB_LLLCHAR (999, "RESERVED PRIVATE USE"),
            /*126*/ new IFB_LLLCHAR (999, "RESERVED PRIVATE USE"),
            /*127*/ new IFB_LLLCHAR (999, "RESERVED PRIVATE USE"),
            /*128*/ new IFB_BINARY  (  8, "MAC 2")
    };
    public ISO87BPackagerForEDC() {
        super();
        setFieldPackager(fld);

        ISO8583Property iso8583Property = new ISO8583Property();

        iso8583Property.setAppendHexCode      ( ""        );
        iso8583Property.setHexCodecYn         ( "Y"       );
        iso8583Property.setIsoHeaderLength    ( 5         );
        iso8583Property.setMessageBound       ( "AFTERLL" );
        iso8583Property.setMessageFieldType   ( "BINARY"  );
        iso8583Property.setMessageLength      ( 2         );
        iso8583Property.setMessageLengthOffset( 0         );

        this.setIso8583Property( iso8583Property );
    }
}
