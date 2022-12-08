package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.ChemicalDetail;
import gov.epa.ccte.api.chemical.repository.ChemicalDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * REST controller for getting the {@link gov.epa.ccte.api.chemical.domain.ChemicalDetail}s.
 */
@Slf4j
@RestController
public class ChemicalDetailResource {
    final private ChemicalDetailRepository detailRepository;

    public ChemicalDetailResource(ChemicalDetailRepository detailRepository) {
        this.detailRepository = detailRepository;
    }

    /**
     * {@code GET  /chemical/detail/by-dtxsid/:dtxsid} : get list of chemicalDetail for the "dtxsid".
     *
     * @param dtxsid the matching dtxsid of the chemicalDetail to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemicalDetail}.
     */
    @RequestMapping(value = "chemical/detail/search/by-dtxsid/{dtxsid}", method = RequestMethod.GET)
    public @ResponseBody
    ChemicalDetail detailByDtxsid(@PathVariable("dtxsid") String dtxsid) throws IOException {

        log.debug("dtxsid = {}", dtxsid);

        return detailRepository.findByDtxsid(dtxsid);
    }

    /**
     * {@code GET  /chemical/detail/by-dtxcid/:dtxcid} : get list of chemicalDetail for the "dtxcid".
     *
     * @param dtxcid the matching dtxcid of the chemicalDetail to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemicalDetail}.
     */
    @RequestMapping(value = "chemical/search/detail/by-dtxcid/{dtxcid}", method = RequestMethod.GET)
    public @ResponseBody
    List<ChemicalDetail> detailsByDtxcid(@PathVariable("dtxcid") String dtxcid) throws IOException {

        log.debug("dtxcid = {}", dtxcid);

        return detailRepository.findByDtxcid(dtxcid);
    }

}

