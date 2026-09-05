package com.synterra.lens.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "QuotationItem")
public class QuotationItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QuotationItemId")
    private Long quotationItemId;

    @Column(name = "ItemName")
    private String itemName;

    @Column(name = "ItemDescription")
    private String itemDescription;

    @Column(name = "Quantity")
    private int quantity;

    @Column(name = "UnitPrice")
    private double unitPrice;

    @Column(name = "TotalPrice")
    private double totalPrice;

    @Column(name = "Currency")
    private String currency;

    @Column(name = "ItemCode")
    private String itemCode;

    @Column(name = "UOM")  
    private String uom;

    @Column(name = "Discount")
    private double discount;

    @Column(name = "Tax")
    private double tax;

    @Column(name = "DrfNo")
    private String drfNo;

    @ManyToOne
    @JoinColumn(name = "QuotationId", referencedColumnName = "QuotationId")
    private Quotation quotation;
}
