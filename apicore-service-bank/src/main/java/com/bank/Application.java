package com.bank;

import com.bank.core.configurations.ApplicationConfiguration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default server Url")})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

	@Bean
	public CommandLineRunner initData(ApplicationConfiguration applicationConfiguration) {
		return args -> {
			applicationConfiguration.start();
		};
	}
}
