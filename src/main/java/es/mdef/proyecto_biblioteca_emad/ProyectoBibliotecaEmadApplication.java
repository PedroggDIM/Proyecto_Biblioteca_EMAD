package es.mdef.proyecto_biblioteca_emad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProyectoBibliotecaEmadApplication {

	public static final Logger log = LoggerFactory.getLogger(ProyectoBibliotecaEmadApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ProyectoBibliotecaEmadApplication.class, args);
	}

}
