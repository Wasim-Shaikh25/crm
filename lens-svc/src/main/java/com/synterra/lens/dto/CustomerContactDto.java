package com.synterra.lens.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class CustomerContactDto {

	@jakarta.validation.constraints.Size(max = 100)
	private String MobileNumber;
	@jakarta.validation.constraints.Size(max = 100)
	private String ContactPerson;
	@jakarta.validation.constraints.Size(max = 2000)
	private String CustomerAddress;
	@jakarta.validation.constraints.Size(max = 100)
	private String ContactDetailReferenceNo;
	@jakarta.validation.constraints.Size(max = 100)
	private String Branch;
	@jakarta.validation.constraints.Size(max = 150)
	private String CustomerReferenceNumber;
	@jakarta.validation.constraints.Size(max = 150)
	private String CustomerName;
}
