package gov.epa.ccte.api.chemical.service;


import gov.epa.ccte.api.chemical.domain.ChemicalSearch;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SearchChemicalService {

    public List<String> getErrorMsg(String notFoundWord){
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
        }else if (isECNumber(searchWord)){
            log.debug("{} is EC number ", searchWord);
            return searchWord;
        }else if(StringUtils.isNumeric(searchWord.replaceAll("-",""))) {
            // partial CASRN or EC Number
            return searchWord;
        }else{
            // For allowing search JW-001 and JW 001
            searchWord = searchWord.replaceAll("-"," ");
            return searchWord;
        }
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


    public boolean checkCasrnFormat(String casrn, boolean checkForDash) {
// Check the string against the mask
        if (checkForDash && !casrn.matches("^\\d{1,7}-\\d{2}-\\d$")) {
            return false;
        } else {
// Remove the dashes
            casrn = casrn.replaceAll("-", "");
            int sum = 0;
            for (int indx = 0; indx < casrn.length() - 1; indx++) {
                sum += (casrn.length() - indx - 1) * Integer.parseInt(casrn.substring(indx, indx + 1));
            }
// Check digit is the last char, compare to sum mod 10.
            log.debug("v1= %1 and v2= %2",Integer.parseInt(casrn.substring(casrn.length() - 1)), (sum % 10));
            return Integer.parseInt(casrn.substring(casrn.length() - 1)) == (sum % 10);
        }
    }

    // This will remove duplicates(same dtxsid number) from search result
    public List<ChemicalSearch> removeDuplicates(List<ChemicalSearch> chemicals) {

        List<ChemicalSearch> returnList = new ArrayList<ChemicalSearch>();
        List<String> dtxsidList = new ArrayList<String>();
        //List<String> dtxcidList = new ArrayList<String>();

        for(ChemicalSearch chemical : chemicals){
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


