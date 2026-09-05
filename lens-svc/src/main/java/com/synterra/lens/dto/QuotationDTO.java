package com.synterra.lens.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuotationDTO {
    private Long quotationId;
    @jakarta.validation.constraints.Size(max = 100)
    private String category;
    @jakarta.validation.constraints.Size(max = 100)
    private String salesInquiryNumber;
    @jakarta.validation.constraints.Size(max = 150)
    private String customerEnquiryNo;
    @jakarta.validation.constraints.Size(max = 100)
    private String branch;
    @jakarta.validation.constraints.Size(max = 100)
    private String enquiryNo;
    @jakarta.validation.constraints.Size(max = 100)
    private String quotationNo;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime quotationDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime enquiryDate;
    @jakarta.validation.constraints.Size(max = 150)
    private String customer;
    @jakarta.validation.constraints.Size(max = 2000)
    private String customerAddress;
    @jakarta.validation.constraints.Size(max = 100)
    private String kindAttentionTo;
    @jakarta.validation.constraints.Size(max = 100)
    private String designation;
    @jakarta.validation.constraints.Size(max = 100)
    private String dueOn;
    @jakarta.validation.constraints.Size(max = 100)
    private String transport;
    @jakarta.validation.constraints.Size(max = 2000)
    private String specialComments;
    @jakarta.validation.constraints.Size(max = 100)
    private String revisionNo;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime revisionDate;
    @jakarta.validation.constraints.Size(max = 100)
    private String validityWeeks;
    @jakarta.validation.constraints.Size(max = 100)
    private String quotationSource;
    @jakarta.validation.constraints.Size(max = 100)
    private String deliverySchedule;
    @jakarta.validation.constraints.Size(max = 150)
    private String engineer;
    private Boolean budgetaryOffer;
    @jakarta.validation.constraints.Size(max = 2000)
    private String paymentTerms;
    @jakarta.validation.constraints.Size(max = 2000)
    private String priceTerm;
    @jakarta.validation.constraints.Size(max = 2000)
    private String startStatement;
    @jakarta.validation.constraints.Size(max = 2000)
    private String endStatement;
    @jakarta.validation.constraints.Size(max = 2000)
    private String statement;
    private double pAndF;
    private double freight;
    private double discount;
    private double sgst;
    private double cgst;
    private double igst;
    private double grandTotal;
    @jakarta.validation.constraints.Size(max = 150)
    private String name;
    @jakarta.validation.constraints.Size(max = 150)
    private String signatoryDesignation;
    @jakarta.validation.constraints.Size(max = 100)
    private String transactiontype;    
    @jakarta.validation.constraints.Size(max = 100)
    private String country;
    @jakarta.validation.constraints.Size(max = 100)
    private String company;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime insertedOn;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdatedOn;
    @jakarta.validation.constraints.Size(max = 100)
    private String insertedByUserId;
    @jakarta.validation.constraints.Size(max = 100)
    private String lastUpdatedByUserId;
    private List<QuotationItemDTO> items;
    private boolean guaranteeWarranty;
    @jakarta.validation.constraints.Size(max = 100)
    private String guarantee;
    @jakarta.validation.constraints.Size(max = 100)
    private String warranty;
}
