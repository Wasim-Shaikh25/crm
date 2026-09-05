package com.synterra.lens.entity;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "Customer")
public class Customer {  
    @Id
    @Column(name = "CustomerId", nullable = false)
    private int customerId;
 
    @Column(name = "CustomerReferenceNumber", unique=true, nullable = false)
    private String customerReferenceNumber;
    
    @Column(name = "Branch", nullable = false)
    private String branch;
    
    @Column(name = "CustomerName", length = 100)
    private String customerName;
    
    @Column(name = "VendorCode", length = 100)
    private String vendorCode;


    @OneToMany(mappedBy = "customer", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<ContactDetail> contactDetail;
    
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
    

}