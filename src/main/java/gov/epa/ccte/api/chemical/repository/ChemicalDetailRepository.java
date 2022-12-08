package gov.epa.ccte.api.chemical.repository;

import gov.epa.ccte.api.chemical.domain.ChemicalDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SuppressWarnings("unused")
@RepositoryRestResource(collectionResourceRel = "chemicalDetails", path = "chemical-details", itemResourceRel = "chemicalDetail", exported = false)
public interface ChemicalDetailRepository extends JpaRepository<ChemicalDetail, Long> {

    @Transactional(readOnly = true)
    @RestResource(rel = "findByDtxsid", path = "by-dtxsid", exported = true)
    ChemicalDetail findByDtxsid(String dtxsid);
    @Transactional(readOnly = true)
    @RestResource(rel = "findByDtxcid", path = "by-dtxcid", exported = true)
    List<ChemicalDetail> findByDtxcid(String dtxcid);

}
