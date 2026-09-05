package com.synterra.lens.entity;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "CustomerDetail")
public class CustomerDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerDetailId", nullable = false)
    private Long customerDetailId;
    
    @Column(name = "CustomerAddress", length = 500)
    private String CustomerAddress;
    
    @Column(name = "AlternateCustomerAddress", length = 500)
    private String alternateCustomerAddress;

    @Column(name = "ContactPerson", length = 100)
    private String contactPerson;

    @Column(name = "Designation", length = 50)
    private String designation;

    @Column(name = "MobileNumber", length = 100)
    private String mobileNumber;
    
    @Column(name = "AlternateMobileNumber", length = 100)
    private String alternateMobileNumber;
    
    @Column(name = "EmailId", length = 100)
    private String emailId;
    
    @Column(name = "AlternateEmailId", length = 100)
    private String alternateemailId;
    
    
    @Column(name = "ECCNo", length = 15)
    private String eccNo;

    @Column(name = "SSTNo", length = 30)
    private String sstNo;

    @Column(name = "CSTNo", length = 30)
    private String cstNo;

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

    @Column(name = "GSTNo", length = 15)
    private String gstNo;

    @Column(name = "IndustryName")
    private String industryName;

    @Column(name = "PANNo", length = 10)
    private String panNo;
    
    @Column(name = "ReferenceDrawingNo", length = 100)
    private String  referenceDrawingNo;

    @JsonIgnoreProperties("customerDetail")
    @ManyToOne
    @JoinColumn(name="CustomerId", nullable=false)
    private Customer customer;
}