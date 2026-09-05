package com.synterra.lens.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReferenceDto {
    private String referenceName;
    private String referenceValue;

    public ReferenceDto(String referenceName, String referenceValue) {
        this.referenceName = referenceName;
        this.referenceValue = referenceValue;
    }
}
