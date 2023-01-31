package gov.epa.ccte.api.chemical.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Entity
@Table(name = "chemical_details", schema = "ms", indexes = {
        @Index(name = "chemical_details_sid_cid", columnList = "dtxsid, dtxcid")
})
public class ChemicalDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 64)
    @Column(name = "h_chem_hash_key", length = 64)
    private String hChemHashKey;

    @Size(max = 255)
    @Column(name = "casrn")
    private String casrn;

    @Column(name = "compound_id")
    private Integer compoundId;

    @Column(name = "generic_substance_id")
    private Integer genericSubstanceId;

    @Size(max = 255)
    @Column(name = "preferred_name")
    private String preferredName;

    @Column(name = "active_assays")
    private Integer activeAssays;

    @Column(name = "cpdata_count")
    private Long cpdataCount;

    @Size(max = 255)
    @Column(name = "mol_formula")
    private String molFormula;

    @Column(name = "monoisotopic_mass")
    private Double monoisotopicMass;

    @Column(name = "percent_assays")
    private Double percentAssays;

    @Column(name = "pubchem_count")
    private Integer pubchemCount;

    @Column(name = "pubmed_count")
    private Double pubmedCount;

    @Column(name = "sources_count")
    private Long sourcesCount;

    @Column(name = "qc_level")
    private Integer qcLevel;

    @Size(max = 255)
    @Column(name = "qc_level_desc")
    private String qcLevelDesc;

    @Size(max = 1)
    @Column(name = "stereo", length = 1)
    private String stereo;

    @Column(name = "isotope")
    private Integer isotope;

    @Column(name = "multicomponent")
    private Integer multicomponent;

    @Column(name = "total_assays")
    private Integer totalAssays;

    @Column(name = "pubchem_cid")
    private Integer pubchemCid;

    @Column(name = "mol_file")
    @Type(type = "org.hibernate.type.TextType")
    private String molFile;

    @Column(name = "mrv_file")
    @Type(type = "org.hibernate.type.TextType")
    private String mrvFile;

    @Column(name = "related_substance_count")
    private Long relatedSubstanceCount;

    @Column(name = "related_structure_count")
    private Long relatedStructureCount;

    @Column(name = "mol_image")
    private byte[] molImage;

    @Column(name = "has_structure_image")
    private Integer hasStructureImage;

    @Size(max = 5000)
    @Column(name = "iupac_name", length = 5000)
    private String iupacName;

    @Column(name = "smiles")
    @Type(type = "org.hibernate.type.TextType")
    private String smiles;

    @Column(name = "inchi_string")
    @Type(type = "org.hibernate.type.TextType")
    private String inchiString;

    @Column(name = "average_mass")
    private Double averageMass;

    @Size(max = 255)
    @Column(name = "inchikey")
    private String inchikey;

    @Size(max = 4000)
    @Column(name = "qc_notes", length = 4000)
    private String qcNotes;

    @Column(name = "qsar_ready_smiles")
    @Type(type = "org.hibernate.type.TextType")
    private String qsarReadySmiles;

    @Column(name = "ms_ready_smiles")
    @Type(type = "org.hibernate.type.TextType")
    private String msReadySmiles;

    @Size(max = 255)
    @Column(name = "iris_link")
    private String irisLink;

    @Size(max = 255)
    @Column(name = "pprtv_link")
    private String pprtvLink;

    @Size(max = 255)
    @Column(name = "wikipedia_article")
    private String wikipediaArticle;

    @Size(max = 20000)
    @Column(name = "descriptor_string_tsv", length = 20000)
    private String descriptorStringTsv;

    @Column(name = "is_markush")
    private Integer isMarkush;

    @NotNull
    @Column(name = "date_loaded", nullable = false)
    private Instant dateLoaded;

    @Size(max = 100)
    @Column(name = "dtxsid", length = 100)
    private String dtxsid;

    @Size(max = 100)
    @Column(name = "dtxcid", length = 100)
    private String dtxcid;

    @Size(max = 10)
    @Column(name = "toxcast_select", length = 10)
    private String toxcastSelect;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHChemHashKey() {
        return hChemHashKey;
    }

    public void setHChemHashKey(String hChemHashKey) {
        this.hChemHashKey = hChemHashKey;
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

    public Long getCpdataCount() {
        return cpdataCount;
    }

    public void setCpdataCount(Long cpdataCount) {
        this.cpdataCount = cpdataCount;
    }

    public String getMolFormula() {
        return molFormula;
    }

    public void setMolFormula(String molFormula) {
        this.molFormula = molFormula;
    }

    public Double getMonoisotopicMass() {
        return monoisotopicMass;
    }

    public void setMonoisotopicMass(Double monoisotopicMass) {
        this.monoisotopicMass = monoisotopicMass;
    }

    public Double getPercentAssays() {
        return percentAssays;
    }

    public void setPercentAssays(Double percentAssays) {
        this.percentAssays = percentAssays;
    }

    public Integer getPubchemCount() {
        return pubchemCount;
    }

    public void setPubchemCount(Integer pubchemCount) {
        this.pubchemCount = pubchemCount;
    }

    public Double getPubmedCount() {
        return pubmedCount;
    }

    public void setPubmedCount(Double pubmedCount) {
        this.pubmedCount = pubmedCount;
    }

    public Long getSourcesCount() {
        return sourcesCount;
    }

    public void setSourcesCount(Long sourcesCount) {
        this.sourcesCount = sourcesCount;
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

    public Integer getIsotope() {
        return isotope;
    }

    public void setIsotope(Integer isotope) {
        this.isotope = isotope;
    }

    public Integer getMulticomponent() {
        return multicomponent;
    }

    public void setMulticomponent(Integer multicomponent) {
        this.multicomponent = multicomponent;
    }

    public Integer getTotalAssays() {
        return totalAssays;
    }

    public void setTotalAssays(Integer totalAssays) {
        this.totalAssays = totalAssays;
    }

    public Integer getPubchemCid() {
        return pubchemCid;
    }

    public void setPubchemCid(Integer pubchemCid) {
        this.pubchemCid = pubchemCid;
    }

    public String getMolFile() {
        return molFile;
    }

    public void setMolFile(String molFile) {
        this.molFile = molFile;
    }

    public String getMrvFile() {
        return mrvFile;
    }

    public void setMrvFile(String mrvFile) {
        this.mrvFile = mrvFile;
    }

    public Long getRelatedSubstanceCount() {
        return relatedSubstanceCount;
    }

    public void setRelatedSubstanceCount(Long relatedSubstanceCount) {
        this.relatedSubstanceCount = relatedSubstanceCount;
    }

    public Long getRelatedStructureCount() {
        return relatedStructureCount;
    }

    public void setRelatedStructureCount(Long relatedStructureCount) {
        this.relatedStructureCount = relatedStructureCount;
    }

    public byte[] getMolImage() {
        return molImage;
    }

    public void setMolImage(byte[] molImage) {
        this.molImage = molImage;
    }

    public Integer getHasStructureImage() {
        return hasStructureImage;
    }

    public void setHasStructureImage(Integer hasStructureImage) {
        this.hasStructureImage = hasStructureImage;
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

    public String getInchiString() {
        return inchiString;
    }

    public void setInchiString(String inchiString) {
        this.inchiString = inchiString;
    }

    public Double getAverageMass() {
        return averageMass;
    }

    public void setAverageMass(Double averageMass) {
        this.averageMass = averageMass;
    }

    public String getInchikey() {
        return inchikey;
    }

    public void setInchikey(String inchikey) {
        this.inchikey = inchikey;
    }

    public String getQcNotes() {
        return qcNotes;
    }

    public void setQcNotes(String qcNotes) {
        this.qcNotes = qcNotes;
    }

    public String getQsarReadySmiles() {
        return qsarReadySmiles;
    }

    public void setQsarReadySmiles(String qsarReadySmiles) {
        this.qsarReadySmiles = qsarReadySmiles;
    }

    public String getMsReadySmiles() {
        return msReadySmiles;
    }

    public void setMsReadySmiles(String msReadySmiles) {
        this.msReadySmiles = msReadySmiles;
    }

    public String getIrisLink() {
        return irisLink;
    }

    public void setIrisLink(String irisLink) {
        this.irisLink = irisLink;
    }

    public String getPprtvLink() {
        return pprtvLink;
    }

    public void setPprtvLink(String pprtvLink) {
        this.pprtvLink = pprtvLink;
    }

    public String getWikipediaArticle() {
        return wikipediaArticle;
    }

    public void setWikipediaArticle(String wikipediaArticle) {
        this.wikipediaArticle = wikipediaArticle;
    }

    public String getDescriptorStringTsv() {
        return descriptorStringTsv;
    }

    public void setDescriptorStringTsv(String descriptorStringTsv) {
        this.descriptorStringTsv = descriptorStringTsv;
    }

    public Integer getIsMarkush() {
        return isMarkush;
    }

    public void setIsMarkush(Integer isMarkush) {
        this.isMarkush = isMarkush;
    }

    public Instant getDateLoaded() {
        return dateLoaded;
    }

    public void setDateLoaded(Instant dateLoaded) {
        this.dateLoaded = dateLoaded;
    }

    public String getDtxsid() {
        return dtxsid;
    }

    public void setDtxsid(String dtxsid) {
        this.dtxsid = dtxsid;
    }

    public String getDtxcid() {
        return dtxcid;
    }

    public void setDtxcid(String dtxcid) {
        this.dtxcid = dtxcid;
    }

    public String getToxcastSelect() {
        return toxcastSelect;
    }

    public void setToxcastSelect(String toxcastSelect) {
        this.toxcastSelect = toxcastSelect;
    }

}