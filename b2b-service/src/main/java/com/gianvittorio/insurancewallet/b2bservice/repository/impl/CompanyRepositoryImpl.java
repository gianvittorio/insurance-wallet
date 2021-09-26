package com.gianvittorio.insurancewallet.b2bservice.repository.impl;

import com.gianvittorio.insurancewallet.b2bservice.domain.entity.CompanyEntity;
import com.gianvittorio.insurancewallet.b2bservice.domain.entity.CoverageEntity;
import com.gianvittorio.insurancewallet.b2bservice.domain.entity.SegmentEntity;
import com.gianvittorio.insurancewallet.b2bservice.repository.CompanyRepository;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.TreeSet;

@Repository
public class CompanyRepositoryImpl implements CompanyRepository {

    private R2dbcEntityTemplate template;

    @Autowired
    public CompanyRepositoryImpl(final ConnectionFactory connectionFactory) {
        this.template = new R2dbcEntityTemplate(connectionFactory);
    }

    @Override
    public Mono<CompanyEntity> findById(Long id) {
        return template.getDatabaseClient()
                .sql("select * from companies as cc inner join segments as s on cc.segment_id = s.segment_id " +
                        "inner join segment_coverage as sc on s.segment_id = sc.segment_id inner join " +
                        "coverage as c on sc.coverage_id = c.coverage_id where cc.company_id = :id")
                .bind("id", id)
                .fetch()
                .all()
                .bufferUntilChanged(result -> result.get("company_id"))
                .map(list -> {
                    final Map<String, Object> firstRow = list.get(0);

                    final CompanyEntity.CompanyEntityBuilder companyEntityBuilder = CompanyEntity.builder()
                            .id(Long.class.cast(firstRow.get("company_id")))
                            .name(String.valueOf(firstRow.get("company_name")))
                            .document(String.valueOf(firstRow.get("company_document")));

                    final TreeSet<CoverageEntity> coverageEntities = new TreeSet<>();
                    list.forEach(map -> {
                        final CoverageEntity coverageEntity = CoverageEntity.builder()
                                .id(Long.class.cast(map.get("coverage_id")))
                                .name(String.valueOf(map.get("coverage_name")))
                                .description(String.valueOf(map.get("coverage_description")))
                                .build();
                        coverageEntities.add(coverageEntity);
                    });

                    final SegmentEntity segmentEntity = SegmentEntity.builder()
                            .id(Long.class.cast(firstRow.get("segment_id")))
                            .name(String.valueOf(firstRow.get("segment_name")))
                            .description(String.valueOf(firstRow.get("segment_description")))
                            .coverageEntities(coverageEntities)
                            .build();


                    return companyEntityBuilder.segmentEntity(segmentEntity)
                            .build();
                })
                .next();
    }

    @Override
    public Mono<CompanyEntity> findByName(String name) {
        return null;
    }

    @Override
    public Mono<CompanyEntity> findByDocument(String document) {
        return null;
    }

    @Override
    public Mono<CompanyEntity> save(CompanyEntity companyEntity) {
        return null;
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return null;
    }

    @Override
    public Mono<Void> deleteByName(String name) {
        return null;
    }
}
