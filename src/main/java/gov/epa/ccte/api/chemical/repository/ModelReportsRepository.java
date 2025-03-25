package gov.epa.ccte.api.chemical.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import gov.epa.ccte.api.chemical.domain.ModelReports;

@RepositoryRestResource(exported = false)
public interface ModelReportsRepository extends JpaRepository<ModelReports, Long>{

    @Transactional(readOnly = true)
   
    <T>List<T> findByDtxsid(String dtxsid);  
    
}
