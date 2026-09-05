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
@Table(name = "ApiPlanInquiry")
public class ApiPlanInquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ApiPlanInquiryId", nullable = false)
    private Long apiPlanInquiryId;

    @Column(name = "ApiPlanInquiryReferenceNo", nullable = false, unique = true)
    private String apiPlanInquiryReferenceNo;

    @Column(name = "EquipmentMake", length = 100)
    private String equipmentMake;

    @Column(name = "Branch", length = 100)
    private String branch;
    
    @Column(name = "EquipmentModel", length = 100)
    private String equipmentModel;

    @Column(name = "EquipmentType", length = 100)
    private String equipmentType;

    @Column(name = "Arrangement", length = 50)
    private String arrangement;

    @Column(name = "TagNumber", length = 50)
    private String tagNumber;

    @Column(name = "PumpMOC", length = 100)
    private String pumpMOC;

    @Column(name = "DrawingNumber", length = 100)
    private String drawingNumber;

    @Column(name = "MechanicalSealMake", length = 100)
    private String mechanicalSealMake;

    @Column(name = "MechanicalSealSeries", length = 100)
    private String mechanicalSealSeries;

    @Column(name = "ConnectionSize", length = 50)
    private String connectionSize;

    @Column(name = "ShaftSize", length = 50)
    private String shaftSize;

    @Column(name = "Rotation", length = 50)
    private String rotation;

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "MAWP_Id")
    private Measurement mawp;

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "MAWT_Id")
    private Measurement mawt;

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "SuctionPressurePumpId")
    private Measurement suctionPressurePump;

    @ManyToOne( fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "DischargePressurePumpId")
    private Measurement dischargePressurePump;

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "BoxPressurePumpId")
    private Measurement boxPressurePump;

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "VesselPressureAgitatorId")
    private Measurement vesselPressureAgitator;

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "OperatingTemperatureId")
    private Measurement operatingTemperature;

    @ManyToOne( fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "MaxTemperatureId")
    private Measurement maxTemperature;


    @Column(name = "Fluid", length = 100)
    private String fluid;
    
    @Column(name = "Speed")
    private Double speed;
    
    @Column(name = "Viscosity")
    private Double viscosity;

    @Column(name = "SpGravity")
    private Double spGravity;

    @Column(name = "PercentageOfSolid")
    private Double percentageOfSolid;

    @Column(name = "SolidSize")
    private Double solidSize;

    @Column(name = "FreezingPoint")
    private Double freezingPoint;

    @Column(name = "BoilingPoint")
    private Double boilingPoint;

    @Column(name = "LeakProofProposalApiPlan", length = 500)
    private String leakProofProposalApiPlan;

    @Column(name = "Capacity")
    private Double capacity;

    @Column(name = "HeatExchangeType", length = 100)
    private String heatExchangeType;

    @Column(name = "HeatExchangeArea")
    private Double heatExchangeArea;

    @Column(name = "Standard", length = 50)
    private String standard;

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

    @ManyToOne
    @JoinColumn(name = "SalesInquiryId", nullable = false)
    private SalesInquiry salesInquiry;
}
