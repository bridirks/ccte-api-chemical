package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.Fate;
import gov.epa.ccte.api.chemical.repository.FateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * REST controller for getting the {@link Fate}s.
 */
@Slf4j
@RestController
public class FateResource {

    final private FateRepository repository;

    public FateResource(FateRepository repository) {
        this.repository = repository;
    }

    /**
     * {@code GET  /fate/by-dtxsid/{dtxsid} : get list of fate data for the "dtxsid".
     *
     * @param dtxsid the matching dtxsid of the fate data to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of fate}.
     */
    @RequestMapping(value = "chemical/fate/search/by-dtxsid/{dtxsid}", method = RequestMethod.GET)
    public @ResponseBody
    List<Fate> fateByDtxsid(@PathVariable("dtxsid") String dtxsid) throws IOException {

        log.debug("dtxsid = {}", dtxsid);

        return repository.findByDtxsid(dtxsid);
    }

}
