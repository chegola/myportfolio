package com.nadee.myportfolio.core;

import com.nadee.myportfolio.model.Transaction;
import com.nadee.myportfolio.model.TransactionType;

public class SellTransaction extends TransactionAbstract {

	@Override
	Transaction setupTransaction(Transaction txn) {
		txn.setTxnType(TransactionType.SELL.getDatabaseId());
		txn.setAmount(txn.getPrice() * txn.getShare());
		return txn;
	}

}
