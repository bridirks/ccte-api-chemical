package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.ChemicalPropertyExperimental;
import gov.epa.ccte.api.chemical.domain.ChemicalPropertyPredicted;
import gov.epa.ccte.api.chemical.projection.chemicalproperty.ChemicalPropertyNames;
import gov.epa.ccte.api.chemical.repository.ChemicalPropertyExperimentalRepository;
import gov.epa.ccte.api.chemical.repository.ChemicalPropertyPredictedRepository;
import gov.epa.ccte.api.chemical.web.rest.errors.HigherNumberOfIdsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
public class ChemicalPropertyResource implements ChemicalPropertyApi {

    private final ChemicalPropertyExperimentalRepository experimentalRepository;
    private final ChemicalPropertyPredictedRepository predictedRepository;

    
    @Value("${application.batch-size}")
    private Integer batchSize;

    public ChemicalPropertyResource(ChemicalPropertyExperimentalRepository experimentalRepository, ChemicalPropertyPredictedRepository predictedRepository) {
        this.experimentalRepository = experimentalRepository;
        this.predictedRepository = predictedRepository;

    }

 // *********************** Experimental - start *************************************
    @Override
    public List<ChemicalPropertyExperimental> experimentalPropertyByDtxsid(String dtxsid) {
        log.info("dtxsid = {}", dtxsid);

        List<ChemicalPropertyExperimental> data =  experimentalRepository.findByDtxsid(dtxsid, ChemicalPropertyExperimental.class);
            
        return data;

    }

    @Override
    public List<ChemicalPropertyExperimental> experimentalPropertyByRange(String propertyName, Double start, Double end) {
        log.debug("property = {}, start = {}, end = {}", propertyName, start, end);
        
        List<ChemicalPropertyExperimental> data = experimentalRepository.findByPropNameAndPropValueBetweenOrderByDtxsidAsc(propertyName, start, end, ChemicalPropertyExperimental.class);
    
        return data;
    }

    @Override
    public List<ChemicalPropertyNames> experimentalPropertyNames() {
        log.debug("experimental property names");
        
        return experimentalRepository.getExperimentalPropertiesList();
    }


    @Override
    public List<ChemicalPropertyExperimental> experimentalBatchSearch(String[] dtxsids) throws HigherNumberOfIdsException {
        log.debug("dtxsids = {}", dtxsids.length);
        if (dtxsids.length > batchSize)
            throw new HigherNumberOfIdsException(dtxsids.length, batchSize, "dtxsid");
        List<ChemicalPropertyExperimental> data = experimentalRepository.findByDtxsidInOrderByDtxsidAsc(dtxsids, ChemicalPropertyExperimental.class);
        
        return data;
    }
    
    // *********************** Experimental - End *************************************

    // *********************** Predicted - start *************************************
    
    @Override
    public List<ChemicalPropertyPredicted> predictedPropertyByDtxsid(String dtxsid) {
        log.info("dtxsid = {}", dtxsid);

        List<ChemicalPropertyPredicted> data =  predictedRepository.findByDtxsid(dtxsid, ChemicalPropertyPredicted.class);
            
        return data;

    }

    @Override
    public List<ChemicalPropertyPredicted> predictedPropertyByRange(String propertyName, Double start, Double end) {
        log.debug("property = {}, start = {}, end = {}", propertyName, start, end);
        
        List<ChemicalPropertyPredicted> data = predictedRepository.findByPropNameAndPropValueBetweenOrderByDtxsidAsc(propertyName, start, end, ChemicalPropertyPredicted.class);
    
        return data;
    }

    @Override
    public List<ChemicalPropertyNames> predictedPropertyNames() {
        log.debug("experimental property names");
        
        return predictedRepository.getPredictedPropertiesList();
    }


    @Override
    public List<ChemicalPropertyPredicted> predictedBatchSearch(String[] dtxsids) throws HigherNumberOfIdsException {
        log.debug("dtxsids = {}", dtxsids.length);
        if (dtxsids.length > batchSize)
            throw new HigherNumberOfIdsException(dtxsids.length, batchSize, "dtxsid");
        List<ChemicalPropertyPredicted> data = predictedRepository.findByDtxsidInOrderByDtxsidAsc(dtxsids, ChemicalPropertyPredicted.class);
        
        return data;
    }
}