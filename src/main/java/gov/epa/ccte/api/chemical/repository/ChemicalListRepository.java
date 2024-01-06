package gov.epa.ccte.api.chemical.repository;

import gov.epa.ccte.api.chemical.domain.ChemicalList;
import gov.epa.ccte.api.chemical.projection.chemicallist.ChemicalListWithDtxsids;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface ChemicalListRepository extends JpaRepository<ChemicalList, Integer> {

    @Transactional(readOnly = true)
    <T> List<T> findByVisibilityOrderByTypeAscListNameAsc(String visibility, Class<T> type);


    @Transactional(readOnly = true)
    @RestResource(rel = "findByType", path = "by-type", exported = false)
    <T>
    List<T> findByType(String listType, Class<T> type);

    @Transactional(readOnly = true)
    @RestResource(rel = "findByListName", path = "by-listname", exported = false)
    <T>
    Optional<T> findByListNameIgnoreCase(String listName, Class<T> type);

    @Transactional(readOnly = true)
    @Cacheable("listTypeNames")
    @Query("SELECT distinct type from ChemicalList order by type")
    List<String> getAllTypes();

    @Transactional(readOnly = true)
    @Query( nativeQuery = true,
            value = "select l.list_name, l.label, l.type, l.visibility, l.short_description, l.long_description, l.chemical_count, " +
                    " l.created_at, l.updated_at, string_agg(c.dtxsid,',') as dtxsids " +
                    " from ch.v_chemical_lists l join ch.v_chemical_list_chemicals c on " +
                    " l.id = c.list_id and l.list_name = 'LCSSPUBCHEM' " +
                    " group by l.list_name, l.label, l.type, l.visibility, l.short_description, l.long_description, l.chemical_count, " +
                    " l.created_at, l.updated_at")
    Optional<ChemicalListWithDtxsids> getChemicalWithDtxsids(String listName);
}
