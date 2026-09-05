package com.synterra.lens.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfmItemDto {

	private Long ofmItemId;
	private int srNo;

	// ✅ NEW
	@jakarta.validation.constraints.Size(max = 100)
	private String header;

	@jakarta.validation.constraints.Size(max = 100)
	private String factor;
	@jakarta.validation.constraints.Size(max = 100)
	private String type;
	@jakarta.validation.constraints.Size(max = 100)
	private String size;
	@jakarta.validation.constraints.Size(max = 100)
	private String face;
	@jakarta.validation.constraints.Size(max = 2000)
	private String description;
	@jakarta.validation.constraints.Size(max = 100)
	private String ciCode;

	// ✅ NEW
	@jakarta.validation.constraints.Size(max = 100)
	private String lpItemCode;

	@jakarta.validation.constraints.Size(max = 100)
	private String drfNo;

	// ✅ NEW
	@jakarta.validation.constraints.Size(max = 100)
	private String drawingNo;

	private int quantity;

	// ✅ NEW
	private int bookedQuantity;

	@jakarta.validation.constraints.Size(max = 100)
	private String unit;
	private double unitPrice;
	private double unitLPrice;
	private double discount;
	private double totalValue;
	private double totalListValue;
    private boolean naDrgNo;  
    private double grandTotalListPrice;
}