package com.synterra.lens.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class MeasurementDto {

    private Long id;

    private double value;

    @jakarta.validation.constraints.Size(max = 100)
    private String unit;
}
