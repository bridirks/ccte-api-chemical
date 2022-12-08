package gov.epa.ccte.api.chemical.domain;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "chemical_details", schema = "ms")
public class ChemicalFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "h_chem_hash_key", length = 64)
    private String hChemHashKey;

    @Column(name = "dtxsid")
    private String dtxsid;

    @Column(name = "dtxcid")
    private String dtxcid;

    @Column(name = "casrn")
    private String casrn;

    @Column(name = "compound_id")
    private Integer compoundId;

    @Column(name = "generic_substance_id")
    private Integer genericSubstanceId;

    @Column(name = "preferred_name")
    private String preferredName;

    @Column(name = "mol_formula")
    private String molFormula;

    @Column(name = "mol_file")
    private String molFile;

    @Column(name = "mrv_file")
    private String mrvFile;

    @Column(name = "mol_image")
    private byte[] molImage;

    @Column(name = "has_structure_image")
    private Integer hasStructureImage;

    public Integer getHasStructureImage() {
        return hasStructureImage;
    }

    public void setHasStructureImage(Integer hasStructureImage) {
        this.hasStructureImage = hasStructureImage;
    }

    public byte[] getMolImage() {
        return molImage;
    }

    public void setMolImage(byte[] molImage) {
        this.molImage = molImage;
    }

    public String getMrvFile() {
        return mrvFile;
    }

    public void setMrvFile(String mrvFile) {
        this.mrvFile = mrvFile;
    }

    public String getMolFile() {
        return molFile;
    }

    public void setMolFile(String molFile) {
        this.molFile = molFile;
    }

    public String getMolFormula() {
        return molFormula;
    }

    public void setMolFormula(String molFormula) {
        this.molFormula = molFormula;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public Integer getGenericSubstanceId() {
        return genericSubstanceId;
    }

    public void setGenericSubstanceId(Integer genericSubstanceId) {
        this.genericSubstanceId = genericSubstanceId;
    }

    public Integer getCompoundId() {
        return compoundId;
    }

    public void setCompoundId(Integer compoundId) {
        this.compoundId = compoundId;
    }

    public String getCasrn() {
        return casrn;
    }

    public void setCasrn(String casrn) {
        this.casrn = casrn;
    }

    public String getDtxcid() {
        return dtxcid;
    }

    public void setDtxcid(String dtxcid) {
        this.dtxcid = dtxcid;
    }

    public String getDtxsid() {
        return dtxsid;
    }

    public void setDtxsid(String dtxsid) {
        this.dtxsid = dtxsid;
    }

    public String getHChemHashKey() {
        return hChemHashKey;
    }

    public void setHChemHashKey(String hChemHashKey) {
        this.hChemHashKey = hChemHashKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}