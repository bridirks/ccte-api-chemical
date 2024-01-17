package gov.epa.ccte.api.chemical.repository;

import gov.epa.ccte.api.chemical.domain.ChemicalListChemical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource(exported = false)
public interface ChemicalListChemicalRepository extends JpaRepository<ChemicalListChemical, Long> {

    @Query("select distinct c.listName from ChemicalListChemical c where c.dtxsid = ?1")
    List<String> getListNames(String dtxsid);

    @Query(nativeQuery = true,
    value = " select distinct s.dtxsid from ms.chemical_search s join ch.v_chemical_list_chemicals l on l.dtxsid = s.dtxsid " +
            " where s.modified_value like :word% and l.list_name = :list ")
    List<String> startWith(String word, String list);

    @Query(nativeQuery = true,
            value = " select distinct s.dtxsid from ms.chemical_search s join ch.v_chemical_list_chemicals l on l.dtxsid = s.dtxsid " +
                    " where s.modified_value like %:word% and l.list_name = :list ")
    List<String> contain(String word, String list);

}