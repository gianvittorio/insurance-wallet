package com.gianvittorio.insurancewallet.common.web.model.b2b;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.SortedSet;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String document;

    @NotBlank
    private String segment;

    @NotNull
    private SortedSet<String> coverage;
}
