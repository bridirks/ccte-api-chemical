package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.ChemicalDetail;
import gov.epa.ccte.api.chemical.dto.BatchRequest;
import gov.epa.ccte.api.chemical.dto.mapper.ChemicalDetailMapper;
import gov.epa.ccte.api.chemical.projection.*;
import gov.epa.ccte.api.chemical.repository.ChemicalDetailRepository;
import gov.epa.ccte.api.chemical.service.ChemicalDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for getting the {@link ChemicalDetail}s.
 */
@Tag(name = "Chemical Details Resource",
        description = "API endpoints for collecting associated data for specified chemical")
@SecurityRequirement(name = "api_key")
@Slf4j
@RestController
@CrossOrigin
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
    @Operation(summary = "Get a Chemicals Details by its dtxsid")
    @RequestMapping(value = "chemical/detail/search/by-dtxsid/{dtxsid}", method = RequestMethod.GET)
    public @ResponseBody
    Object detailByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020182") @PathVariable("dtxsid") String dtxsid,
                                     @RequestParam(value = "projection", required = false, defaultValue = "chemicaldetail") ChemicalDetailProjection projection) {

        log.debug("dtxsid = {}", dtxsid);

        return getChemicalDetail(dtxsid, "dtxsid", projection);
    }
    /**
     * {@code GET  /chemical/detail/by-dtxcid/:dtxcid} : get list of chemicalDetail for the "dtxcid".
     *
     * @param dtxcid the matching dtxcid of the chemicalDetail to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemicalDetail}.
     */
    @Operation(summary = "Get a Chemicals Details by its dtxcid")
    @RequestMapping(value = "chemical/detail/search/by-dtxcid/{dtxcid}", method = RequestMethod.GET)
    public @ResponseBody
    Object detailsByDtxcid(@Parameter(required = true, description = "DSSTox Compound Identifier", example = "DTXCID505")  @PathVariable("dtxcid") String dtxcid,
                           @RequestParam(value = "projection", required = false, defaultValue = "chemicaldetail") ChemicalDetailProjection projection) {

        log.debug("dtxcid = {}", dtxcid);

        return getChemicalDetail(dtxcid, "dtxcid", projection);
    }

    private ChemicalDetailBase getChemicalDetail(String dtxsid, String type, ChemicalDetailProjection projection) {
        switch (projection) {
            case chemicaldetail:
                return detailService.getChemicalDetailsForId(dtxsid, type, ChemicalDetailAll.class);
            case chemicalidentifier:
                return detailService.getChemicalDetailsForId(dtxsid, type, ChemicalIdentifier.class);
            case chemicalstructure:
                return detailService.getChemicalDetailsForId(dtxsid, type, ChemicalStructure.class);
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
    @Operation(summary = "Get a Chemicals Details by batch of dtxsid")
    @RequestMapping(value = "chemical/detail/search/", method = RequestMethod.POST)
    public @ResponseBody
    List detailsByDtxcid(@RequestBody BatchRequest batchRequest,
                                            @RequestParam(value = "projection", required = false, defaultValue = "chemicaldetail") ChemicalDetailProjection projection) {

        log.debug("BatchRequest = {}", batchRequest);

        switch (projection){
            case chemicaldetail: return detailService.getChemicalDetailsForBatch(batchRequest, ChemicalDetailAll.class);
            case chemicalidentifier: return detailService.getChemicalDetailsForBatch(batchRequest, ChemicalIdentifier.class);
            case chemicalstructure: return detailService.getChemicalDetailsForBatch(batchRequest, ChemicalStructure.class);
            default:return null;
        }
    }
}


