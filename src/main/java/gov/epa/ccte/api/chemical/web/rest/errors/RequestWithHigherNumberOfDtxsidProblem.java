package gov.epa.ccte.api.chemical.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

public class RequestWithHigherNumberOfDtxsidProblem extends AbstractThrowableProblem {
    public RequestWithHigherNumberOfDtxsidProblem(Integer size){
        super(
                Problem.DEFAULT_TYPE, "Validation Error", Status.BAD_REQUEST, String.format("System supports only 200 dtxsid at one time, '%s' are submitted.",size)
        );
    }
}
