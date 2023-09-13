package gov.epa.ccte.api.chemical.projection.search;

/**
 * Projection for {@link gov.epa.ccte.api.chemical.domain.ChemicalSearch}
 */
public interface ChemicalSearchAll {
    Integer getId();

    String getDtxsid();

    String getDtxcid();

    String getCasrn();

    String getSmiles();

    String getPreferredName();

    String getSearchGroup();

    String getSearchName();

    String getSearchValue();

    String getModifiedValue();

    Integer getRank();

    Integer getHasStructureImage();

    Integer getIsMarkush();
}