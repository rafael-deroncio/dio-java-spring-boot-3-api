package com.bank;

import com.bank.core.configurations.StartApplicationConfiguration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import org.springframework.core.env.Environment;

import java.util.Objects;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default server Url")})
public class Application {

	private final Environment environment; // Injete o Environment

	public Application(Environment environment) {
		this.environment = environment;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

	@Bean
	public CommandLineRunner initData(StartApplicationConfiguration startApplicationConfiguration) {
		return args -> {
			if (Objects.equals(environment.getProperty("SPRING_PROFILES_ACTIVE"), "dev")) {
				startApplicationConfiguration.start();
			}
		};
	}
}
