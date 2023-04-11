package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.ChemicalDetail;
import gov.epa.ccte.api.chemical.projection.chemicaldetail.*;
import gov.epa.ccte.api.chemical.service.ChemicalDetailService;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for getting the {@link ChemicalDetail}s.
 */
@Tag(name = "Chemical Details Resource",
        description = "API endpoints for collecting data for given chemical(s).")
@SecurityRequirement(name = "api_key")
@Slf4j
@RestController
//@CrossOrigin(origins = "*")
public class ChemicalDetailResource {
    final private ChemicalDetailService detailService;
    public ChemicalDetailResource(ChemicalDetailService detailService) {
        this.detailService = detailService;
    }

    /**
     * {@code GET  /chemical/detail/by-dtxsid/:dtxsid} : get list of chemicalDetail for the "dtxsid".
     *
     * @param dtxsid the matching dtxsid of the chemicalDetail to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemicalDetail}.
     */
    @Operation(summary = "Get a Chemicals Details by its dtxsid",
            description = "Specify the dtxsid as part of the path, and optionally user can also define projection (set of attributes to return).")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalDetailStandard.class, ChemicalIdentifier.class, ChemicalStructure.class})))
    })
    @RequestMapping(value = "chemical/detail/search/by-dtxsid/{dtxsid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ChemicalDetailBase detailByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020182") @PathVariable("dtxsid") String dtxsid,
                                      @RequestParam(value = "projection", required = false, defaultValue = "chemicaldetailall") ChemicalDetailProjection projection) {

        log.debug("dtxsid = {}", dtxsid);

        return getChemicalDetail(dtxsid, "dtxsid", projection);
    }
    /**
     * {@code GET  /chemical/detail/by-dtxcid/:dtxcid} : get list of chemicalDetail for the "dtxcid".
     *
     * @param dtxcid the matching dtxcid of the chemicalDetail to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemicalDetail}.
     */
    @Operation(summary = "Get a Chemicals Details by its dtxcid",
            description = "Specify the dtxcid as part of the path, and optionally user can also define projection (set of attributes to return).")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalDetailStandard.class, ChemicalIdentifier.class, ChemicalStructure.class})))
    })
    @RequestMapping(value = "chemical/detail/search/by-dtxcid/{dtxcid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ChemicalDetailBase detailsByDtxcid(@Parameter(required = true, description = "DSSTox Compound Identifier", example = "DTXCID505")  @PathVariable("dtxcid") String dtxcid,
                           @RequestParam(value = "projection", required = false, defaultValue = "chemicaldetailall") ChemicalDetailProjection projection) {

        log.debug("dtxcid = {}", dtxcid);

        return getChemicalDetail(dtxcid, "dtxcid", projection);
    }

    private ChemicalDetailBase getChemicalDetail(String dtxsid, String type, ChemicalDetailProjection projection) {
        switch (projection) {
            case chemicaldetailall: return detailService.getChemicalDetailsForId(dtxsid, type, ChemicalDetailAll.class);
            case chemicaldetailstandard: return detailService.getChemicalDetailsForId(dtxsid, type, ChemicalDetailStandard.class);
            case chemicalidentifier: return detailService.getChemicalDetailsForId(dtxsid, type, ChemicalIdentifier.class);
            case chemicalstructure: return detailService.getChemicalDetailsForId(dtxsid, type, ChemicalStructure.class);
            default:
                return null;
        }
    }

    /**
     * {@code POST  /chemical/detail/search/ : get list of chemicalDetail for the multiple "dtxsid".
     *
     * @param BatchRequest the matching dtxcid of the chemicalDetail to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemicalDetail}.
     */
    @Operation(summary = "Get Chemicals Details by the batch of dtxsids.",
            description = "Besides batch of the values, the user can also define projection (set of attributes to return)")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalDetailStandard.class, ChemicalIdentifier.class, ChemicalStructure.class})))
    })
    @RequestMapping(value = "chemical/detail/search/by-dtxsid/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List batchSearch( @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "JSON array of DSSTox Substance Identifier",
            content = {@Content (array = @ArraySchema(schema = @Schema(implementation = String.class)),
                        examples = {@ExampleObject("\"[\\\"DTXSID7020182\\\",\\\"DTXSID9020112\\\"]\"")})})
                      @RequestBody String[] dtxsid,
                      @RequestParam(value = "projection", required = false, defaultValue = "chemicaldetailall")
                      ChemicalDetailProjection projection) {

        log.debug("dtxsids = {}", dtxsid);

        switch (projection){
            case chemicaldetailall: return detailService.getChemicalDetailsForBatch(dtxsid, ChemicalDetailAll.class);
            case chemicaldetailstandard: return detailService.getChemicalDetailsForBatch(dtxsid, ChemicalDetailStandard.class);
            case chemicalidentifier: return detailService.getChemicalDetailsForBatch(dtxsid, ChemicalIdentifier.class);
            case chemicalstructure: return detailService.getChemicalDetailsForBatch(dtxsid, ChemicalStructure.class);
            default:return null;
        }
    }
}


