package es.mdef.proyecto_biblioteca_emad.configuration;

import java.util.TimeZone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.annotation.PostConstruct;

// fuente (cors mapping): https://spring.io/guides/gs/rest-service-cors/
@Configuration
public class WebConfig {
	
	@PostConstruct
	private void utc() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {

		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("https://incandescent-klepon-4e2d40.netlify.app");

				registry.addMapping("/**").allowedMethods("POST", "PUT", "GET", "DELETE");
			}
		};
	}

}
