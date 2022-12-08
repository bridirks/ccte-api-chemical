package gov.epa.ccte.api.chemical.domain;import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.*;

/**
 * 
 * @author arashid
 * Create at 2022-09-26 18:46
 */
@Entity
@Table(name = "chemical_lists")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChemicalList {

    /**
     * 
     */
    @Column(name = "id", nullable = false)
    @Id
    private Integer id;

    /**
     * 
     */
    @NaturalId
    @Column(name = "name")
    private String listName;

    /**
     * 
     */
    @Column(name = "label")
    private String label;

    /**
     * 
     */
    @Column(name = "type")
    private String type;

    /**
     * 
     */
    @Column(name = "visibility")
    private String visibility;

    /**
     * 
     */
    @Column(name = "is_visible")
    private Boolean isVisible;

    /**
     * 
     */
    @Column(name = "short_description")
    private String shortDescription;

    /**
     * 
     */
    @Column(name = "long_description")
    private String longDescription;

    /**
     * 
     */
    @Column(name = "created_date")
    private OffsetDateTime createdDate;

    /**
     * 
     */
    @Column(name = "last_modified_date")
    private OffsetDateTime lastModifiedDate;

    /**
     * 
     */
    @Column(name = "created_by")
    private String createdBy;

    /**
     * 
     */
    @Column(name = "chemical_count")
    private Integer chemicalCount;

    /**
     * 
     */
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

}