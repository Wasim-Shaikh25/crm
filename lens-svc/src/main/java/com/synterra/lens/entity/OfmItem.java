package com.synterra.lens.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "OfmItem")
public class OfmItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OfmItemId")
    private Long ofmItemId;

    @Column(name = "SrNo")
    private int srNo;

    // ✅ NEW: Header field
    @Column(name = "Header")
    private String header;

    @Column(name = "Factor")
    private String factor;

    @Column(name = "Type")
    private String type;

    @Column(name = "Size")
    private String size;

    @Column(name = "Face")
    private String face;

    @Column(name = "Description")
    private String description;

    @Column(name = "CICode")
    private String ciCode;

    // ✅ NEW: LP Item Code
    @Column(name = "LpItemCode")
    private String lpItemCode;

    @Column(name = "DrfNo")
    private String drfNo;

    // ✅ NEW: Drawing Number (Dwg No / DRG No)
    @Column(name = "DrawingNo")
    private String drawingNo;

    @Column(name = "Quantity")
    private int quantity;

    // ✅ NEW: Booked Quantity
    @Column(name = "BookedQuantity")
    private int bookedQuantity;

    @Column(name = "Unit")
    private String unit;

    @Column(name = "UnitPrice")
    private double unitPrice;

    @Column(name = "UnitLPrice")
    private double unitLPrice;

    @Column(name = "Discount")
    private double discount;

    @Column(name = "TotalValue")
    private double totalValue;

    // ✅ NEW: Total List Value (₹)
    @Column(name = "TotalListValue")
    private double totalListValue;

    
    @Column(name = "NaDrgNo")
    private boolean naDrgNo;  // "N/A DRGNo" checkbox
    
    @Column(name = "GrandTotalListPrice")
    private double grandTotalListPrice;
    
    @ManyToOne
    @JoinColumn(name = "ofmId", referencedColumnName = "ofmId")
    private OrderForwardingMemo orderForwardingMemo;

}