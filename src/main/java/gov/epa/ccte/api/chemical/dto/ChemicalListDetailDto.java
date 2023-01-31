package gov.epa.ccte.api.chemical.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link gov.epa.ccte.api.chemical.domain.ChemicalListDetail} entity
 */
@Data
public class ChemicalListDetailDto implements Serializable {
    @NotNull
    private final Integer id;
    @Size(max = 50)
    private final String dtxsid;
    @Size(max = 255)
    private final String casrn;
    private final Integer compoundId;
    private final Integer genericSubstanceId;
    private final String preferredName;
    private final Integer activeAssays;
    private final Integer cpdataCount;
    @Size(max = 255)
    private final String molFormula;
    private final BigDecimal molWeight;
    private final BigDecimal monoisotopicmass;
    private final Integer percentAssays;
    private final Integer pubchemCount;
    private final BigDecimal pubmedCount;
    private final Integer qcLevel;
    @Size(max = 255)
    private final String qcLevelDesc;
    @Size(max = 1)
    private final String stereo;
    private final Integer totalAssays;
    private final String toxcastSelect;
    private final Integer pubchemCid;
    private final String listName;
    private final String listLabel;
    private final String listCategory;
    private final String listShortdesc;
    private final Boolean listIsVisible;
    private final String listVisibility;
    private final Integer sourcesCount;
    private final Integer multiComponent;
    private final Integer isotope;
    private final Boolean hasStructureImage;
    private final Integer relatedSubstanceCount;
    private final Integer relatedStructureCount;
    private final String inchi;
    private final String inchiKey;
    private final String iupacName;
    private final String smiles;
}