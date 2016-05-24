package edu.iis.mto.integrationtest.config;


import edu.iis.mto.integrationtest.utils.ModeUtils;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;

@Configuration //wskazuje, że klasa zawiera konfigurację bean’ów Spring
@ComponentScan("edu.iis.mto.integrationtest.repository") //ustala kontekst poszukiwania komponentów
@Import(value = {PersistenceConfig.class}) //klasa związana z konfiguracją bean’ów, która importujemy
public class ApplicationConfig {

    @Bean
    public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer resolver = new PropertyPlaceholderConfigurer();
        resolver.setLocation(new ClassPathResource(ModeUtils.getMode().getModeName()
                + "-persistence.properties"));
        return resolver;
    }

}
