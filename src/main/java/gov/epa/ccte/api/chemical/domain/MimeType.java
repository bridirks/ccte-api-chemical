package gov.epa.ccte.api.chemical.domain;

import org.springframework.http.MediaType;

/**
 * https://www.ch.ic.ac.uk/chemime/
 *
 * @author valt
 */

public class MimeType {
    public final static MediaType TEXT_CSV = new MediaType("text", "csv");
    public final static String TEXT_CSV_VALUE = "text/csv";

    public final static MediaType EXCEL_XLSX = new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    public final static String EXCEL_XLSX_VALUE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public final static MediaType WORD_DOCX = new MediaType("application", "vnd.openxmlformats-officedocument.wordprocessingml.document");
    public final static String WORD_DOCX_VALUE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

    public final static MediaType POWERPOINT_PPTX = new MediaType("application", "vnd.openxmlformats-officedocument.presentationml.presentation");
    public final static String POWERPOINT_PPTX_VALUE = "application/vnd.openxmlformats-officedocument.presentationml.presentation";


    public final static MediaType IMAGE_SVG = new MediaType("image", "svg+xml");
    public final static String IMAGE_SVG_VALUE = "image/svg+xml";


    public final static MediaType CHEMICAL_MOL = new MediaType("chemical", "x-mdl-molfile");
    public final static String CHEMICAL_MOL_VALUE = "chemical/x-mdl-molfile";

    public final static MediaType CHEMICAL_SDF = new MediaType("chemical", "x-mdl-sdfile");
    public final static String CHEMICAL_SDF_VALUE = "chemical/x-mdl-sdfile";

    public final static MediaType CHEMICAL_SMI = new MediaType("chemical", "x-daylight-smiles");
    public final static String CHEMICAL_SMI_VALUE = "chemical/x-daylight-smiles";

    public static MediaType getMimeType(OutputFormat format) {
        switch ( format ) {
            case SDF:
                return MimeType.CHEMICAL_SDF;
            case SMI:
                return MimeType.CHEMICAL_SMI;
            case MOL:
                return MimeType.CHEMICAL_MOL;
            case CSV:
                return MimeType.TEXT_CSV;
            case JSON:
                return MediaType.APPLICATION_JSON;
            case XLSX:
                return MimeType.EXCEL_XLSX;
            default:
                return MediaType.TEXT_PLAIN;
        }
    }

    public static MediaType getMimeType(InputFormat format) {
        switch ( format ) {
            case SDF:
                return MimeType.CHEMICAL_SDF;
            case SMI:
                return MimeType.CHEMICAL_SMI;
            case MOL:
                return MimeType.CHEMICAL_MOL;
            case CSV:
                return MimeType.TEXT_CSV;
            case JSON:
                return MediaType.APPLICATION_JSON;
            case XLSX:
                return MimeType.EXCEL_XLSX;
            default:
                return MediaType.TEXT_PLAIN;
        }
    }
}

