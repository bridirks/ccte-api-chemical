package gov.epa.ccte.api.chemical.web.rest;


import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for getting the {@link ChemicalResource}s.
 */
@Slf4j
@RestController
@CrossOrigin
@Hidden
public class ChemicalResource {
    @GetMapping("test")
    public String greeting(){
        return "Hello world";
    }
}
