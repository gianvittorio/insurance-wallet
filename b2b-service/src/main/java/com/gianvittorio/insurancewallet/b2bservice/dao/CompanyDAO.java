package com.gianvittorio.insurancewallet.b2bservice.dao;

import com.gianvittorio.insurancewallet.b2bservice.domain.entity.CompanyEntity;
import com.gianvittorio.insurancewallet.b2bservice.domain.entity.CoverageEntity;
import com.gianvittorio.insurancewallet.b2bservice.domain.entity.SegmentEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CompanyDAO {

    Flux<CompanyEntity> findAll();

    Mono<CompanyEntity> findById(final Long id);

    Mono<CompanyEntity> findByName(final String name);

    Mono<CompanyEntity> findByDocument(final String document);

    Mono<CompanyEntity> save(final CompanyEntity companyEntity);

    Mono<SegmentEntity> save(final SegmentEntity segmentEntity);

    Mono<CoverageEntity> save(final CoverageEntity coverageEntity);

    Mono<Integer> deleteById(final Long id, final Class<?> entityClass);

    Mono<Integer> deleteByName(final String name, final Class<?> entityClass);
}
