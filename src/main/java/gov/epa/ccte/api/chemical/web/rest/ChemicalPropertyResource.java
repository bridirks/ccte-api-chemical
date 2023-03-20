package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.ChemicalProperty;
import gov.epa.ccte.api.chemical.domain.PropertyType;
import gov.epa.ccte.api.chemical.dto.ChemicalPropertyDto;
import gov.epa.ccte.api.chemical.dto.mapper.ChemicalPropertyMapper;
import gov.epa.ccte.api.chemical.projection.ChemicalPropertyIds;
import gov.epa.ccte.api.chemical.repository.ChemicalPropertyRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for getting the {@link ChemicalProperty}s.
 */
@Tag(name = "Chemical Property Resource",
        description = "API endpoints for getting chemical properties (experimental and/or predictive) for given DTXSID (Chemical Identifier).")
@SecurityRequirement(name = "api_key")
@Slf4j
@RestController
@CrossOrigin
public class ChemicalPropertyResource {

    final private ChemicalPropertyRepository repository;
    final private ChemicalPropertyMapper chemicalPropertyMapper;
    public ChemicalPropertyResource(ChemicalPropertyRepository repository, ChemicalPropertyMapper chemicalPropertyMapper) {
        this.repository = repository;
        this.chemicalPropertyMapper = chemicalPropertyMapper;
    }

    /**
     * {@code GET  /chemical/property/search/by-dtxsid/{dtxsid} : get list of Chemical Property data for the "dtxsid".
     *
     * @param dtxsid the matching dtxsid of the Chemical Property data to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of Chemical Property data}.
     */
    @Operation(summary = "Get chemicals properties for given dtxsid")
    @RequestMapping(value = "chemical/property/search/by-dtxsid/{dtxsid}", method = RequestMethod.GET)
    public @ResponseBody
    List<ChemicalPropertyDto> propertyByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020182") @PathVariable("dtxsid") String dtxsid,
                                               @Parameter(name = "Property Type", description = "In case of absence, both types of properties return")
                                               @RequestParam(value = "type", required = false) PropertyType type
                                            ) {

        log.info("dtxsid = {}, Property Type ={}", dtxsid, type);
        List<ChemicalProperty> properties;

        if(type == null)
            properties = repository.findByDtxsid(dtxsid);
        else
            properties = repository.findByDtxsidAndPropTypeOrderByName(dtxsid, type.toString().toLowerCase());

        return properties.stream()
                .map(chemicalPropertyMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * {@code GET  /chemical/property/search/by-dtxsid/{dtxsid} : get list of Chemical Property data for the "dtxsid".
     *
     * @param dtxsid the matching dtxsid of the Chemical Property data to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of Chemical Property data}.
     */
    @Operation(summary = "Get chemicals for given property and it's value range")
    @RequestMapping(value = "chemical/property/search/by-range/{propertyId}/{start}/{end}", method = RequestMethod.GET)
    public @ResponseBody
    List<ChemicalPropertyDto> propertyByRange(@Parameter(required = true, description = "chemical property id", example = "density") @PathVariable("propertyId") String propertyId,
                                              @Parameter(required = true, description = "numeric value", example = "1.311") @PathVariable("start") Double start,
                                              @Parameter(required = true, description = "numeric value", example = "1.313") @PathVariable("end") Double end ) {

        log.debug("property = {}, start = {}, end = {}", propertyId, start, end);

        List<ChemicalProperty> properties = repository.getPropertiesForRange(propertyId, start, end);

        return properties.stream()
                .map(chemicalPropertyMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * {@code GET  /chemical/property/experimental/name : get list of Chemical Property names in experimental type.".
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of Chemical Property Names}.
     */
    @Operation(summary = "Get list of Chemical Property names in experimental type")
    @RequestMapping(value = "chemical/property/experimental/name", method = RequestMethod.GET)
    public @ResponseBody
    List<ChemicalPropertyIds> experimentalPropertyNames() {

        log.debug("experimental property names");

        return repository.getExperimentalPropertiesList();
    }

    /**
     * {@code GET  /chemical/property/experimental/name : get list of Chemical Property names in experimental type.".
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of Chemical Property Names}.
     */
    @Operation(summary = "Get list of Chemical Property names in experimental type")
    @RequestMapping(value = "chemical/property/predicted/name", method = RequestMethod.GET)
    public @ResponseBody
    List<ChemicalPropertyIds> predictedPropertyNames() {

        log.debug("predicted property names");

        return repository.getPredictedPropertiesList();
    }

}
