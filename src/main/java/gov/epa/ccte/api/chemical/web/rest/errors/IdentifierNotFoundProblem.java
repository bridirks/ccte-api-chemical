package gov.epa.ccte.api.chemical.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class IdentifierNotFoundProblem extends AbstractThrowableProblem {

    private static final URI TYPE = URI.create("https://example.org/not-found");

    public IdentifierNotFoundProblem(String idType, String idValue){
        super(null, "Not found ",
                Status.NOT_FOUND,
                String.format("%s with value %s not found.", idType, idValue));
    }
}
