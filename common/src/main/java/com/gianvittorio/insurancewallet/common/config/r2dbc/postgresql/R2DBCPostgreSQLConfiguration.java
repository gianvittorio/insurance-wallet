package com.gianvittorio.insurancewallet.common.config.r2dbc.postgresql;

import io.r2dbc.spi.ConnectionFactoryOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;

@Configuration
@EnableConfigurationProperties(R2DBCPostgreSQLConfigurationProperties.class)
@RequiredArgsConstructor
public class R2DBCPostgreSQLConfiguration {

    private final R2DBCPostgreSQLConfigurationProperties properties;

    @Bean
    public ConnectionFactoryOptions.Builder connectionFactoryOptions() {

        int port;
        try {
            port = Integer.parseInt(properties.getPort());
        } catch (Exception e) {
            port = 5432;
        }

        return ConnectionFactoryOptions.builder()
                .option(DRIVER, properties.getDriver())
                .option(HOST, properties.getHost())
                .option(PORT, port)
                .option(USER, properties.getUser())
                .option(PASSWORD, properties.getPassword())
                .option(DATABASE, properties.getDatabase());
    }
}
