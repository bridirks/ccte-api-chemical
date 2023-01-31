package gov.epa.ccte.api.chemical.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Entity
@Table(name = "chemical_search", schema = "ms")
public class ChemicalSearch {
    @Id
    @NotNull
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 20)
    @Column(name = "dtxsid", length = 20)
    private String dtxsid;

    @Size(max = 20)
    @Column(name = "dtxcid", length = 20)
    private String dtxcid;

    @Size(max = 255)
    @Column(name = "casrn")
    private String casrn;

    @Column(name = "smiles")
    @Type(type = "org.hibernate.type.TextType")
    private String smiles;

    @Size(max = 255)
    @Column(name = "preferred_name")
    private String preferredName;

    @Size(max = 50)
    @Column(name = "search_group", length = 50)
    private String searchGroup;

    @Size(max = 50)
    @Column(name = "search_name", length = 50)
    private String searchName;

    @Column(name = "search_value")
    @Type(type = "org.hibernate.type.TextType")
    private String searchValue;

    @Column(name = "modified_value")
    @Type(type = "org.hibernate.type.TextType")
    private String modifiedValue;

    @Column(name = "rank")
    private Integer rank;

    @Column(name = "has_structure_image")
    private Integer hasStructureImage;

    @Column(name = "is_markush")
    private Integer isMarkush;

    @Size(max = 50)
    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "created_at")
    private Instant createdAt;

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

    public String getDtxcid() {
        return dtxcid;
    }

    public void setDtxcid(String dtxcid) {
        this.dtxcid = dtxcid;
    }

    public String getCasrn() {
        return casrn;
    }

    public void setCasrn(String casrn) {
        this.casrn = casrn;
    }

    public String getSmiles() {
        return smiles;
    }

    public void setSmiles(String smiles) {
        this.smiles = smiles;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public String getSearchGroup() {
        return searchGroup;
    }

    public void setSearchGroup(String searchGroup) {
        this.searchGroup = searchGroup;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getModifiedValue() {
        return modifiedValue;
    }

    public void setModifiedValue(String modifiedValue) {
        this.modifiedValue = modifiedValue;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getHasStructureImage() {
        return hasStructureImage;
    }

    public void setHasStructureImage(Integer hasStructureImage) {
        this.hasStructureImage = hasStructureImage;
    }

    public Integer getIsMarkush() {
        return isMarkush;
    }

    public void setIsMarkush(Integer isMarkush) {
        this.isMarkush = isMarkush;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

}