package nirmalya.aathithya.webmodule.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ScormResourceConfig implements WebMvcConfigurer {

    @Value("${scrom.url.lms}")
    private String scormExtractPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/scorm/**")
                .addResourceLocations("file:///" + scormExtractPath);
    }
}
