package com.nadee.myportfolio.factory;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import com.nadee.myportfolio.model.Holding;
import com.nadee.myportfolio.model.Portfolio;
import com.nadee.myportfolio.model.Security;
import com.nadee.myportfolio.repositories.HoldingRepository;

@Slf4j
public class HoldingFactory {

	@Autowired private HoldingRepository holdingRepo;

/*	public Holding updateHolding(Integer portfolioId, Integer securityId, Double balance, Double cost) {
		final Holding holding = new Holding();
		//holding.setPortfolioId(portfolioId);
		//holding.setSecurityId(securityId);
		holding.setBalance(balance);
		holding.setCost(cost);
		holdingRepo.saveAndFlush(holding);
		log.info("holding persisted");
		return holding;
	}*/
	public Holding updateHolding(Portfolio portfolio, Security security, Double balance, Double cost) {
		final Holding holding = new Holding();
		holding.setPortfolio(portfolio);
		holding.setSecurity(security);
		holding.setBalance(balance);
		holding.setCost(cost);
		holdingRepo.saveAndFlush(holding);
		log.info("holding persisted");
		return holding;
	}
}
