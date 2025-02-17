package org.newlife.iso8583parser.business.parser.message.iso8583.packager;

import lombok.Getter;
import lombok.Setter;
import org.newlife.iso8583parser.business.parser.model.ISO8583Property;
import org.apache.commons.lang3.StringUtils;

public class ISOBasePackager extends org.jpos.iso.ISOBasePackager {

    @Getter
    @Setter
    private ISO8583Property iso8583Property;

    public enum Packager {
        BASE1PACKAGER,
        EUROPACKAGER,
        BASE24PACKAGER,
        BASE24PACKAGERFORNPG,
        BASE24PACKAGERFORPRIMA,
        ISO87APACKAGER,
        ISO87APACKAGERFORBERSAMA,
        ISO87APACKAGERFORIONPAY,
        ISO87APACKAGERFORTAX,
        ISO87APACKAGERFORVISA,
        ISO87BPACKAGER,
        ISO87BPACKAGERFOREDC,
        ISO93APACKAGER,
        ISO93APACKAGERBBITMAP,
        ISO93BPACKAGER
    }

    ISOBasePackager()
    {
        super();
    }

    public static ISOBasePackager getISOBasePackager( String iso ) throws Exception
    {
        ISOBasePackager isoBasePackager;

        try
        {
            switch ( Packager.valueOf( StringUtils.upperCase( iso ) ) )
            {
                case BASE1PACKAGER:
                    isoBasePackager = new Base1Packager();
                    break;
                case BASE24PACKAGER:
                    isoBasePackager = new BASE24Packager();
                    break;
                case BASE24PACKAGERFORNPG:
                    isoBasePackager = new BASE24PackagerForNPG();
                    break;
                case BASE24PACKAGERFORPRIMA:
                    isoBasePackager = new BASE24PackagerForPrima();
                    break;
                case EUROPACKAGER:
                    isoBasePackager = new EuroPackager();
                    break;
                case ISO87APACKAGER:
                    isoBasePackager = new ISO87APackager();
                    break;
                case ISO87APACKAGERFORBERSAMA:
                    isoBasePackager = new ISO87APackagerForBersama();
                    break;
                case ISO87APACKAGERFORIONPAY:
                    isoBasePackager = new ISO87APackagerForIONPay();
                    break;
                case ISO87APACKAGERFORTAX:
                    isoBasePackager = new ISO87APackagerForTax();
                    break;
                case ISO87APACKAGERFORVISA:
                    isoBasePackager = new ISO87APackagerForVISA();
                    break;
                case ISO87BPACKAGER:
                    isoBasePackager = new ISO87BPackager();
                    break;
                case ISO87BPACKAGERFOREDC:
                    isoBasePackager = new ISO87BPackagerForEDC();
                    break;
                case ISO93APACKAGER:
                    isoBasePackager = new ISO93APackager();
                    break;
                case ISO93APACKAGERBBITMAP:
                    isoBasePackager = new ISO93APackagerBBitmap();
                    break;
                case ISO93BPACKAGER:
                    isoBasePackager = new ISO93BPackager();
                    break;
                default:
                    isoBasePackager = null;
            }
        }
        catch ( Exception e )
        {
            throw new Exception( "Packager is not found" );
        }

        return isoBasePackager;
    }
}
