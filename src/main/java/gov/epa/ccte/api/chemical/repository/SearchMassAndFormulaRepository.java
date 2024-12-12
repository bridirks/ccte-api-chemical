package gov.epa.ccte.api.chemical.repository;


import gov.epa.ccte.api.chemical.domain.SearchMassAndFormula;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface SearchMassAndFormulaRepository extends JpaRepository<SearchMassAndFormula, String> {

    @Query(value = "select dtxsid, dtxcid, casrn, compound_id, generic_substance_id, preferred_name, active_assays," +
            " cpdata_count, mol_formula, monoisotopic_mass, percent_assays, pubchem_count, pubmed_count, sources_count," +
            " qc_level, qc_level_desc, stereo, isotope, multicomponent, total_assays, toxcast_select, pubchem_cid," +
            " related_substance_count, related_structure_count, has_structure_image, " +
            " iupac_name, smiles, inchi_string, average_mass, inchikey,toxval_data, \n" +
            " case when :mass =0.0 then null else monoisotopic_mass - :mass end as mass_diff\n" +
            " from {h-schema}chemical_details where monoisotopic_mass between :start and :end and dtxsid <> 'DTXSID00000000' " +
            " order by monoisotopic_mass ",
            countQuery = "select count(*) from ccd_app.chemical_details where monoisotopic_mass between :start and :end and dtxsid <> 'DTXSID00000000'",
            nativeQuery = true)
    Page<SearchMassAndFormula> getMassValues(@Param("start") Double start, @Param("end") Double end, @Param("mass") Double mass, Pageable pageable);

    Long countByMonoisotopicMassBetweenAndDtxsidIsNot(Double start, Double end, String skipDtxsid);

}
