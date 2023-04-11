package gov.epa.ccte.api.chemical.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.epa.ccte.api.chemical.domain.ApiKey;
import gov.epa.ccte.api.chemical.repository.ApiKeyRepository;
import gov.epa.ccte.api.chemical.web.rest.errors.AuthorizationProblem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

        log.info("*** checking the API key ***");

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String path = req.getRequestURI();

//        if(path.startsWith("/api") == false){
//            filterChain.doFilter(servletRequest, servletResponse);
//            return;
//        }

        // get from header

        String key = getApiKeyfromHttpHeader(req.getHeader(keyName));

        // In case user is provided api key through parameter x-api-key
        if(key == null || key.equals("")){
            // get key from the URL parameter
            key = getApiKeyFromQueryParam(req.getQueryString());
        }

        if(key == null || key.equals("")){
            // api key is missing
            returnErrorMsg(servletResponse, key);
        } else if(isKeyExist(key)){
            // api key found
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            // api doesn't match
            returnErrorMsg(servletResponse, key);
            log.info("*** API key {} not recognized *** ", key);
        }
    }

    private String getApiKeyFromQueryParam(String query) throws UnsupportedEncodingException {

        // an example -  format=svg&x-api-key=f1d96bdd-223a-434e-b1c0-af373a59a19e

        if(query != null){
            String[] params = query.split("&");

            for(String param: params){
                int idx = param.indexOf("=");
                if(param.substring(0,idx).equalsIgnoreCase(keyName)){
                    return URLDecoder.decode(param.substring(idx + 1),"UTF-8");
                }
            }
        }
        return "";
    }

    private String getApiKeyfromHttpHeader(String key) {
        // Get apikey from the http header
        //String key = req.getHeader(keyName) == null ? "" : req.getHeader(keyName);

        if(key == null || key.equals("")){
            log.debug("Custom http  header {} not found", keyName);
        }

        return key;
    }

    private void returnErrorMsg(ServletResponse servletResponse, String key) throws IOException {
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String error = "Invalid API KEY";

        AuthorizationProblem problem;

        if(key == null || key.equals("")){
            // header is missing
            problem = AuthorizationProblem.builder()
                    .title("API Header Not Found")
                    .detail("Every API call should pass assigned API key through custom http header or query parameter. Request is missing " + keyName+ "." )
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
