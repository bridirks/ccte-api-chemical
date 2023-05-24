package gov.epa.ccte.api.chemical.projection.chemicallist;

import java.math.BigDecimal;

/**
 * A Projection for the {@link gov.epa.ccte.api.chemical.domain.ChemicalListDetail} entity
 */
public interface ChemicalListDetailAll {
    Integer getId();

    String getDtxsid();

    String getCasrn();

    Integer getCompoundId();

    Integer getGenericSubstanceId();

    String getPreferredName();

    Integer getActiveAssays();

    Integer getCpdataCount();

    String getMolFormula();

    BigDecimal getMolWeight();

    BigDecimal getMonoisotopicmass();

    Integer getPercentAssays();

    Integer getPubchemCount();

    BigDecimal getPubmedCount();

    Integer getQcLevel();

    String getQcLevelDesc();

    String getStereo();

    Integer getTotalAssays();

    String getToxcastSelect();

    Integer getPubchemCid();

    String getListName();

    String getListLabel();

    String getListCategory();

    String getListShortdesc();

    Boolean getListIsVisible();

    String getListVisibility();

    Integer getSourcesCount();

    Integer getMultiComponent();

    Integer getIsotope();

    Boolean getHasStructureImage();

    Integer getRelatedSubstanceCount();

    Integer getRelatedStructureCount();

    String getInchi();

    String getInchiKey();

    String getIupacName();

    String getSmiles();
}