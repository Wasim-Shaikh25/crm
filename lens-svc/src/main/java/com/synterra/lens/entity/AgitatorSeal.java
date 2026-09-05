package com.synterra.lens.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "AgitatorSeal")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AgitatorSeal {
    
	@Id
	@Column(name = "AgitatorSealId")
	private Long agitatorSealId;
    

    @Column(name = "DrfNumber", nullable = false, unique = true)
    private String drfNumber;

    @Column(name = "Branch", nullable = false)
    private String branch;

    @Column(name = "SalesInquiryItemReferenceNo", nullable = false)
    private String salesInquiryItemReferenceNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreatedOn")
    private LocalDateTime createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UpdatedOn")
    private LocalDateTime updatedOn;

    @Column(name = "CreatedByUser")
    private String createdByUser;

    @Column(name = "UpdatedByUser")
    private String updatedByUser;

    @Column(name = "CustomerName", length = 100)
    private String customerName;

    @Column(name = "EndUser")
    private String endUser;

    @Column(name = "CostingRequirement")
    private boolean costingRequirement;

    @Column(name = "AgitatorMake")
    private String agitatorMake;

    @Column(name = "AgitatorModel")
    private String agitatorModel;

    @Column(name = "AgitatorEntry")
    private String agitatorEntry;

    @Column(name = "AgitatorTagNumber")
    private String agitatorTagNumber;

    @Column(name = "AgitatorVesselMoc")
    private String agitatorVesselMoc;

    @Column(name = "ProposedMechanicalSeal")
    private String proposedMechanicalSeal;

    // Existing Seal Details
    @Column(name = "ExistingSealGA")
    private String existingSealGA;

    @Column(name = "ExistingSealSeries")
    private String existingSealSeries;

    @Column(name = "ExistingSealShaftDia")
    private String existingSealShaftDia;

    @Column(name = "ExistingSealSize")
    private String existingSealSize;

    @Column(name = "ExistingSealType")
    private String existingSealType;

    @Column(name = "ExistingSealIBFace")
    private String existingSealIBFace;

    @Column(name = "ExistingSealIBElastomer")
    private String existingSealIBElastomer;

    @Column(name = "ExistingSealIBSpringElement")
    private String existingSealIBSpringElement;

    @Column(name = "ExistingSealIBContactHardware")
    private String existingSealIBContactHardware;

    @Column(name = "ExistingSealIBNonContactHardware")
    private String existingSealIBNonContactHardware;

    @Column(name = "ExistingSealOBFace")
    private String existingSealOBFace;

    @Column(name = "ExistingSealOBElastomer")
    private String existingSealOBElastomer;

    @Column(name = "ExistingSealOBSpringElement")
    private String existingSealOBSpringElement;

    @Column(name = "ExistingSealOBContactHardware")
    private String existingSealOBContactHardware;

    @Column(name = "ExistingSealOBNonContactHardware")
    private String existingSealOBNonContactHardware;

    // New Seal Details
    @Column(name = "NewSealShaftDia")
    private String newSealShaftDia;

    @Column(name = "NewSealBoreDia")
    private String newSealBoreDia;

    @Column(name = "NewSealBoreDepth")
    private String newSealBoreDepth;

    @Column(name = "NewSealNearestObstruction")
    private String newSealNearestObstruction;

    @Column(name = "NewSealType")
    private String newSealType;

    @Column(name = "NewSealIBFace")
    private String newSealIBFace;

    @Column(name = "NewSealIBElastomer")
    private String newSealIBElastomer;

    @Column(name = "NewSealIBSpringElement")
    private String newSealIBSpringElement;

    @Column(name = "NewSealIBContactHardware")
    private String newSealIBContactHardware;

    @Column(name = "NewSealIBNonContactHardware")
    private String newSealIBNonContactHardware;

    @Column(name = "NewSealOBFace")
    private String newSealOBFace;

    @Column(name = "NewSealOBElastomer")
    private String newSealOBElastomer;

    @Column(name = "NewSealOBSpringElement")
    private String newSealOBSpringElement;

    @Column(name = "NewSealOBContactHardware")
    private String newSealOBContactHardware;

    @Column(name = "NewSealOBNonContactHardware")
    private String newSealOBNonContactHardware;

    // API Plans
    @Column(name = "ApiFlushingPlans")
    private String apiFlushingPlans;

    @Column(name = "ApiBarrierBufferPlans")
    private String apiBarrierBufferPlans;

    @Column(name = "ApiAtmosphericPlans")
    private String apiAtmosphericPlans;

    @Column(name = "ApiCollectionPlans")
    private String apiCollectionPlans;

    // Measurements
    @Column(name = "MeasurementTypeOfPadPlate")
    private String measurementTypeOfPadPlate;

    @Column(name = "MeasurementShaftOd")
    private String measurementShaftOd;

    @Column(name = "MeasurementPadPlateId")
    private String measurementPadPlateId;

    @Column(name = "MeasurementNearestObstruction")
    private String measurementNearestObstruction;

    @Column(name = "MeasurementSpigotDia")
    private String measurementSpigotDia;

    @Column(name = "MeasurementSocketDepth")
    private String measurementSocketDepth;

    @Column(name = "MeasurementShaftDiaD1")
    private String measurementShaftDiaD1;

    @Column(name = "MeasurementShaftDiaD2")
    private String measurementShaftDiaD2;

    @Column(name = "MeasurementShaftStepDistanceL1")
    private String measurementShaftStepDistanceL1;

    @Column(name = "MeasurementDistanceBetweenStepsL2")
    private String measurementDistanceBetweenStepsL2;

    @Column(name = "MeasurementPadPlateThicknessT")
    private String measurementPadPlateThicknessT;

    @Column(name = "MeasurementRadiusR")
    private String measurementRadiusR;

    // Gland Bolting
    @Column(name = "GlandBoltingNumberOfStuds")
    private String glandBoltingNumberOfStuds;

    @Column(name = "GlandBoltingStudSize")
    private String glandBoltingStudSize;

    @Column(name = "GlandBoltingBoltCircleDiameter")
    private String glandBoltingBoltCircleDiameter;

    @Column(name = "GlandBoltingStartAngle")
    private String glandBoltingStartAngle;

    // Connections
    @Column(name = "ConnectionFlushSize")
    private String connectionFlushSize;

    @Column(name = "ConnectionFlushAngle")
    private String connectionFlushAngle;

    @Column(name = "ConnectionQuenchSize")
    private String connectionQuenchSize;

    @Column(name = "ConnectionQuenchAngle")
    private String connectionQuenchAngle;

    @Column(name = "ConnectionDrainSize")
    private String connectionDrainSize;

    @Column(name = "ConnectionDrainAngle")
    private String connectionDrainAngle;

    @Column(name = "OtherDetailsAccessories")
    private String otherDetailsAccessories;

    @Column(name = "OtherDetailsRemarks", length = 150)
    private String otherDetailsRemarks;

    @Column(name = "AttachmentReferenceMechanicalSealDrawing")
    private String attachmentReferenceMechanicalSealDrawing;

    @Column(name = "AttachmentPadPlateDetails")
    private String attachmentPadPlateDetails;
}