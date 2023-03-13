package gov.epa.ccte.api.chemical.projection;

/**
 * A Projection for the {@link gov.epa.ccte.api.chemical.domain.ChemicalDetail} entity
 */
public interface ChemicalStructure extends ChemicalDetailBase{
    Long getId();

    String getCasrn();

    String getPreferredName();

    Integer getHasStructureImage();

    String getSmiles();

    String getInchiString();

    String getInchikey();

    String getQsarReadySmiles();

    String getMsReadySmiles();

    String getDtxsid();

    String getDtxcid();
}