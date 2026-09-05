package com.synterra.lens.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "EndUserDetail")
public class EndUserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EndUserDetailId")
    private Long endUserDetailId;

    @Column(name = "Branch")
    private String branch;                        // Dropdown

    @Column(name = "CustomerName")
    private String customerName;

    @Column(name = "Place")
    private String place;

    @Column(name = "ContactPersonName")
    private String contactPersonName;

    @Column(name = "MobileNumber")
    private String mobileNumber;

    @Column(name = "EmailId")
    private String emailId;

    @Column(name = "EndUserIndustry")
    private String endUserIndustry;

    @Column(name = "Knots")
    private String knots;

    @OneToOne
    @JoinColumn(name = "OfmId", referencedColumnName = "OfmId")
    private OrderForwardingMemo orderForwardingMemo;
}