package gov.epa.ccte.api.chemical.domain;

public enum InputFormat {
    // AUTO - auto-guess the format
    UNKNOWN, AUTO, MOL, SDF, SMI, SMILES, CSV, JSON, XLSX, TXT, MSP;

    public static InputFormat fromFileName(String fileName) {
        try {
            return fileName == null ? UNKNOWN : InputFormat.valueOf(fileName.substring(fileName.lastIndexOf('.') + 1).toUpperCase());
        } catch ( IllegalArgumentException ex ) {
            return UNKNOWN;
        }
    }
}
