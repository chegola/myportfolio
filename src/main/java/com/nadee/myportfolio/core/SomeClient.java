package com.nadee.myportfolio.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nadee.external.ReadTxnFromCsv;
import com.nadee.myportfolio.model.Holding;
import com.nadee.myportfolio.model.Portfolio;
import com.nadee.myportfolio.repositories.PortfolioRepository;


public class SomeClient {
	@Autowired private ReadTxnFromCsv readTxnFromCsv;
	@Autowired private TradeManagerImpl tradeManager;
	@Autowired private PortfolioRepository portRepo;
		
	public void doImport() throws Exception {
		List<UserParameter> userParamList = readTxnFromCsv.readFile("c:\\MyPortfolio\\myalltxn.csv");
		tradeManager.doTradeAll(userParamList);
	}
	
	public void doList() {
		Portfolio p = portRepo.findByName("Che");
		List<Holding> holdings = tradeManager.getHolding(p);
		for (Holding h : holdings) {
			System.out.println(h.getSecurity().getSymbol() + ":" + h.getBalance() + ":" + h.getCost());
		}
	}
}
