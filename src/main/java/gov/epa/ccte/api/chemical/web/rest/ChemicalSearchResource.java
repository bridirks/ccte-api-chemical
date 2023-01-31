package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.ChemicalSearch;
import gov.epa.ccte.api.chemical.dto.ChemicalSearchDto;
import gov.epa.ccte.api.chemical.dto.mapper.ChemicalSearchMapper;
import gov.epa.ccte.api.chemical.repository.ChemicalSearchRepository;
import gov.epa.ccte.api.chemical.service.SearchChemicalService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for getting the {@link gov.epa.ccte.api.chemical.domain.ChemicalSearch}s.
 */
@Tag(name = "Chemical Search Resource",
        description = "API endpoints for searching chemicals using different identifiers or characteristics.")
@SecurityRequirement(name = "api_key")
@Slf4j
@RestController
@CrossOrigin
public class ChemicalSearchResource {

    final private ChemicalSearchRepository searchRepository;
    final private SearchChemicalService chemicalService;
    final private ChemicalSearchMapper mapper;
    private final List<String> searchMatchWithoutInchikey;
    private final List<String> searchMatchAll;

    public ChemicalSearchResource(ChemicalSearchRepository searchRepository, SearchChemicalService chemicalService, ChemicalSearchMapper mapper) {
        this.searchRepository = searchRepository;
        this.chemicalService = chemicalService;
        this.mapper = mapper;

        searchMatchWithoutInchikey = Arrays.asList("Deleted CAS-RN","PC-Code","Substance_id","Approved Name","Alternate CAS-RN",
                "CAS-RN","Synonym","Integrated Source CAS-RN","DSSTox_Compound_Id","Systematic Name","Integrated Source Name",
                "Expert Validated Synonym","Synonym from Valid Source","FDA CAS-Like Identifier","DSSTox_Substance_Id", "EHCA Number", "EC Number");
        searchMatchAll = Arrays.asList("Deleted CAS-RN","PC-Code","Substance_id","Approved Name","Alternate CAS-RN",
                "CAS-RN","Synonym","Integrated Source CAS-RN","DSSTox_Compound_Id","Systematic Name","Integrated Source Name",
                "Expert Validated Synonym","Synonym from Valid Source","FDA CAS-Like Identifier","DSSTox_Substance_Id",
                "InChIKey", "Indigo InChIKey", "EHCA Number", "EC Number");
    }

    /**
     * {@code GET  /chemical/search/start-with/:word} : get list of chemicalSearch starting with the "word".
     *
     * @param word the starting word of the chemicalSearch to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemicalSearch}.
     */

    @RequestMapping(value = "chemical/search/start-with/{word}", method = RequestMethod.GET)
    public @ResponseBody
    List<ChemicalSearchDto> chemicalStartWith(@PathVariable("word") String word ) throws Exception {

        String searchWord = chemicalService.preprocessingSearchWord(word);

        log.debug("input search word = {} and process search word = {}. ", word, searchWord);

        List<ChemicalSearch> searchResult;

        // avoid InChIKey
        if(searchWord.length() > 13){
            searchResult =  searchRepository.findByModifiedValueStartingWithAndSearchNameInOrderByRankAscSearchValue(searchWord, searchMatchAll);
        }else{
            log.debug("searchWord = {}", searchWord);
            searchResult =  searchRepository.findByModifiedValueStartingWithAndSearchNameInOrderByRankAscSearchValue(searchWord, searchMatchWithoutInchikey);
        }
        log.debug("{} records match for {}", searchResult.size(), word);

        searchResult = chemicalService.removeDuplicates(searchResult);

        return searchResult.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * {@code GET  /chemical/search/equal/:word} : get the list of chemicalSearch matching the "word".
     *
     * @param word the starting word of the chemicalSearch to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemicalSearch}.
     */
    @RequestMapping(value = "chemical/search/equal/{word}", method = RequestMethod.GET)
    List<ChemicalSearchDto> chemicalEqual (@PathVariable("word") String word) throws Exception{

        String searchWord = chemicalService.preprocessingSearchWord(word);

        log.debug("input search word = {} and process search word = {}. ", word, searchWord);

        List<ChemicalSearch> result =searchRepository.findByModifiedValue(searchWord);

        return result.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * {@code GET  /chemical/search/contain/:word} : get list of chemicalSearch containing the "word".
     *
     * @param word the containing word of the chemicalSearch to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemicalSearch}.
     */
    @RequestMapping(value = "chemical/search/contain/{word}", method = RequestMethod.GET)
    List<ChemicalSearchDto> chemicalContain(@PathVariable("word") String word) throws Exception{
        String searchWord = chemicalService.preprocessingSearchWord(word);

        log.debug("input search word = {} and process search word = {}. ", word, searchWord);

        List<ChemicalSearch>  result =  searchRepository.findByModifiedValueContains(searchWord);

        return result.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());

    }

    /**
     * {@code GET  chemical/search/msready/formula/{formula} : get list of chemicalSearch containing the "formula".
     *
     * @param formula the containing formula of the MS Ready chemicals.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemicalSearch}.
     */
    @RequestMapping(value = "chemical/msready/search/by-formula/{formula}", method = RequestMethod.GET)
    List<String> msReadyByFormula(@PathVariable("formula") String formula) throws Exception{

        log.debug("input formula = {} ", formula);

        return searchRepository.searchMsReadyFormula(formula);
    }

    @RequestMapping(value = "chemical/msready/search/by-dtxcid/{dtxcid}", method = RequestMethod.GET)
    List<String> msReadyByDtxcid(@PathVariable("dtxcid") String dtxcid) throws Exception{

        log.debug("input dtxcid = {} ", dtxcid);

        return searchRepository.searchMsReadyDtxcid(dtxcid);
    }

    @RequestMapping(value = "chemical/msready/search/by-mass/{start}/{end}", method = RequestMethod.GET)
    List<String> msReadyByMass(@PathVariable("start") Double start,
                                     @PathVariable("end") Double end) throws Exception{

        log.debug("input mass = {} - {} ", start, end);

        return searchRepository.searchMsReadyMass(start, end);
    }
}
