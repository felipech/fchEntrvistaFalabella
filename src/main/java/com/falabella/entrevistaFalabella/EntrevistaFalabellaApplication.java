package com.falabella.entrevistaFalabella;

import java.util.Arrays;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.falabella.entrevistaFalabella.dao.ProductosRepository;
import com.falabella.entrevistaFalabella.model.Productos;
import com.google.common.base.Predicate;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class EntrevistaFalabellaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntrevistaFalabellaApplication.class, args);
	}
	
	 @Bean
	 public Docket api() { 
	        return new Docket(DocumentationType.SWAGGER_2)
	          .select()                                  
	          .apis(RequestHandlerSelectors.any())
	          .paths(PathSelectors.any())                          
	          .build();                                           
	    }
	



}
