package com.synterra.lens.exception;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

	private LocalDate timestamp;
	private HttpStatus status;
	private String detail;
	private String instance;
}
