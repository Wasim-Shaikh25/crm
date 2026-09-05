package com.synterra.lens.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "ApiPlan")
public class ApiPlan {

	@Id
	@Column(name = "ApiPlanId")
	private Long apiPlanId;

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

	// Existing Seal Support Details
	@Column(name = "ExistingSealSupportMake")
	private String existingSealSupportMake;

	@Column(name = "ExistingSealSupportApiPlan")
	private String existingSealSupportApiPlan;

	@Column(name = "ExistingSealSupportCapacity")
	private String existingSealSupportCapacity;

	@Column(name = "ExistingSealSupportReferenceApiPlanDrawingNumber")
	private String existingSealSupportReferenceApiPlanDrawingNumber;

	@Column(name = "ExistingSealSupportHeatExchangeType")
	private String existingSealSupportHeatExchangeType;

	@Column(name = "ExistingSealSupportHeatExchangeArea")
	private String existingSealSupportHeatExchangeArea;

	@Column(name = "ExistingSealSupportStandard")
	private String existingSealSupportStandard;

	// Material of Construction (MOC)
	@Column(name = "MocVessel")
	private String mocVessel;

	@Column(name = "MocCoolingCoil")
	private String mocCoolingCoil;

	@Column(name = "MocPipingAndFitting")
	private String mocPipingAndFitting;

	@Column(name = "MocBladder")
	private String mocBladder;

	@Column(name = "MocStructuralParts")
	private String mocStructuralParts;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "apiPlan")
	private List<InstrumentDetail> instruments;

	@Column(name = "RecommendedBufferOrBarrierFluid", length = 150)
	private String recommendedBufferOrBarrierFluid;

	@Column(name = "Accessories", length = 150)
	private String accessories;

	@Column(name = "Remarks", length = 150)
	private String remarks;

	@Column(name = "AttachmentAvl")
	private String attachmentAvl;

	@Column(name = "AttachmentSpecification")
	private String attachmentSpecification;

	@Column(name = "AttachmentReferenceMechanicalSealDrawing")
	private String attachmentReferenceMechanicalSealDrawing;
}