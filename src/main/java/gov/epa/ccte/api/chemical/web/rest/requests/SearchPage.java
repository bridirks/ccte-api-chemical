package gov.epa.ccte.api.chemical.web.rest.requests;

import gov.epa.ccte.api.chemical.projection.search.KnownChemAll;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchPage {

    private Long totalChemicals;
    private Integer size;    
    private Long next;
    private List<KnownChemAll> data;
}
