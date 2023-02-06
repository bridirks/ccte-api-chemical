package gov.epa.ccte.api.chemical.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.epa.ccte.api.chemical.domain.ApiKey;
import gov.epa.ccte.api.chemical.repository.ApiKeyRepository;
import gov.epa.ccte.api.chemical.web.rest.errors.AuthorizationProblem;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.ThrowableProblem;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Profile("apikey")
@Component
public class ApiKeyRequestFilter extends GenericFilterBean {

    private ConcurrentHashMap<UUID, String> keyStore;// = new ConcurrentHashMap();

    private final ObjectMapper mapper = new ObjectMapper();
    private final String keyName;

    public ApiKeyRequestFilter(ApiKeyRepository repository, @Value("${application.api-key-name}") String keyName) {
        this.keyName = keyName;

        initializeKeyStore(repository);
    }

    private void initializeKeyStore(ApiKeyRepository repository) {
        keyStore = new ConcurrentHashMap<>();

        List<ApiKey> keys = repository.findAll();

        for(ApiKey key : keys)
            keyStore.put(key.getId(), key.getEmail());

        log.info("*** {} keys are loaded. *** ", keys.size());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String path = req.getRequestURI();

//        if(path.startsWith("/api") == false){
//            filterChain.doFilter(servletRequest, servletResponse);
//            return;
//        }

        // Get apikey from the http header
        String key = req.getHeader(keyName) == null ? "" : req.getHeader(keyName);
        log.debug("Trying {}: {}", keyName, key );

        // In case user is provided api key through requrest parameter api-key
        if(key == null || key.equals("")){
            String queryString = req.getQueryString();
            if(StringUtils.isNoneEmpty(queryString)){
                queryString = URLDecoder.decode(queryString, StandardCharsets.UTF_8.toString());
                int pos = StringUtils.indexOfIgnoreCase(queryString, keyName + "=");
                String keyValue = StringUtils.substring(queryString, pos+8);
                key = StringUtils.substringBefore(keyValue,"&");
                // key = StringUtils.substringBetween(queryString, "api_key=","&");
            }
        }

        if(key == null || key.equals("")){
            returnErrorMsg(servletResponse, key);
        } else if(isKeyExist(key)){
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            returnErrorMsg(servletResponse, key);

            log.info("*** API key {} not recognized *** ", key);
        }
    }

    private void returnErrorMsg(ServletResponse servletResponse, String key) throws IOException {
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String error = "Invalid API KEY";

        AuthorizationProblem problem;

        if(key == null || key.equals("")){
            // header is missing
            problem = AuthorizationProblem.builder()
                    .title("API Header Not Found")
                    .detail("Every API call should pass assigned API key through http header. Request is missing header " + keyName+ "." )
                    .build();

        }else{
            // incorrect apikey
            problem = AuthorizationProblem.builder()
                    .title("API Key Not Found")
                    .detail("Please contact API admin for requesting a valid API key. Key " + key + " not found.")
                    .build();
        }

        String obj = mapper.writeValueAsString(problem);

        resp.reset();
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        resp.setContentType("application/json");
        servletResponse.setContentLength(obj.length());
        servletResponse.getWriter().write(obj);
    }

    private boolean isKeyExist(String key) {
        if(key == null)
            return false;
        else if(keyStore.isEmpty())
            return false;
        else{
            try{
                return keyStore.containsKey(UUID.fromString(key));
            } catch (Exception e){
                return false;
            }
        }

    }
}
