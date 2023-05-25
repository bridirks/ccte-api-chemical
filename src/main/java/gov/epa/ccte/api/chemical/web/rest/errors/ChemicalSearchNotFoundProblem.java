package gov.epa.ccte.api.chemical.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChemicalSearchNotFoundProblem extends AbstractThrowableProblem {

    public ChemicalSearchNotFoundProblem(List<String> errorMsgs, List<String> suggestions) {
        super(null, "Not found ",
                Status.BAD_REQUEST, String.join("\n", errorMsgs),
                null, null,
                getSuggestionsMap(suggestions));

    }

    private static Map<String, Object> getSuggestionsMap(List<String> suggestions) {
        if(suggestions == null){
            return null;
        }else{
            HashMap<String, Object> returnObj =  new HashMap<>();
            returnObj.put("suggestions", suggestions);

            return returnObj;
        }
    }

}
