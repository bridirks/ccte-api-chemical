package gov.epa.ccte.api.chemical.projection.chemicallist;

import java.time.Instant;

/**
 * A Projection for the {@link gov.epa.ccte.api.chemical.domain.ChemicalList} entity
 */
public interface ChemicalListAll extends ChemicalListBase {
    Integer getId();

    String getName();

    String getShortDescription();

    String getLongDescription();

    Instant getCreatedDate();

    Instant getLastModifiedDate();

    Integer getChemicalCount();

    String getLabel();

    String getType();
}