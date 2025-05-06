package gov.epa.ccte.api.chemical.dto;

import gov.epa.ccte.api.chemical.domain.ApiKey;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

/**
 * A DTO for the {@link ApiKey} entity
 */
@Data
public class ApiKeyDto implements Serializable {
    private final UUID id;
    private final String email;
    private final LocalDate createdOn;
}