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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "cash_balance")
@NoArgsConstructor
public class CashBalance implements Serializable {

	private static final long serialVersionUID = 8120900197627024040L;

	@Id
	@JoinColumn(name = "portfolio_id", nullable = true)
	@Column(name = "portfolio_id")
	private Integer portfolioId;

	@Column(name = "balance")
	private Double balance;

}
