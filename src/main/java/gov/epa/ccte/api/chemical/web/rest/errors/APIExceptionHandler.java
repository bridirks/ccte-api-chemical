package gov.epa.ccte.api.chemical.web.rest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(HigherNumberOfDtxsidException.class)
    ProblemDetail handleHigherNumberOfDtxsidException(HigherNumberOfDtxsidException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(IdentifierNotFoundException.class)
    ProblemDetail handleIdentifierNotFoundException(IdentifierNotFoundException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(TypeValueNotFoundException.class)
    ProblemDetail handleTypeValueNotFoundException(TypeValueNotFoundException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ChemicalSearchNotFoundException.class)
    ProblemDetail handleChemicalSearchNotFoundException(ChemicalSearchNotFoundException ex){

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                String.join("\n",ex.getErrorMsgs()));
        problemDetail.setProperty("suggestions", ex.getSuggestions());

        return problemDetail;
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    ProblemDetail handleValidationException(MethodArgumentNotValidException ex){
//        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
//    }
}
