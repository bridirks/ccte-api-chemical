package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.PropertyType;
import gov.epa.ccte.api.chemical.dto.mapper.ChemicalPropertyMapper;
import gov.epa.ccte.api.chemical.projection.ChemicalPropertyAll;
import gov.epa.ccte.api.chemical.projection.ChemicalPropertyIds;
import gov.epa.ccte.api.chemical.repository.ChemicalPropertyRepository;
import gov.epa.ccte.api.chemical.web.rest.errors.HigherNumberOfIdsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
public class ChemicalPropertyResource implements ChemicalPropertyResourceApi {

    private final ChemicalPropertyRepository repository;
    private final ChemicalPropertyMapper mapper;
    
    @Value("${application.batch-size}")
    private Integer batchSize;

    public ChemicalPropertyResource(ChemicalPropertyRepository repository, ChemicalPropertyMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<ChemicalPropertyAll> propertyByDtxsid(String dtxsid, PropertyType type) {
        log.info("dtxsid = {}, Property Type ={}", dtxsid, type);
        if (type == null)
            return repository.findByDtxsid(dtxsid, ChemicalPropertyAll.class);
        else
            return repository.findByDtxsidAndPropTypeOrderByName(dtxsid, type.toString().toLowerCase(), ChemicalPropertyAll.class);
    }

    @Override
    public List<ChemicalPropertyAll> propertyByRange(String propertyId, Double start, Double end) {
        log.debug("property = {}, start = {}, end = {}", propertyId, start, end);
        return repository.findByPropertyIdAndValueBetweenOrderByDtxsidAsc(propertyId, start, end, ChemicalPropertyAll.class);
    }

    @Override
    public List<ChemicalPropertyIds> experimentalPropertyNames() {
        log.debug("experimental property names");
        return repository.getExperimentalPropertiesList();
    }

    @Override
    public List<ChemicalPropertyIds> predictedPropertyNames() {
        log.debug("predicted property names");
        return repository.getPredictedPropertiesList();
    }

    @Override
    public List<ChemicalPropertyAll> batchSearch(String[] dtxsids) throws HigherNumberOfIdsException {
        log.debug("dtxsids = {}", dtxsids.length);
        if (dtxsids.length > batchSize)
            throw new HigherNumberOfIdsException(dtxsids.length, batchSize, "dtxsid");
        return repository.findByDtxsidInOrderByDtxsidAscPropTypeAscNameAsc(dtxsids, ChemicalPropertyAll.class);
    }
}