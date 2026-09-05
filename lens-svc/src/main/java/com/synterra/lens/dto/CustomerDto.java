package com.synterra.lens.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDto {
    private Integer customerId;
    @jakarta.validation.constraints.Size(max = 150)
    private String customerReferenceNumber;
    @jakarta.validation.constraints.Size(max = 100)
    private String branch;
    @jakarta.validation.constraints.NotBlank
    @jakarta.validation.constraints.Size(max = 150)
    private String customerName;
    private Set<ContactDetailDto> contactDetail;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedOn;
    @jakarta.validation.constraints.Size(max = 100)
    private String createdByUser;
    @jakarta.validation.constraints.Size(max = 100)
    private String updatedByUser;
    @jakarta.validation.constraints.Size(max = 100)
    private String vendorCode;
      
}