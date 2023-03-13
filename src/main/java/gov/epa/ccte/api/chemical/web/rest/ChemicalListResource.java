package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.ChemicalList;
import gov.epa.ccte.api.chemical.domain.ChemicalListDetail;
import gov.epa.ccte.api.chemical.dto.ChemicalListDetailDto;
import gov.epa.ccte.api.chemical.dto.ChemicalListDto;
import gov.epa.ccte.api.chemical.dto.mapper.ChemicalListDetailMapper;
import gov.epa.ccte.api.chemical.dto.mapper.ChemicalListMapper;
import gov.epa.ccte.api.chemical.repository.ChemicalListDetailRepository;
import gov.epa.ccte.api.chemical.repository.ChemicalListRepository;
import gov.epa.ccte.api.chemical.web.rest.errors.IdentifierNotFoundProblem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for getting the {@link ChemicalList}s.
 */
@Tag(name = "Chemical List Resource",
        description = "API endpoints for getting chemical lists and chemicals in those lists.")
@SecurityRequirement(name = "api_key")
@Slf4j
@RestController
@CrossOrigin
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
    @Operation(summary = "Get all public Chemicals lists")
    @RequestMapping(value = "chemical/list/", method = RequestMethod.GET)
    public @ResponseBody
    List<ChemicalListDto> listAll() throws IOException {
        //return listRepository.findAll();
       List<ChemicalList> lists =  listRepository.getAllList();

       return lists.stream()
               .map(chemicalListMapper::toDto)
               .collect(Collectors.toList());
    }

    /**
     * {@code GET  /chemical/list/type : get all types.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemical list types}.
     */
    @Operation(summary = "Get all types")
    @RequestMapping(value = "chemical/list/type", method = RequestMethod.GET)
    public @ResponseBody
    List<String> getAllType() throws IOException {

        return listRepository.getAllTypes();
    }

    /**
     * {@code GET  /chemical/list/search : get list of chemical lists.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemical lists}.
     */
    @Operation(summary = "Get public Chemicals lists matching given type")
    @RequestMapping(value = "chemical/list/search/by-type/{type}", method = RequestMethod.GET)
    public @ResponseBody
    List<ChemicalListDto> listByType( @Parameter(required = true, description = "Chemical List Type (for full list of types access /chemical/list/type)", example = "other")
            @PathVariable String type) throws IOException {

        //return listRepository.findAll();
        List<ChemicalList> lists =  listRepository.findByType(type);

        return lists.stream()
                .map(chemicalListMapper::toDto)
                .collect(Collectors.toList());
    }


    /**
     * {@code GET  /chemical/list/{listName}/chemicals : get chemical lists names.
     *
     * @param dtxsid return chemical list name where this chemical is present.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemical list names}.
     */
    @Operation(summary = "Get public Chemicals lists names which has given chemical's dtxsid")
    @RequestMapping(value = "chemical/list/search/by-dtxsid/{dtxsid}", method = RequestMethod.GET)
    public @ResponseBody
    List<String> listByDtxsid( @Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID1020560")
            @PathVariable String dtxsid) throws IOException {

        log.debug("dtxsid={}", dtxsid);

        return detailRepository.findByDtxsid(dtxsid);
    }

    /**
     * {@code GET  /chemical/list/by-dtxsid/{dtxsid} : get list of chemical lists names.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemical lists name}.
     */
    @Operation(summary = "Get a public Chemicals list matching given list's name")
    @RequestMapping(value = "chemical/list/search/by-name/{listName}", method = RequestMethod.GET)
    public @ResponseBody
    ChemicalListDto listByName( @Parameter(required = true, description = "Chemical List Name", example = "40CFR1164")
            @PathVariable String listName) throws IOException {

        log.debug("list name={}", listName);

        ChemicalList list = listRepository.findByName(listName)
                .orElseThrow(()->new IdentifierNotFoundProblem("List name", listName));

        return chemicalListMapper.toDto(list);
    }

    /**
     * {@code GET  /chemical/list/{listName}/chemicals : get list of chemical lists.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of chemical lists}.
     */
    @Operation(summary = "Get chemicals present in given Chemicals list's name")
    @RequestMapping(value = "chemical/list/chemicals/search/by-listname/{listName}", method = RequestMethod.GET)
    public @ResponseBody
    List<ChemicalListDetailDto> chemicalInList( @Parameter(required = true, description = "Chemical List Name", example = "40CFR1164")
            @PathVariable String listName) throws IOException {

        log.debug("list name={}", listName);

        List<ChemicalListDetail> details = detailRepository.findByListNameOrderByDtxsid(listName).get();

        return details.stream()
                .map(chemicalListDetailMapper::toDto)
                .collect(Collectors.toList());
    }
}
