package gov.epa.ccte.api.chemical.service;


import gov.epa.ccte.api.chemical.projection.search.ChemicalBatchSearchResult;
import gov.epa.ccte.api.chemical.projection.search.ChemicalSearchAll;
import gov.epa.ccte.api.chemical.projection.search.ChemicalSearchResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Service
public class SearchChemicalService {

    private final CaffeineFixSynonymService caffeineFixService;
    private static final Pattern ENCODED_PATTERN = Pattern.compile("(%[0-9A-Fa-f]{2}|\\+)");

    public SearchChemicalService(CaffeineFixSynonymService caffeineFixService) {
        this.caffeineFixService = caffeineFixService;
    }


    public List<String> getErrorMsgs(String notFoundWord){
        List<String> errors = new ArrayList<String>();

        if(isDtxcid(notFoundWord)){
            errors.add("Searched by DTX Compound Id: Found 0 results for '" + notFoundWord + "'.");
        } else if(isDtxsid(notFoundWord)){
            errors.add("Searched by DTX Substance Id: Found 0 results for '" + notFoundWord + "'.");
        } else if(isInchiKey(notFoundWord)){
            errors.add("Searched by Inchi Key: Found 0 results for '" + notFoundWord + "'.");
        } else if(isInchiKeySkeleton(notFoundWord)) {
            errors.add("Searched by Inchi String: Found 0 results for '" + notFoundWord + "'.");
        } else if(isCasrn(notFoundWord)) {
            errors.add("Searched by CASRN: Found 0 results for '" + notFoundWord + "'.");
            if (!checkCasrnChecksum(notFoundWord))
                errors.add("CAS number fails checksum.");
        }else{
            errors.add("Searched by Synonym: Found 0 results for '" + notFoundWord + "'.");
        }

        return errors;
    }

    public String[] preprocessingSearchWord(String[] searchWords){

        List<String> searches = new ArrayList<String>();

        for(String word : searchWords){
            searches.add(preprocessingSearchWord(word));
        }

        return searches.toArray(new String[searches.size()]);
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

    public boolean isDtxcid(String dtxcid) {
        dtxcid = dtxcid.toUpperCase();
        return dtxcid.matches("DTXCID(.*)");
    }

    public boolean isDtxsid(String dtxsid) {
        dtxsid = dtxsid.toUpperCase();
        return dtxsid.matches("DTXSID(.*)");
    }


    public boolean isCasrn(String casrn) {
        return casrn.matches("^\\d{1,7}-\\d{2}-\\d$");
    }

    public boolean isECNumber(String casrn) {
        return casrn.matches("^\\d{3}-\\d{3}-\\d$");
    }

    public boolean isInchiKey(String inchikey) {
        inchikey = inchikey.toUpperCase();
        return inchikey.matches("[A-Z]{14}-[A-Z]{10}-[A-Z]");
    }

    public boolean isInchiKeySkeleton(String inchikeyskeleton) {
        inchikeyskeleton = inchikeyskeleton.toUpperCase();
        return inchikeyskeleton.matches("[A-Z]{14}");
    }

    public boolean isChemicalSynonym(String word){

        if(!isCasrn(word) && !isDtxcid(word) && !isDtxsid(word) && !isECNumber(word) && !isInchiKey(word) && !isInchiKeySkeleton(word)){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkCasrnChecksum(String casrn) {
// Check the string against the mask
        if (!casrn.matches("^\\d{1,7}-\\d{2}-\\d$")) {
            return false;
        } else {
// Remove the dashes
            casrn = casrn.replaceAll("-", "");
            int sum = 0;
            for (int indx = 0; indx < casrn.length() - 1; indx++) {
                sum += (casrn.length() - indx - 1) * Integer.parseInt(casrn.substring(indx, indx + 1));
            }
// Check digit is the last char, compare to sum mod 10.
            log.debug("v1= {} and v2= {}",Integer.parseInt(casrn.substring(casrn.length() - 1)), (sum % 10));
            return Integer.parseInt(casrn.substring(casrn.length() - 1)) == (sum % 10);
        }
    }

    // This will remove duplicates(same dtxsid number) from search result
    public List<ChemicalSearchResult> removeDuplicates(List<ChemicalSearchResult> chemicals) {

        List<ChemicalSearchResult> returnList = new ArrayList<ChemicalSearchResult>();
        List<String> dtxsidList = new ArrayList<String>();
        //List<String> dtxcidList = new ArrayList<String>();

        for(ChemicalSearchResult chemical : chemicals){
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
        word = word.toLowerCase();
        if(isChemicalSynonym(word)){
            return caffeineFixService.caffeineFix(word);
        }else{
            return null;
        }
    }

    public List<ChemicalBatchSearchResult> processBatchResult(List<ChemicalSearchAll> searchResult, String[] searchWords) {
        // create a hashmap with searchResult where use searchValue as key
        // this will help us to find the searchResult for a given searchWord
        HashMap<String, ChemicalSearchAll> searchResultMap = new HashMap<String, ChemicalSearchAll>();

        // create hasmap for dtxsid to check duplicates
        HashMap<String, String> dtxsidMap = new HashMap<String, String>();

        for(ChemicalSearchAll result : searchResult){
            searchResultMap.put(result.getModifiedValue(), result);
        }

        log.debug("searchResultMap size = {}, keys = {}", searchResultMap.size(), searchResultMap.keySet());


        // create a new list of ChemicalBatchSearchResult to return
        List<ChemicalBatchSearchResult> returnList = new ArrayList<ChemicalBatchSearchResult>();

        // I need to loop through searchWords, if they are in searchResultMap, I will add them to returnList
        for(String searchWord : searchWords){
            String processedSearchWord = preprocessingSearchWord(searchWord);
            if(searchResultMap.containsKey(processedSearchWord)){
                ChemicalSearchAll result = searchResultMap.get(processedSearchWord);
                returnList.add(new ChemicalBatchSearchResult(result.getDtxsid(), result.getDtxcid(), result.getCasrn(), result.getSmiles(), result.getPreferredName(), result.getSearchName(), searchWord, result.getRank(), result.getHasStructureImage(), result.getIsMarkush(), null, null, dtxsidMap.containsKey(result.getDtxsid())));
                dtxsidMap.put(result.getDtxsid(), result.getDtxsid());
            }else{
                returnList.add(new ChemicalBatchSearchResult(null, null, null, null, null, null, searchWord, null, null, null, getErrorMsgs(searchWord), getSuggestions(searchWord),false));
            }
        }

        return returnList;
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


