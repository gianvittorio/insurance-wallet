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
@Table("coverage")
public class CoverageEntity {

    @Id
    @Column("coverage_id")
    private Integer id;

    @Column("coverage_name")
    @NotBlank
    private String name;

    @Column("coverage_description")
    private String description;
}
