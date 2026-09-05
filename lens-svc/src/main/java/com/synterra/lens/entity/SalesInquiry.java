package com.synterra.lens.entity;

import java.time.LocalDateTime;
import java.util.Set;

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
@Table(name = "SalesInquiry")
public class SalesInquiry {

  
    
	@Id
	@Column(name = "SalesInquiryId")
	private Long salesInquiryId;

    @Column(name = "SalesInquiryReferenceNo", nullable = false, unique = true)
    private String salesInquiryReferenceNo;

    @Column(name = "CustomerReferenceNo", length = 100)
    private String customerReferenceNo;

    @Column(name = "CustomerName", length = 100)
    private String customerName;

    @Column(name = "CustomerAddress", length = 300)
    private String customerAddress;
  
    @Column(name = "ContactPerson", length = 100)
    private String contactPerson;

    @Column(name = "MobileNumber", length = 20)
    private String mobileNumber;

    @Column(name = "SourceOfInquiry", length = 100)
    private String sourceOfInquiry;

    @Column(name = "Industry", length = 100)
    private String industry;

    @Column(name = "Branch", length = 100)
    private String branch;

    @Column(name = "CreatedByUser", length = 50)
    private String createdByUser;

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreatedOn")
    private LocalDateTime createdOn;

    
    @Column(name = "UpdatedByUser", length = 50)
    private String updatedByUser;

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UpdatedOn")
    private LocalDateTime updatedOn;

    @OneToMany(mappedBy = "salesInquiry", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PumpInquiry> pumpInquiries;

    @OneToMany(mappedBy = "salesInquiry", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AgitatorInquiry> agitatorInquiries;

    @OneToMany(mappedBy = "salesInquiry", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ApiPlanInquiry> apiPlanInquiries;

    @OneToMany(mappedBy = "salesInquiry", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RotaryJointInquiry> rotaryJointInquiries;
}
