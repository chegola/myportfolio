package com.nadee.myportfolio.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;

@Entity
@IdClass(HoldingKey.class)
@Data
@Table(name = "holding")
public class Holding implements Serializable {
	
	private static final long serialVersionUID = 2363651684877605995L;

	@ManyToOne
	@Id
	@JoinColumn(name="portfolio_id", nullable = false, referencedColumnName="portfolio_id")
	private Portfolio portfolio;

	@ManyToOne
	@Id
	@JoinColumn(name="sec_id", nullable = false, referencedColumnName="sec_id")
	private Security security;
	
	@Column(name = "balance")
	private Double balance;
	
	@Column(name = "cost")
	private Double cost;
}
