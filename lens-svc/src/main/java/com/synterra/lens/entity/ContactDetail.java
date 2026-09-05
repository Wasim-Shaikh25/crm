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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "ContactDetail")
public class ContactDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ContactDetailId", nullable = false)
    private Long contactDetailId;
    
    @Column(name = "ContactDetailReferenceNo", length = 100)
    private String contactDetailReferenceNo;

    @Column(name = "ContactPerson", length = 100)
    private String contactPerson;

    @Column(name = "IndustryType", length = 100)
    private String industryType;

    @Column(name = "Designation", length = 50)
    private String designation;

    @Column(name = "CustomerAddress", length = 500)
    private String customerAddress;
    
    @Column(name = "MobileNumber", length = 100)
    private String mobileNumber;
    
    @Column(name = "EmailId", length = 100)
    private String emailId;
    
    @Column(name = "GSTNo", length = 15)
    private String gstNo;

    @Column(name = "PANNo", length = 10)
    private String panNo;

    @Column(name = "CreatedByUser", length = 50)
    private String createdByUser;

    @Column(name = "CreatedOn")
    private LocalDateTime createdOn;

    @Column(name = "UpdatedByUser", length = 50)
    private String updatedByUser;

    @Column(name = "UpdatedOn")
    private LocalDateTime updatedOn;

    @JsonIgnoreProperties("contactDetail")
    @ManyToOne
    @JoinColumn(name="CustomerId", nullable=false)
    private Customer customer;
}
