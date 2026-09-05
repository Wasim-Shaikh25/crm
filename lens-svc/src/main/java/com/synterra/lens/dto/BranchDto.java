package com.synterra.lens.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchDto {

	private Long branchId;
	@jakarta.validation.constraints.Size(max = 150)
	private String branchName;
	@jakarta.validation.constraints.Size(max = 100)
	private String region;

}