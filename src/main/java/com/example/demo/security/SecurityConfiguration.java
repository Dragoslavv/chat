package com.example.demo.security;

import com.example.demo.security.jwt.JWTConfigurer;
import com.example.demo.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private TokenProvider tokenProvider;
    private CorsFilter corsFilter;
    private JwtAuthenticationEntryPoint authenticationErrorHandler;
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfiguration(
            TokenProvider tokenProvider,
            CorsFilter corsFilter,
            JwtAuthenticationEntryPoint authenticationErrorHandler,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ){
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.authenticationErrorHandler = authenticationErrorHandler;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //we don't need CSRF because our token is invulnerable
                .csrf().disable()

                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(authenticationErrorHandler)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                //create no session
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()

                .antMatchers("/users").permitAll()
                .antMatchers("/users/user/{id}").permitAll()
                .antMatchers("/users/register").hasAuthority("ROLE_ADMIN")
                .antMatchers("/users/update").permitAll()
                .antMatchers("/users/login").permitAll()
                .antMatchers("/users/logout").permitAll()

                .anyRequest().authenticated()

                .and().apply(securityConfigurerAdapter());

    }

    private JWTConfigurer securityConfigurerAdapter(){
        return new JWTConfigurer(tokenProvider);
    }

}
