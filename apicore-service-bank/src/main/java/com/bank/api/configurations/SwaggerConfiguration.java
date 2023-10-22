package com.bank.api.configurations;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.servers.Server;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.info.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Configuration
public class SwaggerConfiguration {
    @Autowired
    private ServerProperties serverProperties;
    @Bean
    OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Developers Bank")
                        .description("This API was created based on studies from the Santander Dev Week 2023 bootcamp. It applies RESTful, SOLID and design pattern concepts.")
                        .version("1.0.0")
                        .license(this.getLicense())
                        .contact(this.getContact())
                        ).externalDocs(this.getExternalDocumentation())
                .servers(this.getServers());
    }

    private Contact getContact() {
        return new Contact()
                .name("Rafael Deroncio");
    }

    private License getLicense(){
        return new License()
                .name("License - MIT")
                .url("https://opensource.org/license/mit/")
                ;
    }

    private ExternalDocumentation getExternalDocumentation() {
        return new ExternalDocumentation()
                .description("Contact - Github")
                .url("https://github.com/rafael-deroncio");
    }

    private List<Server> getServers() {

        List<Server> servers = new ArrayList<>();
        String teste = serverProperties.getServlet().getContextPath() == null ? "localhost" : serverProperties.getServlet().getContextPath();
        int serverPort = serverProperties.getPort() == null ? 8080 : serverProperties.getPort();
        servers.add(new Server().url("http://localhost:" + serverPort + "/api/").description("HTTP"));
        servers.add(new Server().url("https://localhost:" + serverPort + "/api").description("HTTPS"));

        return servers;
    }
}