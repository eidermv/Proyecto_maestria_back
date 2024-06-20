package co.edu.unicauca.gestordocumental.config;

import co.edu.unicauca.gestordocumental.util.ValorProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static co.edu.unicauca.gestordocumental.util.ValorProperties.ValorPropertiesBuilder.aValorProperties;

@Configuration
public class BeanConfig {

    @Bean
    public ValorProperties valorProperties(
            @Value("${url.openkm:http://localhost:8080/OpenKM}") String HOST,
            @Value("${openkm.user}") String USUARIO,
            @Value("${openkm.pass}") String CONTRASENA) {
        return aValorProperties()
                .withHOST(HOST)
                .withUSUARIO(USUARIO)
                .withCONTRASENA(CONTRASENA)
                .build();
    }
}
