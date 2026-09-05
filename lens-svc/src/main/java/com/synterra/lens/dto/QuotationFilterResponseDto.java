package com.synterra.lens.dto;


import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QuotationFilterResponseDto {

    private Long quotationId;
    @jakarta.validation.constraints.Size(max = 100)
    private String quotationNo;
    @jakarta.validation.constraints.Size(max = 100)
    private String branch;
    @jakarta.validation.constraints.Size(max = 150)
    private String customer;
    @jakarta.validation.constraints.Size(max = 100)
    private String industry;
    @jakarta.validation.constraints.Size(max = 100)
    private String category;
    @jakarta.validation.constraints.Size(max = 150)
    private String engineer;        
    private LocalDateTime insertedOn;
    private LocalDateTime lastUpdatedOn;
    @jakarta.validation.constraints.Size(max = 100)
    private String insertedByUserId;
    @jakarta.validation.constraints.Size(max = 100)
    private String lastUpdatedByUserId;
}
