package com.synterra.lens.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EndUserDetailDTO {

    private Long endUserDetailId;
    @jakarta.validation.constraints.Size(max = 100)
    private String branch;
    @jakarta.validation.constraints.Size(max = 150)
    private String customerName;
    @jakarta.validation.constraints.Size(max = 100)
    private String place;
    @jakarta.validation.constraints.Size(max = 150)
    private String contactPersonName;
    @jakarta.validation.constraints.Size(max = 100)
    private String mobileNumber;
    @jakarta.validation.constraints.Email
    @jakarta.validation.constraints.Size(max = 150)
    private String emailId;
    @jakarta.validation.constraints.Size(max = 100)
    private String endUserIndustry;
    @jakarta.validation.constraints.Size(max = 100)
    private String knots;
}