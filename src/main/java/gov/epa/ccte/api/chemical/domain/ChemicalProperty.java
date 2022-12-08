package gov.epa.ccte.api.chemical.domain;import lombok.*;
import javax.persistence.*;

/**
 * 
 * @author arashid
 * Create at 2022-09-26 18:46
 */
@Entity
@Table(name = "chemical_properties")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChemicalProperty {

    /**
     * 
     */
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * 
     */
    @Column(name = "dtxsid", length = 45)
    private String dtxsid;

    /**
     * 
     */
    @Column(name = "dtxcid", length = 45)
    private String dtxcid;

    /**
     * 
     */
    @Column(name = "prop_type")
    private String propertyType;

    /**
     * 
     */
    @Column(name = "unit")
    private String unit;

    /**
     * 
     */
    @Column(name = "name")
    private String name;

    /**
     * 
     */
    @Column(name = "value")
    private Double value;

    /**
     * 
     */
    @Column(name = "source")
    private String source;

    /**
     * 
     */
    @Column(name = "description", length = 1024)
    private String description;

//    /**
//     *
//     */
//    @Column(name = "created_by", length = 50)
//    private String createdBy;
//
//    /**
//     *
//     */
//    @Column(name = "created_at")
//    private OffsetDateTime createdAt;
}