package com.synterra.lens.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "AgitatorInquiry")
public class AgitatorInquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AgitatorInquiryId", nullable = false)
    private Long agitatorInquiryId;

    @Column(name = "AgitatorInquiryReferenceNo", unique = true, nullable = false)
    private String agitatorInquiryReferenceNo;

    @Column(name = "Series")
    private String series;

    private String performance;

    @Column(name = "SealArrangement")
    private String sealArrangement;

    @Column(name = "ExistingSealMake")
    private String existingSealMake;

    @Column(name = "ExistingSealSize")
    private String existingSealSize;

    @Column(name = "ExistingSealMOC")
    private String existingSealMOC;

    @Column(name = "ExistingSealApiPlan")
    private String existingSealApiPlan;

    @Column(name = "VesselPressureOperating")
    private Double vesselPressureOperating;

    @Column(name = "VesselPressureOperatingUnit")
    private String vesselPressureOperatingUnit;

    @Column(name = "VesselPressureDesign")
    private Double vesselPressureDesign;

    @Column(name = "VesselPressureDesignUnit")
    private String vesselPressureDesignUnit;

    @Column(name = "DirectionOfRotation")
    private String directionOfRotation;

    @Column(name = "Speed")
    private Double speed;

    @Column(name = "Fluid")
    private String fluid;

    @Column(name = "Nature")
    private String nature;

    @Column(name = "Branch")
    private String branch;

    @Column(name = "SpGravity")
    private Double spGravity;

    @Column(name = "FreezingPoint")
    private Double freezingPoint;

    @Column(name = "BoilingPoint")
    private Double boilingPoint;

    @Column(name = "Viscosity")
    private Double viscosity;

    @Column(name = "PercentageOfSolid")
    private Double percentageOfSolid;

    @Column(name = "SolidSize")
    private Double solidSize;

    @Column(name = "SpecialNote", length = 500)
    private String specialNote;
    
    @ManyToOne( fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "PumpingTemperature")
    private Measurement pumpingTemperature;

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "MaximumTemperature")
    private Measurement maximumTemperature;

    @Column(name = "CreatedByUser")
    private String createdByUser;

    @Column(name = "CreatedOn")
    private LocalDateTime createdOn;

    @Column(name = "UpdatedByUser")
    private String updatedByUser;

    @Column(name = "UpdatedOn")
    private LocalDateTime updatedOn;

    @ManyToOne
    @JoinColumn(name = "SalesInquiryId", nullable = false)
    private SalesInquiry salesInquiry;
}
