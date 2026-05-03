package com.prod.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebAuthConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        httpSecurity

                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(
                        re->re.requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/posts/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .cors(c->c.disable())
                .csrf(c->c.disable());

        return httpSecurity.build();
    }

}
