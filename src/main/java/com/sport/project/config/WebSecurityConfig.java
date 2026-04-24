package com.sport.project.config;

import com.sport.project.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private static final String SESSION_COOKIE = "JSESSION";

    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(this.userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    @Order(2)
    public SecurityFilterChain mvcFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .formLogin(Customizer.withDefaults())
                .authenticationManager(authenticationManager());

        httpSecurity.authorizeHttpRequests(authz -> {
            authz.requestMatchers("/index", "/login").permitAll();
            authz.requestMatchers("/styles/**").permitAll();
            authz.requestMatchers("/access-denied").permitAll();
            authz.requestMatchers("/admin/**").hasAuthority("moderator");
            authz.requestMatchers("/error").permitAll();
            authz.anyRequest().authenticated(); // для всех остальных страниц нужна авторизация
        });

        httpSecurity.exceptionHandling(handling -> {
            handling.accessDeniedPage("/access-denied");
        });

        httpSecurity.logout(out -> {
            out.logoutUrl("/logout")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies(SESSION_COOKIE)
                    .logoutSuccessUrl("/login");
        });

        return httpSecurity.build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")   // ← ОГРАНИЧИВАЕМ цепочку только API
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
}