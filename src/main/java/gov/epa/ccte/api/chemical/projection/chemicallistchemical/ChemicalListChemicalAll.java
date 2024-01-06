package gov.epa.ccte.api.chemical.projection.chemicallistchemical;

/**
 * Projection for {@link gov.epa.ccte.api.chemical.domain.ChemicalListChemical}
 */
public interface ChemicalListChemicalAll {
    Integer getListId();

    String getListName();

    String getDtxsid();
}