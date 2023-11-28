package gov.epa.ccte.api.chemical.service;

import gov.epa.ccte.api.chemical.repository.ChemicalDetailRepository;
import gov.epa.ccte.api.chemical.web.rest.errors.IdentifierNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
                    .orElseThrow(() -> new IdentifierNotFoundException("dtxsid", id));
        }else{
            return detailRepository.findByDtxcid(id, tClass)
                    .orElseThrow(() -> new IdentifierNotFoundException("dtxcid", id));
        }
    }

    public  <T> List<T> getChemicalDetailsForBatch(String[] dtxsids, Class<T> tClass) {

        return detailRepository.findByDtxsidInOrderByDtxsidAsc(dtxsids, tClass);

//        switch (request.getSearchTerm()){
//            case DTXSID: return detailRepository.findByDtxsidInOrderByDtxsidAsc(request.getValues(), tClass);
//            //case DTXCID: return detailRepository.findByDtxcidInOrderByDtxcidAsc(request.getValues(),tClass);
//            default: return Collections.emptyList();
//        }
    }
}
