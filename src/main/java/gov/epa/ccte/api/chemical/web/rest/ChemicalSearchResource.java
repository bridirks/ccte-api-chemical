package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.projection.search.ChemicalSearchAll;
import gov.epa.ccte.api.chemical.projection.search.CcdChemicalSearchResult;
import gov.epa.ccte.api.chemical.repository.ChemicalSearchRepository;
import gov.epa.ccte.api.chemical.service.SearchChemicalService;
import gov.epa.ccte.api.chemical.web.rest.errors.ChemicalSearchNotFoundProblem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;

import java.util.Arrays;
import java.util.List;


/**
 * REST controller for getting the {@link gov.epa.ccte.api.chemical.domain.ChemicalSearch}s.
 */
@Tag(name = "Chemical Search Resource",
        description = "API endpoints for searching chemicals using different identifiers or characteristics.")
@SecurityRequirement(name = "api_key")
@Slf4j
@RestController
public class ChemicalSearchResource {
    final private ChemicalSearchRepository searchRepository;
    final private SearchChemicalService chemicalService;
    private final List<String> searchMatchWithoutInchikey;
    private final List<String> searchMatchAll;

    public ChemicalSearchResource(ChemicalSearchRepository searchRepository, SearchChemicalService chemicalService) {
        this.searchRepository = searchRepository;
        this.chemicalService = chemicalService;

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
    @Operation(summary = "Search by starting value", description = "NOTE: Search value need to be URLencoded for synonyms")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalSearchAll.class}))),
            @ApiResponse(responseCode = "400", description = "Data not found, it might have some suggestions for chemical synonyms.",
                    content = @Content( mediaType = "application/problem+json",
                    examples = {@ExampleObject(value = "{\"title\":\"Not found \",\"status\":400,\"detail\":\"No search result found for caffiene.\",\"suggestions\":[\"caffine\"]}", description = "Here response is with suggestion for 'caffiene'")},
                    schema=@Schema(oneOf = {Problem.class})))
    })
    @RequestMapping(value = "chemical/search/start-with/{word}",  method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<ChemicalSearchAll> chemicalStartWith(@Parameter(required = true, description = "Starting characters for search word",
                                                examples = {@ExampleObject(name="DSSTox Substance Identifier", value = "DTXSID7020182", description = "Starting part of DTXSID"),
                                                        @ExampleObject(name="DSSTox Compound Identifier", value = "DTXCID505", description = "Starting part of DTXCID"),
                                                        @ExampleObject(name="Synonym Starting characters", value = "atraz", description = "URLencoded starting characters of chemical name"),
                                                        @ExampleObject(name="CASRN", value = "1912-24", description = "Starting part of CASRN"),
                                                        @ExampleObject(name="InChIKey", value = "MXWJVTOOROXGIU", description = "For InChIKey starting 13 characters are needed")
                                                            })
                                              @PathVariable("word") String word ) {

        String searchWord = chemicalService.preprocessingSearchWord(word);

        log.debug("input search word = {} and process search word = {}. ", word, searchWord);

        List<ChemicalSearchAll> searchResult; // exact search and final results
        List<ChemicalSearchAll> searchResult2; // start-with results

        // for adding exact search on top of return result
        String removeSpaces = searchWord.replaceAll(" ", "");

        searchResult = searchRepository.findByModifiedValueInOrderByRankAsc(List.of(searchWord, removeSpaces),ChemicalSearchAll.class);

        // avoid InChIKey
        if(searchWord.length() > 13){
            searchResult2 =  searchRepository.findByModifiedValueStartingWithAndSearchNameInOrderByRankAscSearchValue(searchWord, searchMatchAll, ChemicalSearchAll.class);
        }else{
            log.debug("searchWord = {}", searchWord);
            searchResult2 =  searchRepository.findByModifiedValueStartingWithAndSearchNameInOrderByRankAscSearchValue(searchWord, searchMatchWithoutInchikey, ChemicalSearchAll.class);
        }

        searchResult.addAll(searchResult2); // append start-with results

        log.debug("{} records match for {}", searchResult.size(), word);

        searchResult = chemicalService.removeDuplicates(searchResult);

        if(!searchResult.isEmpty())
            return searchResult;
        else {
            throw new ChemicalSearchNotFoundProblem(chemicalService.getErrorMsgs(word), chemicalService.getSuggestions(word));
        }
    }

    @Operation(hidden = true)
    @RequestMapping(value = "chemical/search/start-with2/{word}",  method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<ChemicalSearchAll> chemicalStartWith2(@Parameter(required = true, description = "Starting characters for search word",
            examples = {@ExampleObject(name="DSSTox Substance Identifier", value = "DTXSID7020182", description = "Starting part of DTXSID"),
                    @ExampleObject(name="DSSTox Compound Identifier", value = "DTXCID505", description = "Starting part of DTXCID"),
                    @ExampleObject(name="Synonym Starting characters", value = "atraz", description = "URLencoded starting characters of chemical name"),
                    @ExampleObject(name="CASRN", value = "1912-24", description = "Starting part of CASRN"),
                    @ExampleObject(name="InChIKey", value = "MXWJVTOOROXGIU", description = "For InChIKey starting 13 characters are needed")
            })
                                              @PathVariable("word") String word ) {

        String searchWord = chemicalService.preprocessingSearchWord(word);

        log.debug("input search word = {} and process search word = {}. ", word, searchWord);

        List<ChemicalSearchAll> searchResult;

        // avoid InChIKey
        if(searchWord.length() > 13){
            searchResult =  searchRepository.findTop20ByModifiedValueStartsWithAndSearchNameInOrderByRankAscSearchValueAsc(searchWord, searchMatchAll, ChemicalSearchAll.class);
        }else{
            log.debug("searchWord = {}", searchWord);
            searchResult =  searchRepository.findTop20ByModifiedValueStartsWithAndSearchNameInOrderByRankAscSearchValueAsc(searchWord, searchMatchWithoutInchikey, ChemicalSearchAll.class);
        }
        log.debug("{} records match for {}", searchResult.size(), word);

        searchResult = chemicalService.removeDuplicates(searchResult);

        if(!searchResult.isEmpty())
            return searchResult;
        else {
            throw new ChemicalSearchNotFoundProblem(chemicalService.getErrorMsgs(word), chemicalService.getSuggestions(word));
        }
    }

    /**
     * {@code GET  /chemical/search/equal/:word} : get the list of chemicalSearch matching the "word".
     *
     * @param word the starting word of the chemicalSearch to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemicalSearch}.
     */
    @Operation(summary = "Search by exact value", description = "NOTE: Search value need to be URLencoded for synonyms")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalSearchAll.class}))),
            @ApiResponse(responseCode = "400", description = "Data not found, it might have some suggestions for chemical synonyms.",
                    content = @Content( mediaType = "application/problem+json",
                    examples = {@ExampleObject( value = "{\"title\":\"Not found \",\"status\":400,\"detail\":\"No search result found for caffiene.\",\"suggestions\":[\"caffine\"]}", description = "Here response is with suggestion for 'caffiene'")},
                    schema=@Schema(oneOf = {Problem.class})))
    })
    @RequestMapping(value = "chemical/search/equal/{word}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalSearchAll> chemicalEqual (@Parameter(required = true, description = "Exact match of search word",
            examples = {@ExampleObject(name="DSSTox Substance Identifier", value = "DTXSID7020182", description = "Exact match of DTXSID"),
                    @ExampleObject(name="DSSTox Compound Identifier", value = "DTXCID505", description = "Exact match of DTXCID"),
                    @ExampleObject(name="Synonym", value = "atrazine", description = "Exact match of URLencoded chemical name(including synonyms)"),
                    @ExampleObject(name="CASRN", value = "1912-24-9", description = "Exact match of CASRN"),
                    @ExampleObject(name="InChIKey", value = "MXWJVTOOROXGIU-UHFFFAOYSA-N", description = "Exact match of InChIKey")})
                                           @PathVariable("word") String word) {

        String searchWord = chemicalService.preprocessingSearchWord(word);

        log.debug("input search word = {} and process search word = {}. ", word, searchWord);

        List<ChemicalSearchAll> searchResult =  searchRepository.findByModifiedValueOrderByRankAsc(searchWord, ChemicalSearchAll.class);

        if(!searchResult.isEmpty())
            return chemicalService.removeDuplicates(searchResult);
        else
            throw new ChemicalSearchNotFoundProblem(chemicalService.getErrorMsgs(word), chemicalService.getSuggestions(word));
    }

    /**
     * {@code GET  /chemical/search/contain/:word} : get list of chemicalSearch containing the "word".
     *
     * @param word the containing word of the chemicalSearch to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemicalSearch}.
     */
    @Operation(summary = "Search by substring value", description = "NOTE: Search value need to be URLencoded for synonyms")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalSearchAll.class}))),
            @ApiResponse(responseCode = "400", description = "Data not found, it might have some suggestions for chemical synonyms.",
                    content = @Content( mediaType = "application/problem+json",
                    examples = {@ExampleObject(value = "{\"title\":\"Not found \",\"status\":400,\"detail\":\"No search result found for caffiene.\",\"suggestions\":[\"caffine\"]}", description = "Here response is with suggestion for 'caffiene'")},
                    schema=@Schema(oneOf = {Problem.class})))
    })
    @RequestMapping(value = "chemical/search/contain/{word}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChemicalSearchAll> chemicalContain(@Parameter(required = true, description = "Substring of search word",
            examples = {@ExampleObject(name="DSSTox Substance Identifier", value = "DTXSID7020182", description = "Exact match of DTXSID"),
                    @ExampleObject(name="DSSTox Compound Identifier", value = "DTXCID505", description = "Substring match of DTXCID"),
                    @ExampleObject(name="Synonym", value = "razine", description = "Substring match of URLencoded chemical name(including synonyms)"),
                    @ExampleObject(name="CASRN", value = "1912-24", description = "Substring match of CASRN"),
                    @ExampleObject(name="InChIKey", value = "MXWJVTOOROXGIU", description = "Substring match of InChIKey")})
            @PathVariable("word") String word) {

        String searchWord = chemicalService.preprocessingSearchWord(word);

        log.debug("input search word = {} and process search word = {}. ", word, searchWord);

        List<ChemicalSearchAll> searchResult = searchRepository.findByModifiedValueContainsOrderByRankAscDtxsid(searchWord, ChemicalSearchAll.class);

        if(!searchResult.isEmpty())
            return chemicalService.removeDuplicates(searchResult);
        else
            throw new ChemicalSearchNotFoundProblem(chemicalService.getErrorMsgs(word), chemicalService.getSuggestions(word));
    }

    /**
     * {@code GET  chemical/search/msready/formula/{formula} : get list of chemicalSearch containing the "formula".
     * @param formula the containing formula of the MS Ready chemicals.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemicalSearch}.
     */
    @Operation(summary = "Search ms ready chemicals by formula")
    @RequestMapping(value = "chemical/msready/search/by-formula/{formula}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    List<String> msReadyByFormula(@Parameter(required = true, description = "formula", example = "C16H24N2O5S")
                                  @PathVariable("formula") String formula){

        log.debug("input formula = {} ", formula);

        return searchRepository.searchMsReadyFormula(formula);
    }

    @Operation(summary = "Search ms ready chemicals by DTXCID")
    @RequestMapping(value = "chemical/msready/search/by-dtxcid/{dtxcid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    List<String> msReadyByDtxcid(@Parameter(required = true, description = "DSSTox Compound Identifier", example = "DTXCID30182")
                                 @PathVariable("dtxcid") String dtxcid) {

        log.debug("input dtxcid = {} ", dtxcid);

        return searchRepository.searchMsReadyDtxcid(dtxcid);
    }

    @Operation(summary = "Search ms ready chemical using mass range")
    @RequestMapping(value = "chemical/msready/search/by-mass/{start}/{end}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    List<String> msReadyByMass(@Parameter(required = true, description = "Starting mass value", example = "200.90") @PathVariable("start") Double start,
                               @Parameter(required = true, description = "Ending mass value", example = "200.95") @PathVariable("end") Double end){

        log.debug("input mass = {} - {} ", start, end);

        return searchRepository.searchMsReadyMass(start, end);
    }

    @RequestMapping(value = "chemical/test/{word}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    List<CcdChemicalSearchResult> testSearch( @PathVariable("word") String searchWord){
        return searchRepository.equalCcd(searchWord);
    }
}
