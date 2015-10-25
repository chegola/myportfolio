package com.nadee.myportfolio.factory;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import com.nadee.myportfolio.model.Portfolio;
import com.nadee.myportfolio.repositories.PortfolioRepository;

@Slf4j
public class PortfolioFactory {

	@Autowired private PortfolioRepository portfolioRepository;

	public Portfolio createPortfolio(String portfolioName) {
		final Portfolio portfolio = new Portfolio();
		portfolio.setName(portfolioName);
		portfolioRepository.saveAndFlush(portfolio);
		log.info(portfolio.getName() + " persisted");
		return portfolio;
	}
}
