package tn.smartcity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * SmartCity Backend Application
 * Application de gestion des réclamations citoyennes
 * 
 * @author SmartCity Team
 * @version 1.0.0
 */
@SpringBootApplication
@EnableJpaAuditing
public class SmartCityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartCityApplication.class, args);
        System.out.println("🚀 SmartCity Backend started successfully!");
        System.out.println("📚 Swagger UI: http://localhost:8080/swagger-ui.html");
        System.out.println("🔍 API Docs: http://localhost:8080/api-docs");
    }
}
