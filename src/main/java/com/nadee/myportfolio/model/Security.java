package com.nadee.myportfolio.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@Table(name="security")
@NoArgsConstructor
public class Security implements Serializable  {
	
	private static final long serialVersionUID = -8506560585628371134L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sec_id", nullable = false)
	private Integer securityId;

	@Column(name = "symbol", nullable=false)
	@NonNull
	private String symbol;
	
	public Security(String symbol){
		this.symbol =symbol;
	}

	/*@OneToOne(mappedBy = "securityId")
	private final List<Holding> holdings = new ArrayList<Holding>();*/
}
