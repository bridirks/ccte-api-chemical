package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.ChemicalList;
import gov.epa.ccte.api.chemical.domain.ChemicalListDetail;
import gov.epa.ccte.api.chemical.repository.ChemicalListDetailRepository;
import gov.epa.ccte.api.chemical.repository.ChemicalListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * REST controller for getting the {@link ChemicalList}s.
 */
@Slf4j
@RestController
public class ChemicalListResource {

    final private ChemicalListRepository listRepository;
    final private ChemicalListDetailRepository detailRepository;

    public ChemicalListResource(ChemicalListRepository repository, ChemicalListDetailRepository detailRepository) {
        this.listRepository = repository;
        this.detailRepository = detailRepository;
    }

    /**
     * {@code GET  /chemical/list/ : get list of chemical lists.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemical lists}.
     */
    @RequestMapping(value = "chemical/list/", method = RequestMethod.GET)
    public @ResponseBody
    List<ChemicalList> listAll() throws IOException {
        //return listRepository.findAll();
       return listRepository.findAll();
    }

    /**
     * {@code GET  /chemical/list/search : get list of chemical lists.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemical lists}.
     */
    @RequestMapping(value = "chemical/list/search/by-type/{type}", method = RequestMethod.GET)
    public @ResponseBody
    List<ChemicalList> listByType(@PathVariable String type) throws IOException {

        //return listRepository.findAll();
        return listRepository.findByType(type);

    }


    /**
     * {@code GET  /chemical/list/{listName}/chemicals : get chemical lists names.
     *
     * @param dtxsid return chemical list name where this chemical is present.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemical list names}.
     */
    @RequestMapping(value = "chemical/list/search/by-dtxsid/{dtxsid}", method = RequestMethod.GET)
    public @ResponseBody
    List<String> listByDtxsid( @PathVariable String dtxsid) throws IOException {

        log.debug("dtxsid={}", dtxsid);

        return detailRepository.findByDtxsid(dtxsid);
    }

    /**
     * {@code GET  /chemical/list/by-dtxsid/{dtxsid} : get list of chemical lists names.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemical lists name}.
     */
    @RequestMapping(value = "chemical/list/search/by-name/{listName}", method = RequestMethod.GET)
    public @ResponseBody
    ChemicalList listByName( @PathVariable String listName) throws IOException {

        log.debug("list name={}", listName);

        return listRepository.findByListName(listName);
    }

    /**
     * {@code GET  /chemical/list/{listName}/chemicals : get list of chemical lists.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemical lists}.
     */
    @RequestMapping(value = "chemical/list/chemicals/search/by-listname/{listName}", method = RequestMethod.GET)
    public @ResponseBody
    List<ChemicalListDetail> chemicalInList( @PathVariable String listName) throws IOException {

        log.debug("list name={}", listName);

        List<ChemicalListDetail> details = detailRepository.findByListNameOrderByDtxsid(listName);

        return details;
    }
}
