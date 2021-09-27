package com.gianvittorio.insurancewallet.b2bservice.unit.repository;

import com.gianvittorio.insurancewallet.b2bservice.config.R2DBCConfig;
import com.gianvittorio.insurancewallet.b2bservice.domain.entity.CompanyEntity;
import com.gianvittorio.insurancewallet.b2bservice.domain.entity.CoverageEntity;
import com.gianvittorio.insurancewallet.b2bservice.domain.entity.SegmentEntity;
import com.gianvittorio.insurancewallet.b2bservice.dao.CompanyDAO;
import com.gianvittorio.insurancewallet.b2bservice.dao.impl.CompanyDAOImpl;
import com.gianvittorio.insurancewallet.common.config.r2dbc.h2.R2DBCH2FactoryConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Set;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@EnableAutoConfiguration
@DataR2dbcTest
@ContextConfiguration(classes = {R2DBCConfig.class, CompanyDAOImpl.class})
@Import(R2DBCH2FactoryConfiguration.class)
public class CompanyDAOTest {

    @Autowired
    CompanyDAO companyDAO;

    @Autowired
    R2dbcEntityTemplate entityTemplate;

    @BeforeEach
    public void resetBeforeEach() {

        final DatabaseClient databaseClient = entityTemplate.getDatabaseClient();

        databaseClient
                .sql("DELETE FROM companies")
                .then()
                .then(databaseClient.sql("DELETE FROM segment_coverage").then())
                .then(databaseClient.sql("DELETE FROM coverage").then())
                .then(databaseClient.sql("DELETE FROM segments").then())
                .block();
    }

    @Test
    @DisplayName("Must find all company entities, which number is greater than zero.")
    public void findAllTest() {

        // Given
        final String segmentName = "housing";
        final String segmentDescription = "blah blah";
        final String coverageName = "fire";
        final String coverageDescription = "blah blah";
        final String companyName = "Y";
        final String companyDocument = "000.000.000-03";

        // When and Then
        final Mono<Integer> segmentIdMono = entityTemplate
                .insert(SegmentEntity.class)
                .into("segments")
                .using(
                        SegmentEntity.builder()
                                .name(segmentName)
                                .description(segmentDescription)
                                .build()
                )
                .map(SegmentEntity::getId);

        final Mono<Integer> coverageIdMono = entityTemplate
                .insert(CoverageEntity.class)
                .using(
                        CoverageEntity.builder()
                                .name(coverageName)
                                .description(coverageDescription)
                                .build()
                )
                .map(CoverageEntity::getId);

        segmentIdMono.zipWith(coverageIdMono,
                        (segmentId, coverageId) ->
                        {
                            final Mono<Void> insertSegmentCoverageMono = entityTemplate
                                    .getDatabaseClient()
                                    .sql("INSERT INTO segment_coverage(segment_id, coverage_id) VALUES(:segment_id, :coverage_id)")
                                    .bind("segment_id", segmentId)
                                    .bind("coverage_id", coverageId)
                                    .then();

                            final Mono<Void> insertCompanyMono = entityTemplate
                                    .getDatabaseClient()
                                    .sql("INSERT INTO companies(company_name, company_document, segment_id) VALUES(:company_name, :company_document, :segment_id)")
                                    .bind("company_name", companyName)
                                    .bind("company_document", companyDocument)
                                    .bind("segment_id", segmentId)
                                    .then();

                            final Mono<CompanyEntity> companyEntityMono = insertSegmentCoverageMono
                                    .then(insertCompanyMono)
                                    .thenMany(companyDAO.findAll())
                                    .next();

                            return companyEntityMono;
                        }
                )
                .flatMap(Function.identity())
                .log()
                .as(StepVerifier::create)
                .consumeNextWith(companyEntity -> {
                    assertThat(companyEntity)
                            .isNotNull();
                    assertThat(companyEntity.getId())
                            .isNotNull();
                    assertThat(companyEntity.getName())
                            .isEqualTo(companyName);
                    assertThat(companyEntity.getDocument())
                            .isEqualTo(companyDocument);

                    final SegmentEntity segmentEntity = companyEntity.getSegmentEntity();
                    assertThat(segmentEntity)
                            .isNotNull();
                    assertThat(segmentEntity.getId())
                            .isNotNull();
                    assertThat(segmentEntity.getName())
                            .isEqualTo(segmentName);
                    assertThat(segmentEntity.getDescription())
                            .isEqualTo(segmentDescription);

                    final Set<CoverageEntity> coverageEntities = segmentEntity.getCoverageEntities();
                    assertThat(coverageEntities)
                            .isNotNull();
                    assertThat(coverageEntities)
                            .hasSize(1);

                    final CoverageEntity coverageEntity = coverageEntities.iterator().next();
                    assertThat(coverageEntity.getName())
                            .isEqualTo(coverageName);
                    assertThat(coverageEntity.getDescription())
                            .isEqualTo(coverageDescription);
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Must find company referred to by name.")
    public void findByNameTest() {

        // Given
        final String segmentName = "housing";
        final String segmentDescription = "blah blah";
        final String coverageName = "fire";
        final String coverageDescription = "blah blah";
        final String companyName = "Y";
        final String companyDocument = "000.000.000-03";

        // When and Then
        final Mono<Integer> segmentIdMono = entityTemplate
                .insert(SegmentEntity.class)
                .into("segments")
                .using(
                        SegmentEntity.builder()
                                .name(segmentName)
                                .description(segmentDescription)
                                .build()
                )
                .map(SegmentEntity::getId);

        final Mono<Integer> coverageIdMono = entityTemplate
                .insert(CoverageEntity.class)
                .using(
                        CoverageEntity.builder()
                                .name(coverageName)
                                .description(coverageDescription)
                                .build()
                )
                .map(CoverageEntity::getId);

        segmentIdMono.zipWith(coverageIdMono,
                        (segmentId, coverageId) ->
                        {
                            final Mono<Void> insertSegmentCoverageMono = entityTemplate
                                    .getDatabaseClient()
                                    .sql("INSERT INTO segment_coverage(segment_id, coverage_id) VALUES(:segment_id, :coverage_id)")
                                    .bind("segment_id", segmentId)
                                    .bind("coverage_id", coverageId)
                                    .then();

                            final Mono<Void> insertCompanyMono = entityTemplate
                                    .getDatabaseClient()
                                    .sql("INSERT INTO companies(company_name, company_document, segment_id) VALUES(:company_name, :company_document, :segment_id)")
                                    .bind("company_name", companyName)
                                    .bind("company_document", companyDocument)
                                    .bind("segment_id", segmentId)
                                    .then();

                            final Mono<CompanyEntity> companyEntityMono = insertSegmentCoverageMono
                                    .then(insertCompanyMono)
                                    .then(companyDAO.findByName(companyName));

                            return companyEntityMono;
                        }
                )
                .flatMap(Function.identity())
                .log()
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("Must find company referred to by document.")
    public void findByDocumentTest() {

        // Given
        final String segmentName = "housing";
        final String segmentDescription = "blah blah";
        final String coverageName = "fire";
        final String coverageDescription = "blah blah";
        final String companyName = "Y";
        final String companyDocument = "000.000.000-03";

        // When and Then
        final Mono<Integer> segmentIdMono = entityTemplate
                .insert(SegmentEntity.class)
                .into("segments")
                .using(
                        SegmentEntity.builder()
                                .name(segmentName)
                                .description(segmentDescription)
                                .build()
                )
                .map(SegmentEntity::getId);

        final Mono<Integer> coverageIdMono = entityTemplate
                .insert(CoverageEntity.class)
                .using(
                        CoverageEntity.builder()
                                .name(coverageName)
                                .description(coverageDescription)
                                .build()
                )
                .map(CoverageEntity::getId);

        segmentIdMono.zipWith(coverageIdMono,
                        (segmentId, coverageId) ->
                        {
                            final Mono<Void> insertSegmentCoverageMono = entityTemplate
                                    .getDatabaseClient()
                                    .sql("INSERT INTO segment_coverage(segment_id, coverage_id) VALUES(:segment_id, :coverage_id)")
                                    .bind("segment_id", segmentId)
                                    .bind("coverage_id", coverageId)
                                    .then();

                            final Mono<Void> insertCompanyMono = entityTemplate
                                    .getDatabaseClient()
                                    .sql("INSERT INTO companies(company_name, company_document, segment_id) VALUES(:company_name, :company_document, :segment_id)")
                                    .bind("company_name", companyName)
                                    .bind("company_document", companyDocument)
                                    .bind("segment_id", segmentId)
                                    .then();

                            final Mono<CompanyEntity> companyEntityMono = insertSegmentCoverageMono
                                    .then(insertCompanyMono)
                                    .then(companyDAO.findByDocument(companyDocument));

                            return companyEntityMono;
                        }
                )
                .flatMap(Function.identity())
                .log()
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("Must save company.")
    public void saveCompanyEntityTest() {
        // Given

        // When
        final CompanyEntity companyEntity = CompanyEntity.builder()
                .name("Y")
                .document("000.000.000-03")
                .build();

        final Mono<CompanyEntity> savedCompanyEntityMono = companyDAO.save(companyEntity);

        // Then
        savedCompanyEntityMono
                .log()
                .as(StepVerifier::create)
                .consumeNextWith(savedCompanyEntity -> {
                    assertThat(savedCompanyEntity.getId())
                            .isNotNull();
                    assertThat(savedCompanyEntity.getName())
                            .isEqualTo(companyEntity.getName());
                    assertThat(savedCompanyEntity.getDocument())
                            .isEqualTo(companyEntity.getDocument());
                })
                .verifyComplete();
    }
}
