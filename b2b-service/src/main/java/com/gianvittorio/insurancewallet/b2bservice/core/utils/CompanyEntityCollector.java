package com.gianvittorio.insurancewallet.b2bservice.core.utils;

import com.gianvittorio.insurancewallet.b2bservice.domain.entity.CompanyEntity;
import com.gianvittorio.insurancewallet.b2bservice.domain.entity.CoverageEntity;
import com.gianvittorio.insurancewallet.b2bservice.domain.entity.SegmentEntity;
import com.google.common.collect.Sets;
import io.r2dbc.spi.Row;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class CompanyEntityCollector implements Collector<Row, CompanyEntity, CompanyEntity> {

    @Override
    public Supplier<CompanyEntity> supplier() {
        return () -> CompanyEntity.builder()
                .segmentEntity(
                        SegmentEntity.builder()
                                .coverageEntities(new HashSet<>())
                                .build()
                )
                .build();
    }

    @Override
    public BiConsumer<CompanyEntity, Row> accumulator() {
        return ((companyEntity, row) -> {
            companyEntity.setId(row.get("company_id", Integer.class));
            companyEntity.setName(row.get("company_name", String.class));
            companyEntity.setDocument(row.get("company_document", String.class));

            final SegmentEntity segmentEntity = companyEntity.getSegmentEntity();
            segmentEntity.setId(row.get("segment_id", Integer.class));
            segmentEntity.setName(row.get("segment_name", String.class));
            segmentEntity.setDescription(row.get("segment_description", String.class));

            final CoverageEntity coverageEntity = CoverageEntity.builder()
                    .id(row.get("coverage_id", Integer.class))
                    .name(row.get("coverage_name", String.class))
                    .description(row.get("coverage_description", String.class))
                    .build();

            segmentEntity.getCoverageEntities()
                    .add(coverageEntity);
        });
    }

    @Override
    public BinaryOperator<CompanyEntity> combiner() {
        return null;
    }

    @Override
    public Function<CompanyEntity, CompanyEntity> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Sets.immutableEnumSet(Characteristics.UNORDERED);
    }
}
