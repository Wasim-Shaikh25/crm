package com.synterra.lens.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerDetailDto {
    private Long customerDetailId;
    @jakarta.validation.constraints.Size(max = 2000)
    private String CustomerAddress;
    @jakarta.validation.constraints.Size(max = 2000)
    private String alternateCustomerAddress;
    @jakarta.validation.constraints.Size(max = 100)
    private String contactPerson;
    @jakarta.validation.constraints.Size(max = 100)
    private String designation;
    @jakarta.validation.constraints.Size(max = 100)
    private String mobileNumber;
    @jakarta.validation.constraints.Size(max = 100)
    private String alternateMobileNumber;
    @jakarta.validation.constraints.Email
    @jakarta.validation.constraints.Size(max = 150)
    private String emailId;
    @jakarta.validation.constraints.Email
    @jakarta.validation.constraints.Size(max = 150)
    private String alternateemailId;
    @jakarta.validation.constraints.Size(max = 100)
    private String eccNo;
    @jakarta.validation.constraints.Size(max = 100)
    private String sstNo;
    @jakarta.validation.constraints.Size(max = 100)
    private String cstNo;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime insertedOn;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdatedOn;
    @jakarta.validation.constraints.Size(max = 100)
    private String insertedByUserId;
    @jakarta.validation.constraints.Size(max = 100)
    private String lastUpdatedByUserId;
    @jakarta.validation.constraints.Size(max = 100)
    private String gstNo;
    @jakarta.validation.constraints.Size(max = 150)
    private String industryName;
    @jakarta.validation.constraints.Size(max = 100)
    private String panNo;
    private String  referenceDrawingNo;

    
}

