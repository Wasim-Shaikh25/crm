package com.synterra.lens.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "Quotation")
public class Quotation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QuotationId")
    private Long quotationId;

    @Column(name = "Category")
    private String category;
    
    @Column(name = "SalesInquiryNumber")
    private String salesInquiryNumber;

    @Column(name = "CustomerEnquiryNo")
    private String customerEnquiryNo;

    @Column(name = "Branch")
    private String branch;

    @Column(name = "EnquiryNo")
    private String enquiryNo;

    @Column(name = "QuotationNo")
    private String quotationNo;

    @Column(name = "QuotationDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime quotationDate;

    @Column(name = "EnquiryDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime enquiryDate;

    @Column(name = "Customer")
    private String customer;

    @Column(name = "CustomerAddress")
    private String customerAddress;

    @Column(name = "KindAttentionTo")
    private String kindAttentionTo;

    @Column(name = "Designation")
    private String designation;

    @Column(name = "DueOn")
    private String dueOn;

    @Column(name = "Transport")
    private String transport;

    @Column(name = "SpecialComments")
    private String specialComments;

    @Column(name = "RevisionNo")
    private String revisionNo;

    @Column(name = "RevisionDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime revisionDate;

    @Column(name = "ValidityWeeks")
    private String validityWeeks;

    @Column(name = "QuotationSource")
    private String quotationSource;

    @Column(name = "DeliverySchedule")
    private String deliverySchedule;

    @Column(name = "Engineer")
    private String engineer;

    @Column(name = "BudgetaryOffer")
    private Boolean budgetaryOffer;

    @Column(name = "PaymentTerms")
    private String paymentTerms;

    @Column(name = "PriceTerm")
    private String priceTerm;

    @Column(name = "StartStatement")
    private String startStatement;

    @Column(name = "EndStatement")
    private String endStatement;

    @Column(name = "Statement")
    private String statement;

    @JsonProperty("pAndF")
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

    @Column(name = "SignatoryName")
    private String name;

    @Column(name = "SignatoryDesignation")
    private String signatoryDesignation;

    @Column(name = "TransactionType")
    private String transactiontype;
    
    @Column(name = "Country")
    private String country;
    
    @Column(name = "Company")
    private String company;
    
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

    @Column(name = "GuaranteeWarranty")
    private boolean guaranteeWarranty;

    @Column(name = "Guarantee")
    private String guarantee;

    @Column(name = "Warranty")
    private String warranty;
    
    @OneToMany(mappedBy = "quotation", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<QuotationItem> items;
}
