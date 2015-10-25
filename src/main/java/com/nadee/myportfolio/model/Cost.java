package com.nadee.myportfolio.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "cost")
@NoArgsConstructor
public class Cost implements Serializable {

	private static final long serialVersionUID = 8120900197627024040L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cost_id", nullable = false)
	private Integer costId;

	@JoinColumn(name = "txn_id", nullable = false)
	private Transaction transaction;

	@JoinColumn(name = "portfolio_id", nullable = false)
	@ManyToOne
	private Portfolio portfolio;

	@Column(name = "running_total")
	private Double runningTotal;

	@Column(name = "balance")
	private Double balance;

	@Column(name = "cost")
	private Double cost;

	@Column(name = "sale_cost")
	private Double saleCost;

	@Column(name = "remaining_cost")
	private Double remainingCost;

	@Column(name = "matched")
	private Short matched = 0;
	
	@Column(name = "remaining_share")
	private Double remainingshare;
}
