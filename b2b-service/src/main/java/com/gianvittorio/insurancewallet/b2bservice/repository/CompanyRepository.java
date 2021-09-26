package com.gianvittorio.insurancewallet.b2bservice.repository;

import com.gianvittorio.insurancewallet.b2bservice.domain.entity.CompanyEntity;
import reactor.core.publisher.Mono;

public interface CompanyRepository {

    Mono<CompanyEntity> findById(final Long id);

    Mono<CompanyEntity> findByName(final String name);

    Mono<CompanyEntity> findByDocument(final String document);

    Mono<CompanyEntity> save(final CompanyEntity companyEntity);

    Mono<Void> deleteById(final Long id);

    Mono<Void> deleteByName(final String name);
}
