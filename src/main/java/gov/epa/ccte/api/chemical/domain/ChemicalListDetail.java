package gov.epa.ccte.api.chemical.domain;import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.*;

/**
 * 
 * @author arashid
 * Create at 2022-09-26 18:46
 */
@Entity
@Table(name = "chemical_list_details")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChemicalListDetail {

    /**
     * 
     */
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * 
     */
    @Column(name = "dtxsid", length = 50)
    private String dtxsid;

    /**
     * 
     */
    @Column(name = "casrn")
    private String casrn;

    /**
     * 
     */
    @Column(name = "compound_id")
    private Integer compoundId;

    /**
     * 
     */
    @Column(name = "generic_substance_id")
    private Integer genericSubstanceId;

    /**
     * 
     */
    @Column(name = "preferred_name")
    private String preferredName;

    /**
     * 
     */
    @Column(name = "active_assays")
    private Integer activeAssays;

    /**
     * 
     */
    @Column(name = "cpdata_count")
    private Integer cpdataCount;

    /**
     * 
     */
    @Column(name = "mol_formula")
    private String molFormula;

    /**
     * 
     */
    @Column(name = "mol_weight")
    private BigDecimal molWeight;

    /**
     * 
     */
    @Column(name = "monoisotopicmass")
    private BigDecimal monoisotopicmass;

    /**
     * 
     */
    @Column(name = "percent_assays")
    private Integer percentAssays;

    /**
     * 
     */
    @Column(name = "pubchem_count")
    private Integer pubchemCount;

    /**
     * 
     */
    @Column(name = "pubmed_count")
    private BigDecimal pubmedCount;

    /**
     * 
     */
    @Column(name = "qc_level")
    private Integer qcLevel;

    /**
     * 
     */
    @Column(name = "qc_level_desc")
    private String qcLevelDesc;

    /**
     * 
     */
    @Column(name = "stereo", length = 1)
    private String stereo;

    /**
     * 
     */
    @Column(name = "total_assays")
    private Integer totalAssays;

    /**
     * 
     */
    @Column(name = "toxcast_select")
    private String toxcastSelect;

    /**
     * 
     */
    @Column(name = "pubchem_cid")
    private Integer pubchemCid;

    /**
     * 
     */
    @Column(name = "list_name")
    private String listName;

    /**
     * 
     */
    @Column(name = "list_label")
    private String listLabel;

    /**
     * 
     */
    @Column(name = "list_category")
    private String listCategory;

    /**
     * 
     */
    @Column(name = "list_shortdesc")
    private String listShortdesc;

    /**
     * 
     */
    @Column(name = "list_is_visible")
    private Boolean listIsVisible;

    /**
     * 
     */
    @Column(name = "list_visibility")
    private String listVisibility;

    /**
     * 
     */
    @Column(name = "sources_count")
    private Integer sourcesCount;

    /**
     * 
     */
    @Column(name = "multi_component")
    private Integer multiComponent;

    /**
     * 
     */
    @Column(name = "isotope")
    private Integer isotope;

    /**
     * 
     */
    @Column(name = "has_structure_image")
    private Boolean hasStructureImage;

    /**
     * 
     */
    @Column(name = "related_substance_count")
    private Integer relatedSubstanceCount;

    /**
     * 
     */
    @Column(name = "related_structure_count")
    private Integer relatedStructureCount;

    /**
     * 
     */
    @Column(name = "inchi")
    private String inchi;

    /**
     * 
     */
    @Column(name = "inchi_key")
    private String inchiKey;

    /**
     * 
     */
    @Column(name = "iupac_name")
    private String iupacName;

    /**
     * 
     */
    @Column(name = "smiles")
    private String smiles;

    /**
     * 
     */
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    /**
     * 
     */
    @Column(name = "created_by", length = 50)
    private String createdBy;
}