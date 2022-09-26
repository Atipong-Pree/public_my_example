package com.example.securityjwt.security;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;


public class JWTAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    Logger logger = LoggerFactory.getLogger(JWTAuthenticationProcessingFilter.class);

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    JWTService jwtService;

    protected JWTAuthenticationProcessingFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(defaultFilterProcessesUrl, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        try {
            logger.info("attemptAuthentication");
            if (!request.getMethod().equals("POST")) {
                throw new HttpRequestMethodNotSupportedException(request.getMethod());
            }
            // read jason raw data from request
            ObjectMapper mapper = new ObjectMapper();
            UserCredential userCredential = mapper.readValue(request.getInputStream(), UserCredential.class);
            // authenticated with username password from request
            Authentication authentication = getAuthenticationManager()
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    userCredential.getUsername(),
                                    userCredential.getPassword()
                            )
                    );
            return authentication;

        }catch (BadCredentialsException ex) {
            logger.error(ex.getMessage());
            throw ex;

        }catch (Exception ex) {
            logger.error(ex.getMessage());
            throw ex;
        }


    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        logger.info("SuccessfulAuthentication");

        try {
            // create access token
            String accessToken = jwtService.generateJwtToken(authResult.getName(), authResult);
            // create detail for response
            AuthResponeBody authResponeBody = new AuthResponeBody();
            authResponeBody.setTimestamp(new Date());
            authResponeBody.setStatus(HttpStatus.OK);
            authResponeBody.setMessage("authentication successful");
            authResponeBody.setAccessToken(accessToken);
            // create body for response data
            ResponseEntity<AuthResponeBody> responseEntity = new ResponseEntity<>(authResponeBody, authResponeBody.getStatus());
            // set access token to header key Authorization
            response.setHeader(jwtService.HEADER_STRING, accessToken);
            // set response type json format
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            // response to body json format
            ObjectMapper mapper = new ObjectMapper();
            // serialize object to string json format
            String valueAsString = mapper.writeValueAsString(responseEntity);
            // response data
            response.getWriter().println(valueAsString);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        logger.info("UnsuccessfulAuthentication : "+failed.getMessage());

        ErrorResponseBody errorResponseBody = new ErrorResponseBody(new Date(), HttpStatus.UNAUTHORIZED, failed.getMessage());
        ResponseEntity<ErrorResponseBody> responseEntity = new ResponseEntity<>(errorResponseBody, errorResponseBody.getStatus());
        // set content type of application/json
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // set status
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        // set object  as string to json format
        ObjectMapper mapper = new ObjectMapper();
        String serializeResponseEntity = mapper.writeValueAsString(responseEntity);
        // response
        response.getWriter().println(serializeResponseEntity);

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        try {

            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String token = httpServletRequest.getHeader(jwtService.HEADER_STRING);
            if (token != null) {
                Authentication authentication = jwtService.getAuthenticationFromToken(token);
                if (authentication != null) {
                    SecurityContext context = SecurityContextHolder.getContext();
                    context.setAuthentication(authentication);
                }
            }
            super.doFilter(request, response, chain);

        } catch (Exception ex) {

            logger.error(ex.getMessage());
            ErrorResponseBody errorResponseBody = new ErrorResponseBody(new Date(), HttpStatus.UNAUTHORIZED, ex.getMessage());
            ResponseEntity<ErrorResponseBody> responseEntity = new ResponseEntity<>(errorResponseBody, errorResponseBody.getStatus());
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ObjectMapper mapper = new ObjectMapper();
            String valueAsString = mapper.writeValueAsString(responseEntity);
            response.getWriter().println(valueAsString);

        }
    }

}
