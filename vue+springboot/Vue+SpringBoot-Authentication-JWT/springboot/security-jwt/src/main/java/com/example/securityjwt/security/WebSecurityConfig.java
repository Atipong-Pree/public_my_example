package com.example.securityjwt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    Environment env;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.cors().configurationSource(corsConfigurationSource());
        http.authorizeHttpRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/auth").permitAll()
                .antMatchers(HttpMethod.POST,"/api/signup").permitAll()
                .antMatchers("/api/admin").hasRole("ADMIN") //  no has ROLE_ prefix
                .antMatchers("/api/mod").hasAnyRole("MODERATOR","ADMIN")
                .antMatchers("/api/user").hasAnyRole("USER", "MODERATOR", "ADMIN")
                .antMatchers("/api/user/*").hasAnyRole("USER", "MODERATOR", "ADMIN")
                .anyRequest().authenticated()
                .and()
                // add filter for generate access token
                .addFilterAt(new JWTAuthenticationProcessingFilter("/auth",
                                authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class
                );

        http.exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    // response message if user can not access URI

                    HttpStatus httpStatus = HttpStatus.FORBIDDEN;

                    ErrorResponseBody errorResponseBody = new ErrorResponseBody();
                    errorResponseBody.setTimestamp(new Date());
                    errorResponseBody.setStatus(httpStatus);
                    errorResponseBody.setMessage(accessDeniedException.getMessage());

                    ResponseEntity<ErrorResponseBody> responseEntity = new ResponseEntity<>(
                            errorResponseBody,
                            httpStatus
                    );

                    // response message if user login not yet
                    ObjectMapper objectMapper = new ObjectMapper();
                    // serialize object to string json format
                    String valueAsString = objectMapper.writeValueAsString(responseEntity);
                    // set status response
                    response.setStatus(httpStatus.value());
                    // set content type application/json
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    // response message
                    response.getWriter().println(valueAsString);
                })

                .authenticationEntryPoint((request, response, authException) -> {

                    HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

                    ErrorResponseBody errorResponseBody = new ErrorResponseBody();
                    errorResponseBody.setTimestamp(new Date());
                    errorResponseBody.setStatus(httpStatus);
                    errorResponseBody.setMessage(authException.getMessage());

                    ResponseEntity<ErrorResponseBody> responseEntity = new ResponseEntity<>(
                            errorResponseBody,
                            httpStatus
                    );

                    // response message if user login not yet
                    ObjectMapper objectMapper = new ObjectMapper();
                    // serialize object to string json format
                    String valueAsString = objectMapper.writeValueAsString(responseEntity);
                    // set status response
                    response.setStatus(httpStatus.value());
                    // set content type application/json
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    // response message
                    response.getWriter().println(valueAsString);

                });

        return http.build();
    }


    @Bean // lode user from database
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }


    @Bean // set password encoder
    public PasswordEncoder passwordEncoder() {
        String idForEncode = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());
        return new DelegatingPasswordEncoder(idForEncode, encoders);

    }

    @Bean
    public AuthenticationManager authenticationManager() {
        // set  type of authentication
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        // set user details
        dao.setUserDetailsService(userDetailsService());
        //dao.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(dao);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        //configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        String originsDomain = env.getProperty("origins.domain");
        configuration.setAllowedOrigins(Arrays.asList(originsDomain));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","DELETE","PUT","OPTIONS"));
        // set allow request to send head special value * allow all headers
        configuration.addAllowedHeader("*");
        // This allow us to expose the headers  special value * allow all headers to be exposed
        configuration.addExposedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
