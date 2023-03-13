package gov.epa.ccte.api.chemical.projection;

/**
 * A Projection for the {@link gov.epa.ccte.api.chemical.domain.ChemicalDetail} entity
 */
public interface ChemicalIdentifier extends ChemicalDetailBase {
    String getCasrn();

    String getPreferredName();

    String getIupacName();

    String getInchikey();

    String getDtxsid();

    String getDtxcid();
}