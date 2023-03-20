package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.ChemicalProperty;
import gov.epa.ccte.api.chemical.domain.ChemicalSynonym;
import gov.epa.ccte.api.chemical.domain.PropertyType;
import gov.epa.ccte.api.chemical.dto.ChemicalPropertyDto;
import gov.epa.ccte.api.chemical.dto.ChemicalSynonymDto;
import gov.epa.ccte.api.chemical.dto.mapper.ChemicalSynonymMapper;
import gov.epa.ccte.api.chemical.repository.ChemicalSynonymRepository;
import gov.epa.ccte.api.chemical.web.rest.errors.IdentifierNotFoundProblem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for getting the {@link ChemicalProperty}s.
 */
@Tag(name = "Chemical Synonym Resource",
        description = "API endpoints for getting chemical synonym for given DTXSID (Chemical Identifier).")
@SecurityRequirement(name = "api_key")
@Slf4j
@RestController
@CrossOrigin
public class ChemicalSynonymResource {

    final private ChemicalSynonymRepository repository;
    final private ChemicalSynonymMapper mapper;

    public ChemicalSynonymResource(ChemicalSynonymRepository synonymRepository, ChemicalSynonymMapper mapper) {
        this.repository = synonymRepository;
        this.mapper = mapper;
    }

    /**
     * {@code GET  /chemical/Synonym/search/by-dtxsid/{dtxsid} : get ChemicalSynonymDto for the "dtxsid".
     *
     * @param dtxsid the matching dtxsid of the Chemical Property data to retrieve.
     * @return the {@link ResponseEntity } with status {@code 200 (OK)} and with body the list of Chemical Property data}.
     */
    @Operation(summary = "Get chemicals synonym for given dtxsid")
    @RequestMapping(value = "chemical/synonym/search/by-dtxsid/{dtxsid}", method = RequestMethod.GET)
    public @ResponseBody
    ChemicalSynonymDto synoymsByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020182") @PathVariable("dtxsid") String dtxsid) {

        log.info("dtxsid = {}", dtxsid);

        List<ChemicalSynonymDto> synonymDtos;

        ChemicalSynonym synonym = repository.findByDtxsid(dtxsid).orElseThrow(()->new IdentifierNotFoundProblem("DTXSID", dtxsid));

        return mapper.toDto(synonym);
    }

}
