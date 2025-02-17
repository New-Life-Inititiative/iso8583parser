package org.newlife.iso8583parser.business.parser;

import jakarta.servlet.http.HttpServletRequest;
import org.newlife.iso8583parser.business.parser.message.iso8583.packager.ISOBasePackager;
import org.newlife.iso8583parser.business.parser.model.ISO8583Property;
import org.newlife.iso8583parser.business.parser.service.ParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/iso8583parser")
@RestController
public class ParserController {

    @Autowired
    private ParserService parserService;

    @PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE
                , produces = MediaType.APPLICATION_JSON_VALUE
                , value    = "/parser/to/fixedlength/{iso}"
    )
    public Map<String, Object> toFixedlength(@PathVariable String iso , @RequestBody Map<String, Object> consumes) throws Exception
    {
        ISOBasePackager isoBasePackager = ISOBasePackager.getISOBasePackager( iso );

        return parserService.toFixedLength( isoBasePackager , iso , consumes );
    }

    @PostMapping( consumes = MediaType.TEXT_PLAIN_VALUE
                , produces = MediaType.APPLICATION_JSON_VALUE
                , value    = "/parser/{format}/to/json/{iso}"
    )
    public Map<String, Object> toJSON(@PathVariable String format , @PathVariable String iso , @RequestBody String consumes) throws Exception
    {
        ISOBasePackager isoBasePackager = ISOBasePackager.getISOBasePackager( iso );

        return parserService.toJSON( isoBasePackager , format , iso , consumes );
    }

    @PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE
            , value    = "/parser/custom/to/fixedlength/{iso}"
    )
    public Map<String, Object> customToFixedlength(@PathVariable String iso , @RequestBody Map<String, Object> consumes , HttpServletRequest request) throws Exception
    {
        ISOBasePackager isoBasePackager = ISOBasePackager.getISOBasePackager( iso );

        isoBasePackager.getIso8583Property().setAppendHexCode      (                   request.getHeader( "X-Append-Hex-Code"       )   );
        isoBasePackager.getIso8583Property().setHexCodecYn         (                   request.getHeader( "X-Hex-Codec-Yn"          )   );
        isoBasePackager.getIso8583Property().setIsoHeaderLength    ( Integer.parseInt( request.getHeader( "X-Iso-Header-Length"     ) ) );
        isoBasePackager.getIso8583Property().setMessageBound       (                   request.getHeader( "X-Message-Bound"         )   );
        isoBasePackager.getIso8583Property().setMessageFieldType   (                   request.getHeader( "X-Message-Field-Type"    )   );
        isoBasePackager.getIso8583Property().setMessageLength      ( Integer.parseInt( request.getHeader( "X-Message-Length"        ) ) );
        isoBasePackager.getIso8583Property().setMessageLengthOffset( Integer.parseInt( request.getHeader( "X-Message-Length-Offset" ) ) );

        return parserService.toFixedLength( isoBasePackager , iso , consumes );
    }

    @PostMapping( consumes = MediaType.TEXT_PLAIN_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE
            , value    = "/parser/custom/{format}/to/json/{iso}"
    )
    public Map<String, Object> customToJSON(@PathVariable String format , @PathVariable String iso , @RequestBody String consumes , HttpServletRequest request) throws Exception
    {
        ISOBasePackager isoBasePackager = ISOBasePackager.getISOBasePackager( iso );

        isoBasePackager.getIso8583Property().setAppendHexCode      (                   request.getHeader( "X-Append-Hex-Code"       )   );
        isoBasePackager.getIso8583Property().setHexCodecYn         (                   request.getHeader( "X-Hex-Codec-Yn"          )   );
        isoBasePackager.getIso8583Property().setIsoHeaderLength    ( Integer.parseInt( request.getHeader( "X-Iso-Header-Length"     ) ) );
        isoBasePackager.getIso8583Property().setMessageBound       (                   request.getHeader( "X-Message-Bound"         )   );
        isoBasePackager.getIso8583Property().setMessageFieldType   (                   request.getHeader( "X-Message-Field-Type"    )   );
        isoBasePackager.getIso8583Property().setMessageLength      ( Integer.parseInt( request.getHeader( "X-Message-Length"        ) ) );
        isoBasePackager.getIso8583Property().setMessageLengthOffset( Integer.parseInt( request.getHeader( "X-Message-Length-Offset" ) ) );

        return parserService.toJSON( isoBasePackager , format , iso , consumes );
    }

    @RequestMapping( value = "/packager/list" )
    public ISOBasePackager.Packager[] list() throws Exception
    {
        ISOBasePackager.Packager[] listPackager = ISOBasePackager.Packager.values();

        return listPackager;
    }

    @RequestMapping( value = "packager/{iso}/property" )
    public ISO8583Property property( @PathVariable String iso ) throws Exception
    {
        ISOBasePackager isoBasePackager = ISOBasePackager.getISOBasePackager( iso );

        return isoBasePackager.getIso8583Property();
    }
}
