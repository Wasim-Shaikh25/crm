package com.synterra.lens.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderForwardingMemoDTO {
	private Long ofmId;
	@jakarta.validation.constraints.NotBlank
	@jakarta.validation.constraints.Size(max = 100)
	private String branch;
	@jakarta.validation.constraints.Size(max = 100)
	private String ofmNo;
	@jakarta.validation.constraints.Size(max = 100)
	private String qutationNumber;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime ofmDate;
	@jakarta.validation.constraints.Size(max = 100)
	private String poNo;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime poDate;
	@jakarta.validation.constraints.Size(max = 100)
	private String orderType;
	@jakarta.validation.constraints.Size(max = 100)
	private String category;
	@jakarta.validation.constraints.Size(max = 100)
	private String transportThrough;
	@jakarta.validation.constraints.NotBlank
	@jakarta.validation.constraints.Size(max = 150)
	private String customer;
	@jakarta.validation.constraints.Size(max = 2000)
	private String customerAddress;
	@jakarta.validation.constraints.Size(max = 100)
	private String kindAttentionTo;
	@jakarta.validation.constraints.Size(max = 100)
	private String transport;
	@jakarta.validation.constraints.Size(max = 100)
	private String deliveryPeriod;
	@jakarta.validation.constraints.Size(max = 100)
	private String preQANo;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime preQADate;
	private boolean statutoryRegulatoryRequirements;
	@jakarta.validation.constraints.Size(max = 100)
	private String specialInformation;
	@jakarta.validation.constraints.Size(max = 150)
	private String engineer;
	@jakarta.validation.constraints.Size(max = 2000)
	private String paymentTerms;
	@jakarta.validation.constraints.Size(max = 100)
	private String oaNo;
	@jakarta.validation.constraints.Size(max = 100)
	private String industry;
	private boolean projectOrder;
	private boolean penaltyApplicable;
	private boolean poReceived;
	@jakarta.validation.constraints.Size(max = 100)
	private String invoiceTo;
	@jakarta.validation.constraints.Size(max = 100)
	private String quotationNo;
	@jakarta.validation.constraints.Size(max = 100)
	private String priority;
	@jakarta.validation.constraints.Size(max = 100)
	private String ofmStatus;
	private boolean externalInspection;
	@jakarta.validation.constraints.Size(max = 100)
	private String externalInspectionWhere;
	@jakarta.validation.constraints.Size(max = 100)
	private String externalInspectionByWhom;
	private boolean rawMaterialTC;
	private boolean qcReport;
	private boolean testReport;
	private boolean guaranteeCertificate;
	private boolean fitmentCertificate;
	private boolean complianceCertificate;
	@jakarta.validation.constraints.Size(max = 150)
	private String consigneeName;
	@jakarta.validation.constraints.Size(max = 2000)
	private String consigneeAddress;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdOn;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updatedOn;
	@jakarta.validation.constraints.Size(max = 100)
	private String createdByUser;
	@jakarta.validation.constraints.Size(max = 100)
	private String updatedByUser;
	private boolean insurance;
	@jakarta.validation.constraints.Size(max = 100)
	private String insuranceBy;
	@jakarta.validation.constraints.Size(max = 100)
	private String insuranceBorneBy;
	@jakarta.validation.constraints.Size(max = 100)
	private String company;
    @jakarta.validation.constraints.Size(max = 100)
    private String otherCharges; 
    private double discount;
	private boolean qapRequired;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime oaDate;
	private EndUserDetailDTO endUserDetail;
	private List<OfmItemDto> OfmItems;
}
