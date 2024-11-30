package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.repository.ChemicalListChemicalRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for checking availability of data in Pubchem for GHS Safety data
 */
@Tag(name = "Pubchem link to GHS Classification ",
        description = "API endpoints for checking if chemical has GHS classification safety data in Pubchem. ")
@SecurityRequirement(name = "api_key") // no need for api_key for this endpoint
@Slf4j
@RestController
public class WikipediaLinkResource {

    final private ChemicalListChemicalRepository chemicalListChemicalRepository;

    public WikipediaLinkResource(ChemicalListChemicalRepository chemicalListChemicalRepository) {

        this.chemicalListChemicalRepository = chemicalListChemicalRepository;
    }

    @Operation(summary = "Check existence by dtxsid", description = "This endpoint will return Y if Wikipedia has GHS Safety data otherwise it will return N.")
    @RequestMapping(value = "chemical/wikipedia/by-dtxsid/{dtxsid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    WikipediaLinkResponse byDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020182") @PathVariable("dtxsid") String dtxsid) {

        log.debug("Received request to check existence by dtxsid: {} ", dtxsid);

        if(chemicalListChemicalRepository.isWikipediaLinkExists(new String[]{dtxsid}).isEmpty())
            return new WikipediaLinkResponse(dtxsid, null);
        else
            return chemicalListChemicalRepository.isWikipediaLinkExists(new String[]{dtxsid}).get(0);
    }

    @Operation(summary = "Check existence for batch of dtxsid", description = "This endpoint will return Y if Wikipedia has GHS Safety data otherwise it will return N.")
    @RequestMapping(value = "chemical/wikipedia/by-dtxsid/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<WikipediaLinkResponse> byBatchDtxsid(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "JSON array of DSSTox Substance Identifier",
            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject("\"[\\\"DTXSID7020182\\\",\\\"DTXSID9020112\\\"]\"")})})
                                        @RequestBody String[] dtxsids) {

        log.debug("Received request to check existence by {} dtxsid: ", dtxsids.length);

        return chemicalListChemicalRepository.isWikipediaLinkExists(dtxsids);
    }

}

