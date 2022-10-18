package com.tutego.date4u;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableJpaRepositories
@SpringBootApplication
@EnableCaching
@EnableAsync
public class Date4uApplication {
	public static void main( String[] args ) {
		SpringApplication.run( Date4uApplication.class, args );
	}

}
