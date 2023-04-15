package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.Fate;
import gov.epa.ccte.api.chemical.dto.mapper.FateMapper;
import gov.epa.ccte.api.chemical.projection.FateAll;
import gov.epa.ccte.api.chemical.repository.FateRepository;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * REST controller for getting the {@link Fate}s.
 */
@Tag(name = "Chemical Fate Resource",
        description = "API endpoints for getting chemical fate data for given DTXSID (Chemical Identifier).")
@SecurityRequirement(name = "api_key")
@Slf4j
@RestController
public class FateResource {

    final private FateRepository repository;
    final private FateMapper mapper;
    public FateResource(FateRepository repository, FateMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * {@code GET  /fate/by-dtxsid/{dtxsid} : get list of fate data for the "dtxsid".
     *
     * @param dtxsid the matching dtxsid of the fate data to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of fate}.
     */
    @Operation(summary = "Get chemical Fate values for given dtxsid")
    @RequestMapping(value = "chemical/fate/search/by-dtxsid/{dtxsid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<FateAll> fateByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020182")
                               @PathVariable("dtxsid") String dtxsid) throws IOException {

        log.debug("dtxsid = {}", dtxsid);

        return repository.findByDtxsid(dtxsid, FateAll.class);
    }

    /**
     * {@code POST  /chemical/fate/search/ : get list of chemical fate data for the multiple "dtxsid".
     *
     * @param BatchRequest the matching dtxsid of the chemical fate data to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemicalDetail}.
     */
    @Operation(summary = "Get Chemicals fate values by the batch of dtxsids.")
    @RequestMapping(value = "chemical/fate/search/by-dtxsid/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<FateAll> batchSearch(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "JSON array of DSSTox Substance Identifier",
            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject("\"[\\\"DTXSID7020182\\\",\\\"DTXSID9020112\\\"]\"")})})
                              @RequestBody String[] dtxsids) {

        log.debug("dtxsids = {}", dtxsids);

        return repository.findByDtxsidInOrderByDtxsidAscEndpointNameAsc(dtxsids, FateAll.class);
    }
}
