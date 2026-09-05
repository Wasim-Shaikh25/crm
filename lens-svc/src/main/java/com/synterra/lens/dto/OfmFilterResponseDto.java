package com.synterra.lens.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfmFilterResponseDto {
    private Long ofmId;
    @jakarta.validation.constraints.Size(max = 100)
    private String ofmNo;
    @jakarta.validation.constraints.Size(max = 100)
    private String poNo;
    @jakarta.validation.constraints.Size(max = 100)
    private String category;
    @jakarta.validation.constraints.Size(max = 100)
    private String industry;
    @jakarta.validation.constraints.Size(max = 150)
    private String customer;
    @jakarta.validation.constraints.Size(max = 100)
    private String branch;
    @jakarta.validation.constraints.Size(max = 150)
    private String engineer;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime insertedOn;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdatedOn;
    @jakarta.validation.constraints.Size(max = 100)
    private String insertedByUserId;
    @jakarta.validation.constraints.Size(max = 100)
    private String lastUpdatedByUserId;
}
