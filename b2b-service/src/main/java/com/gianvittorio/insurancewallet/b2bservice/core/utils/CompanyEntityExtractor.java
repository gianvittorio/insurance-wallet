package com.gianvittorio.insurancewallet.b2bservice.core.utils;

import com.gianvittorio.insurancewallet.b2bservice.domain.entity.CompanyEntity;
import com.gianvittorio.insurancewallet.b2bservice.domain.entity.CoverageEntity;
import com.gianvittorio.insurancewallet.b2bservice.domain.entity.SegmentEntity;
import io.r2dbc.spi.Row;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CompanyEntityExtractor implements Function<Mono<List<Row>>, Mono<CompanyEntity>> {

    @Override
    public Mono<CompanyEntity> apply(Mono<List<Row>> resultListMono) {
        return resultListMono.filter(resultList -> resultList.size() > 0)
                .map(resultList -> {
                    final Set<CoverageEntity> coverageEntities = resultList
                            .stream()
                            .map(
                                    row -> CoverageEntity.builder()
                                            .id(row.get("coverage_id", Integer.class))
                                            .name(row.get("coverage_name", String.class))
                                            .description(row.get("coverage_description", String.class))
                                            .build()
                            )
                            .collect(Collectors.toSet());

                    final Row firstRow = resultList.get(0);
                    final SegmentEntity segmentEntity = SegmentEntity.builder()
                            .id(firstRow.get("segment_id", Integer.class))
                            .name(firstRow.get("segment_name", String.class))
                            .description(firstRow.get("segment_description", String.class))
                            .coverageEntities(coverageEntities)
                            .build();

                    final CompanyEntity companyEntity = CompanyEntity.builder()
                            .id(firstRow.get("company_id", Integer.class))
                            .name(firstRow.get("company_name", String.class))
                            .document(firstRow.get("company_document", String.class))
                            .segmentEntity(segmentEntity)
                            .build();

                    return companyEntity;
                });
    }
}
