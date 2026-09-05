package com.synterra.lens.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "OfmCommunication")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfmCommunication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "OfmNo")
	private String ofmNo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "OfmDate")
	private LocalDateTime ofmDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ActivityOn")
	private LocalDateTime activityOn;

	@Column(name = "CurrentActivity")
	private String currentActivity;

	@Column(name = "ActivityBy")
	private String activityBy;

	@Column(name = "PreviousActivity")         
	private String previousActivity;

	@Column(name = "Comments")
	private String comments;

	@Column(name = "FileName")
	private String fileName;

}