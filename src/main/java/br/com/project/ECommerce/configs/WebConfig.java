package br.com.project.ECommerce.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${cors.originPatterns}")
    private String cors;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        var allowedCors = cors.split(",");
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins(allowedCors)
                .allowCredentials(true);
    }
}
