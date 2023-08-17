package gov.epa.ccte.api.chemical.repository;

import gov.epa.ccte.api.chemical.domain.ChemicalSearch;
import gov.epa.ccte.api.chemical.projection.search.CcdChemicalSearchResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@SuppressWarnings("unused")
@RepositoryRestResource(exported = false)
public interface ChemicalSearchRepository extends JpaRepository<ChemicalSearch, Long> {

    <T> List<T> findByModifiedValueStartingWithAndSearchNameInOrderByRankAscSearchValue(String word, List<String> searchWords, Class<T> type);

    <T> List<T> findByModifiedValueOrderByRankAsc(String word, Class<T> type);

    <T> List<T> findByModifiedValueContainsOrderByRankAscDtxsid(String word, Class<T> type);

    @Query(nativeQuery = true)
    List<CcdChemicalSearchResult> equalCcd(String searchWord);

    @Query(nativeQuery = true)
    List<CcdChemicalSearchResult> containCcd(String searchWord);

    @Query(value = "select s.id, s.search_group, s.search_name, s.search_value, s.modified_value, s.rank, \n" +
            "       d.h_chem_hash_key, d.dtxsid, d.dtxcid, d.casrn, d.compound_id, d.generic_substance_id, d.preferred_name, d.active_assays, \n" +
            "       d.cpdata_count, d.mol_formula, d.monoisotopic_mass, d.percent_assays, d.pubchem_count, d.pubmed_count, d.sources_count, \n" +
            "       d.qc_level, d.qc_level_desc, d.stereo, d.isotope, d.multicomponent, d.total_assays, d.toxcast_select, d.pubchem_cid, \n" +
            "       d.mol_file, d.mrv_file, d.related_substance_count, d.related_structure_count, d.mol_image, d.has_structure_image, \n" +
            "       d.iupac_name, d.smiles, d.inchi_string, d.average_mass, d.inchikey, d.qc_notes, d.qsar_ready_smiles, d.ms_ready_smiles, \n" +
            "       d.iris_link, d.pprtv_link, d.wikipedia_article, d.expocat_median_prediction, d.expocat, d.nhanes, d.toxval_data, \n" +
            "       d.water_solubility_test, d.water_solubility_opera, d.viscosity_cp_cp_test_pred, d.vapor_pressure_mmhg_test_pred,\n" +
            "       d.vapor_pressure_mmhg_opera_pred, d.thermal_conductivity, d.tetrahymena_pyriformis, d.surface_tension, d.soil_adsorption_coefficient, \n" +
            "       d.oral_rat_ld50_mol, d.opera_km_days_opera_pred, d.octanol_water_partition, d.octanol_air_partition_coeff, d.melting_point_degc_test_pred, \n" +
            "       d.melting_point_degc_opera_pred, d.hr_fathead_minnow, d.hr_diphnia_lc50, d.henrys_law_atm, d.flash_point_degc_test_pred, \n" +
            "       d.devtox_test_pred, d.density, d.boiling_point_degc_test_pred, d.boiling_point_degc_opera_pred, d.biodegradation_half_life_days, \n" +
            "       d.bioconcentration_factor_test_pred, d.bioconcentration_factor_opera_pred, d.atmospheric_hydroxylation_rate, d.ames_mutagenicity_test_pred, \n" +
            "       d.pkaa_opera_pred, d.pkab_opera_pred, d.descriptor_string_tsv, d.is_markush \n" +
            "from ms.chemical_search s join ms.chemical_details d on s.dtxsid = d.dtxsid  where s.modified_value = :word", nativeQuery = true)
    <T>
    List<T> searchChemical(String word, Class<T> type);

    @Query(value = "select distinct dtxsid from ms.search_msready where mol_formula = :formula", nativeQuery = true)
    List<String> searchMsReadyFormula(String formula);

    @Query(value = "select distinct dtxsid from ms.search_msready where dtxcid = :dtxcid", nativeQuery = true)
    List<String> searchMsReadyDtxcid(String dtxcid);

    @Query(value = "select distinct dtxsid from ms.search_msready where monoisotopic_mass between :start and :end", nativeQuery = true)
    List<String> searchMsReadyMass(Double start, Double end);
}
