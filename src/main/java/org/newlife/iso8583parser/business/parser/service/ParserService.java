package org.newlife.iso8583parser.business.parser.service;

import org.jpos.iso.*;
import org.newlife.iso8583parser.business.parser.message.iso8583.packager.ISOBasePackager;
import org.newlife.iso8583parser.business.parser.module.ParserHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service
public class ParserService {

    public Map<String, Object> toFixedLength( ISOBasePackager isoBasePackager , String iso , Map<String, Object> consumes ) throws Exception
    {
        log.debug("toFixedLength consumes[{}]", consumes);

        ISOMsg isoMsg = isoBasePackager.createISOMsg();

        isoMsg.setPackager( isoBasePackager );
        isoMsg.setMTI( consumes.get( "mti" ).toString() );

        for ( String key : consumes.keySet() )
        {
            if ( null == consumes.get( key ) )
            {
                continue;
            }

            String fldNumStr = StringUtils.replace( key , "field" , "" );

            if ( StringUtils.isNumeric( fldNumStr ) )
            {
                Object val    = consumes.get( key );
                int    fldNum = Integer.parseInt( fldNumStr );

                isoMsg.set( fldNum , "" );

                ISOComponent fld = isoMsg.getComponent( fldNum );

                log.debug("field [{}]-[{}]" , fldNumStr , val);

                if ( StringUtils.equals( fld.getClass().getSimpleName() , "ISOBinaryField" ) )
                {
                    fld.setValue( ISOUtil.hex2byte( val.toString() ) );
                }
                else
                {
                    fld.setValue( val );
                }

                isoMsg.set( fld );
            }
        }

        byte[] msg;

        if ( isoBasePackager.getIso8583Property().getIsoHeaderLength() > 0 )
        {
            String header = consumes.get( "header" ).toString();
            header        = StringUtils.rightPad( header , isoBasePackager.getIso8583Property().getIsoHeaderLength() , ' ' );

            if ( StringUtils.equals( isoBasePackager.getIso8583Property().getHexCodecYn() , "Y" ) )
            {
                msg = ISOUtil.concat( ISOUtil.hex2byte( header ) , isoMsg.pack() );
            }
            else
            {
                msg = ISOUtil.concat( header.getBytes() , isoMsg.pack() );
            }
        }
        else
        {
            msg = isoMsg.pack();
        }

        if ( StringUtils.isNotBlank( isoBasePackager.getIso8583Property().getAppendHexCode() ) )
        {
            msg = ArrayUtils.add( msg , Byte.decode( isoBasePackager.getIso8583Property().getAppendHexCode() ) );
        }

        int iLen = 0;

        switch ( isoBasePackager.getIso8583Property().getMessageBound() )
        {
            case "ENTIRE":
                iLen = msg.length;
                break;
            case "AFTERLL":
                iLen = msg.length - isoBasePackager.getIso8583Property().getMessageLengthOffset();
                break;
        }

        byte[] len = ParserHelper.getMessageFieldByte( isoBasePackager.getIso8583Property() , iLen );

        try ( ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); PrintStream printStream = new PrintStream( outputStream ) )
        {
            isoMsg.dump( printStream , "  " );
        }
        catch ( Exception e )
        {
            log.error( "dumpStr Error" , e );
        }

        log.debug( "len [{}] [{}]" , len , msg.length + isoBasePackager.getIso8583Property().getMessageLength() );

        byte[] tr;

        if ( isoBasePackager.getIso8583Property().getMessageLengthOffset() > 0 )
        {
            tr = ArrayUtils.insert( isoBasePackager.getIso8583Property().getMessageLengthOffset() , msg , len );
        }
        else
        {
            tr = ISOUtil.concat( len , msg );
        }

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> consume  = new HashMap<>();

        consume .put( "json"    , consumes   );
        response.put( "consume" , consume );

        Map<String, Object> produce = new HashMap<>();

        produce.put( "text" , ISOUtil.dumpString( tr ) );
        produce.put( "hex"  , ISOUtil.byte2hex  ( tr ) );

        response.put( "produce" , produce );

        return response;
    }

    public Map<String, Object> toJSON( ISOBasePackager isoBasePackager , String format , String iso , String consumes ) throws Exception
    {
        log.debug("toJSON consumes[{}]", consumes);

        byte[] msgByte = consumes.getBytes();
        byte[] isoByte;

        if ( StringUtils.equals( StringUtils.upperCase( format ) , "HEX" ) )
        {
            msgByte = ISOUtil.hex2byte( consumes );
        }

        if ( isoBasePackager.getIso8583Property().getMessageLengthOffset() > 0 )
        {
            byte[] prefix  = ArrayUtils.subarray( msgByte
                                                , 0
                                                , isoBasePackager.getIso8583Property().getMessageLengthOffset()
                                                );
            byte[] postfix = ArrayUtils.subarray( msgByte
                                                , isoBasePackager.getIso8583Property().getMessageLengthOffset() + isoBasePackager.getIso8583Property().getMessageLength()
                                                , msgByte.length
                                                );

            isoByte = ISOUtil.concat( prefix , postfix );
        }
        else
        {
            isoByte = ArrayUtils.subarray( msgByte , isoBasePackager.getIso8583Property().getMessageLength() , msgByte.length );
        }

        byte[] isoHeaderByte = new byte[0];

        if ( isoBasePackager.getIso8583Property().getIsoHeaderLength() > 0 )
        {
            isoHeaderByte = ArrayUtils.subarray( isoByte , 0 , isoBasePackager.getIso8583Property().getIsoHeaderLength() );
        }

        int                 prefixLen = isoBasePackager.getIso8583Property().getIsoHeaderLength();
        byte[]              pm        = ArrayUtils.subarray( isoByte , prefixLen , prefixLen + isoByte.length );
        ISOMsg              isoMsg    = new ISOMsg();
        Map<String, Object> jsonMap   = new LinkedHashMap<>();
        String              header;

        isoMsg.setPackager( isoBasePackager );

        if ( isoBasePackager.getIso8583Property().getIsoHeaderLength() > 0 )
        {
            for ( int index = 0; index < isoHeaderByte.length; index++ )
            {
                if ( isoHeaderByte[index] == 0 )
                {
                    isoHeaderByte[index] = 32;
                }
            }

            if ( StringUtils.equals( isoBasePackager.getIso8583Property().getHexCodecYn() , "Y" ) )
            {
                header = ISOUtil.hexString( isoHeaderByte );

                isoMsg.setHeader( isoHeaderByte );
            }
            else
            {
                header = ISOUtil.dumpString( isoHeaderByte );

                isoMsg.setHeader( ISOUtil.decodeHexDump( header ) );
            }

            jsonMap.put( "header" , header );
        }

        isoMsg.unpack( pm );
        isoMsg.dump( System.out , "  " );

        Map<Integer, Object> isoMap = isoMsg.getChildren();

        jsonMap.put( "mti" , isoMsg.getMTI() );

        for ( Integer key : isoMap.keySet() )
        {
            Object val = isoMap.get( key );

            switch ( val.getClass().getSimpleName() )
            {
                case "ISOBitMap":
                    ISOBitMap isoBitMap = ( ISOBitMap ) val;

                    jsonMap.put( "field" + key , isoBitMap.getValue().toString() );
                    break;
                case "ISOField":
                    ISOField isoField = ( ISOField ) val;

                    jsonMap.put( "field" + key , isoField.getValue().toString() );
                    break;
                case "ISOBinaryField":
                    ISOBinaryField isoBinaryField = ( ISOBinaryField ) val;

                    jsonMap.put( "field" + key , ISOUtil.byte2hex( ( byte[] ) isoBinaryField.getValue() ) );
                    break;
                default:
                    ISOComponent isoComponent = ( ISOComponent ) val;

                    jsonMap.put( "field" + key , isoComponent.getValue().toString() );
            }
        }

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> consume  = new HashMap<>();

        if ( StringUtils.equals( StringUtils.upperCase( format ) , "HEX" ) )
        {
            consume.put( "hex" , consumes );
        }
        else
        {
            consume.put( "text" , consumes );
        }

        response.put( "consume" , consume );

        Map<String, Object> produce = new HashMap<>();

        produce.put( "json" , jsonMap );

        response.put( "produce" , produce );

        return response;
    }

}
