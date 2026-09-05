package com.synterra.lens.dto;


import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
@AllArgsConstructor
public class ApiPlanInquiryDto {

    private Long apiPlanInquiryId;

    @jakarta.validation.constraints.Size(max = 100)
    private String apiPlanInquiryReferenceNo;

    @jakarta.validation.constraints.Size(max = 100)
    private String equipmentMake;

    @jakarta.validation.constraints.Size(max = 100)
    private String equipmentModel;

    @jakarta.validation.constraints.Size(max = 100)
    private String equipmentType;

    @jakarta.validation.constraints.Size(max = 100)
    private String arrangement;

    @jakarta.validation.constraints.Size(max = 100)
    private String tagNumber;

    @jakarta.validation.constraints.Size(max = 100)
    private String pumpMOC;

    @jakarta.validation.constraints.Size(max = 100)
    private String drawingNumber;

    @jakarta.validation.constraints.Size(max = 100)
    private String mechanicalSealMake;

    @jakarta.validation.constraints.Size(max = 100)
    private String mechanicalSealSeries;

    @jakarta.validation.constraints.Size(max = 100)
    private String connectionSize;

    @jakarta.validation.constraints.Size(max = 100)
    private String shaftSize;

    @jakarta.validation.constraints.Size(max = 100)
    private String rotation;

    private MeasurementDto mawp;

    private MeasurementDto mawt;

    private MeasurementDto suctionPressurePump;

    private MeasurementDto dischargePressurePump;

    private MeasurementDto boxPressurePump;

    private MeasurementDto vesselPressureAgitator;

    private MeasurementDto operatingTemperature;

    private MeasurementDto maxTemperature;

    @jakarta.validation.constraints.Size(max = 100)
    private String fluid;

    private Double speed;

    private Double viscosity;

    private Double spGravity;

    private Double percentageOfSolid;

    private Double solidSize;

    private Double freezingPoint;

    private Double boilingPoint;

    @jakarta.validation.constraints.Size(max = 100)
    private String leakProofProposalApiPlan;

    private Double capacity;

    @jakarta.validation.constraints.Size(max = 100)
    private String heatExchangeType;

    private Double heatExchangeArea;

    @jakarta.validation.constraints.Size(max = 100)
    private String standard;

    @jakarta.validation.constraints.Size(max = 100)
    private String createdByUser;
    
    @jakarta.validation.constraints.Size(max = 100)
    private String branch;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;

    @jakarta.validation.constraints.Size(max = 100)
    private String updatedByUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedOn;

    private Long salesInquiryId; 
}
