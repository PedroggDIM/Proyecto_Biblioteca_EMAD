package es.mdef.proyecto_biblioteca_emad.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig {
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
