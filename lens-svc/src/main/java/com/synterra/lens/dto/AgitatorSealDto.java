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
public class AgitatorSealDto {

    private Long agitatorSealId;
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
    private String agitatorMake;
    @jakarta.validation.constraints.Size(max = 100)
    private String agitatorModel;
    @jakarta.validation.constraints.Size(max = 100)
    private String agitatorEntry;
    @jakarta.validation.constraints.Size(max = 100)
    private String agitatorTagNumber;
    @jakarta.validation.constraints.Size(max = 100)
    private String agitatorVesselMoc;
    @jakarta.validation.constraints.Size(max = 100)
    private String proposedMechanicalSeal;

    // Existing Seal Details
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

    // Measurements
    @jakarta.validation.constraints.Size(max = 100)
    private String measurementTypeOfPadPlate;
    @jakarta.validation.constraints.Size(max = 100)
    private String measurementShaftOd;
    @jakarta.validation.constraints.Size(max = 100)
    private String measurementPadPlateId;
    @jakarta.validation.constraints.Size(max = 100)
    private String measurementNearestObstruction;
    @jakarta.validation.constraints.Size(max = 100)
    private String measurementSpigotDia;
    @jakarta.validation.constraints.Size(max = 100)
    private String measurementSocketDepth;
    @jakarta.validation.constraints.Size(max = 100)
    private String measurementShaftDiaD1;
    @jakarta.validation.constraints.Size(max = 100)
    private String measurementShaftDiaD2;
    @jakarta.validation.constraints.Size(max = 100)
    private String measurementShaftStepDistanceL1;
    @jakarta.validation.constraints.Size(max = 100)
    private String measurementDistanceBetweenStepsL2;
    @jakarta.validation.constraints.Size(max = 100)
    private String measurementPadPlateThicknessT;
    @jakarta.validation.constraints.Size(max = 100)
    private String measurementRadiusR;

    @jakarta.validation.constraints.Size(max = 100)
    private String glandBoltingNumberOfStuds;
    @jakarta.validation.constraints.Size(max = 100)
    private String glandBoltingStudSize;
    @jakarta.validation.constraints.Size(max = 100)
    private String glandBoltingBoltCircleDiameter;
    @jakarta.validation.constraints.Size(max = 100)
    private String glandBoltingStartAngle;

    @jakarta.validation.constraints.Size(max = 100)
    private String connectionFlushSize;
    @jakarta.validation.constraints.Size(max = 100)
    private String connectionFlushAngle;
    @jakarta.validation.constraints.Size(max = 100)
    private String connectionQuenchSize;
    @jakarta.validation.constraints.Size(max = 100)
    private String connectionQuenchAngle;
    @jakarta.validation.constraints.Size(max = 100)
    private String connectionDrainSize;
    @jakarta.validation.constraints.Size(max = 100)
    private String connectionDrainAngle;

    @jakarta.validation.constraints.Size(max = 100)
    private String otherDetailsAccessories;
    @jakarta.validation.constraints.Size(max = 2000)
    private String otherDetailsRemarks;
    @jakarta.validation.constraints.Size(max = 100)
    private String attachmentReferenceMechanicalSealDrawing;
    @jakarta.validation.constraints.Size(max = 100)
    private String attachmentPadPlateDetails;
    
    private AgitatorInquiryDto agitatorInquiryItem;


}
