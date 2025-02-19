package gov.epa.ccte.api.chemical.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

/**
 * API interface for getting chemical info from Indigo Toolkit.
 */
@Tag(name = "Indigo Service",
        description = "API endpoints for getting chemical info from Indigo Toolkit.")
@SecurityRequirement(name = "api_key")
@RequestMapping(value = "chemical/indigo")
public interface IndigoApi {

    @Operation(summary = "Get InChI from mol")
    @PostMapping(value = "/to-inchi", produces = MediaType.APPLICATION_JSON_VALUE)
    String toInChI(@Parameter(required = true, description = "mol file") @RequestBody String mol) throws IOException;

    @Operation(summary = "Get InChIKey from mol")
    @PostMapping(value = "/to-inchikey", produces = MediaType.APPLICATION_JSON_VALUE)
    String toInChIkey(@Parameter(required = true, description = "mol file") @RequestBody String mol) throws IOException;

    @Operation(summary = "Get Smiles from mol")
    @PostMapping(value = "/to-smiles", produces = MediaType.APPLICATION_JSON_VALUE)
    String toSmiles(@Parameter(required = true, description = "mol file") @RequestBody String mol) throws IOException;

    @Operation(summary = "Get Canonical Smiles from mol")
    @PostMapping(value = "/to-canonicalsmiles", produces = MediaType.APPLICATION_JSON_VALUE)
    String toCanonicalSmiles(@Parameter(required = true, description = "mol file") @RequestBody String mol) throws IOException;

    @Operation(summary = "Get molecular weight from mol")
    @PostMapping(value = "/to-molweight", produces = MediaType.APPLICATION_JSON_VALUE)
    Double toMolecularWeight(@Parameter(required = true, description = "mol file") @RequestBody String mol) throws IOException;

    @Operation(summary = "Get mol file v2000 from mol")
    @PostMapping(value = "/to-mol2000", produces = MediaType.APPLICATION_JSON_VALUE)
    String toMol2000(@Parameter(required = true, description = "mol file") @RequestBody String mol) throws IOException;

    @Operation(summary = "Get mol file v3000 from mol")
    @PostMapping(value = "/to-mol3000", produces = MediaType.APPLICATION_JSON_VALUE)
    String toMol3000(@Parameter(required = true, description = "mol file") @RequestBody String mol) throws IOException;
}