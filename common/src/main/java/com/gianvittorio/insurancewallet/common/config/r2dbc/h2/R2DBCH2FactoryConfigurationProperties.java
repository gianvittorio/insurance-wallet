package com.gianvittorio.insurancewallet.common.config.r2dbc.h2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.r2dbc")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class R2DBCH2FactoryConfigurationProperties {

    private String driver;
    private String protocol;
    private String user;
    private String password;
    private String database;
}
