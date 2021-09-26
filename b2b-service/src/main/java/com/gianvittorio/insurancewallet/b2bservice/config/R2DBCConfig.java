package com.gianvittorio.insurancewallet.b2bservice.config;

import com.gianvittorio.insurancewallet.common.config.r2dbc.R2DBCConfiguration;
import com.gianvittorio.insurancewallet.common.config.r2dbc.h2.R2DBCH2FactoryConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({R2DBCConfiguration.class, R2DBCH2FactoryConfiguration.class})
public class R2DBCConfig {
}
