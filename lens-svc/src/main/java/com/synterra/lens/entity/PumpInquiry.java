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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "PumpInquiry")
public class PumpInquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PumpInquiryId", nullable = false)
    private Long pumpInquiryId;

    @Column(name = "PumpInquiryReferenceNo", nullable = false)
    private String pumpInquiryReferenceNo;

    @Column(name = "CreatedByUser", length = 50)
    private String createdByUser;

    @Column(name = "CreatedOn")
	@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdOn;

    @Column(name = "UpdatedByUser", length = 50)
    private String updatedByUser;

    @Column(name = "UpdatedOn")
	@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedOn;
    
    @Column(name = "Branch", length = 100)
    private String branch;

    @Column(name = "Make", length = 100)
    private String make;

    @Column(name = "Model", length = 100)
    private String model;

    @Column(name = "PumpMOC", length = 100)
    private String pumpMOC;

    @Column(name = "ImpellerCasingMOC", length = 100)
    private String impellerCasingMOC;

    @Column(name = "ShaftMOC", length = 100)
    private String shaftMOC;

    @Column(name = "BearingBKT", length = 100)
    private String bearingBKT;

    @Column(name = "TagNumber", length = 50)
    private String tagNumber;

    @Column(name = "Arrangement", length = 50)
    private String arrangement;

    @Column(name = "PumpType", length = 50)
    private String pumpType;

    @Column(name = "Stage", length = 50)
    private String stage;

    @Column(name = "CasingType", length = 50)
    private String casingType;

    @Column(name = "Series", length = 50)
    private String series;

    @Column(name = "Performance", length = 50)
    private String performance;

    @Column(name = "SealArrangement", length = 50)
    private String sealArrangement;

    @Column(name = "ExistingSealMake", length = 100)
    private String existingSealMake;

    @Column(name = "ExistingSealSize", length = 50)
    private String existingSealSize;

    @Column(name = "ExistingSealMOC", length = 100)
    private String existingSealMOC;

    @Column(name = "ExistingSealApiPlan", length = 100)
    private String existingSealApiPlan;

    @ManyToOne( fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "SuctionPressureId")
    private Measurement suctionPressure;

    @ManyToOne( fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "DischargePressureId")
    private Measurement dischargePressure;

    @ManyToOne( fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "BoxPressureId")
    private Measurement boxPressure;

    @ManyToOne(fetch = FetchType.EAGER,  cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "TotalHeadId")
    private Measurement totalHead;

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "PumpingTemperatureId")
    private Measurement pumpingTemperature;

    @ManyToOne( fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "MaximumTemperatureId")
    private Measurement maximumTemperature;

    @Column(name = "DirectionOfRotation", length = 50)
    private String directionOfRotation;

    @Column(name = "Speed")
    private double speed; 

    @Column(name = "Fluid", length = 50)
    private String fluid;

    @Column(name = "Nature", length = 50)
    private String nature;

    @Column(name = "SpGravity")
    private double spGravity; 

    @Column(name = "FreezingPoint")
    private double freezingPoint; 

    @Column(name = "BoilingPoint")
    private double boilingPoint; 

    @Column(name = "Viscosity")
    private double viscosity; 

    @Column(name = "PercentageOfSolid")
    private double percentageOfSolid; 

    @Column(name = "SolidSize")
    private double solidSize; 

    @Column(name = "SpecialNote", length = 150)
    private String specialNote;
  
    @ManyToOne
    @JoinColumn(name = "SalesInquiryId", nullable = false)
    private SalesInquiry salesInquiry;
}
