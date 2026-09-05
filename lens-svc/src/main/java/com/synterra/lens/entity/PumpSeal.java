package com.synterra.lens.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PumpSeal")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PumpSeal {

	@Id
	@Column(name = "PumpSealId")
	private Long pumpSealId;

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

	@Column(name = "ProposedMechanicalSeal")
	private String proposedMechanicalSeal;

	@Column(name = "SealType")
	private String sealType;

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

	// Fields related to new seal
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

	// Fields related to API flushing plans
	@Column(name = "ApiFlushingPlans")
	private String apiFlushingPlans;

	@Column(name = "ApiBarrierBufferPlans")
	private String apiBarrierBufferPlans;

	@Column(name = "ApiAtmosphericPlans")
	private String apiAtmosphericPlans;

	@Column(name = "ApiCollectionPlans")
	private String apiCollectionPlans;

	// Measurement fields
	@Column(name = "MeasurementTypeOfStuffingBox")
	private String measurementTypeOfStuffingBox;

	@Column(name = "MeasurementShaftOd")
	private String measurementShaftOd;

	@Column(name = "MeasurementStuffingBoxId")
	private String measurementStuffingBoxId;

	@Column(name = "MeasurementStuffingBoxDepth")
	private String measurementStuffingBoxDepth;

	@Column(name = "MeasurementNearestObstruction")
	private String measurementNearestObstruction;

	@Column(name = "MeasurementSpigotDia")
	private String measurementSpigotDia;

	@Column(name = "MeasurementSocketDepth")
	private String measurementSocketDepth;

	@Column(name = "MeasurementShaftSleeveAvailable")
	private String measurementShaftSleeveAvailable;

	@Column(name = "MeasurementSleeveOd")
	private String measurementSleeveOd;

	@Column(name = "MeasurementStuffingBoxThroatDia")
	private String measurementStuffingBoxThroatDia;

	@Column(name = "MeasurementSleeveShoulderLength")
	private String measurementSleeveShoulderLength;

	@Column(name = "MeasurementSleeveExtensionLength")
	private String measurementSleeveExtensionLength;

	@Column(name = "MeasurementShaftHubDistance")
	private String measurementShaftHubDistance;

	@Column(name = "MeasurementNumberOfStuds")
	private String measurementNumberOfStuds;

	@Column(name = "MeasurementStudSize")
	private String measurementStudSize;

	@Column(name = "MeasurementBoltCircleDiameter")
	private String measurementBoltCircleDiameter;

	@Column(name = "MeasurementStartAngle")
	private String measurementStartAngle;

	@Column(name = "MeasurementFlushSize")
	private String measurementFlushSize;

	@Column(name = "MeasurementFlushAngle")
	private String measurementFlushAngle;

	@Column(name = "MeasurementQuenchSize")
	private String measurementQuenchSize;

	@Column(name = "MeasurementQuenchAngle")
	private String measurementQuenchAngle;

	@Column(name = "MeasurementDrainSize")
	private String measurementDrainSize;

	@Column(name = "MeasurementDrainAngle")
	private String measurementDrainAngle;

	@Column(name = "MeasurementStuffingBox")
	private String measurementStuffingBox;

	@Column(name = "OtherDetailsAccessories")
	private String otherDetailsAccessories;

	@Column(name = "OtherDetailsRemarks")
	private String otherDetailsRemarks;

	@Column(name = "AttachmentReferenceMechanicalSealDrawing")
	private String attachmentReferenceMechanicalSealDrawing;

	@Column(name = "AttachmentStuffingBoxDetails")
	private String attachmentStuffingBoxDetails;

}
