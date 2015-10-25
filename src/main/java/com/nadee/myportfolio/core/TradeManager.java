package com.nadee.myportfolio.core;

import java.util.List;

import com.nadee.myportfolio.model.Holding;
import com.nadee.myportfolio.model.Portfolio;
import com.nadee.myportfolio.model.Transaction;

public interface TradeManager {
	public void doTrade(UserParameter userParam) throws Exception;
	public List<Holding> getHolding(Portfolio portfolio);
	public List<Transaction> getDividend(Portfolio portfolio);
}
