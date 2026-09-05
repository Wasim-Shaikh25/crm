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

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name = "Moc")
public class Moc {

	@Id
	@Column(name = "MocId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mocId;
	
	@Column(name = "MocType")
	private String mocType;
	
	@Column(name = "MatingRing")
	private String matingRing;
	
	@Column(name = "SealRing")
	private String sealRing;
	
	@Column(name = "Elastomer")
	private String elastomer;
	
	@Column(name = "SpringElement")
	private String springElement;
	
	@Column(name = "Hardware")
	private String hardware;
	
	@Column(name = "fasteners")
	private String fasteners;
	
	@ManyToOne
	@JoinColumn(name="SalesItemId", nullable=false)
	private SalesItem salesItem;
}
