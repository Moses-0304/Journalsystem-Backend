package se.kth.journalsystem.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000", "http://localhost:3001") // Tillåt flera origins
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Tillåt alla relevanta HTTP-metoder
                        .allowedHeaders("*") // Tillåt alla headers
                        .allowCredentials(true); // Tillåt cookies och autentisering
            }
        };
    }
}
