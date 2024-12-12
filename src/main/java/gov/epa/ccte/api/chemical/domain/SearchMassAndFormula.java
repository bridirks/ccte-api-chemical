package gov.epa.ccte.api.chemical.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.data.annotation.Immutable;

@Entity
@Data
@Immutable
@Table(name = "chemical_details")
public class SearchMassAndFormula extends ChemicalDetails {

    private Double massDiff;
    private String toxvalData;

}
