package com.synterra.lens.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SalesInquiryDto {

	private Long salesInquiryId;
    @jakarta.validation.constraints.Size(max = 100)
    private String salesInquiryReferenceNo;
    @jakarta.validation.constraints.Size(max = 150)
    private String customerReferenceNo;
    @jakarta.validation.constraints.NotBlank
    @jakarta.validation.constraints.Size(max = 150)
    private String customerName;
    @jakarta.validation.constraints.Size(max = 2000)
    private String customerAddress;
    @jakarta.validation.constraints.Size(max = 100)
    private String contactPerson;
    @jakarta.validation.constraints.Size(max = 100)
    private String mobileNumber;
    @jakarta.validation.constraints.Size(max = 100)
    private String sourceOfInquiry;
    @jakarta.validation.constraints.Size(max = 100)
    private String industry;
    @jakarta.validation.constraints.NotBlank
    @jakarta.validation.constraints.Size(max = 100)
    private String branch;
    @jakarta.validation.constraints.Size(max = 100)
    private String createdByUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    @jakarta.validation.constraints.Size(max = 100)
    private String updatedByUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedOn;
    private Set<PumpInquiryDto> pumpInquiries;
    private Set<AgitatorInquiryDto> agitatorInquiries;
    private Set<ApiPlanInquiryDto> apiPlanInquiries;
    private Set<RotaryJointInquiryDto> rotaryJointInquiries;
}
