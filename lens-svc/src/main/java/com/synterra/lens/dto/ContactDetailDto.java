package com.synterra.lens.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactDetailDto {

    private Long contactDetailId;
    
    @jakarta.validation.constraints.Size(max = 100)
    private String contactDetailReferenceNo;

    @jakarta.validation.constraints.Size(max = 100)
    private String contactPerson;

    @jakarta.validation.constraints.Size(max = 100)
    private String industryType;

    @jakarta.validation.constraints.Size(max = 100)
    private String designation;

    @jakarta.validation.constraints.Size(max = 2000)
    private String customerAddress;
    
    @jakarta.validation.constraints.Size(max = 100)
    private String mobileNumber;
    
    @jakarta.validation.constraints.Email
    @jakarta.validation.constraints.Size(max = 150)
    private String emailId;
    
    @jakarta.validation.constraints.Size(max = 100)
    private String gstNo;

    @jakarta.validation.constraints.Size(max = 100)
    private String panNo;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedOn;
    @jakarta.validation.constraints.Size(max = 100)
    private String createdByUser;
    @jakarta.validation.constraints.Size(max = 100)
    private String updatedByUser;
}
