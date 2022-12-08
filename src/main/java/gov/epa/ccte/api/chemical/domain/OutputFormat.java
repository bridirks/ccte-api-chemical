package gov.epa.ccte.api.chemical.domain;

public enum OutputFormat {
    NONE, SDF, SMI, MOL, CSV, JSON, XLSX, PDF, HTML, XML;

    public static OutputFormat fromFileName(String fileName) {
        return OutputFormat.valueOf(fileName.substring(fileName.lastIndexOf('.') + 1).toUpperCase());
    }
}
