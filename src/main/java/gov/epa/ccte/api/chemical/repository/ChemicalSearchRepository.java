package gov.epa.ccte.api.chemical.repository;

import gov.epa.ccte.api.chemical.domain.ChemicalSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@SuppressWarnings("unused")
@RepositoryRestResource(exported = false)
public interface ChemicalSearchRepository extends JpaRepository<ChemicalSearch, Long> {

    List<ChemicalSearch> findByModifiedValueStartingWithAndSearchNameInOrderByRankAscSearchValue(String word, List<String> searchWords);

    List<ChemicalSearch> findByModifiedValue(String word);

    List<ChemicalSearch> findByModifiedValueContains(String word);

    @Query(value = "select distinct dtxsid from ms.search_msready where mol_formula = :formula", nativeQuery = true)
    List<String> searchMsReadyFormula(String formula);

    @Query(value = "select distinct dtxsid from ms.search_msready where dtxcid = :dtxcid", nativeQuery = true)
    List<String> searchMsReadyDtxcid(String dtxcid);

    @Query(value = "select distinct dtxsid from ms.search_msready where monoisotopic_mass between :start and :end", nativeQuery = true)
    List<String> searchMsReadyMass(Double start, Double end);
}
