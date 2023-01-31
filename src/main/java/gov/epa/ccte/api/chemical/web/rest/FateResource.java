package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.Fate;
import gov.epa.ccte.api.chemical.dto.FateDto;
import gov.epa.ccte.api.chemical.dto.mapper.FateMapper;
import gov.epa.ccte.api.chemical.repository.FateRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for getting the {@link Fate}s.
 */
@Tag(name = "Chemical Fate Resource",
        description = "API endpoints for getting chemical fate data for given DTXSID (Chemical Identifier).")
@SecurityRequirement(name = "api_key")
@Slf4j
@RestController
@CrossOrigin
public class FateResource {

    final private FateRepository repository;
    final private FateMapper mapper;
    public FateResource(FateRepository repository, FateMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * {@code GET  /fate/by-dtxsid/{dtxsid} : get list of fate data for the "dtxsid".
     *
     * @param dtxsid the matching dtxsid of the fate data to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of fate}.
     */
    @RequestMapping(value = "chemical/fate/search/by-dtxsid/{dtxsid}", method = RequestMethod.GET)
    public @ResponseBody
    List<FateDto> fateByDtxsid(@PathVariable("dtxsid") String dtxsid) throws IOException {

        log.debug("dtxsid = {}", dtxsid);

        List<Fate> fates = repository.findByDtxsid(dtxsid);

        return fates.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

}
