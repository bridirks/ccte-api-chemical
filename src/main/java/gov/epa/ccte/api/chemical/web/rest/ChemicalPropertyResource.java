package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.ChemicalProperty;
import gov.epa.ccte.api.chemical.repository.ChemicalPropertyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * REST controller for getting the {@link ChemicalProperty}s.
 */
@Slf4j
@RestController
public class ChemicalPropertyResource {

    final private ChemicalPropertyRepository repository;

    public ChemicalPropertyResource(ChemicalPropertyRepository repository) {
        this.repository = repository;
    }

    /**
     * {@code GET  /fate/by-dtxsid/{dtxsid} : get list of Chemical Property data for the "dtxsid".
     *
     * @param dtxsid the matching dtxsid of the Chemical Property data to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of Chemical Property data}.
     */
    @RequestMapping(value = "chemical/property/search/by-dtxsid/{dtxsid}", method = RequestMethod.GET)
    public @ResponseBody
    List<ChemicalProperty> propertyByDtxsid(@PathVariable("dtxsid") String dtxsid) throws IOException {

        log.debug("dtxsid = {}", dtxsid);

        return repository.findByDtxsid(dtxsid);
    }

}
