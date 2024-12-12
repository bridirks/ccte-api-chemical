package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.SearchMassAndFormula;
import gov.epa.ccte.api.chemical.repository.SearchMassAndFormulaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
public class SearchMassResource {

    private final SearchMassAndFormulaRepository searchMassAndFormulaRepository;

    public SearchMassResource(SearchMassAndFormulaRepository searchMassAndFormulaRepository) {
        this.searchMassAndFormulaRepository = searchMassAndFormulaRepository;
    }

    @RequestMapping(value = "/search/mass/{start}/{end}", method = RequestMethod.GET)
    public @ResponseBody
    Page<SearchMassAndFormula> getChemicalsForMassBetween(@PathVariable("start") Double start,
                                                          @PathVariable("end") Double end,
                                                          @RequestParam(value = "mass", required = false, defaultValue = "0.0") Double mass, Pageable pageable) throws Exception {

        log.debug("mass search start with {} to {}", start, end);

        return searchMassAndFormulaRepository.getMassValues(start, end, mass, pageable);
    }

    @RequestMapping(value = "/search/mass/count/{start}/{end}", method = RequestMethod.GET)
    public @ResponseBody
    Long getCountForMassBetween(@PathVariable("start") Double start, @PathVariable("end") Double end) throws Exception {

        log.debug("get count for mass between {} to {}", start, end);

        return searchMassAndFormulaRepository.countByMonoisotopicMassBetweenAndDtxsidIsNot(start, end, "DTXSID00000000");
    }


}
