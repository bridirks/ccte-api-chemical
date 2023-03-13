package gov.epa.ccte.api.chemical.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum ChemicalDetailProjection {
    chemicaldetail, chemicalidentifier, chemicalstructure
}
