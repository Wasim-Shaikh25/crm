package com.synterra.lens.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfmCommunicationDto {

	private Long id;
	@jakarta.validation.constraints.NotBlank
	@jakarta.validation.constraints.Size(max = 100)
	private String ofmNo;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime ofmDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime activityOn;
	@jakarta.validation.constraints.Size(max = 100)
	private String previousActivity;
	@jakarta.validation.constraints.NotBlank
	@jakarta.validation.constraints.Size(max = 100)
	private String currentActivity;
	@jakarta.validation.constraints.Size(max = 100)
	private String activityBy;
	@jakarta.validation.constraints.Size(max = 2000)
	private String comments;
	@jakarta.validation.constraints.Size(max = 150)
	private String fileName;
}