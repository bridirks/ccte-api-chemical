package gov.epa.ccte.api.chemical.service;

import gov.epa.ccte.api.chemical.dto.BatchRequest;
import gov.epa.ccte.api.chemical.projection.*;
import gov.epa.ccte.api.chemical.repository.ChemicalDetailRepository;
import gov.epa.ccte.api.chemical.web.rest.errors.IdentifierNotFoundProblem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class ChemicalDetailService {
    private ChemicalDetailRepository detailRepository;

    public ChemicalDetailService(ChemicalDetailRepository detailRepository) {
        this.detailRepository = detailRepository;
    }

    public <T> T getChemicalDetailsForId(String id, String type, Class<T> tClass) {

        if(type.equals("dtxsid")) {
            return detailRepository.findByDtxsid(id, tClass)
                    .orElseThrow(() -> new IdentifierNotFoundProblem("dtxsid", id));
        }else{
            return detailRepository.findByDtxcid(id, tClass)
                    .orElseThrow(() -> new IdentifierNotFoundProblem("dtxcid", id));
        }
    }

    public  <T> List<T> getChemicalDetailsForBatch(BatchRequest request, Class<T> tClass) {
        if(request.getDtxcid() != null && request.getDtxcid().length > 0){
            return detailRepository.findByDtxcidInOrderByDtxcidAsc(request.getDtxcid(),tClass);
        } if(request.getDtxsid() != null && request.getDtxsid().length > 0) {
            return detailRepository.findByDtxsidInOrderByDtxsidAsc(request.getDtxsid(), tClass);
        }else{
            return Collections.emptyList();
        }
    }
}
