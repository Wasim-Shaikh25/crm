package com.synterra.lens.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PumpInquiryDto {

    private Long pumpInquiryId;
    @jakarta.validation.constraints.Size(max = 100)
    private String pumpInquiryReferenceNo;
    @jakarta.validation.constraints.Size(max = 100)
    private String createdByUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;

    @jakarta.validation.constraints.Size(max = 100)
    private String updatedByUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedOn;

    @jakarta.validation.constraints.Size(max = 100)
    private String branch;

    @jakarta.validation.constraints.Size(max = 100)
    private String make;
    @jakarta.validation.constraints.Size(max = 100)
    private String model;
    @jakarta.validation.constraints.Size(max = 100)
    private String pumpMOC;
    @jakarta.validation.constraints.Size(max = 100)
    private String impellerCasingMOC;
    @jakarta.validation.constraints.Size(max = 100)
    private String shaftMOC;
    @jakarta.validation.constraints.Size(max = 100)
    private String bearingBKT;
    @jakarta.validation.constraints.Size(max = 100)
    private String tagNumber;
    @jakarta.validation.constraints.Size(max = 100)
    private String arrangement;
    @jakarta.validation.constraints.Size(max = 100)
    private String pumpType;
    @jakarta.validation.constraints.Size(max = 100)
    private String stage;
    @jakarta.validation.constraints.Size(max = 100)
    private String casingType;
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

    private MeasurementDto suctionPressure;
    private MeasurementDto dischargePressure;
    private MeasurementDto boxPressure;
    private MeasurementDto totalHead;
    private MeasurementDto pumpingTemperature;
    private MeasurementDto maximumTemperature;

    @jakarta.validation.constraints.Size(max = 100)
    private String directionOfRotation;
    private double speed;
    @jakarta.validation.constraints.Size(max = 100)
    private String fluid;
    @jakarta.validation.constraints.Size(max = 100)
    private String nature;
    private double spGravity;
    private double freezingPoint;
    private double boilingPoint;
    private double viscosity;
    private double percentageOfSolid;
    private double solidSize;
    @jakarta.validation.constraints.Size(max = 100)
    private String specialNote;

    private Long salesInquiryId;  // To keep track of the associated SalesInquiry
}
