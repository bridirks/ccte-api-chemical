package gov.epa.ccte.api.chemical.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

/**
 * API interface for getting chemical structure data through Systematic IUPAC name using OPSIN.
 */
@Tag(name = "Structure from Systematic IUPAC",
        description = "API endpoints for getting chemical structure data through Systematic IUPAC name using OPSIN. ")
@SecurityRequirement(name = "api_key")
@RequestMapping(value = "chemical/opsin")
public interface OpsinApi {

    @Operation(summary = "Get InChI")
    @GetMapping(value = "/to-inchi/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    String toInChI(@Parameter(required = true, description = "Systematic IUPAC Name", example = "acetamide") @PathVariable("name") String name) throws IOException;

    @Operation(summary = "Get InChIKey")
    @GetMapping(value = "/to-inchikey/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    String toInChIKey(@Parameter(required = true, description = "Systematic IUPAC Name", example = "acetamide") @PathVariable("name") String name) throws IOException;

    @Operation(summary = "Get Smiles")
    @GetMapping(value = "/to-smiles/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    String toSmiles(@Parameter(required = true, description = "Systematic IUPAC Name", example = "acetamide") @PathVariable("name") String name) throws IOException;
}
