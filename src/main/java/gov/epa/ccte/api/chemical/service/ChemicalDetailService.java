package gov.epa.ccte.api.chemical.service;

import gov.epa.ccte.api.chemical.projection.chemicallist.ChemicalListWithDtxsids;
import gov.epa.ccte.api.chemical.repository.ChemicalDetailRepository;
import gov.epa.ccte.api.chemical.repository.ChemicalListRepository;
import gov.epa.ccte.api.chemical.web.rest.errors.IdentifierNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ChemicalDetailService {
    private final ChemicalDetailRepository detailRepository;
    private final ChemicalListRepository listRepository;

    public ChemicalDetailService(ChemicalDetailRepository detailRepository, ChemicalListRepository listRepository) {
        this.detailRepository = detailRepository;
        this.listRepository = listRepository;    }


    public  <T> List<T> getChemicalDetailsForBatch(String[] ids, Class<T> tClass, String type) {

        if(type.equalsIgnoreCase("dtxsid"))
            return detailRepository.findByDtxsidInOrderByDtxsidAsc(ids, tClass);
        else
            return detailRepository.findByDtxcidInOrderByDtxcidAsc(ids, tClass);
    }

}
