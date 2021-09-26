package com.gianvittorio.insurancewallet.common.config.r2dbc.postgresql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.r2dbc")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class R2DBCPostgreSQLConfigurationProperties {

    private String driver;
    private String host;
    private String port;
    private String user;
    private String password;
    private String database;
}
