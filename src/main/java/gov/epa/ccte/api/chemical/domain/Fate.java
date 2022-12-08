package gov.epa.ccte.api.chemical.domain;import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.UUID;

/**
 * 
 * @author arashid
 * Create at 2022-09-26 18:46
 */
@Entity
@Table(name = "fate")
@Data
@RequiredArgsConstructor
public class Fate {

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
    @Column(name = "endpoint_name")
    private String endpointName;

    /**
     * 
     */
    @Column(name = "result_value")
    private Double resultValue;

    /**
     * 
     */
    @Column(name = "unit")
    private String unit;

    /**
     * 
     */
    @Column(name = "max_value")
    private Double maxValue;

    /**
     * 
     */
    @Column(name = "min_value")
    private Double minValue;

    /**
     * 
     */
    @Column(name = "model_source")
    private String modelSource;

    /**
     * 
     */
    @Column(name = "description", length = 1024)
    private String description;

    /**
     * 
     */
    @Column(name = "value_type")
    private String valueType;

    /**
     * 
     */
    @Column(name = "created_by", length = 50)
    private String createdBy;

    /**
     * 
     */
    @Column(name = "created_at")
    private OffsetDateTime createdAt;
}