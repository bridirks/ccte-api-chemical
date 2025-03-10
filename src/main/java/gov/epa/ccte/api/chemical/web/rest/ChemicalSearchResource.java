package gov.epa.ccte.api.chemical.web.rest;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import gov.epa.ccte.api.chemical.projection.search.ChemicalBatchSearchResult;
import gov.epa.ccte.api.chemical.projection.search.ChemicalSearchAll;
import gov.epa.ccte.api.chemical.projection.search.ChemicalSearchInternal;
import gov.epa.ccte.api.chemical.repository.ChemicalSearchRepository;
import gov.epa.ccte.api.chemical.service.SearchChemicalService;
import gov.epa.ccte.api.chemical.web.rest.errors.ChemicalSearchNotFoundException;
import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for chemical search operations.
 */
@Slf4j
@RestController
public class ChemicalSearchResource implements ChemicalSearchApi {

    private final ChemicalSearchRepository searchRepository;
    private final SearchChemicalService chemicalService;

    public ChemicalSearchResource(ChemicalSearchRepository searchRepository, SearchChemicalService chemicalService) {
        this.searchRepository = searchRepository;
        this.chemicalService = chemicalService;
    }

    @Override
    public List<ChemicalSearchAll> chemicalStartWith(String word, Integer top) {
        List<ChemicalSearchAll> searchResult = chemicalService.getStartWith(word, top);
        if (!searchResult.isEmpty()) {
            return searchResult.subList(0, Math.min(searchResult.size(), top));
        } else {
            throw new ChemicalSearchNotFoundException(chemicalService.getErrorMsgs(word), chemicalService.getSuggestions(word));
        }
    }

    @Override
    public List chemicalEqual(String word, String projection) {
        String searchWord = chemicalService.preprocessingSearchWord(word);
        List searchResult = searchRepository.findByModifiedValueOrderByRankAsc(searchWord, ChemicalSearchAll.class);
        if (searchResult.isEmpty()) {
            throw new ChemicalSearchNotFoundException(chemicalService.getErrorMsgs(word), chemicalService.getSuggestions(word));
        }
        return searchResult;
    }

    @Override
    public List<ChemicalBatchSearchResult> chemicalBatchEqual(String words) {
        String[] searchWords = chemicalService.preprocessingSearchWord(words.split("\n"));
        log.debug("input search words = {} and process search word count = {}. ", words, searchWords.length);
        List<ChemicalSearchInternal> searchResult = searchRepository.findByModifiedValueInOrderByRankAsc(List.of(searchWords), ChemicalSearchInternal.class);
        return chemicalService.processBatchResult(searchResult, searchWords);
    }

    @Override
    public List chemicalContain(String word, Integer top, String projection) {
        String searchWord = chemicalService.preprocessingSearchWord(word);
        List searchResult = chemicalService.getContain(projection, searchWord, top);
        if (searchResult.isEmpty()) {
            throw new ChemicalSearchNotFoundException(chemicalService.getErrorMsgs(word), chemicalService.getSuggestions(word));
        }
        return searchResult;
    }

    @Override
    public List<String> msReadyByFormula(String formula) {
        return searchRepository.searchMsReadyFormula(formula);
    }

    @Override
    public List<String> msReadyByDtxcid(String dtxcid) {
        return searchRepository.searchMsReadyDtxcid(dtxcid);
    }

    @Override
    public List<String> msReadyByMass(Double start, Double end) {
        return searchRepository.searchMsReadyMass(start, end);
    }

    @Override
    public List<String> getChemicalsForExactFormula(String formula) {
        return searchRepository.getExactFormula(formula);
    }

    @Override
    public Long getChemicalsCountForExactFormula(String formula, String projection) {
        return searchRepository.getExactFormulaCount(formula);
    }
}