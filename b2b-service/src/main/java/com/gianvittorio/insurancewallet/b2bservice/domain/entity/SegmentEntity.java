package com.gianvittorio.insurancewallet.b2bservice.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("segments")
public class SegmentEntity {

    @Id
    @Column("segment_id")
    private Integer id;

    @Column("segment_name")
    @NotBlank
    private String name;

    @Column("segment_description")
    private String description;

    @Transient
    Set<CoverageEntity> coverageEntities;
}
