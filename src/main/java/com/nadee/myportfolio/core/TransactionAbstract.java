package com.nadee.myportfolio.core;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import com.nadee.myportfolio.factory.PortfolioFactory;
import com.nadee.myportfolio.model.Portfolio;
import com.nadee.myportfolio.model.Security;
import com.nadee.myportfolio.model.Transaction;
import com.nadee.myportfolio.repositories.PortfolioRepository;
import com.nadee.myportfolio.repositories.SecurityRepository;
import com.nadee.myportfolio.repositories.TxnRepository;

@Slf4j
public abstract class TransactionAbstract {

	@Autowired private PortfolioRepository portfolioRepository;
	@Autowired private PortfolioFactory portfolioFactory;
	@Autowired private SecurityRepository securityRepository;
	@Autowired private TxnRepository transactionRepo;
	
	public Transaction createTransaction(UserParameter param) throws Exception{
		
		final Portfolio portfolio = portfolioRepository.findOne(param.getPortfolioId());
		final Security security = securityRepository.findBySymbol(param.getSecuritySymbol());

		Transaction txn = new Transaction();
		txn.setPortfolio(portfolio);
		txn.setSecurity(security);
		txn.setPrice(param.getPrice());
		txn.setShare(param.getShare());
		txn.setTxnDate(param.getTradeDate());
		txn.setNote(param.getNote());
		txn = setupTransaction(txn);
		transactionRepo.saveAndFlush(txn);

		log.debug("Transaction Id:" + txn.getTransactionId() + " persisted.");
		
		return txn;
	}

	abstract Transaction setupTransaction(Transaction txn);

}
