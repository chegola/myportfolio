package com.nadee.myportfolio.core;

import com.nadee.myportfolio.model.Transaction;
import com.nadee.myportfolio.model.TransactionType;

public class WithdrawTransaction extends TransactionAbstract {

	@Override
	Transaction setupTransaction(Transaction txn) {
		txn.setTxnType(TransactionType.WITHDRAW.getDatabaseId());
		txn.setAmount(txn.getPrice());
		return txn;
	}

}
