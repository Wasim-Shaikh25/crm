package com.synterra.lens.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthorityDto {

	private Long authorityId;
	@jakarta.validation.constraints.Size(max = 150)
	private String authorityName;
}
