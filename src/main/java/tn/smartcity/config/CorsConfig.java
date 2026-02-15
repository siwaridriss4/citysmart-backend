package tn.smartcity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // ✅ AUTORISER LES ORIGINES (Flutter sur différents environnements)
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:3000",           // Web local
            "http://localhost:8081",           // Flutter Web
            "http://10.0.2.2:8081",           // Android Emulator
            "http://10.255.148.204:8080",     // Votre IP
            "http://127.0.0.1:8080"           // Localhost
        ));
        
        // ✅ AUTORISER TOUTES LES MÉTHODES HTTP
        configuration.setAllowedMethods(Arrays.asList(
            "GET", 
            "POST", 
            "PUT", 
            "DELETE", 
            "OPTIONS", 
            "PATCH"
        ));
        
        // ✅ AUTORISER TOUS LES HEADERS
        configuration.setAllowedHeaders(List.of("*"));
        
        // ✅ PERMETTRE LES CREDENTIALS (cookies, authorization headers)
        configuration.setAllowCredentials(true);
        
        // ✅ TEMPS DE CACHE DES PREFLIGHT REQUESTS
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}