package gov.epa.ccte.api.chemical.repository;

import gov.epa.ccte.api.chemical.domain.ChemicalFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@SuppressWarnings("unused")
@RepositoryRestResource(exported = false)
public interface ChemicalFileRepository extends JpaRepository<ChemicalFile, Long> {


    @RestResource(path = "by-dtxsid",rel = "find-by-dtxsid",exported = true)
    @Query(value = "select c.molImage from ChemicalFile c where c.dtxsid = :dtxsid")
    byte[] getMolImageForDtxsid(@Param("dtxsid") String dtxsid);

    @RestResource(path = "by-dtxcid",rel = "find-by-dtxcid",exported = true)
    @Query(value = "select c.molImage from ChemicalFile c where c.dtxcid = :dtxcid")
    byte[] getMolImageForDtxcid(@Param("dtxcid") String dtxcid);

    @RestResource(path = "by-dtxsid",rel = "find-by-dtxsid",exported = true)
    @Query(value = "select c.molFile from ChemicalFile c where c.dtxsid = :dtxsid")
    String getMolFileForDtxsid(@Param("dtxsid") String dtxsid);

    @RestResource(path = "by-dtxcid",rel = "find-by-dtxcid",exported = true)
    @Query(value = "select c.molFile from ChemicalFile c where c.dtxcid = :dtxcid")
    String getMolFileForDtxcid(@Param("dtxcid") String dtxcid);

    @RestResource(path = "by-dtxsid",rel = "find-by-dtxsid",exported = true)
    @Query(value = "select c.mrvFile from ChemicalFile c where c.dtxsid = :dtxsid")
    String getMrvFileForDtxsid(@Param("dtxsid") String dtxsid);

    @RestResource(path = "by-dtxcid",rel = "find-by-dtxcid",exported = true)
    @Query(value = "select c.mrvFile from ChemicalFile c where c.dtxcid = :dtxcid")
    String getMrvFileForDtxcid(@Param("dtxcid") String dtxcid);
//
//    @Query(value = "select c.molFile from ChemicalFile c where c.dtxsid = :dtxsid")
//    String getMolFileForDtxsid(@Param("dtxsid") String dtxsid);
//
//    @Query(value = "select c.molFile from ChemicalFile c where c.dtxcid = :dtxcid")
//    String getMolFileForDtxcid(@Param("dtxcid") String dtxcid);
}
