package gov.epa.ccte.api.chemical.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link gov.epa.ccte.api.chemical.domain.ChemicalList} entity
 */
@Data
public class ChemicalListDto implements Serializable {
    @NotNull
    private final Integer id;
    @Size(max = 255)
    private final String name;
    @Size(max = 255)
    private final String visibility;
    private final Boolean isVisible;
    private final String shortDescription;
    private final String longDescription;
    private final Instant createdDate;
    private final Instant lastModifiedDate;
    private final Integer chemicalCount;
    @Size(max = 255)
    private final String label;
    @Size(max = 100)
    private final String type;
}