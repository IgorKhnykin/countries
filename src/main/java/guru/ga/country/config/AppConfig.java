package guru.ga.country.config;

import guru.ga.country.service.CountriesErrorAttributes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${api.version}")
    public String apiVersion;

    @Bean
    public ErrorAttributes errorAttributes() {
        return new CountriesErrorAttributes(apiVersion);
    }

}
