package gov.epa.ccte.api.chemical.repository;

import gov.epa.ccte.api.chemical.domain.ChemicalListChemical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource(exported = false)
public interface ChemicalListChemicalRepository extends JpaRepository<ChemicalListChemical, Long> {

    @Query("select distinct c.listName from ChemicalListChemical c where c.dtxsid = ?1")
    List<String> getListNames(String dtxsid);
}