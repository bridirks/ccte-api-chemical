package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.projection.FateAll;
import gov.epa.ccte.api.chemical.repository.FateRepository;
import gov.epa.ccte.api.chemical.web.rest.errors.HigherNumberOfIdsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * REST controller for retrieving chemical fate data.
 */
@Slf4j
@RestController
public class FateResource implements FateApi {

    private final FateRepository repository;
    @Value("${application.batch-size}")
    private Integer batchSize;

    public FateResource(FateRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<FateAll> fateByDtxsid(String dtxsid) {
        log.debug("dtxsid = {}", dtxsid);
        return repository.findByDtxsid(dtxsid, FateAll.class);
    }

    @Override
    public List<FateAll> batchSearch(String[] dtxsids) {
        log.debug("dtxsids = {}", dtxsids.length);

        if (dtxsids.length > batchSize) {
            throw new HigherNumberOfIdsException(dtxsids.length, batchSize, "dtxsid");
        }

        return repository.findByDtxsidInOrderByDtxsidAscEndpointNameAsc(dtxsids, FateAll.class);
    }
}