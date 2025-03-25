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

    /**
     * {@code GET  /chemical/file/mol/search/by-dtxsid/:dtxsid} : get the "dtxsid" chemical mol file.
     *
     * @param dtxsid the matching dtxsid of the chemical mol file to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chemical mol file}.
     */
    @Operation(summary = "Get mol file by dtxsid")
    @RequestMapping(value = "/mol/search/by-dtxsid/{dtxsid}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<String> molByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020182") @PathVariable("dtxsid") String dtxsid);
    
    /**
     * {@code GET  /chemical/file/mol/search/by-dtxcid/:dtxcid} : get the "dtxcid" chemical mol file.
     *
     * @param dtxcid the matching dtxcid of the chemical mol file to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chemical mol file}.
     */
    @Operation(summary = "Get mol file by dtxcid")
    @RequestMapping(value = "/mol/search/by-dtxcid/{dtxcid}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<String> molByDtxcid(@Parameter(required = true, description = "DSSTox Compound Identifier", example = "DTXCID505") @PathVariable("dtxcid") String dtxcid);
    
    /**
     * {@code GET  chemical/file/mrv/search/by-dtxsid/{dtxsid}" : get the "dtxsid" chemical mrv file.
     * @param dtxsid the matching dtxcid of the chemical mrv file to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chemical mrv file}.
     */
    @Operation(summary = "Get mrv file by dtxsid")
    @RequestMapping(value = "/mrv/search/by-dtxsid/{dtxsid}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<String> mrvByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020182") @PathVariable("dtxsid") String dtxsid);
    
    /**
     * {@code GET  chemical/file/mrv/search/by-dtxcid/{dtxcid} : get the "dtxcid" chemical mrv file.
     * @param dtxcid the matching dtxcid of the chemical mrv file to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chemical mrv file}.
     */
    @Operation(summary = "Get mrv file by dtxcid")
    @RequestMapping(value = "/mrv/search/by-dtxcid/{dtxcid}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<String> mrvByDtxcid(@Parameter(required = true, description = "DSSTox Compound Identifier", example = "DTXCID505") @PathVariable("dtxcid") String dtxcid);
    
    /**
     * {@code GET  chemical/file/image/generate?smile=<smiles-string> : get generated structure image for smiles.
     * @param smiles generate structure image using smiles.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chemical structure image file}.
     *
     *
     */
    @Operation(summary = "Get generated structure image for smiles",
            description = "User can use generate structure image by providing smiles string")
    @RequestMapping(value = "/image/generate", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<byte[]> generateImageBySmiles(@Parameter(required = true, description = "SMILES String", example = "CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1")
                                                 @RequestParam(required = true, value = "smiles") String smiles,
                                                 @Parameter(name = "Image Format", description = "In case of absence, it will return png image")
                                                 @RequestParam(value = "format", required = false, defaultValue = "PNG") ImageFormat format);    
    
}
