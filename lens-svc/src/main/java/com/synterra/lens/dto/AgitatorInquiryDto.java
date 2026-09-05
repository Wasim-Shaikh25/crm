package com.synterra.lens.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class AgitatorInquiryDto {

    private Long agitatorInquiryId;
    @jakarta.validation.constraints.Size(max = 100)
    private String agitatorInquiryReferenceNo;
    @jakarta.validation.constraints.Size(max = 100)
    private String series;
    @jakarta.validation.constraints.Size(max = 100)
    private String performance;
    @jakarta.validation.constraints.Size(max = 100)
    private String sealArrangement;
    @jakarta.validation.constraints.Size(max = 100)
    private String existingSealMake;
    @jakarta.validation.constraints.Size(max = 100)
    private String existingSealSize;
    @jakarta.validation.constraints.Size(max = 100)
    private String existingSealMOC;
    @jakarta.validation.constraints.Size(max = 100)
    private String existingSealApiPlan;
    private Double vesselPressureOperating;
    @jakarta.validation.constraints.Size(max = 100)
    private String vesselPressureOperatingUnit;
    private Double vesselPressureDesign;
    @jakarta.validation.constraints.Size(max = 100)
    private String vesselPressureDesignUnit;
    @jakarta.validation.constraints.Size(max = 100)
    private String directionOfRotation;
    private Double speed;
    @jakarta.validation.constraints.Size(max = 100)
    private String fluid;
    @jakarta.validation.constraints.Size(max = 100)
    private String nature;
    @jakarta.validation.constraints.Size(max = 100)
    private String branch;

    
    private MeasurementDto pumpingTemperature;  
    private MeasurementDto maximumTemperature; 
    
    private Double spGravity;
    private Double freezingPoint;
    private Double boilingPoint;
    private Double viscosity;
    private Double percentageOfSolid;
    private Double solidSize;
    @jakarta.validation.constraints.Size(max = 100)
    private String specialNote;
    @jakarta.validation.constraints.Size(max = 100)
    private String createdByUser;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    
    @jakarta.validation.constraints.Size(max = 100)
    private String updatedByUser;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedOn;

    private Long salesInquiryId;  
}
