package com.gianvittorio.insurancewallet.b2bservice.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("companies")
public class CompanyEntity {

    @Id
    @Column("company_id")
    private Long id;

    @Column("company_name")
    @NotBlank
    private String name;

    @Column("company_document")
    @NotBlank
    private String document;

    private SegmentEntity segmentEntity;
}
