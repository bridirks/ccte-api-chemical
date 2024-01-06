package gov.epa.ccte.api.chemical.repository;

import gov.epa.ccte.api.chemical.domain.ChemicalListChemical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource(exported = false)
public interface ChemicalListChemicalRepository extends JpaRepository<ChemicalListChemical, Long> {

    @Query(nativeQuery = true, value = "select string_agg(c.dtxsid,',') as dtxsids from ch.v_chemical_list_chemicals c where upper(c.list_name) = upper(:listName)")
    List<String> getDtxsids(@Param("listName") String listName);

    <T> List<T> findDistinctByDtxsidOrderByListNameAsc(String dtxsid, Class<T> type);

}