package gov.epa.ccte.api.chemical.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "chemical_list_details", schema = "ms")
public class ChemicalListDetail {
    @Id
    @NotNull
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "dtxsid", length = 50)
    private String dtxsid;

    @Size(max = 255)
    @Column(name = "casrn")
    private String casrn;

    @Column(name = "compound_id")
    private Integer compoundId;

    @Column(name = "generic_substance_id")
    private Integer genericSubstanceId;

    @Column(name = "preferred_name")
    @Type(type = "org.hibernate.type.TextType")
    private String preferredName;

    @Column(name = "active_assays")
    private Integer activeAssays;

    @Column(name = "cpdata_count")
    private Integer cpdataCount;

    @Size(max = 255)
    @Column(name = "mol_formula")
    private String molFormula;

    @Column(name = "mol_weight")
    private BigDecimal molWeight;

    @Column(name = "monoisotopicmass")
    private BigDecimal monoisotopicmass;

    @Column(name = "percent_assays")
    private Integer percentAssays;

    @Column(name = "pubchem_count")
    private Integer pubchemCount;

    @Column(name = "pubmed_count")
    private BigDecimal pubmedCount;

    @Column(name = "qc_level")
    private Integer qcLevel;

    @Size(max = 255)
    @Column(name = "qc_level_desc")
    private String qcLevelDesc;

    @Size(max = 1)
    @Column(name = "stereo", length = 1)
    private String stereo;

    @Column(name = "total_assays")
    private Integer totalAssays;

    @Column(name = "toxcast_select")
    @Type(type = "org.hibernate.type.TextType")
    private String toxcastSelect;

    @Column(name = "pubchem_cid")
    private Integer pubchemCid;

    @Column(name = "list_name")
    @Type(type = "org.hibernate.type.TextType")
    private String listName;

    @Column(name = "list_label")
    @Type(type = "org.hibernate.type.TextType")
    private String listLabel;

    @Column(name = "list_category")
    @Type(type = "org.hibernate.type.TextType")
    private String listCategory;

    @Column(name = "list_shortdesc")
    @Type(type = "org.hibernate.type.TextType")
    private String listShortdesc;

    @Column(name = "list_is_visible")
    private Boolean listIsVisible;

    @Column(name = "list_visibility")
    @Type(type = "org.hibernate.type.TextType")
    private String listVisibility;

    @Column(name = "sources_count")
    private Integer sourcesCount;

    @Column(name = "multi_component")
    private Integer multiComponent;

    @Column(name = "isotope")
    private Integer isotope;

    @Column(name = "has_structure_image")
    private Boolean hasStructureImage;

    @Column(name = "related_substance_count")
    private Integer relatedSubstanceCount;

    @Column(name = "related_structure_count")
    private Integer relatedStructureCount;

    @Column(name = "inchi")
    @Type(type = "org.hibernate.type.TextType")
    private String inchi;

    @Column(name = "inchi_key")
    @Type(type = "org.hibernate.type.TextType")
    private String inchiKey;

    @Column(name = "iupac_name")
    @Type(type = "org.hibernate.type.TextType")
    private String iupacName;

    @Column(name = "smiles")
    @Type(type = "org.hibernate.type.TextType")
    private String smiles;

    @Column(name = "created_at")
    private Instant createdAt;

    @Size(max = 50)
    @Column(name = "created_by", length = 50)
    private String createdBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDtxsid() {
        return dtxsid;
    }

    public void setDtxsid(String dtxsid) {
        this.dtxsid = dtxsid;
    }

    public String getCasrn() {
        return casrn;
    }

    public void setCasrn(String casrn) {
        this.casrn = casrn;
    }

    public Integer getCompoundId() {
        return compoundId;
    }

    public void setCompoundId(Integer compoundId) {
        this.compoundId = compoundId;
    }

    public Integer getGenericSubstanceId() {
        return genericSubstanceId;
    }

    public void setGenericSubstanceId(Integer genericSubstanceId) {
        this.genericSubstanceId = genericSubstanceId;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public Integer getActiveAssays() {
        return activeAssays;
    }

    public void setActiveAssays(Integer activeAssays) {
        this.activeAssays = activeAssays;
    }

    public Integer getCpdataCount() {
        return cpdataCount;
    }

    public void setCpdataCount(Integer cpdataCount) {
        this.cpdataCount = cpdataCount;
    }

    public String getMolFormula() {
        return molFormula;
    }

    public void setMolFormula(String molFormula) {
        this.molFormula = molFormula;
    }

    public BigDecimal getMolWeight() {
        return molWeight;
    }

    public void setMolWeight(BigDecimal molWeight) {
        this.molWeight = molWeight;
    }

    public BigDecimal getMonoisotopicmass() {
        return monoisotopicmass;
    }

    public void setMonoisotopicmass(BigDecimal monoisotopicmass) {
        this.monoisotopicmass = monoisotopicmass;
    }

    public Integer getPercentAssays() {
        return percentAssays;
    }

    public void setPercentAssays(Integer percentAssays) {
        this.percentAssays = percentAssays;
    }

    public Integer getPubchemCount() {
        return pubchemCount;
    }

    public void setPubchemCount(Integer pubchemCount) {
        this.pubchemCount = pubchemCount;
    }

    public BigDecimal getPubmedCount() {
        return pubmedCount;
    }

    public void setPubmedCount(BigDecimal pubmedCount) {
        this.pubmedCount = pubmedCount;
    }

    public Integer getQcLevel() {
        return qcLevel;
    }

    public void setQcLevel(Integer qcLevel) {
        this.qcLevel = qcLevel;
    }

    public String getQcLevelDesc() {
        return qcLevelDesc;
    }

    public void setQcLevelDesc(String qcLevelDesc) {
        this.qcLevelDesc = qcLevelDesc;
    }

    public String getStereo() {
        return stereo;
    }

    public void setStereo(String stereo) {
        this.stereo = stereo;
    }

    public Integer getTotalAssays() {
        return totalAssays;
    }

    public void setTotalAssays(Integer totalAssays) {
        this.totalAssays = totalAssays;
    }

    public String getToxcastSelect() {
        return toxcastSelect;
    }

    public void setToxcastSelect(String toxcastSelect) {
        this.toxcastSelect = toxcastSelect;
    }

    public Integer getPubchemCid() {
        return pubchemCid;
    }

    public void setPubchemCid(Integer pubchemCid) {
        this.pubchemCid = pubchemCid;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getListLabel() {
        return listLabel;
    }

    public void setListLabel(String listLabel) {
        this.listLabel = listLabel;
    }

    public String getListCategory() {
        return listCategory;
    }

    public void setListCategory(String listCategory) {
        this.listCategory = listCategory;
    }

    public String getListShortdesc() {
        return listShortdesc;
    }

    public void setListShortdesc(String listShortdesc) {
        this.listShortdesc = listShortdesc;
    }

    public Boolean getListIsVisible() {
        return listIsVisible;
    }

    public void setListIsVisible(Boolean listIsVisible) {
        this.listIsVisible = listIsVisible;
    }

    public String getListVisibility() {
        return listVisibility;
    }

    public void setListVisibility(String listVisibility) {
        this.listVisibility = listVisibility;
    }

    public Integer getSourcesCount() {
        return sourcesCount;
    }

    public void setSourcesCount(Integer sourcesCount) {
        this.sourcesCount = sourcesCount;
    }

    public Integer getMultiComponent() {
        return multiComponent;
    }

    public void setMultiComponent(Integer multiComponent) {
        this.multiComponent = multiComponent;
    }

    public Integer getIsotope() {
        return isotope;
    }

    public void setIsotope(Integer isotope) {
        this.isotope = isotope;
    }

    public Boolean getHasStructureImage() {
        return hasStructureImage;
    }

    public void setHasStructureImage(Boolean hasStructureImage) {
        this.hasStructureImage = hasStructureImage;
    }

    public Integer getRelatedSubstanceCount() {
        return relatedSubstanceCount;
    }

    public void setRelatedSubstanceCount(Integer relatedSubstanceCount) {
        this.relatedSubstanceCount = relatedSubstanceCount;
    }

    public Integer getRelatedStructureCount() {
        return relatedStructureCount;
    }

    public void setRelatedStructureCount(Integer relatedStructureCount) {
        this.relatedStructureCount = relatedStructureCount;
    }

    public String getInchi() {
        return inchi;
    }

    public void setInchi(String inchi) {
        this.inchi = inchi;
    }

    public String getInchiKey() {
        return inchiKey;
    }

    public void setInchiKey(String inchiKey) {
        this.inchiKey = inchiKey;
    }

    public String getIupacName() {
        return iupacName;
    }

    public void setIupacName(String iupacName) {
        this.iupacName = iupacName;
    }

    public String getSmiles() {
        return smiles;
    }

    public void setSmiles(String smiles) {
        this.smiles = smiles;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

}