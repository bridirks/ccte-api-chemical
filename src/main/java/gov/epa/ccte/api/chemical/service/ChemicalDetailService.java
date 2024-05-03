package gov.epa.ccte.api.chemical.service;

import gov.epa.ccte.api.chemical.repository.ChemicalDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ChemicalDetailService {
    private final ChemicalDetailRepository detailRepository;

    public ChemicalDetailService(ChemicalDetailRepository detailRepository) {
        this.detailRepository = detailRepository;
    }


    public  <T> List<T> getChemicalDetailsForBatch(String[] ids, Class<T> tClass, String type) {

        if(type.equalsIgnoreCase("dtxsid"))
            return detailRepository.findByDtxsidInOrderByDtxsidAsc(ids, tClass);
        else
            return detailRepository.findByDtxcidInOrderByDtxcidAsc(ids, tClass);
    }

}
