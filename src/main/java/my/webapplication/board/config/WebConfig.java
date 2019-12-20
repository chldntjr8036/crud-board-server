package my.webapplication.board.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${ui.url}")
    private String UI_SERVER_URL;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(UI_SERVER_URL)
                .allowCredentials(false)
                .allowedMethods("PUT", "GET", "POST", "DELETE")
                .maxAge(1800);

    }
}
