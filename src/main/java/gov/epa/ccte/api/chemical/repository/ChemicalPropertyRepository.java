package gov.epa.ccte.api.chemical.repository;

import gov.epa.ccte.api.chemical.domain.ChemicalProperty;
import gov.epa.ccte.api.chemical.projection.ChemicalPropertyIds;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "chemicalProperties", path = "chemical-properties", itemResourceRel = "chemicalProperty", exported = false)
public interface ChemicalPropertyRepository extends JpaRepository<ChemicalProperty, Integer> {

    @Transactional(readOnly = true)
    @RestResource(rel = "findByDtxsid", path = "by-dtxsid", exported = false)
    List<ChemicalProperty> findByDtxsid(String dtxsid);

    @Transactional(readOnly = true)
    @RestResource(rel = "findByDtxsid", path = "by-dtxsid", exported = false)
    List<ChemicalProperty> findByDtxsidAndPropTypeOrderByName(String dtxsid, String type);

    @Transactional(readOnly = true)
    @RestResource(rel = "findByPropertyValueRange", path = "by-range", exported = false)
    List<ChemicalProperty> findByNameAndValueBetweenAllIgnoreCaseOrderByDtxsid(String name, Double start, Double end);

    @Transactional(readOnly = true)
    @Cacheable("expPropetyNames")
    @Query("SELECT c.propType as propType, c.name as name, c.propertyId as propertyId from ChemicalProperty c where c.propType = 'experimental' group by c.propType, c.name, c.propertyId ")
    List<ChemicalPropertyIds> getExperimentalPropertiesList();

    @Transactional(readOnly = true)
    @Cacheable("prdPropetyNames")
    @Query("SELECT c.propType as propType, c.name as name, c.propertyId as propertyId from ChemicalProperty c where c.propType = 'predicted' group by c.propType, c.name, c.propertyId ")
    List<ChemicalPropertyIds> getPredictedPropertiesList();
}
