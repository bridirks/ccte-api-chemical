package gov.epa.ccte.api.chemical.repository;

import gov.epa.ccte.api.chemical.domain.ChemicalProperty;
import gov.epa.ccte.api.chemical.projection.ChemicalPropertyIds;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "chemicalProperties", path = "chemical-properties", itemResourceRel = "chemicalProperty", exported = false)
public interface ChemicalPropertyRepository extends JpaRepository<ChemicalProperty, Integer> {
    <T> List<T> findByDtxsidInOrderByDtxsidAscPropTypeAscNameAsc(String[] dtxsids, Class<T> type);

    @Transactional(readOnly = true)
    @RestResource(rel = "findByDtxsid", path = "by-dtxsid", exported = false)
    <T>
    List<T> findByDtxsid(String dtxsid, Class<T> type);

    @Transactional(readOnly = true)
    @RestResource(rel = "findByDtxsid", path = "by-dtxsid", exported = false)
    <T>
    List<T> findByDtxsidAndPropTypeOrderByName(String dtxsid, String propertyType, Class<T> type);

    @Transactional(readOnly = true)
    @RestResource(rel = "findByPropertyValueRange", path = "by-range", exported = false)
    @Query("select c.dtxsid, c.dtxcid, c.propType, c.unit, c.name, c.value, c.source, c.description, c.propertyId from ChemicalProperty c where c.propertyId = :propertyid and c.value > :start and c.value < :end")
    <T>
    List<T> getPropertiesForRange(@Param("propertyid") String propertyid,
                                  @Param("start") Double start,
                                  @Param("end") Double end, Class<T> type);

    @Transactional(readOnly = true)
    <T> List<T> findByPropertyIdAndValueBetweenOrderByDtxsidAsc(String propertyId, Double valueStart, Double valueEnd, Class<T> type);


    @Transactional(readOnly = true)
    @Cacheable("expPropetyNames")
    @Query("SELECT c.propType as propType, c.name as name, c.propertyId as propertyId from ChemicalProperty c where c.propType = 'experimental' group by c.propType, c.name, c.propertyId ")
    List<ChemicalPropertyIds> getExperimentalPropertiesList();

    @Transactional(readOnly = true)
    @Cacheable("prdPropetyNames")
    @Query("SELECT c.propType as propType, c.name as name, c.propertyId as propertyId from ChemicalProperty c where c.propType = 'predicted' group by c.propType, c.name, c.propertyId ")
    List<ChemicalPropertyIds> getPredictedPropertiesList();
}
