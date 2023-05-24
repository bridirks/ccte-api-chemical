package gov.epa.ccte.api.chemical.web.rest.errors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import java.util.Optional;

import static org.zalando.problem.Status.BAD_REQUEST;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 * The error response follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807).
 */
@ControllerAdvice
public class ExceptionTranslator implements ProblemHandling{

    @Value("${spring.application.name}")
    private String applicationName;

    private final Environment env;

    public ExceptionTranslator(Environment env) {
        this.env = env;
    }

    @Override
    public boolean isCausalChainsEnabled() {
        return false; // disable stack traces as part of error message
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Problem> handleConversionFailedException() {

        return ResponseEntity.of(
          Optional.of(
                  Problem.builder()
                          .withStatus(BAD_REQUEST)
                          .withTitle("Validation Error")
                          .withDetail("Type value is not correct. Correct value could be experimental or predicted.")
                          .build()
          )
        );
    }
}
