package gov.epa.ccte.api.chemical.service;


import gov.epa.ccte.api.chemical.projection.search.ChemicalSearchAll;
import gov.epa.ccte.api.chemical.repository.ChemicalSearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static gov.epa.ccte.api.chemical.service.ChemicalUtils.*;

@Slf4j
@Service
public class SearchChemicalService {

    private final CaffeineFixSynonymService caffeineFixService;
    private final ChemicalSearchRepository searchRepository;
    private static final Pattern ENCODED_PATTERN = Pattern.compile("(%[0-9A-Fa-f]{2}|\\+)");

    public SearchChemicalService(CaffeineFixSynonymService caffeineFixService, ChemicalSearchRepository searchRepository) {
        this.caffeineFixService = caffeineFixService;
        this.searchRepository = searchRepository;
    }


    public List<String> getErrorMsgs(String notFoundWord){
        List<String> errors = new ArrayList<String>();

        if(isCasrn(notFoundWord)){
            errors.add("Searched by CASRN: Found 0 results for '" + notFoundWord + "'.");
            if(checkCasrnFormat(notFoundWord, true) == false)
                errors.add("CAS number fails checksum.");
        } else if(isDtxcid(notFoundWord)){
            errors.add("Searched by DTX Compound Id: Found 0 results for '" + notFoundWord + "'.");
        } else if(isDtxsid(notFoundWord)){
            errors.add("Searched by DTX Substance Id: Found 0 results for '" + notFoundWord + "'.");
        } else if(isInchiKey(notFoundWord)){
            errors.add("Searched by Inchi Key: Found 0 results for '" + notFoundWord + "'.");
        } else if(isInchiKeySkeleton(notFoundWord)){
            errors.add("Searched by Inchi Key: Found 0 results for '" + notFoundWord + "'.");
        }else{
            errors.add("Searched by Synonym: Found 0 results for '" + notFoundWord + "'.");
        }

        return errors;
    }

    public String preprocessingSearchWord(String searchWord){

        // From https://confluence.epa.gov/display/CCTEA/Search+Requirements
        // Make all character upper case
        searchWord = searchWord.toUpperCase();
        // Search word should be trim
        searchWord = searchWord.trim();

        if(isCasrn(searchWord)){
            log.debug("{} is casrn ", searchWord);
            return searchWord;
        } else if (isInchiKey (searchWord)) {
            log.debug("{} is inchiKey ", searchWord);
            return searchWord;
        } else if (isCasrn(searchWord.replaceAll("–","-"))){ 
            log.debug("{} is casrn with wrong dash", searchWord);
            return searchWord.replaceAll("–","-");
        }else if (isECNumber(searchWord)){
            log.debug("{} is EC number ", searchWord);
            return searchWord;
        }else if(StringUtils.isNumeric(searchWord.replaceAll("-",""))) {
            // partial CASRN or EC Number
            return searchWord;
        }else{
            // check for URLencoded value for Synonyms
//            if(isUrlEncoded(searchWord)){
//                try {
//                    log.info("searchWord is URL encoded: {}", searchWord);
//                    searchWord = URLDecoder.decode(searchWord, "UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    throw new RuntimeException(e);
//                }
//            }
            // For allowing search JW-001 and JW 001
            searchWord = searchWord.replaceAll("-"," ");
            return searchWord;
        }
    }

    private boolean isUrlEncoded(String input) {
        return ENCODED_PATTERN.matcher(input).find();
    }


    // This will remove duplicates(same dtxsid number) from search result
    public List<ChemicalSearchAll> removeDuplicates(List<ChemicalSearchAll> chemicals) {

        List<ChemicalSearchAll> returnList = new ArrayList<ChemicalSearchAll>();
        List<String> dtxsidList = new ArrayList<String>();
        //List<String> dtxcidList = new ArrayList<String>();

        for(ChemicalSearchAll chemical : chemicals){
            //if(chemical.getDtxsid() != null ){}
            if(dtxsidList.contains(chemical.getDtxsid()) == false){
                dtxsidList.add(chemical.getDtxsid());
                returnList.add(chemical);
            } else{
                log.debug("skip duplicate -  " + chemical );
            }
        }
        return returnList;
    }



    public List<String> getSuggestions(String word) {

        // we will get caffeinefix suggestion for synonyms,
        // caffeinefix data dictionary is case-sensitive, this is why we are converting word to lower case.
        if(isChemicalSynonym(word)) {
            return caffeineFixService.caffeineFix(word.toLowerCase());
        }else if(isInchiKey(word)){
            return inchikeySuggestion(word);
        }else{
            return null;
        }
    }

    private List<String> inchikeySuggestion(String word) {
        String search = inchikeyWithoutCharge(word);

        List<String> suggestions = searchRepository.getInchiKey(inchikeyWithoutCharge(word));

        if(suggestions.size() > 0){
            return suggestions;
        }else{
            return searchRepository.getInchiKey(inchikeyWithoutSecondlayer(word));
        }
    }


    // This will remove duplicates(same dtxsid number) from search result
/*    public List<SearchResult> removeSearchResultDuplicates(List<SearchResult> results) {

        List<SearchResult> returnList = new ArrayList<SearchResult>();
        List<String> dtxsidList = new ArrayList<String>();

        for(SearchResult result : results){
            if(dtxsidList.contains(result.getDtxsid()) == false){
                dtxsidList.add(result.getDtxsid());
                returnList.add(result);
            } else{
                log.debug("skip duplicate -  " + result );
            }
        }
        return returnList;
    }*/
}


