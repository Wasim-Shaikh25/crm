package com.synterra.lens.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiPlanDto {
	
	private Long apiPlanId;

	 private String drfNumber;
	    private String branch;
	    private String salesInquiryItemReferenceNo;

	    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	    private LocalDateTime createdOn;
	    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	    private LocalDateTime updatedOn;
	    private String createdByUser;
	    private String updatedByUser;

	    private String customerName;
	    private String endUser;
	    private boolean costingRequirement;

	    // Existing Seal Support Details
	    private String existingSealSupportMake;
	    private String existingSealSupportApiPlan;
	    private String existingSealSupportCapacity;
	    private String existingSealSupportReferenceApiPlanDrawingNumber;
	    private String existingSealSupportHeatExchangeType;
	    private String existingSealSupportHeatExchangeArea;
	    private String existingSealSupportStandard;

	    // Material of Construction (MOC)
	    private String mocVessel;
	    private String mocCoolingCoil;
	    private String mocPipingAndFitting;
	    private String mocBladder;
	    private String mocStructuralParts;

	    private List<InstrumentDetaiDto> instruments;
	    private String recommendedBufferOrBarrierFluid;
	    private String accessories;
	    private String remarks;

	    // Attachments
	    private String attachmentAvl;
	    private String attachmentSpecification;
	    private String attachmentReferenceMechanicalSealDrawing;
	    
	    private ApiPlanInquiryDto apiPlanInquiryItem;
    
}

