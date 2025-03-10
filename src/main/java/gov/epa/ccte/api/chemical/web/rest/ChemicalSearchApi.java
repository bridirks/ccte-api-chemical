package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.projection.search.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * API interface for searching chemicals.
 */
@Tag(name = "Chemical Search Resource",
        description = "API endpoints for searching chemicals using different identifiers or characteristics.")
@SecurityRequirement(name = "api_key")
@RequestMapping(value = "chemical/search", produces = MediaType.APPLICATION_JSON_VALUE)
public interface ChemicalSearchApi {

    @Operation(summary = "Search by starting value", description = "NOTE: Search value needs to be URL encoded for synonyms.")
    @GetMapping(value = "/start-with/{word}")
    List<ChemicalSearchAll> chemicalStartWith(@Parameter(required = true, description = "Starting characters for search word")
                                             @PathVariable("word") String word,
                                             @RequestParam(value = "top", required = false, defaultValue = "500") Integer top);

    @Operation(summary = "Search by exact value", description = "NOTE: Search value needs to be URL encoded for synonyms.")
    @GetMapping(value = "/equal/{word}")
    List chemicalEqual(@Parameter(required = true, description = "Exact match of search word")
                        @PathVariable("word") String word,
                        @RequestParam(value = "projection", required = false, defaultValue = "chemicalsearchall") String projection);

    @Operation(summary = "Search by exact batch of values", description = "NOTE: Search batch of values (values are separated by EOL character and maximum 200 values are allowed).")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalSearchAll.class})))
    })
    @PostMapping(value = "/equal/")
    List<ChemicalBatchSearchResult> chemicalBatchEqual(@Parameter(required = true, description = "Exact match of batch of search words (separated by end of line character)",
            examples = {@ExampleObject(name="DSSTox Substance Identifier", value = "DTXSID7020182"),
                    @ExampleObject(name="DSSTox Compound Identifier", value = "DTXCID505"),
                    @ExampleObject(name="Synonym", value = "atrazine"),
                    @ExampleObject(name="CASRN", value = "1912-24-9"),
                    @ExampleObject(name="InChIKey", value = "MXWJVTOOROXGIU-UHFFFAOYSA-N")})
            @RequestBody String words);

    @Operation(summary = "Search by substring value", description = "NOTE: Search value needs to be URL encoded for synonyms.")
    @GetMapping(value = "/contain/{word}")
    List chemicalContain(@Parameter(required = true, description = "Substring of search word")
                          @PathVariable("word") String word,
                          @RequestParam(value = "top", required = false, defaultValue = "0") Integer top,
                          @RequestParam(value = "projection", required = false, defaultValue = "chemicalsearchall") String projection);

    @Operation(summary = "Search ms ready chemicals by formula")
    @GetMapping(value = "/msready/search/by-formula/{formula}")
    List<String> msReadyByFormula(@Parameter(required = true, description = "Chemical formula") @PathVariable("formula") String formula);

    @Operation(summary = "Search ms ready chemicals by DTXCID")
    @GetMapping(value = "/msready/search/by-dtxcid/{dtxcid}")
    List<String> msReadyByDtxcid(@Parameter(required = true, description = "DSSTox Compound Identifier") @PathVariable("dtxcid") String dtxcid);

    @Operation(summary = "Search ms ready chemical using mass range")
    @GetMapping(value = "/msready/search/by-mass/{start}/{end}")
    List<String> msReadyByMass(@Parameter(required = true, description = "Starting mass value") @PathVariable("start") Double start,
                                @Parameter(required = true, description = "Ending mass value") @PathVariable("end") Double end);

    @Operation(summary = "Search chemicals by exact formula")
    @GetMapping(value = "/search/by-exact-formula/{formula}")
    List<String> getChemicalsForExactFormula(@Parameter(required = true, description = "Chemical Formula") @PathVariable("formula") String formula);

    @Operation(summary = "Search chemicals Count by exact formula")
    @GetMapping(value = "/count/by-exact-formula/{formula}")
    Long getChemicalsCountForExactFormula(@Parameter(required = true, description = "Chemical Formula") @PathVariable("formula") String formula,
                                           @RequestParam(value = "projection", required = false, defaultValue = "count") String projection);
}