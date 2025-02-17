package org.newlife.iso8583parser.business.parser.module;

import org.newlife.iso8583parser.business.parser.model.ISO8583Property;
import org.apache.commons.lang3.StringUtils;
import org.jpos.iso.ISOUtil;

public class ParserHelper {

    public static byte[] getMessageFieldByte(ISO8583Property iso8583Property, int len) throws Exception
    {
        byte[] bLen = new byte[ iso8583Property.getMessageLength() ];

        switch ( iso8583Property.getMessageFieldType() )
        {
            case "BINARY":
                bLen = ISOUtil.int2byte( len );
                break;
            case "DECIMAL":
                bLen = StringUtils.leftPad( String.valueOf( len ) , iso8583Property.getMessageLength() , '0' ).getBytes();
                break;
            case "MPNBINARY":
                String nullStr     = "0000";
                String tmpStr      = Integer.toHexString( len );
                int    tmpStrLen   = tmpStr.length();
                String digitString = StringUtils.substring( nullStr , 0 , 4 - tmpStrLen ) + tmpStr;
                byte[] asc         = new byte[ iso8583Property.getMessageLength() ];

                int index1 = 0;

                for ( int index2 = iso8583Property.getMessageLength() - 1; index2 >= 0; index2-- )
                {
                    asc[ index2 ] = ( byte ) Integer.parseInt( StringUtils.substring( digitString , 2 * index1 , 2 * index1 + 2 ) , 16 );
                    index1++;
                }

                return asc;
        }

        int prefixLen = iso8583Property.getMessageLengthOffset() + iso8583Property.getMessageLength();

        if ( bLen.length < iso8583Property.getMessageLength() )
        {
            bLen = ISOUtil.concat( new byte[ prefixLen - bLen.length ] , bLen );
        }

        return bLen;
    }

//    public static int getMessageFieldValue( ISO8583Property iso8583Property , byte[] byteMsg ) throws Exception
//    {
//        if ( byteMsg.length < iso8583Property.getMessageLengthOffset() + iso8583Property.getMessageLength() )
//        {
//            return -1;
//        }
//
//        byte[] mLenBytes = ArrayUtils.subarray( byteMsg
//                                              , iso8583Property.getMessageLengthOffset()
//                                              , iso8583Property.getMessageLengthOffset() + iso8583Property.getMessageLength()
//                                              );
//
//        switch ( iso8583Property.getMessageFieldType() )
//        {
//            case "BINARY":
//                return ISOUtil.byte2int( mLenBytes );
//            case "DECIMAL":
//                return Integer.parseInt( new String( mLenBytes ) );
//            case "MPNBINARY":
//                String str     = "";
//                int    ascSize = mLenBytes.length;
//
//                for ( int index = ascSize - 1; index >= 0; index-- )
//                {
//                    String hex = Integer.toHexString( mLenBytes[index] );
//
//                    if ( hex.length() == 1 )
//                    {
//                        str += "0" + hex;
//                    }
//                    else
//                    {
//                        str += StringUtils.substring( hex , hex.length() - 2 , hex.length() );
//                    }
//                }
//
//                long ll = Long.parseLong( str , 16 );
//
//                return ( int ) ll;
//        }
//
//        return 0;
//    }
}
