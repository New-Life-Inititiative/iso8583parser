package org.newlife.iso8583parser.business.parser.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ISO8583Property {

    private String  appendHexCode      ;
    private String  hexCodecYn         ;
    private Integer isoHeaderLength    ;
    private String  messageBound       ;
    private String  messageFieldType   ;
    private Integer messageLength      ;
    private Integer messageLengthOffset;

}
