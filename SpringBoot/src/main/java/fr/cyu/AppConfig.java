package fr.cyu;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration of the app.
 */
@Configuration
public class AppConfig implements WebMvcConfigurer {

    /**
     * App middlewares.
     */
    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
    }
}
