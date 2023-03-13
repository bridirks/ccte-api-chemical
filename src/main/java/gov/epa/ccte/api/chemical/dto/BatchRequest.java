package gov.epa.ccte.api.chemical.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BatchRequest {
    private String[] dtxsid;
    private String[] dtxcid;

}
