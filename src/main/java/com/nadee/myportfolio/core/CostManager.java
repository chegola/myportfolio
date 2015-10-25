package com.nadee.myportfolio.core;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import com.nadee.myportfolio.exception.NotEnoughUnitException;
import com.nadee.myportfolio.model.Cost;
import com.nadee.myportfolio.model.Transaction;
import com.nadee.myportfolio.model.TransactionType;
import com.nadee.myportfolio.repositories.CostRepository;

@Slf4j
public class CostManager {

	@Autowired private CostRepository costRepo;
	
	public Cost createCost(Transaction txn) throws Exception {
		final Cost cost = new Cost();
		cost.setTransaction(txn);
		cost.setPortfolio(txn.getPortfolio());

		if (txn.getTxnType() == TransactionType.SELL.getDatabaseId()) {
			log.info("createCost SELL " + txn.getSecurity().getSymbol() + " on " + txn.getTxnDate()) ;
			cost.setBalance(getUnitBalance(txn));
			cost.setSaleCost(calSaleCost(txn));
			if (cost.getBalance() == 0.0) {
				cost.setRemainingCost(0.0);
				cost.setRunningTotal(0.0);
			} else {
				cost.setRemainingCost(getLatestRunningTotal(txn) - cost.getSaleCost());
				cost.setRunningTotal(cost.getRemainingCost());
			}
				
		} else if (txn.getTxnType() == TransactionType.BUY.getDatabaseId()) {
			log.info("createCost BUY " + txn.getSecurity().getSymbol() + " on " + txn.getTxnDate()) ;
			cost.setRunningTotal(getLatestRunningTotal(txn));
			cost.setBalance(getUnitBalance(txn));
			cost.setMatched((short) 0);
		}
		
		if (cost.getBalance() != 0.0) {
			cost.setCost(cost.getRunningTotal() / cost.getBalance());
		} else {
			cost.setCost(0.0);
		}
		
		costRepo.saveAndFlush(cost);
		return cost;
	}
	
	public void saveCost(Cost cost){
		costRepo.saveAndFlush(cost);
	}

	private Double calSaleCost(Transaction txn) throws Exception {
		log.info("calSaleCost " + txn.getTxnType() + ":" + txn.getSecurity().getSymbol() + " on " + txn.getTxnDate()) ;
		double saleCost = 0.0;
		List<Cost> costs = costRepo.findByPortfolioAndMatchedOrderByCostIdAsc(txn.getPortfolio(), (short) 0, txn.getSecurity());

		if (costs.isEmpty()) {
			throw new NotEnoughUnitException();
		}
		
		double buyShare = 0.0;
		double sellShare = txn.getShare();
		int index = 0;
		Cost cost;
		while(sellShare > 0) {
			cost = costs.get(index);
			buyShare = cost.getRemainingshare() == null ? cost.getTransaction().getShare() : cost.getRemainingshare();  
			if (buyShare > sellShare) {
				saleCost += cost.getCost() * sellShare;
				cost.setRemainingshare(buyShare - sellShare);
				sellShare -= buyShare;
			} else if (buyShare < sellShare) {
				saleCost += cost.getCost() * buyShare;
				sellShare -= buyShare;
				cost.setMatched((short) 1);
			} else {
				saleCost += cost.getCost() * sellShare;
				cost.setRemainingshare(0.0);
				cost.setMatched((short) 1);
				sellShare = -1;
			}
			costRepo.saveAndFlush(cost);
			index += 1;
		}
		
		return saleCost;
	}

	private Double getLatestRunningTotal(Transaction txn) {
		Double runningTotal = 0.0;
		if (txn.getTxnType() == TransactionType.BUY.getDatabaseId()) {
			runningTotal = txn.getAmount();
		}

		//Integer secId  = txn.getSecurity().getSecurityId();
		Cost cost = costRepo.findByPortfolioAndLatestCostIdAndSecurity(txn.getPortfolio(),  txn.getSecurity());
		
		if (cost != null) {
			runningTotal += cost.getRunningTotal();
		}
		log.info("getLastestRunningTotal " + txn.getSecurity().getSymbol() + " on " + txn.getTxnDate() + " running total = " + runningTotal) ;
		return runningTotal;
	}

	private Double getUnitBalance(Transaction txn) {
		double balance = 0.0;
		List<Cost> costs = costRepo.findByPortfolioAndSecurituOrderByCostIdAsc(txn.getPortfolio(), txn.getSecurity());

		if (!costs.isEmpty()) {
			Cost cost = costs.get(costs.size() - 1);
			balance = cost.getBalance();
		}
		if (txn.getTxnType() == TransactionType.BUY.getDatabaseId()) {
			balance += txn.getShare();
		} else if (txn.getTxnType() == TransactionType.SELL.getDatabaseId()) {
			if (balance < txn.getShare()) {
				throw new NotEnoughUnitException();				
			} else {
				balance -= txn.getShare();
			}
		}
		return balance;
	}
}