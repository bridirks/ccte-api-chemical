package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.ChemicalDetail;
import gov.epa.ccte.api.chemical.dto.ChemicalDetailDto;
import gov.epa.ccte.api.chemical.dto.mapper.ChemicalDetailMapper;
import gov.epa.ccte.api.chemical.repository.ChemicalDetailRepository;
import gov.epa.ccte.api.chemical.web.rest.errors.IdentifierNotFoundProblem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    final private ChemicalDetailRepository detailRepository;
    final private ChemicalDetailMapper chemicalDetailMapper;
    public ChemicalDetailResource(ChemicalDetailRepository detailRepository, ChemicalDetailMapper chemicalDetailMapper) {
        this.detailRepository = detailRepository;
        this.chemicalDetailMapper = chemicalDetailMapper;
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
    ChemicalDetailDto detailByDtxsid(@PathVariable("dtxsid") String dtxsid) {

        log.debug("dtxsid = {}", dtxsid);

        ChemicalDetail chemicalDetail = detailRepository.findByDtxsid(dtxsid)
                .orElseThrow(()->new IdentifierNotFoundProblem("dtxsid", dtxsid));

        return chemicalDetailMapper.toDto(chemicalDetail);
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
    ChemicalDetailDto detailsByDtxcid(@PathVariable("dtxcid") String dtxcid) {

        log.debug("dtxcid = {}", dtxcid);
        ChemicalDetail chemicalDetail = detailRepository.findByDtxcid(dtxcid)
                .orElseThrow(()->new IdentifierNotFoundProblem("dtxcid", dtxcid));

        return chemicalDetailMapper.toDto(chemicalDetail);
    }

}


