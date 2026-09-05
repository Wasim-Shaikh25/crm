package com.synterra.lens.dto;

import java.util.Set;

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
public class RoleDto {

	private Long roleId;
	@jakarta.validation.constraints.Size(max = 150)
	private String roleName;
	private Set<AuthorityDto> authorities;
}
