package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.ImageFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * API interface for retrieving chemical structure data.
 */
@Tag(name = "Chemical File Resource",
        description = "API endpoints for getting chemical structure data in mol, mrv and image (png or svg format) for given Chemical Identifier (DTXSID or DTXCID).")
@SecurityRequirement(name = "api_key")
@RequestMapping(value = "chemical/file")
public interface ChemicalFileApi {

    @Operation(summary = "Get structure image by gsid")
    @GetMapping(value = "/image/search/by-gsid/{gsid}", produces = {MediaType.IMAGE_PNG_VALUE, "image/svg+xml"})
    ResponseEntity<byte[]> imageByGsid(@Parameter(required = true, description = "DSSTox Generic Substance Identifier", example = "DTXSID7020182") @PathVariable("gsid") String gsid,
                                       @Parameter(name = "Image Format", description = "In case of absence, it will return png image") @RequestParam(value = "format", required = false) ImageFormat format);

    @Operation(summary = "Get structure image by dtxsid")
    @GetMapping(value = "/image/search/by-dtxsid/{dtxsid}", produces = {MediaType.IMAGE_PNG_VALUE, "image/svg+xml"})
    ResponseEntity<byte[]> imageByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020182") @PathVariable("dtxsid") String dtxsid,
                                         @Parameter(name = "Image Format", description = "In case of absence, it will return png image") @RequestParam(value = "format", required = false) ImageFormat format);

    @Operation(summary = "Get structure image by dtxcid", description = "This endpoint is deprecated. Please use /chemical-file/image/search/by-dtxcid/{dtxcid} instead.")
    @GetMapping(value = "/image/search/by-dtxcid/{dtxcid}", produces = {MediaType.IMAGE_PNG_VALUE, "image/svg+xml"})
    ResponseEntity<byte[]> imageByDtxcid(@Parameter(required = true, description = "DSSTox Compound Identifier", example = "DTXCID505") @PathVariable("dtxcid") String dtxcid,
                                         @Parameter(name = "Image Format", description = "In case of absence, it will return png image") @RequestParam(value = "format", required = false) ImageFormat format);
}