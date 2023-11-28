package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.ChemicalList;
import gov.epa.ccte.api.chemical.dto.mapper.ChemicalListDetailMapper;
import gov.epa.ccte.api.chemical.dto.mapper.ChemicalListMapper;
import gov.epa.ccte.api.chemical.projection.chemicallist.*;
import gov.epa.ccte.api.chemical.repository.ChemicalListDetailRepository;
import gov.epa.ccte.api.chemical.repository.ChemicalListRepository;
import gov.epa.ccte.api.chemical.web.rest.errors.IdentifierNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * REST controller for getting the {@link ChemicalList}s.
 */
@Tag(name = "Chemical List Resource",
        description = "API endpoints for getting chemical lists and chemicals in those lists.")
@SecurityRequirement(name = "api_key")
@Slf4j
@RestController
public class ChemicalListResource {

    final private ChemicalListRepository listRepository;
    final private ChemicalListDetailRepository detailRepository;
    final private ChemicalListDetailMapper chemicalListDetailMapper;
    final private ChemicalListMapper chemicalListMapper;

    public ChemicalListResource(ChemicalListRepository repository, ChemicalListDetailRepository detailRepository, ChemicalListDetailMapper chemicalListDetailMapper, ChemicalListMapper chemicalListMapper) {
        this.listRepository = repository;
        this.detailRepository = detailRepository;
        this.chemicalListDetailMapper = chemicalListDetailMapper;
        this.chemicalListMapper = chemicalListMapper;
    }

    /**
     * {@code GET  /chemical/list/ : get list of chemical lists.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemical lists}.
     */
    @Operation(summary = "Get all public lists")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalListName.class, ChemicalListAll.class})))
    })
    @RequestMapping(value = "chemical/list/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List listAll(@RequestParam(value = "projection", required = false, defaultValue = "chemicallistall") ChemicalListProjection projection) throws IOException {
        //return listRepository.findAll();
        switch (projection){
            case chemicallistall: return listRepository.findByVisibilityOrderByTypeAscNameAsc("PUBLIC",ChemicalListAll.class);
            case chemicallistname: return listRepository.findByVisibilityOrderByTypeAscNameAsc("PUBLIC",ChemicalListName.class);
            default:
                return null;
        }
    }

    /**
     * {@code GET  /chemical/list/type : get all types.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemical list types}.
     */
    @Operation(summary = "Get all list types")
    @RequestMapping(value = "chemical/list/type", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<String> getAllType() throws IOException {

        return listRepository.getAllTypes();
    }

    /**
     * {@code GET  /chemical/list/search : get list of chemical lists.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemical lists}.
     */
    @Operation(summary = "Get public lists by type")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalListName.class, ChemicalListAll.class})))
    })
    @RequestMapping(value = "chemical/list/search/by-type/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List listByType( @Parameter(required = true, description = "Chemical List Type", example = "other") @PathVariable String type,
                                      @RequestParam(value = "projection", required = false, defaultValue = "chemicallistall") ChemicalListProjection projection
                                      ) throws IOException {
        switch (projection){
            case chemicallistall: return listRepository.findByType(type, ChemicalListAll.class);
            case chemicallistname: return listRepository.findByType(type, ChemicalListName.class);
            default:
                return null;
        }
    }

    /**
     * {@code GET  /chemical/list/by-dtxsid/{dtxsid} : get list of chemical lists names.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemical lists name}.
     */
    @Operation(summary = "Get public list by name")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content( mediaType = "application/json",
                    schema=@Schema(oneOf = {ChemicalListName.class, ChemicalListAll.class})))
    })
    @RequestMapping(value = "chemical/list/search/by-name/{listName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ChemicalListBase listByName(@Parameter(required = true, description = "Chemical List Name", example = "40CFR1164") @PathVariable String listName,
                                @RequestParam(value = "projection", required = false, defaultValue = "chemicallistall") ChemicalListProjection projection) throws IOException {

        log.debug("list name={}", listName);

        switch (projection){
            case chemicallistall: return listRepository.findByNameIgnoreCase(listName, ChemicalListAll.class)
                    .orElseThrow(()->new IdentifierNotFoundException("List name", listName));
            case chemicallistname: return listRepository.findByNameIgnoreCase(listName, ChemicalListName.class)
                    .orElseThrow(()->new IdentifierNotFoundException("List name", listName));
            default:
                return null;
        }
    }

    /**
     * {@code GET  chemical/list/search/by-dtxsid/ : get chemical lists names.
     *
     * @param dtxsid return chemical list name where this chemical is present.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemical list names}.
     */
    @Operation(summary = "Get lists names by dtxsid")
    @RequestMapping(value = "chemical/list/search/by-dtxsid/{dtxsid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<String> listByDtxsid( @Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID1020560")
            @PathVariable String dtxsid) throws IOException {

        log.debug("dtxsid={}", dtxsid);

        return detailRepository.findByDtxsid(dtxsid);
    }


    /**
     * {@code GET  /chemical/list/{listName}/chemicals : get list of chemical lists.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemical lists}.
     */
    @Operation(summary = "Get list chemicals by list name")
    @RequestMapping(value = "chemical/list/chemicals/search/by-listname/{listName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<ChemicalListDetailAll> chemicalInList(@Parameter(required = true, description = "Chemical List Name", example = "40CFR1164")
            @PathVariable String listName) throws IOException {

        log.debug("list name={}", listName);

        List<ChemicalListDetailAll> details = detailRepository.findByListNameIgnoreCaseOrderByDtxsid(listName, ChemicalListDetailAll.class);

        return details;
    }
}
