package com.gianvittorio.insurancewallet.b2bservice.unit.repository;

import com.gianvittorio.insurancewallet.b2bservice.config.R2DBCConfig;
import com.gianvittorio.insurancewallet.b2bservice.repository.CompanyRepository;
import com.gianvittorio.insurancewallet.b2bservice.repository.impl.CompanyRepositoryImpl;
import com.gianvittorio.insurancewallet.common.config.r2dbc.h2.R2DBCH2FactoryConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles("test")
@EnableAutoConfiguration
@DataR2dbcTest
@ContextConfiguration(classes = {R2DBCConfig.class, CompanyRepositoryImpl.class})
@Import(R2DBCH2FactoryConfiguration.class)
public class CompanyRepositoryTest {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    DatabaseClient databaseClient;

    @Test
    @DisplayName("Must find company referred to by Id.")
    public void findByIdTest() {

        // Given
        final long companyId = 1l;
        // When

    }
}
