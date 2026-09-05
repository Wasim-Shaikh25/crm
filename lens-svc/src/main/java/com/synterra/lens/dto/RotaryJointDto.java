package com.synterra.lens.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RotaryJointDto {

    private Long rotaryJointId;
    @jakarta.validation.constraints.Size(max = 100)
    private String drfNumber;
    @jakarta.validation.constraints.Size(max = 100)
    private String branch;
    @jakarta.validation.constraints.Size(max = 100)
    private String salesInquiryItemReferenceNo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedOn;
    @jakarta.validation.constraints.Size(max = 100)
    private String createdByUser;
    @jakarta.validation.constraints.Size(max = 100)
    private String updatedByUser;
    @jakarta.validation.constraints.Size(max = 150)
    private String customerName;
    @jakarta.validation.constraints.Size(max = 100)
    private String endUser;
    private boolean costingRequirement;
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
    
    private RotaryJointInquiryDto rotaryJointInquiryItem;

}
