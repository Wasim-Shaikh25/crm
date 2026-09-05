package com.synterra.lens.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
@Entity(name = "RotaryJoint")
public class RotaryJoint {
    
	@Id
	@Column(name = "RotaryJointId")  // Consistent naming
	private Long rotaryJointId;

    @Column(name = "DrfNumber", nullable = false, unique = true)
    private String drfNumber;

    @Column(name = "Branch", nullable = false)
    private String branch;

    @Column(name = "SalesInquiryItemReferenceNo", nullable = false)
    private String salesInquiryItemReferenceNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreatedOn")
    private LocalDateTime createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UpdatedOn")
    private LocalDateTime updatedOn;

    @Column(name = "CreatedByUser")
    private String createdByUser;

    @Column(name = "UpdatedByUser")
    private String updatedByUser;

    @Column(name = "CustomerName", length = 100)
    private String customerName;

    @Column(name = "EndUser")
    private String endUser;

    @Column(name = "CostingRequirement")
    private boolean costingRequirement;

    // Equipment Details
    @Column(name = "Equipment")
    private String equipment;

    @Column(name = "Make")
    private String make;

    @Column(name = "Model")
    private String model;

    // Operating Conditions
    @Column(name = "Fluid")
    private String fluid;

    @Column(name = "OperatingTemperature")
    private Double operatingTemperature;

    @Column(name = "OperatingTemperatureUnit")
    private String operatingTemperatureUnit;

    @Column(name = "FlowRate")
    private Double flowRate;

    @Column(name = "Speed")
    private Double speed;

    @Column(name = "OperatingPressure")
    private Double operatingPressure;

    @Column(name = "OperatingPressureUnit")
    private String operatingPressureUnit;

    // Existing Rotary Joint Details
    @Column(name = "ExistingRotaryJointMake")
    private String existingRotaryJointMake;

    @Column(name = "ExistingRotaryJointModelType")
    private String existingRotaryJointModelType;

    @Column(name = "ExistingRotaryJointConnectionSize")
    private String existingRotaryJointConnectionSize;

    @Column(name = "ExistingRotaryJointConnectionType")
    private String existingRotaryJointConnectionType;

    // Joint Specifications
    @Column(name = "JointType")
    private String jointType;

    @Column(name = "ProposedRotaryJointMake")
    private String proposedRotaryJointMake;

    @Column(name = "ProposedRotaryJointModelType")
    private String proposedRotaryJointModelType;

    @Column(name = "InletConnectionSize")
    private String inletConnectionSize;

    @Column(name = "OutletConnectionSize")
    private String outletConnectionSize;

    @Column(name = "ConnectionType")
    private String connectionType;

    @Column(name = "Handing")
    private String handing;

    @Column(name = "InletFlangedSize")
    private String inletFlangedSize;

    @Column(name = "OutletFlangedSize")
    private String outletFlangedSize;

    @Column(name = "ReferenceDrawing")
    private String referenceDrawing;
}
