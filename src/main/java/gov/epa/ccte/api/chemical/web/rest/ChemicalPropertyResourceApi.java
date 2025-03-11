package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.PropertyType;
import gov.epa.ccte.api.chemical.projection.ChemicalPropertyAll;
import gov.epa.ccte.api.chemical.projection.ChemicalPropertyIds;
import gov.epa.ccte.api.chemical.web.rest.errors.HigherNumberOfIdsException;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Chemical Property Resource", description = "API endpoints for getting chemical properties (experimental and/or predictive) for a given DTXSID (Chemical Identifier).")
@SecurityRequirement(name = "api_key")
public interface ChemicalPropertyResourceApi {

    @Operation(summary = "Get properties by dtxsid")
    @GetMapping(value = "chemical/property/search/by-dtxsid/{dtxsid}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertyAll> propertyByDtxsid(@PathVariable("dtxsid") String dtxsid, @RequestParam(value = "type", required = false) PropertyType type);

    @Operation(summary = "Get chemical properties by property id and range")
    @GetMapping(value = "chemical/property/search/by-range/{propertyId}/{start}/{end}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertyAll> propertyByRange(@PathVariable("propertyId") String propertyId, @PathVariable("start") Double start, @PathVariable("end") Double end);

    @Operation(summary = "Get property ids by type=experimental")
    @GetMapping(value = "chemical/property/experimental/name", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertyIds> experimentalPropertyNames();

    @Operation(summary = "Get property ids by type=predicted")
    @GetMapping(value = "chemical/property/predicted/name", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertyIds> predictedPropertyNames();

    @Operation(summary = "Get properties by the batch of dtxsid(s)", description = "Note: Maximum ${application.batch-size} DTXSIDs per request")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "Successfull",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalPropertyAll.class}))),
            @ApiResponse(responseCode = "400", description = "When user has submitted more then allowed number (${application.batch-size}) of DTXSID(s).",
                    content = @Content( mediaType = "application/json",
                    examples = {@ExampleObject(value = "{\"title\":\"Validation Error\",\"status\":400,\"detail\":\"System supports only '200' dtxsid at one time, '202' are submitted.\"}", description = "Validation error for more then allowed number of dtxsid(s).")},
                    schema=@Schema(oneOf = {ProblemDetail.class})))
    })          
    @PostMapping(value = "chemical/property/search/by-dtxsid/", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertyAll> batchSearch(@RequestBody String[] dtxsids) throws HigherNumberOfIdsException;
}