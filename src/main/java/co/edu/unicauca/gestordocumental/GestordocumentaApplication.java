package co.edu.unicauca.gestordocumental;

import co.edu.unicauca.gestordocumental.util.ValorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Field;
import java.util.Arrays;

@SpringBootApplication
@ComponentScan(basePackages = "co.edu.unicauca.gestordocumental.*")
@EnableJpaRepositories
public class GestordocumentaApplication extends SpringBootServletInitializer {

	@Autowired
	private ValorProperties valorProperties;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(GestordocumentaApplication.class);
	}
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		//Test test = new Test();
		//test.getName();
		SpringApplication.run(GestordocumentaApplication.class, args);
	}

}
