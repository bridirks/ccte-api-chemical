package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.repository.ChemicalDetailRepository;
import gov.epa.ccte.api.chemical.repository.ChemicalListChemicalRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for checking availability of data in Pubchem for GHS Safety data
 */
@Tag(name = "Pubchem link to GHS Classification ",
        description = "API endpoints for checking if chemical has GHS classification safety data in Pubchem. ")
@SecurityRequirement(name = "api_key") // no need for api_key for this endpoint
@Slf4j
@RestController
public class GhsLinkResource {

    final private ChemicalListChemicalRepository chemicalListChemicalRepository;
    final private ChemicalDetailRepository chemicalDetailRepository;

    public GhsLinkResource(ChemicalListChemicalRepository chemicalListChemicalRepository, ChemicalDetailRepository chemicalDetailRepository) {

        this.chemicalListChemicalRepository = chemicalListChemicalRepository;

        this.chemicalDetailRepository = chemicalDetailRepository;
    }

    @Operation(summary = "Check existence by dtxsid", description = "This endpoint will return Y if Pubchem has GHS Safety data otherwise it will return N.")
    @RequestMapping(value = "chemical/ghslink/to-dtxsid/{dtxsid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    GhsLinkResponse byDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020182") @PathVariable("dtxsid") String dtxsid) {
        if (chemicalListChemicalRepository.isGhsLinkExists(dtxsid))
            return new GhsLinkResponse(dtxsid,true, getSafetyUrl(dtxsid));
        else
            return new GhsLinkResponse(dtxsid, false, null);

        //return chemicalListChemicalRepository.isGhsLinkExists(dtxsid);
    }

    private String getSafetyUrl(String dtxsid) {
        String inchikey = chemicalDetailRepository.getInchikeyForDtxsid(dtxsid).get();

        return "https://pubchem.ncbi.nlm.nih.gov/" + inchikey + "#section=GHS-Classification";
    }
}
