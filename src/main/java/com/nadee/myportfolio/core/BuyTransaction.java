package com.nadee.myportfolio.core;

import com.nadee.myportfolio.model.Transaction;
import com.nadee.myportfolio.model.TransactionType;


public class BuyTransaction extends TransactionAbstract {

	@Override
	Transaction setupTransaction(Transaction txn) {
		txn.setTxnType(TransactionType.BUY.getDatabaseId());
		txn.setAmount(txn.getPrice() * txn.getShare());
		return txn;
	}
}
