package gov.epa.ccte.api.chemical.repository;

import gov.epa.ccte.api.chemical.domain.ChemicalList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "chemicalLists", path = "chemical-lists", itemResourceRel = "chemicalList", exported = false)
public interface ChemicalListRepository extends JpaRepository<ChemicalList, Integer> {


    @Transactional(readOnly = true)
    @RestResource(rel = "findAll", path = "all", exported = false)
    @Query("from ChemicalList order by type, name")
    List<ChemicalList> getAllList();

    @Transactional(readOnly = true)
    @RestResource(rel = "findByType", path = "by-type", exported = false)
    List<ChemicalList> findByType(String type);

    @Transactional(readOnly = true)
    @RestResource(rel = "findByListName", path = "by-listname", exported = false)
    Optional<ChemicalList> findByName(String listName);
}
