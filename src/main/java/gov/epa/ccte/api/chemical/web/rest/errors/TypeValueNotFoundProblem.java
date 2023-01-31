package gov.epa.ccte.api.chemical.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class TypeValueNotFoundProblem extends AbstractThrowableProblem {

    private static final URI TYPE = URI.create("https://example.org/not-found");

    public TypeValueNotFoundProblem(String typeName, String typeValue) {
        super(null, "Not found",
                Status.BAD_REQUEST,
                String.format("%s with value %s not found.", typeName, typeValue));
    }
}
