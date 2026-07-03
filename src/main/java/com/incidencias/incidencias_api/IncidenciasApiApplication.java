package com.incidencias.incidencias_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class IncidenciasApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(IncidenciasApiApplication.class, args);
	}

}
