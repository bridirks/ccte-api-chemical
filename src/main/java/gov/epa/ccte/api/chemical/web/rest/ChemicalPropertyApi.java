package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.ChemicalPropertyExperimental;
import gov.epa.ccte.api.chemical.domain.ChemicalPropertyPredicted;
import gov.epa.ccte.api.chemical.projection.chemicalproperty.ChemicalPropertyNames;
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
public interface ChemicalPropertyApi {

	 // *********************** Experimental - start *************************************
	
    @Operation(summary = "Get experimental properties by dtxsid")
    @GetMapping(value = "chemical/property/experimental/search/by-dtxsid/{dtxsid}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertyExperimental> experimentalPropertyByDtxsid(@PathVariable("dtxsid") String dtxsid);

    @Operation(summary = "Get experimental chemical properties by property id and range")
    @GetMapping(value = "chemical/property/experimental/search/by-range/{propertyId}/{start}/{end}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertyExperimental> experimentalPropertyByRange(@PathVariable("propertyId") String propertyName, @PathVariable("start") Double start, @PathVariable("end") Double end);

    @Operation(summary = "Get experimental property names")
    @GetMapping(value = "chemical/property/experimental/name", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertyNames> experimentalPropertyNames();

    @Operation(summary = "Get experimental properties by the batch of dtxsid(s)", description = "Note: Maximum ${application.batch-size} DTXSIDs per request")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "Successfull",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalPropertyExperimental.class}))),
            @ApiResponse(responseCode = "400", description = "When user has submitted more then allowed number (${application.batch-size}) of DTXSID(s).",
                    content = @Content( mediaType = "application/json",
                    examples = {@ExampleObject(value = "{\"title\":\"Validation Error\",\"status\":400,\"detail\":\"System supports only '200' dtxsid at one time, '202' are submitted.\"}", description = "Validation error for more then allowed number of dtxsid(s).")},
                    schema=@Schema(oneOf = {ProblemDetail.class})))
    })          
    @PostMapping(value = "chemical/property/experimental/search/by-dtxsid/", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertyExperimental> experimentalBatchSearch(@RequestBody String[] dtxsids) throws HigherNumberOfIdsException;
    
    // *********************** Experimental - End *************************************

    // *********************** Predicted - start *************************************
    
	
    @Operation(summary = "Get predicted properties by dtxsid")
    @GetMapping(value = "chemical/property/predicted/search/by-dtxsid/{dtxsid}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertyPredicted> predictedPropertyByDtxsid(@PathVariable("dtxsid") String dtxsid);

    @Operation(summary = "Get predicted chemical properties by property id and range")
    @GetMapping(value = "chemical/property/predicted/search/by-range/{propertyId}/{start}/{end}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertyPredicted> predictedPropertyByRange(@PathVariable("propertyId") String propertyName, @PathVariable("start") Double start, @PathVariable("end") Double end);

    @Operation(summary = "Get predicted property names")
    @GetMapping(value = "chemical/property/predicted/name", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertyNames> predictedPropertyNames();

    @Operation(summary = "Get predicted properties by the batch of dtxsid(s)", description = "Note: Maximum ${application.batch-size} DTXSIDs per request")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "Successfull",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalPropertyExperimental.class}))),
            @ApiResponse(responseCode = "400", description = "When user has submitted more then allowed number (${application.batch-size}) of DTXSID(s).",
                    content = @Content( mediaType = "application/json",
                    examples = {@ExampleObject(value = "{\"title\":\"Validation Error\",\"status\":400,\"detail\":\"System supports only '200' dtxsid at one time, '202' are submitted.\"}", description = "Validation error for more then allowed number of dtxsid(s).")},
                    schema=@Schema(oneOf = {ProblemDetail.class})))
    })          
    @PostMapping(value = "chemical/property/predicted/search/by-dtxsid/", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalPropertyPredicted> predictedBatchSearch(@RequestBody String[] dtxsids) throws HigherNumberOfIdsException;
    
}