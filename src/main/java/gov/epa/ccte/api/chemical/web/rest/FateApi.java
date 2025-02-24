package gov.epa.ccte.api.chemical.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;

import gov.epa.ccte.api.chemical.projection.FateAll;

import java.util.List;

/**
 * API interface for retrieving chemical fate data.
 */
@Tag(name = "Chemical Fate Resource",
        description = "API endpoints for getting chemical fate data for given DTXSID (Chemical Identifier).")
@SecurityRequirement(name = "api_key")
@RequestMapping(value = "chemical/fate")
public interface FateApi {

    @Operation(summary = "Get data by dtxsid")
    @GetMapping(value = "/search/by-dtxsid/{dtxsid}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<FateAll> fateByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020182")
                               @PathVariable("dtxsid") String dtxsid);

    @Operation(summary = "Get data by the batch of dtxsid(s)", description = "Note: Maximum ${application.batch-size} DTXSIDs per request")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {FateAll.class}))),
            @ApiResponse(responseCode = "400", description = "When user has submitted more then allowed number (${application.batch-size}) of DTXSID(s).",
                    content = @Content( mediaType = "application/problem+json",
                    examples = {@ExampleObject(value = "{\"title\":\"Validation Error\",\"status\":400,\"detail\":\"System supports only '200' dtxsid at one time, '202' are submitted.\"}", description = "Validation error for more then allowed number of dtxsid(s).")},
                    schema=@Schema(oneOf = {ProblemDetail.class})))
    })
    @PostMapping(value = "/search/by-dtxsid/", produces = MediaType.APPLICATION_JSON_VALUE)
    List<FateAll> batchSearch(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "JSON array of DSSTox Substance Identifier",
            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject("\"[\\\"DTXSID7020182\\\",\\\"DTXSID9020112\\\"]\"")})})
                              @RequestBody String[] dtxsids);
}