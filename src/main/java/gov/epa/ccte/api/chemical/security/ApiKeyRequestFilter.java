package gov.epa.ccte.api.chemical.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Profile("apikey")
@Component
public class ApiKeyRequestFilter extends GenericFilterBean {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String path = req.getRequestURI();

//        if(path.startsWith("/api") == false){
//            filterChain.doFilter(servletRequest, servletResponse);
//            return;
//        }

        String key = req.getHeader("x-api-key") == null ? "" : req.getHeader("x-api-key");
        log.debug("Trying x-api-key: {}", key );

        // In case user is provided api key through requrest parameter api-key
        if(key == null || key.equals("")){
            String queryString = req.getQueryString();
            if(StringUtils.isNoneEmpty(queryString)){
                queryString = URLDecoder.decode(queryString, StandardCharsets.UTF_8.toString());
                int pos = StringUtils.indexOfIgnoreCase(queryString, "api_key=");
                String keyValue = StringUtils.substring(queryString, pos+8);
                key = StringUtils.substringBefore(keyValue,"&");
                // key = StringUtils.substringBetween(queryString, "api_key=","&");
            }
        }

        if(key != null && key.equals("ccte-api-1")){
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            HttpServletResponse resp = (HttpServletResponse) servletResponse;
            String error = "Invalid API KEY";

            resp.reset();
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            servletResponse.setContentLength(error .length());
            servletResponse.getWriter().write(error);
        }
    }
}
