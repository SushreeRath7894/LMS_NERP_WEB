package nirmalya.aathithya.webmodule.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ScormStaticResourceConfig implements WebMvcConfigurer {

    @Value("${scrom.url.lms}")
    private String scormDiskPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String base = scormDiskPath;
        if (base == null) base = "";
        if (!base.endsWith("/")) base = base + "/";

        String location = "file:" + base;

        registry.addResourceHandler("/scorm/**", "/scrom/**")
                .addResourceLocations(location);
    }
}
