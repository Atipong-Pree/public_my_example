package com.example.securityjwt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.authorizeHttpRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/admin").hasRole("ADMIN") //  no has ROLE_ prefix
                .antMatchers("/mod").hasRole("MODERATOR")
                .antMatchers("/user").hasAnyRole("USER","MODERATOR","ADMIN")
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
        return  new CustomUserDetailsService();
    }


    @Bean // set password encoder
    public PasswordEncoder passwordEncoder(){
        String  idForEncode = "bcrypt";
        Map<String,PasswordEncoder> encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());
        return new DelegatingPasswordEncoder(idForEncode ,encoders);

    }

    @Bean
    public AuthenticationManager authenticationManager(){
        // set  type of authentication
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        // set user details
        dao.setUserDetailsService(userDetailsService());
        return new ProviderManager(dao);
    }





   /* @Bean // set user in memmory
    public UserDetailsService userDetailsService() {
        // The builder will ensure the passwords are encoded before saving in memory
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        UserDetails user = users
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        UserDetails mod = users
                .username("mod")
                .password("password")
                .roles("MODERATOR")
                .build();
        UserDetails admin = users
                .username("admin")
                .password("password")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user,mod, admin);
    }*/


}
