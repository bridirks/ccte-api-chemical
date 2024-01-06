package gov.epa.ccte.api.chemical.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;


@Getter
@Setter
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
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
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
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String toxcastSelect;

    @Column(name = "pubchem_cid")
    private Integer pubchemCid;

    @Column(name = "list_name")
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String listName;

    @Column(name = "list_label")
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String listLabel;

    @Column(name = "list_category")
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String listCategory;

    @Column(name = "list_shortdesc")
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String listShortdesc;

    @Column(name = "list_is_visible")
    private Boolean listIsVisible;

    @Column(name = "list_visibility")
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
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
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String inchi;

    @Column(name = "inchi_key")
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String inchiKey;

    @Column(name = "iupac_name")
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String iupacName;

    @Column(name = "smiles")
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String smiles;

    @Column(name = "created_at")
    private Instant createdAt;

    @Size(max = 50)
    @Column(name = "created_by", length = 50)
    private String createdBy;
}