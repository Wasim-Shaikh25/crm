package com.synterra.lens.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuotationItemDTO {

    private Long quotationItemId;
    @jakarta.validation.constraints.Size(max = 150)
    private String itemName;
    @jakarta.validation.constraints.Size(max = 2000)
    private String itemDescription;
    private int quantity;
    private double unitPrice;
    private double totalPrice;
    @jakarta.validation.constraints.Size(max = 100)
    private String currency;
    @jakarta.validation.constraints.Size(max = 100)
    private String itemCode;
    @jakarta.validation.constraints.Size(max = 100)
    private String uom;
    private double discount;
    private double tax;
    @jakarta.validation.constraints.Size(max = 100)
    private String drfNo;
}
