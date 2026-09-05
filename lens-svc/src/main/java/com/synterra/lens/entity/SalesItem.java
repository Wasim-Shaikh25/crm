package com.synterra.lens.entity;

import java.time.LocalDateTime;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name = "SalesItem")
public class SalesItem {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SalesItemId", nullable = false)
	private Integer salesItemId;
	
	@Column(name = "Quantity")
    private String quantity; 
    
    @Column(name = "Unit")
    private String unit;

    @Column(name = "NewOrExisting")
    private String newOrExisting;
    
    @Column(name = "Type")
    private String type;
    
    @Column(name = "Size")
    private String size;
    
    @Column(name = "HeaderDescription")
    private String headerDescription;
    
    @Column(name = "ItemDescription")
    private String itemDescription;
    
    @Column(name = "CICode")
    private String ciCode;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "InsertedOn")
    private LocalDateTime insertedOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LastUpdatedOn")
    private LocalDateTime lastUpdatedOn;

    @Column(name = "InsertedByUserId")
    private String insertedByUserId;

    @Column(name = "LastUpdatedByUserId")
    private String lastUpdatedByUserId;
    
    @ManyToOne
	@JoinColumn(name="SalesInquiryId", nullable=false)
	private SalesInquiry salesInquiry;
    
    @OneToMany(mappedBy = "salesItem", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Moc> moc;
}
