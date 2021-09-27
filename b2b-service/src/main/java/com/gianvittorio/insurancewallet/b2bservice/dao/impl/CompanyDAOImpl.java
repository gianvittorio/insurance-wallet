package com.gianvittorio.insurancewallet.b2bservice.dao.impl;

import com.gianvittorio.insurancewallet.b2bservice.core.utils.CompanyEntityCollector;
import com.gianvittorio.insurancewallet.b2bservice.core.utils.CompanyEntityExtractor;
import com.gianvittorio.insurancewallet.b2bservice.domain.entity.CompanyEntity;
import com.gianvittorio.insurancewallet.b2bservice.dao.CompanyDAO;
import com.gianvittorio.insurancewallet.b2bservice.domain.entity.CoverageEntity;
import com.gianvittorio.insurancewallet.b2bservice.domain.entity.SegmentEntity;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.data.relational.core.query.Criteria.where;

@Repository
public class CompanyDAOImpl implements CompanyDAO {

    public static final String FIND_ALL_COMPANIES = "select * from companies as cc inner join segments as s on cc.segment_id = s.segment_id inner join " +
            "segment_coverage as sc on s.segment_id = sc.segment_id inner join coverage as c on sc.coverage_id = c.coverage_id ";

    public static final String FIND_COMPANY_BY_ID = "select * from companies as cc inner join segments as s on cc.segment_id = s.segment_id inner join " +
            "segment_coverage as sc on s.segment_id = sc.segment_id inner join coverage as c on sc.coverage_id = c.coverage_id " +
            "where cc.company_id = :id";

    public static final String FIND_COMPANY_BY_NAME = "select * from companies as cc inner join segments as s on cc.segment_id = s.segment_id inner join " +
            "segment_coverage as sc on s.segment_id = sc.segment_id inner join coverage as c on sc.coverage_id = c.coverage_id " +
            "where cc.company_name = :name";

    public static final String FIND_COMPANY_BY_DOCUMENT = "select * from companies as cc inner join segments as s on cc.segment_id = s.segment_id inner join " +
            "segment_coverage as sc on s.segment_id = sc.segment_id inner join coverage as c on sc.coverage_id = c.coverage_id " +
            "where cc.company_document = :document";

    private R2dbcEntityTemplate entityTemplate;

    @Autowired
    public CompanyDAOImpl(final ConnectionFactory connectionFactory) {
        this.entityTemplate = new R2dbcEntityTemplate(connectionFactory);
    }

    @Override
    public Flux<CompanyEntity> findAll() {

        return entityTemplate.getDatabaseClient()
                .sql(FIND_ALL_COMPANIES)
                .map(Function.identity())
                .all()
                .collectList()
                .filter(resultList -> resultList.size() > 0)
                .flatMapMany(resultList -> {
                    final Collection<CompanyEntity> companyEntities = resultList.stream()
                            .collect(
                                    Collectors.groupingBy(
                                            row -> row.get("company_id", Integer.class),
                                            new CompanyEntityCollector()
                                    )
                            ).values();

                    return Flux.fromIterable(companyEntities);
                });
    }

    @Override
    public Mono<CompanyEntity> findById(final Long id) {
        return entityTemplate.getDatabaseClient()
                .sql(FIND_COMPANY_BY_ID)
                .bind("id", id)
                .map(Function.identity())
                .all()
                .collectList()
                .transform(new CompanyEntityExtractor());
    }

    @Override
    public Mono<CompanyEntity> findByName(final String name) {
        return entityTemplate.getDatabaseClient()
                .sql(FIND_COMPANY_BY_NAME)
                .bind("name", name)
                .map(Function.identity())
                .all()
                .collectList()
                .transform(new CompanyEntityExtractor());
    }

    @Override
    public Mono<CompanyEntity> findByDocument(final String document) {
        return entityTemplate.getDatabaseClient()
                .sql(FIND_COMPANY_BY_DOCUMENT)
                .bind("document", document)
                .map(Function.identity())
                .all()
                .collectList()
                .transform(new CompanyEntityExtractor());
    }

    @Override
    public Mono<CompanyEntity> save(final CompanyEntity companyEntity) {
        return entityTemplate.insert(CompanyEntity.class)
                .using(companyEntity);
    }

    @Override
    public Mono<SegmentEntity> save(SegmentEntity segmentEntity) {
        return entityTemplate.insert(SegmentEntity.class)
                .using(segmentEntity);
    }

    @Override
    public Mono<CoverageEntity> save(CoverageEntity coverageEntity) {
        return entityTemplate.insert(CoverageEntity.class)
                .using(coverageEntity);
    }

    @Override
    public Mono<Integer> deleteById(final Long id, Class<?> entityClass) {
        return entityTemplate.delete(
                Query.query(where("company_id").is(id)), entityClass
        );
    }

    @Override
    public Mono<Integer> deleteByName(final String name, Class<?> entityClass) {
        return entityTemplate.delete(
                Query.query(where("company_name").is(name)), entityClass
        );
    }
}
