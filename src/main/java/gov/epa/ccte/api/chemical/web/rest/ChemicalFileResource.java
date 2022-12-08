package gov.epa.ccte.api.chemical.web.rest;

import com.epam.indigo.Indigo;
import com.epam.indigo.IndigoObject;
import com.epam.indigo.IndigoRenderer;
import gov.epa.ccte.api.chemical.domain.ImageFormat;
import gov.epa.ccte.api.chemical.repository.ChemicalFileRepository;
import gov.epa.ccte.api.chemical.service.ChemicalImageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.springframework.http.MediaType.IMAGE_PNG;
import static org.springframework.http.MediaType.TEXT_PLAIN;

/**
 * REST controller for getting the {@link gov.epa.ccte.api.chemical.domain.ChemicalFile}s.
 */
@Slf4j
@RestController
public class ChemicalFileResource {

    //image size constants
    private static final int MIN_WIDTH = 300;
    private static final int MIN_HEIGHT = 300;

    private static final int MAX_WIDTH = 2560;
    private static final int MAX_HEIGHT = 2560;

    final private ChemicalFileRepository fileRepository;

    public ChemicalFileResource(ChemicalFileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    /**
     * {@code GET  /chemical/file/image/by-dtxsid/:dtxsid} : get the "dtxsid" chemical image.
     *
     * @param dtxsid the matching dtxsid of the chemical image to retrieve.
     * @param format is enum with two values pgn and svg
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chemical image}.
     */
    @RequestMapping(value = "chemical/file/image/by-dtxsid/{dtxsid}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<byte[]> imageByDtxsid(@PathVariable("dtxsid") String dtxsid,
                                         @RequestParam(value = "format", required = false) ImageFormat format) throws IOException {

        log.debug("dtxsid = {}, format = {}", dtxsid, format);


        if(format == ImageFormat.PNG || format == null){
            byte[] image = fileRepository.getMolImageForDtxsid(dtxsid);
            return ResponseEntity.ok().contentType(IMAGE_PNG).body(image);
        }else if(format == ImageFormat.SVG){
            String mol = fileRepository.getMolFileForDtxsid(dtxsid);

            byte[] image = getSvgImage(mol);

            return ResponseEntity.ok().contentType(MediaType.valueOf("image/svg+xml")).body(image);
        }else{
            return null;
        }
    }

    /**
     * {@code GET  /chemical/file/image/by-dtxcid/:dtxcid} : get the "dtxcid" chemical image.
     *
     * @param dtxcid the matching dtxcid of the chemical image to retrieve.
     * @param format is enum with two values pgn and svg
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chemical image}.
     */
    @RequestMapping(value = "chemical/file/image/by-dtxcid/{dtxcid}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<byte[]> imageByDtxcid(@PathVariable("dtxcid") String dtxcid,
                                         @RequestParam(value = "format", required = false) ImageFormat format) throws IOException {

        log.debug("dtxcid = {}, format = {}", dtxcid, format);


        if(format == ImageFormat.PNG || format == null){
            byte[] image = fileRepository.getMolImageForDtxcid(dtxcid);
            return ResponseEntity.ok().contentType(IMAGE_PNG).body(image);
        }else if(format == ImageFormat.SVG){
            String mol = fileRepository.getMolFileForDtxcid(dtxcid);

            byte[] image = getSvgImage(mol);

            return ResponseEntity.ok().contentType(MediaType.valueOf("image/svg+xml")).body(image);
        }else{
            return null;
        }
    }

    private static byte[] getSvgImage(String mol) {
        Integer width=200;
        Integer height = 200;

        Indigo indigo = new Indigo();

        indigo.setOption("ignore-stereochemistry-errors", true);
        indigo.setOption("ignore-noncritical-query-features", true);
        indigo.setOption("aromaticity-model", "generic");

        IndigoRenderer renderer = new IndigoRenderer(indigo);
        indigo.setOption("render-margins", 5, 5);
        indigo.setOption("render-coloring", true);
//            if ( width != null )
//                indigo.setOption("render-image-width", width);
//            if ( height != null )
//                indigo.setOption("render-image-height", height);
        indigo.setOption("render-output-format", "svg");

        IndigoObject molecule = indigo.loadMolecule(mol);
        //ChemicalImageUtils.toSvg(molecule);
        molecule.dearomatize();

        byte[] image = renderer.renderToBuffer(molecule);
        return image;
    }

    /**
     * {@code GET  /chemical/file/mol/by-dtxsid/:dtxsid} : get the "dtxsid" chemical mol file.
     *
     * @param dtxsid the matching dtxsid of the chemical mol file to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chemical mol file}.
     */
    @RequestMapping(value = "chemical/file/mol/by-dtxsid/{dtxsid}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<String> molByDtxsid(@PathVariable("dtxsid") String dtxsid) throws IOException {

        log.debug("dtxsid = {}", dtxsid);

        String mol = fileRepository.getMolFileForDtxsid(dtxsid);

        return ResponseEntity.ok().contentType(TEXT_PLAIN).body(mol);
    }

    /**
     * {@code GET  /chemical/file/mol/by-dtxcid/:dtxcid} : get the "dtxcid" chemical mol file.
     *
     * @param dtxcid the matching dtxcid of the chemical mol file to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chemical mol file}.
     */
    @RequestMapping(value = "chemical/file/mol/by-dtxcid/{dtxcid}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<String> molByDtxcid(@PathVariable("dtxcid") String dtxcid) throws IOException {
        log.debug("dtxsid = {}", dtxcid);

        String mol = fileRepository.getMolFileForDtxcid(dtxcid);

        return ResponseEntity.ok().contentType(TEXT_PLAIN).body(mol);
    }

    /**
     * {@code GET  /chemical/file/mol/by-dtxsid/:dtxsid} : get the "dtxsid" chemical mrv file.
     *
     * @param dtxsid the matching dtxcid of the chemical mrv file to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chemical mrv file}.
     */
    @RequestMapping(value = "chemical/file/mrv/by-dtxsid/{dtxsid}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<String> mrvByDtxsid(@PathVariable("dtxsid") String dtxsid) throws IOException {

        log.debug("dtxsid = {}", dtxsid);

        String mol = fileRepository.getMrvFileForDtxsid(dtxsid);

        return ResponseEntity.ok().contentType(TEXT_PLAIN).body(mol);
    }

    /**
     * {@code GET  /chemical/file/mol/by-dtxcid/:dtxcid} : get the "dtxcid" chemical mrv file.
     *
     * @param dtxcid the matching dtxcid of the chemical mrv file to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chemical mrv file}.
     */
    @RequestMapping(value = "chemical/file/mrv/by-dtxcid/{dtxcid}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<String> mrvByDtxcid(@PathVariable("dtxcid") String dtxcid) throws IOException {
        log.debug("dtxsid = {}", dtxcid);

        String mol = fileRepository.getMrvFileForDtxcid(dtxcid);

        return ResponseEntity.ok().contentType(TEXT_PLAIN).body(mol);
    }
}
