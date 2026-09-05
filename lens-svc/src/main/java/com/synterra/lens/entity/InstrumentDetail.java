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
@Table(name = "InstrumentDetail")
public class InstrumentDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InstrumentDetailId")
    private Long instrumentDetailId;

    @Column(name = "RequiredInstrumentOrLooseItem")
    private String requiredInstrumentOrLooseItem;

    @Column(name = "MakeOfInstrument")
    private String makeOfInstrument;

    @ManyToOne
    @JoinColumn(name = "ApiPlanId", nullable = false)
    private ApiPlan apiPlan;
}