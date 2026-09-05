package com.synterra.lens.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DrfDataDto {
    @jakarta.validation.constraints.Size(max = 100)
    private String drfNumber;
    @jakarta.validation.constraints.Size(max = 100)
    private String branch;
    @jakarta.validation.constraints.Size(max = 150)
    private String customerName;
    @jakarta.validation.constraints.Size(max = 100)
    private String shaftSize;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedOn;
    @jakarta.validation.constraints.Size(max = 100)
    private String createdBy;
    @jakarta.validation.constraints.Size(max = 100)
    private String lastUpdateBy;
}