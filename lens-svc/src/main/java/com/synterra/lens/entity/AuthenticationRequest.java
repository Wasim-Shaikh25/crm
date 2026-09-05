package com.synterra.lens.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
	
  @jakarta.validation.constraints.NotBlank
	
  private String empId;
  @jakarta.validation.constraints.NotBlank
  private String password;

}
