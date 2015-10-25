package com.nadee.myportfolio.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "portfolio")
@NoArgsConstructor
public class Portfolio implements Serializable {
	
	private static final long serialVersionUID = 5636240525222614874L;

	public Portfolio(String name) {
		this.name = name;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "portfolio_id", nullable = false)
	private Integer portfolioId;

	@Column(name = "name", nullable = false)
	private String name;

	@OneToMany(mappedBy = "portfolio")
	private final List<Transaction> transactions = new ArrayList<Transaction>();

	
	@OneToMany(mappedBy = "portfolio")
	private final List<Cost> cost = new ArrayList<Cost>();

	/*@OneToMany(mappedBy = "portfolioId")
	private final List<Holding> holdings = new ArrayList<Holding>();
*/
}
