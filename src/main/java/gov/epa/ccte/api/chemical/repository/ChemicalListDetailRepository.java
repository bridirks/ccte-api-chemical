package gov.epa.ccte.api.chemical.repository;

import gov.epa.ccte.api.chemical.domain.ChemicalListDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "chemicalListDetails", path = "chemical-lists-details", itemResourceRel = "chemicalListDetail", exported = false)
public interface ChemicalListDetailRepository extends JpaRepository<ChemicalListDetail, Integer> {

    @Transactional(readOnly = true)
    @RestResource(rel = "findByListName", path = "by-listname", exported = false)
    List<ChemicalListDetail> findByListNameOrderByDtxsid(String name);

    @Transactional(readOnly = true)
    @RestResource(rel = "findByDtxsid", path = "by-dtxsid", exported = false)
    @Query("select distinct listName from ChemicalListDetail where dtxsid = :dtxsid")
    List<String> findByDtxsid(String dtxsid);
}
