package com.synterra.lens.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class InstrumentDetaiDto {

    private Long instrumentDetailId;

    @jakarta.validation.constraints.Size(max = 100)
    private String requiredInstrumentOrLooseItem;

    @jakarta.validation.constraints.Size(max = 100)
    private String makeOfInstrument;
}