package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.projection.chemicallist.*;
import gov.epa.ccte.api.chemical.web.rest.requests.ChemicalListsAndDtxsids;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * API interface for retrieving chemical lists.
 */
@Tag(name = "Chemical List Resource",
        description = "API endpoints for getting chemical lists and chemicals in those lists.")
@SecurityRequirement(name = "api_key")
@RequestMapping(value = "chemical/list", produces = MediaType.APPLICATION_JSON_VALUE)
public interface ChemicalListApi {

    @Operation(summary = "Get all public lists")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalListName.class, ChemicalListAll.class})))
    })
    @GetMapping(value = "/")
    List listAll(@RequestParam(value = "projection", required = false, defaultValue = "chemicallistall") ChemicalListProjection projection);

    @Operation(summary = "Get all list types")
    @GetMapping(value = "/type")
    List<String> getAllType();

    @Operation(summary = "Get public lists by type")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalListName.class, ChemicalListAll.class})))
    })
    @GetMapping(value = "/search/by-type/{type}")
    List listByType(@Parameter(required = true, description = "Chemical List Type", example = "other") @PathVariable String type,
                    @RequestParam(value = "projection", required = false, defaultValue = "chemicallistall") ChemicalListProjection projection);

    @Operation(summary = "Get public list by name")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalListName.class, ChemicalListAll.class})))
    })
    @GetMapping(value = "/search/by-name/{listName}")
    ChemicalListBase listByName(@Parameter(required = true, description = "Chemical List Name", example = "40CFR1164") @PathVariable String listName,
                                @RequestParam(value = "projection", required = false, defaultValue = "chemicallistall") ChemicalListProjection projection);

    @Operation(summary = "Get lists names by dtxsid")
    @GetMapping(value = "/search/by-dtxsid/{dtxsid}")
    List listByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID1020560") @PathVariable String dtxsid,
                      @RequestParam(value = "projection", required = false, defaultValue = "chemicallistall") ChemicalListProjection projection);

    @Operation(summary = "Get DTXSIDs for a starting value")
    @GetMapping(value = "/chemicals/search/start-with/{list}/{word}")
    List<String> startWith(@PathVariable String list, @PathVariable String word);

    @Operation(summary = "Get DTXSIDs for a substring match")
    @GetMapping(value = "/chemicals/search/contain/{list}/{word}")
    List<String> contain(@PathVariable String list, @PathVariable String word);

    @Operation(summary = "Get DTXSIDs for an exact match")
    @GetMapping(value = "/chemicals/search/equal/{list}/{word}")
    List<String> exact(@PathVariable String list, @PathVariable String word);

    @Operation(summary = "Get DTXSIDs for a specific list name")
    @GetMapping(value = "/chemicals/search/by-listname/{list}")
    List<String> listDtxsids(@PathVariable String list);

    @Operation(hidden = true)
    @PostMapping(value = "/chemicals/search/by-dtxsid")
    List<String> contain(@RequestBody ChemicalListsAndDtxsids request);
}