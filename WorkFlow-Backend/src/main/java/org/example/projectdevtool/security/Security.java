package org.example.projectdevtool.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class Security {

    private final CustomUserDetailsService customUserDetailsService;
    private final String[] SWAGGER_URLS = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/webjars/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, Filter filter) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(SWAGGER_URLS).permitAll()
                        .requestMatchers("/api/auth/signup",
                                "/api/auth/login").permitAll()
                        .requestMatchers("/api/emp/invite",
                                "/api/emp/get-all",
                                "/api/pro/add-emp",
                                "/api/emp/findBy/**",
                                "/api/pro/get-list",
                                "/api/pro/update-status",
                                "/api/pro/delay/**",
                                "api/pro/list-today",
                                "/api/emp/findById/**",
                                "/api/task/create",
                                "/api/task/assignTo-emp",
                                "/api/pro/emp-list/**",
                                "/api/pro/getById/**",
                                "/api/task/rate/**",
                                "/api/task/moveToBacklog/**").permitAll() // dr, pm
                        .requestMatchers("/api/emp/fill-profile",
                                "/api/pro/findBy/empId/**").hasAnyRole("EMPLOYEE","PM","DIRECTOR")// employee
                        .requestMatchers("/api/task/update-status",
                                "/api/emp/get-profile",
                                "/**",
                                "/api/task/listBy-proId/**",
                                "/api/comment/add",
                                "/api/report/download/**",
                                "/api/report/get/**",
                                "/api/pro/all").permitAll() // dr, pm, em
                        .requestMatchers("/api/pro/create").hasAnyRole("DIRECTOR", "PM")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(m -> m.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.userDetailsService(customUserDetailsService);
        return http.build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
