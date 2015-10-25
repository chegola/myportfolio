package com.nadee.myportfolio.core;

import java.util.Date;

import com.nadee.myportfolio.model.TransactionType;

import lombok.Data;

@Data
public class UserParameter {
	private Integer portfolioId;
	private String securitySymbol;
	private Date tradeDate;
	private Double price;
	private Double share;
	private String note;
	private TransactionType type;
}
