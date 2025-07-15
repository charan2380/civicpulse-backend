package com.example.civicpulse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Bean #1: Password Encoder
    // Defines the password hashing algorithm (BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean #2: User Details Service
    // Defines the users who can log in. For now, they are stored in memory.
    @Bean
    public UserDetailsService userDetailsService() {
        // Create an admin user with username "admin" and password "password"
        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("password"))
                .roles("ADMIN")
                .build();

        // Create a regular user for potential future use
        UserDetails regularUser = User.builder()
                .username("user")
                .password(passwordEncoder().encode("userpass"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(adminUser, regularUser);
    }

    // Bean #3: Security Filter Chain
    // This is where we define our application's security rules.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Since this is a stateless API, we can disable CSRF protection.
            .csrf(AbstractHttpConfigurer::disable)
            
            // Define authorization rules for HTTP requests
            .authorizeHttpRequests(auth -> auth
                // CRUCIAL FIX: Allow all OPTIONS requests for CORS preflight.
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                
                // Allow anyone to submit new issues or complaints
                .requestMatchers(HttpMethod.POST, "/api/issues", "/api/complaints").permitAll()
                
                // Allow anyone to view the list of civic issues for the map
                .requestMatchers(HttpMethod.GET, "/api/issues").permitAll()
                
                // ONLY allow users with the 'ADMIN' role to view the list of complaints
                .requestMatchers(HttpMethod.GET, "/api/complaints").hasRole("ADMIN")
                
                // All other requests not specified above must be authenticated
                .anyRequest().authenticated()
            )
            // Use HTTP Basic Authentication (sends username/password with each request)
            .httpBasic(Customizer.withDefaults());
        
        return http.build();
    }
}