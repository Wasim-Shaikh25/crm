package com.synterra.lens.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class RotaryJointInquiryDto {

    private Long rotaryJointInquiryId;
    @jakarta.validation.constraints.Size(max = 100)
    private String rotaryJointInquiryReferenceNo;
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
    private String equipment;
    @jakarta.validation.constraints.Size(max = 100)
    private String make;
    @jakarta.validation.constraints.Size(max = 100)
    private String model;
    @jakarta.validation.constraints.Size(max = 100)
    private String fluid;
    private Double operatingTemperature;
    @jakarta.validation.constraints.Size(max = 100)
    private String operatingTemperatureUnit;
    private Double flowRate;
    private Double speed;
    private Double operatingPressure;
    @jakarta.validation.constraints.Size(max = 100)
    private String operatingPressureUnit;
    @jakarta.validation.constraints.Size(max = 100)
    private String existingRotaryJointMake;
    @jakarta.validation.constraints.Size(max = 100)
    private String existingRotaryJointModelType;
    @jakarta.validation.constraints.Size(max = 100)
    private String existingRotaryJointConnectionSize;
    @jakarta.validation.constraints.Size(max = 100)
    private String existingRotaryJointConnectionType;
    @jakarta.validation.constraints.Size(max = 100)
    private String jointType;
    @jakarta.validation.constraints.Size(max = 100)
    private String proposedRotaryJointMake;
    @jakarta.validation.constraints.Size(max = 100)
    private String proposedRotaryJointModelType;
    @jakarta.validation.constraints.Size(max = 100)
    private String inletConnectionSize;
    @jakarta.validation.constraints.Size(max = 100)
    private String outletConnectionSize;
    @jakarta.validation.constraints.Size(max = 100)
    private String connectionType;
    @jakarta.validation.constraints.Size(max = 100)
    private String handing;
    @jakarta.validation.constraints.Size(max = 100)
    private String inletFlangedSize;
    @jakarta.validation.constraints.Size(max = 100)
    private String outletFlangedSize;
    @jakarta.validation.constraints.Size(max = 100)
    private String referenceDrawing;
    private Long salesInquiryId; 
	@jakarta.validation.constraints.Size(max = 150)
	private String fileName;

}
