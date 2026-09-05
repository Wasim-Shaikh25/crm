package com.synterra.lens.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "RotaryJointInquiry")
public class RotaryJointInquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RotaryJointInquiryId", nullable = false)
    private Long rotaryJointInquiryId;

    @Column(name = "RotaryJointInquiryIdReferenceNo", nullable = false, unique = true)
    private String rotaryJointInquiryReferenceNo;

    @Column(name = "CreatedByUser", length = 50)
    private String createdByUser;

    @Column(name = "CreatedOn")
	@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdOn;

    @Column(name = "Branch", length = 100)
    private String branch;
    
    @Column(name = "UpdatedByUser", length = 50)
    private String updatedByUser;

    @Column(name = "UpdatedOn")
	@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedOn;

    @Column(name = "Equipment", length = 100)
    private String equipment;

    @Column(name = "Make", length = 100)
    private String make;

    @Column(name = "Model", length = 100)
    private String model;

    @Column(name = "Fluid", length = 100)
    private String fluid;

    @Column(name = "OperatingTemperature")
    private Double operatingTemperature;

    @Column(name = "OperatingTemperatureUnit", length = 20)
    private String operatingTemperatureUnit; // "℃ or ℉"

    @Column(name = "FlowRate")
    private Double flowRate;

    @Column(name = "Speed")
    private Double speed;

    @Column(name = "OperatingPressure")
    private Double operatingPressure;
    @Column(name = "OperatingPressureUnit", length = 20)
    private String operatingPressureUnit;

    @Column(name = "ExistingRotaryJointMake", length = 100)
    private String existingRotaryJointMake;

    @Column(name = "ExistingRotaryJointModelType", length = 50)
    private String existingRotaryJointModelType; 

    @Column(name = "ExistingRotaryJointConnectionSize", length = 50)
    private String existingRotaryJointConnectionSize;

    @Column(name = "ExistingRotaryJointConnectionType", length = 50)
    private String existingRotaryJointConnectionType;

    @Column(name = "JointType", length = 50)
    private String jointType; 

    @Column(name = "ProposedRotaryJointMake", length = 100)
    private String proposedRotaryJointMake;

    @Column(name = "ProposedRotaryJointModelType", length = 50)
    private String proposedRotaryJointModelType; 

    @Column(name = "InletConnectionSize", length = 50)
    private String inletConnectionSize;

    @Column(name = "OutletConnectionSize", length = 50)
    private String outletConnectionSize;

    @Column(name = "ConnectionType", length = 50)
    private String connectionType;

    @Column(name = "Handing", length = 50)
    private String handing;

    @Column(name = "InletFlangedSize", length = 50)
    private String inletFlangedSize;

    @Column(name = "OutletFlangedSize", length = 50)
    private String outletFlangedSize;

    @Column(name = "ReferenceDrawing", length = 255) 
    private String referenceDrawing;
    
    @Column(name = "FileName", length = 50) 
	private String fileName;

    @ManyToOne
    @JoinColumn(name = "SalesInquiryId", nullable = false)
    private SalesInquiry salesInquiry;
    
}
