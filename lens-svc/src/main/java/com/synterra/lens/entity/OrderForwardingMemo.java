package com.synterra.lens.entity;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
@AllArgsConstructor
@Table(name = "OrderForwardingMemo")
public class OrderForwardingMemo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OfmId")
    private Long ofmId;

    @Column(name = "Branch")
    private String branch;
    
    @Column(name = "QutationNumber")
	private String qutationNumber;


    @Column(name = "OfmNo")
    private String ofmNo;

    @Column(name = "OfmDate")
    private LocalDateTime ofmDate;

    @Column(name = "PoNo")
    private String poNo;

    @Column(name = "PoDate")
    private LocalDateTime poDate;

    @Column(name = "OrderType")
    private String orderType;

    @Column(name = "Category")
    private String category;

    @Column(name = "TransportThrough")
    private String transportThrough;

    @Column(name = "Customer")
    private String customer;

    @Column(name = "CustomerAddress")
    private String customerAddress;

    @Column(name = "KindAttentionTo")
    private String kindAttentionTo;

    @Column(name = "Transport")
    private String transport;

    @Column(name = "DeliveryPeriod")
    private String deliveryPeriod;

    @Column(name = "PreQANo")
    private String preQANo;

    @Column(name = "PreQADate")
    private LocalDateTime preQADate;

    @Column(name = "StatutoryRegulatoryRequirements")
    private boolean statutoryRegulatoryRequirements;

    @Column(name = "SpecialInformation")
    private String specialInformation;

    @Column(name = "Engineer")
    private String engineer;

    @Column(name = "PaymentTerms")
    private String paymentTerms;

    @Column(name = "OaNo")
    private String oaNo;

    @Column(name = "Industry")
    private String industry;

    @Column(name = "ProjectOrder")
    private boolean projectOrder;

    @Column(name = "PenaltyApplicable")
    private boolean penaltyApplicable;

    @Column(name = "PoReceived")
    private boolean poReceived;

    @Column(name = "InvoiceTo")
    private String invoiceTo;

    @Column(name = "QuotationNo")
    private String quotationNo;
 
    @Column(name = "Priority")
    private String priority;
    
    @Column(name = "OfmStatus")
    private String ofmStatus; 

    @Column(name = "ExternalInspection")
    private boolean externalInspection;

    @Column(name = "ExternalInspectionWhere")
    private String externalInspectionWhere;

    @Column(name = "ExternalInspectionByWhom")
    private String externalInspectionByWhom;

    @Column(name = "RawMaterialTC")
    private boolean rawMaterialTC;

    @Column(name = "QCReport")
    private boolean qcReport;

    @Column(name = "TestReport")
    private boolean testReport;

    @Column(name = "GuaranteeCertificate")
    private boolean guaranteeCertificate;

    @Column(name = "FitmentCertificate")
    private boolean fitmentCertificate;

    @Column(name = "ComplianceCertificate")
    private boolean complianceCertificate;

    @Column(name = "ConsigneeName")
    private String consigneeName;

    @Column(name = "ConsigneeAttentionTo")
    private String consigneeAttentionTo;

    @Column(name = "ConsigneeAddress")
    private String consigneeAddress;

    @Column(name = "InvoiceName")
    private String invoiceName;

    @Column(name = "AttentionTo")
    private String attentionTo;

    @Column(name = "InvoiceAddress")
    private String invoiceAddress;

    @Column(name = "EndUserName")
    private String endUserName;

    @Column(name = "EndUserPlace")
    private String endUserPlace;

    @Column(name = "EndUserBranch")
    private String endUserBranch;

    @Column(name = "PAndF")
    private double pAndF;

    @Column(name = "Freight")
    private double freight;

    @Column(name = "Discount")
    private double discount;

    @Column(name = "SGST")
    private double sgst;

    @Column(name = "CGST")
    private double cgst;

    @Column(name = "IGST")
    private double igst;

    @Column(name = "GrandTotal")
    private double grandTotal;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "InsertedOn")
    private LocalDateTime insertedOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LastUpdatedOn")
    private LocalDateTime lastUpdatedOn;

    @Column(name = "InsertedByUserId")
    private String insertedByUserId;

    @Column(name = "LastUpdatedByUserId")
    private String lastUpdatedByUserId;
    
    
    @Column(name = "Insurance")
    private boolean insurance;

    @Column(name = "InsuranceBy")
    private String insuranceBy;

    @Column(name = "InsuranceBorneBy")
    private String insuranceBorneBy;
    
    @Column(name = "Company")
    private String company; 

    @Column(name = "OtherCharges")
    private String otherCharges; 
    
    @Column(name = "OADate")
    private LocalDateTime oaDate;
    
    @Column(name = "QapRequired")
    private boolean qapRequired; 
    
    
    @OneToOne(mappedBy = "orderForwardingMemo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private EndUserDetail endUserDetail;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "orderForwardingMemo")
    private List<OfmItem> ofmItems;
    
    
}
