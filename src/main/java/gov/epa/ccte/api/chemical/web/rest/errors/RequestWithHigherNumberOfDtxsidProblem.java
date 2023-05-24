package gov.epa.ccte.api.chemical.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

public class RequestWithHigherNumberOfDtxsidProblem extends AbstractThrowableProblem {
    public RequestWithHigherNumberOfDtxsidProblem(Integer size, Integer maxSize){
        super(
                Problem.DEFAULT_TYPE, "Validation Error", Status.BAD_REQUEST, String.format("System supports only '%s' dtxsid at one time, '%s' are submitted.",maxSize, size)
        );
    }
}
