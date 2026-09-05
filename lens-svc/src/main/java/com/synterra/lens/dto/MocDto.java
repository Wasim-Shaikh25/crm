package com.synterra.lens.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MocDto {

	private Long mocId;
	
	@jakarta.validation.constraints.Size(max = 100)
	private String mocType;
	
	@jakarta.validation.constraints.Size(max = 100)
	private String matingRing;
	
	@jakarta.validation.constraints.Size(max = 100)
	private String sealRing;
	
	@jakarta.validation.constraints.Size(max = 100)
	private String elastomer;
	
	@jakarta.validation.constraints.Size(max = 100)
	private String springElement;
	
	@jakarta.validation.constraints.Size(max = 100)
	private String hardware;

	@jakarta.validation.constraints.Size(max = 100)
	private String fasteners;
}
