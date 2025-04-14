package gov.epa.ccte.api.chemical.repository;

import gov.epa.ccte.api.chemical.domain.ChemicalPropertyPredicted;
import gov.epa.ccte.api.chemical.projection.chemicalproperty.ChemicalPropertyNames;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface ChemicalPropertyPredictedRepository extends JpaRepository<ChemicalPropertyPredicted, Long> {
    @Transactional(readOnly = true)
    <T> List<T> findByDtxsidInOrderByDtxsidAsc(String[] dtxsids, Class<T> type);

    @Transactional(readOnly = true)
    <T>List<T> findByDtxsid(String dtxsid, Class<T> type);

    @Transactional(readOnly = true)
    <T> List<T> findByPropNameAndPropValueBetweenOrderByDtxsidAsc(String propertyName, Double valueStart, Double valueEnd, Class<T> type);


    @Query(value = """
    				SELECT prop_name as propertyName
					FROM chemprop.mv_predicted_data
    				GROUP BY prop_name
    				""", nativeQuery = true)
    List<ChemicalPropertyNames> getPredictedPropertiesList();
}
