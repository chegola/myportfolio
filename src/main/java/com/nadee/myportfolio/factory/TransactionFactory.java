package com.nadee.myportfolio.factory;

import org.springframework.beans.factory.annotation.Autowired;

import com.nadee.myportfolio.core.BuyTransaction;
import com.nadee.myportfolio.core.DepositTransaction;
import com.nadee.myportfolio.core.DividendTransaction;
import com.nadee.myportfolio.core.SellTransaction;
import com.nadee.myportfolio.core.UserParameter;
import com.nadee.myportfolio.core.WithdrawTransaction;
import com.nadee.myportfolio.model.Transaction;

	

public class TransactionFactory {

	@Autowired private BuyTransaction buyTxn;
	@Autowired private SellTransaction sellTxn;
	@Autowired private DepositTransaction depositTxn;
	@Autowired private WithdrawTransaction withdrawTxn;
	@Autowired private DividendTransaction dividendTxn;

	public Transaction createBuyTransaction(UserParameter userParameter) throws Exception {
		return buyTxn.createTransaction(userParameter);
	}
	
	public Transaction createSellTransaction(UserParameter userParameter) throws Exception {
		return sellTxn.createTransaction(userParameter);
	}
	
	public Transaction createDepositTransaction(UserParameter userParameter) throws Exception {
		return depositTxn.createTransaction(userParameter);
	}
	
	public Transaction createWithdrawTransaction(UserParameter userParameter) throws Exception {
		return withdrawTxn.createTransaction(userParameter);
	}

	public Transaction createDividendTranction(UserParameter userParameter) throws Exception {
		return dividendTxn.createTransaction(userParameter);
	}

}
