package com.nadee.myportfolio.model;

public enum TransactionType {
	BUY,
	SELL,
	DEPOSIT,
	WITHDRAW,
	DIVIDEND;
	
	public int getDatabaseId(){
		return ordinal() + 1;
	}
}
