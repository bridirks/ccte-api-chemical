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

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "chemical_lists", schema = "ms")
public class ChemicalList {
    @Id
    @NotNull
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "list_name")
    private String name;

    @Size(max = 255)
    @Column(name = "visibility")
    private String visibility;

    @Column(name = "is_visible")
    private Boolean isVisible;

    @Column(name = "short_description")
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String shortDescription;

    @Column(name = "long_description")
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String longDescription;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @Size(max = 255)
    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "chemical_count")
    private Integer chemicalCount;

    @Column(name = "created_at")
    private Instant createdAt;

    @Size(max = 255)
    @Column(name = "label")
    private String label;

    @Size(max = 100)
    @Column(name = "type", length = 100)
    private String type;
}