package com.synterra.lens.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PumpSealDto {

	private Long pumpSealId;

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
	private String proposedMechanicalSeal;

	@jakarta.validation.constraints.Size(max = 100)
	private String sealType;

	@jakarta.validation.constraints.Size(max = 100)
	private String existingSealGA;

	@jakarta.validation.constraints.Size(max = 100)
	private String existingSealSeries;

	@jakarta.validation.constraints.Size(max = 100)
	private String existingSealShaftDia;

	@jakarta.validation.constraints.Size(max = 100)
	private String existingSealSize;

	@jakarta.validation.constraints.Size(max = 100)
	private String existingSealType;

	@jakarta.validation.constraints.Size(max = 100)
	private String existingSealIBFace;

	@jakarta.validation.constraints.Size(max = 100)
	private String existingSealIBElastomer;

	@jakarta.validation.constraints.Size(max = 100)
	private String existingSealIBSpringElement;

	@jakarta.validation.constraints.Size(max = 100)
	private String existingSealIBContactHardware;

	@jakarta.validation.constraints.Size(max = 100)
	private String existingSealIBNonContactHardware;

	@jakarta.validation.constraints.Size(max = 100)
	private String existingSealOBFace;

	@jakarta.validation.constraints.Size(max = 100)
	private String existingSealOBElastomer;

	@jakarta.validation.constraints.Size(max = 100)
	private String existingSealOBSpringElement;

	@jakarta.validation.constraints.Size(max = 100)
	private String existingSealOBContactHardware;

	@jakarta.validation.constraints.Size(max = 100)
	private String existingSealOBNonContactHardware;

	@jakarta.validation.constraints.Size(max = 100)
	private String newSealShaftDia;

	@jakarta.validation.constraints.Size(max = 100)
	private String newSealBoreDia;

	@jakarta.validation.constraints.Size(max = 100)
	private String newSealBoreDepth;

	@jakarta.validation.constraints.Size(max = 100)
	private String newSealNearestObstruction;

	@jakarta.validation.constraints.Size(max = 100)
	private String newSealType;

	@jakarta.validation.constraints.Size(max = 100)
	private String newSealIBFace;

	@jakarta.validation.constraints.Size(max = 100)
	private String newSealIBElastomer;

	@jakarta.validation.constraints.Size(max = 100)
	private String newSealIBSpringElement;

	@jakarta.validation.constraints.Size(max = 100)
	private String newSealIBContactHardware;

	@jakarta.validation.constraints.Size(max = 100)
	private String newSealIBNonContactHardware;

	@jakarta.validation.constraints.Size(max = 100)
	private String newSealOBFace;

	@jakarta.validation.constraints.Size(max = 100)
	private String newSealOBElastomer;

	@jakarta.validation.constraints.Size(max = 100)
	private String newSealOBSpringElement;

	@jakarta.validation.constraints.Size(max = 100)
	private String newSealOBContactHardware;

	@jakarta.validation.constraints.Size(max = 100)
	private String newSealOBNonContactHardware;

	@jakarta.validation.constraints.Size(max = 100)
	private String apiFlushingPlans;

	@jakarta.validation.constraints.Size(max = 100)
	private String apiBarrierBufferPlans;

	@jakarta.validation.constraints.Size(max = 100)
	private String apiAtmosphericPlans;

	@jakarta.validation.constraints.Size(max = 100)
	private String apiCollectionPlans;

	@jakarta.validation.constraints.Size(max = 100)
	private String measurementTypeOfStuffingBox;

	@jakarta.validation.constraints.Size(max = 100)
	private String measurementShaftOd;

	@jakarta.validation.constraints.Size(max = 100)
	private String measurementStuffingBoxId;

	@jakarta.validation.constraints.Size(max = 100)
	private String measurementStuffingBoxDepth;

	@jakarta.validation.constraints.Size(max = 100)
	private String measurementNearestObstruction;

	@jakarta.validation.constraints.Size(max = 100)
	private String measurementSpigotDia;

	@jakarta.validation.constraints.Size(max = 100)
	private String measurementSocketDepth;

	@jakarta.validation.constraints.Size(max = 100)
	private String measurementShaftSleeveAvailable;

	@jakarta.validation.constraints.Size(max = 100)
	private String measurementSleeveOd;

	@jakarta.validation.constraints.Size(max = 100)
	private String measurementStuffingBoxThroatDia;

	@jakarta.validation.constraints.Size(max = 100)
	private String measurementSleeveShoulderLength;

	@jakarta.validation.constraints.Size(max = 100)
	private String measurementSleeveExtensionLength;

	@jakarta.validation.constraints.Size(max = 100)
	private String measurementShaftHubDistance;

	@jakarta.validation.constraints.Size(max = 100)
	private String measurementNumberOfStuds;

	@jakarta.validation.constraints.Size(max = 100)
	private String measurementStudSize;

	@jakarta.validation.constraints.Size(max = 100)
	private String measurementBoltCircleDiameter;

	@jakarta.validation.constraints.Size(max = 100)
	private String measurementStartAngle;

	@jakarta.validation.constraints.Size(max = 100)
	private String measurementFlushSize;

	@jakarta.validation.constraints.Size(max = 100)
	private String measurementFlushAngle;

	@jakarta.validation.constraints.Size(max = 100)
	private String measurementQuenchSize;

	@jakarta.validation.constraints.Size(max = 100)
	private String measurementQuenchAngle;

	@jakarta.validation.constraints.Size(max = 100)
	private String measurementDrainSize;

	@jakarta.validation.constraints.Size(max = 100)
	private String measurementDrainAngle;

	@jakarta.validation.constraints.Size(max = 100)
	private String measurementStuffingBox;

	@jakarta.validation.constraints.Size(max = 100)
	private String otherDetailsAccessories;

	@jakarta.validation.constraints.Size(max = 2000)
	private String otherDetailsRemarks;

	@jakarta.validation.constraints.Size(max = 100)
	private String attachmentReferenceMechanicalSealDrawing;

	@jakarta.validation.constraints.Size(max = 100)
	private String attachmentStuffingBoxDetails;
	
    private PumpInquiryDto pumpInquiryItem;

}
