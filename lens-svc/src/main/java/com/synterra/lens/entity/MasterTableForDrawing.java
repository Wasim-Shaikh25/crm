package com.synterra.lens.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Entity(name = "MasterTableForDrawing")
public class MasterTableForDrawing {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MasterTableForDrawingId")
	private int masterTableForDrawingId;

	@Column(name = "ColumnName")
	private String columnName;
	
	@Column(name = "Value") 
	private String value;

	

}
