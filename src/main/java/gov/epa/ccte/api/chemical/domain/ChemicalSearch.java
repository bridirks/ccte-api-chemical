package gov.epa.ccte.api.chemical.domain;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "chemical_search", schema = "ms")
public class ChemicalSearch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "dtxsid", length = 20)
    private String dtxsid;

    @Column(name = "dtxcid", length = 20)
    private String dtxcid;

    @Column(name = "casrn")
    private String casrn;

    @Column(name = "smiles")
    private String smiles;

    @Column(name = "preferred_name")
    private String preferredName;

    @Column(name = "search_group", length = 50)
    private String searchGroup;

    @Column(name = "search_name", length = 50)
    private String searchName;

    @Column(name = "search_value")
    private String searchValue;

    @Column(name = "modified_value")
    private String modifiedValue;

    @Column(name = "rank")
    private Integer rank;

    @Column(name = "has_structure_image")
    private Integer hasStructureImage;

    @Column(name = "is_markush")
    private Integer isMarkush;


    public Integer getIsMarkush() {
        return isMarkush;
    }

    public void setIsMarkush(Integer isMarkush) {
        this.isMarkush = isMarkush;
    }

    public Integer getHasStructureImage() {
        return hasStructureImage;
    }

    public void setHasStructureImage(Integer hasStructureImage) {
        this.hasStructureImage = hasStructureImage;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getModifiedValue() {
        return modifiedValue;
    }

    public void setModifiedValue(String modifiedValue) {
        this.modifiedValue = modifiedValue;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getSearchGroup() {
        return searchGroup;
    }

    public void setSearchGroup(String searchGroup) {
        this.searchGroup = searchGroup;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public String getSmiles() {
        return smiles;
    }

    public void setSmiles(String smiles) {
        this.smiles = smiles;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}