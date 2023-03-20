package gov.epa.ccte.api.chemical.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "chemical_synonym", schema = "ms")
public class ChemicalSynonym {
    @Id
    @Size(max = 45)
    @Column(name = "dtxsid", length = 45)
    private String dtxsid;

    @Column(name = "pc_code")
    @Type(type = "org.hibernate.type.TextType")
    private String pcCode;

    @Column(name = "synonyms")
    @Type(type = "org.hibernate.type.TextType")
    private String synonyms;

}