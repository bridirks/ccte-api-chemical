package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.ChemicalProperty;
import gov.epa.ccte.api.chemical.domain.PropertyType;
import gov.epa.ccte.api.chemical.dto.ChemicalPropertyDto;
import gov.epa.ccte.api.chemical.dto.mapper.ChemicalPropertyMapper;
import gov.epa.ccte.api.chemical.projection.*;
import gov.epa.ccte.api.chemical.repository.ChemicalPropertyRepository;
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

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for getting the {@link ChemicalProperty}s.
 */
@Tag(name = "Chemical Property Resource",
        description = "API endpoints for getting chemical properties (experimental and/or predictive) for a given DTXSID (Chemical Identifier).")
@SecurityRequirement(name = "api_key")
@Slf4j
@RestController
@CrossOrigin(origins = "*")
public class ChemicalPropertyResource {

    final private ChemicalPropertyRepository repository;
    final private ChemicalPropertyMapper mapper;
    public ChemicalPropertyResource(ChemicalPropertyRepository repository, ChemicalPropertyMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * {@code GET  /chemical/property/search/by-dtxsid/{dtxsid} : get list of Chemical Property data for the "dtxsid".
     *
     * @param dtxsid the matching dtxsid of the Chemical Property data to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of Chemical Property data}.
     */
    @Operation(summary = "Get chemical properties for given dtxsid")
    @RequestMapping(value = "chemical/property/search/by-dtxsid/{dtxsid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<ChemicalPropertyAll> propertyByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020182") @PathVariable("dtxsid") String dtxsid,
                                               @Parameter(name = "Property Type", description = "In case of absence, both types of properties return")
                                               @RequestParam(value = "type", required = false) PropertyType type
                                            ) {

        log.info("dtxsid = {}, Property Type ={}", dtxsid, type);

        if(type == null)
            return repository.findByDtxsid(dtxsid, ChemicalPropertyAll.class);
        else
            return repository.findByDtxsidAndPropTypeOrderByName(dtxsid, type.toString().toLowerCase(), ChemicalPropertyAll.class);
    }

    /**
     * {@code GET  /chemical/property/search/by-dtxsid/{dtxsid} : get list of Chemical Property data for the "dtxsid".
     *
     * @param dtxsid the matching dtxsid of the Chemical Property data to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of Chemical Property data}.
     */
    @Operation(summary = "Get chemical properties for the given property id and its value range.")
    @RequestMapping(value = "chemical/property/search/by-range/{propertyId}/{start}/{end}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<ChemicalPropertyAll> propertyByRange(@Parameter(required = true, description = "chemical property id", example = "density") @PathVariable("propertyId") String propertyId,
                                              @Parameter(required = true, description = "numeric value", example = "1.311") @PathVariable("start") Double start,
                                              @Parameter(required = true, description = "numeric value", example = "1.313") @PathVariable("end") Double end ) {

        log.debug("property = {}, start = {}, end = {}", propertyId, start, end);

        return repository.getPropertiesForRange(propertyId, start, end, ChemicalPropertyAll.class);
    }

    /**
     * {@code GET  /chemical/property/experimental/name : get list of Chemical Property names in experimental type.".
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of Chemical Property Names}.
     */
    @Operation(summary = "Get a list of Chemical Property ids and names in experimental type.")
    @RequestMapping(value = "chemical/property/experimental/name", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<ChemicalPropertyIds> experimentalPropertyNames() {

        log.debug("experimental property names");

        return repository.getExperimentalPropertiesList();
    }

    /**
     * {@code GET  /chemical/property/predicted/name : get list of Chemical Property names in predicted type.".
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of Chemical Property Names}.
     */
    @Operation(summary = "Get a list of Chemical Property ids and names in the predicted type. ")
    @RequestMapping(value = "chemical/property/predicted/name", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<ChemicalPropertyIds> predictedPropertyNames() {

        log.debug("predicted property names");

        return repository.getPredictedPropertiesList();
    }

    /**
     * {@code POST  /chemical/property/search/ : get list of chemical properties for the multiple "dtxsid".
     *
     * @param BatchRequest the matching dtxsid of the chemicalDetail to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemicalDetail}.
     */
    @Operation(summary = "Get Chemicals properties by the batch of dtxsids.")
    @RequestMapping(value = "chemical/property/search/by-dtxsid/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<ChemicalPropertyAll> batchSearch(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "JSON array of DSSTox Substance Identifier",
            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject("\"[\\\"DTXSID7020182\\\",\\\"DTXSID9020112\\\"]\"")})})
            @RequestBody String[] dtxsids) {

        log.debug("dtxsids = {}", dtxsids);

        return repository.findByDtxsidInOrderByDtxsidAscPropTypeAscNameAsc(dtxsids, ChemicalPropertyAll.class);
    }

}
