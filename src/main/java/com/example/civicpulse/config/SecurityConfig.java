// SecurityConfig.java
package com.example.civicpulse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("password"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(adminUser);
    }

    // --- NEW: Global CORS Configuration Bean ---
    // This is the standard way to fix CORS errors for PUT, DELETE, etc.
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Allow requests ONLY from our React frontend's origin
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        // Allow all necessary HTTP methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Allow all headers in the request
        configuration.setAllowedHeaders(Arrays.asList("*"));
        // Allow credentials (like the Basic Auth header) to be sent
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Apply this configuration to all routes in our application
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // --- NEW: Enable CORS using our global configuration bean above ---
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                // --- CORRECTED ORDER: Define MOST SPECIFIC public endpoints FIRST ---
                .requestMatchers(HttpMethod.GET, "/api/issues").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/complaints/locations").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/issues").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/complaints").permitAll()
                
                // --- Admin-Only Endpoints ---
                .requestMatchers(HttpMethod.PUT, "/api/issues/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/complaints/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/complaints").hasRole("ADMIN")
                
                // Any other request that is not defined above must be authenticated
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());
        
        return http.build();
    }
}