package com.fase1.todo_api;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.postgresql.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

    @Bean
    @ServiceConnection                    //Spring configura el datasource con los datos del contenedor
    PostgreSQLContainer postgresContainer() {
        return new PostgreSQLContainer("postgres:17");  //Misma versión que compose.yaml
    }
}
