package com.nadee.myportfolio.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nadee.myportfolio.exception.NotEnoughCashException;
import com.nadee.myportfolio.factory.HoldingFactory;
import com.nadee.myportfolio.factory.PortfolioFactory;
import com.nadee.myportfolio.factory.SecurityFactory;
import com.nadee.myportfolio.factory.TransactionFactory;
import com.nadee.myportfolio.model.CashBalance;
import com.nadee.myportfolio.model.Cost;
import com.nadee.myportfolio.model.Holding;
import com.nadee.myportfolio.model.Portfolio;
import com.nadee.myportfolio.model.Transaction;
import com.nadee.myportfolio.repositories.CashBalanceRepository;
import com.nadee.myportfolio.repositories.HoldingRepository;
import com.nadee.myportfolio.repositories.PortfolioRepository;
import com.nadee.myportfolio.repositories.SecurityRepository;
import com.nadee.myportfolio.repositories.TxnRepository;

public class TradeManagerImpl implements TradeManager {
	@Autowired
	private TransactionFactory txnFactory;
	@Autowired
	private PortfolioFactory portfolioFactory;
	@Autowired
	private SecurityFactory secFactory;
	@Autowired
	private UserParameter userParam;
	@Autowired
	private TxnRepository txnRepo;
	@Autowired
	private PortfolioRepository portfolioRepo;
	@Autowired
	private SecurityRepository secRepo;
	@Autowired
	private CostManager costMgr;
	@Autowired
	private HoldingFactory holdingFactory;
	@Autowired
	private CashBalanceRepository cashBalanceRepo;
	@Autowired
	private HoldingRepository holdingRepo;
	
	public void doTradeAll(List<UserParameter> userParamList) throws Exception {
		for(UserParameter userParam : userParamList) {
			doTrade(userParam);
		}
	}
	
	public void doTrade(UserParameter userParam) throws Exception {
		final Transaction txn;
		final Cost cost;
		switch (userParam.getType().getDatabaseId()) {
		case 1: // buy
			txn = txnFactory.createBuyTransaction(userParam);
			double cash = 0.0;
			CashBalance cashBalance = cashBalanceRepo.findByPortfolioId(userParam.getPortfolioId());
			if (cashBalance != null) {
				cash = cashBalance.getBalance();
			}
			if (txn.getAmount() > cash ) {
				throw new NotEnoughCashException();
			} else {
				cost = updateCost(txn);
				updateHolding(cost);
				updateCashBal(txn.getPortfolio().getPortfolioId(), txn.getAmount() * -1);
			}
			break;
		case 2: // sell
			txn = txnFactory.createSellTransaction(userParam);
			cost = updateCost(txn);
			updateHolding(cost);
			updateCashBal(txn.getPortfolio().getPortfolioId(), txn.getAmount());
			break;
		case 3: // deposit
			txn = txnFactory.createDepositTransaction(userParam);
			updateCashBal(txn.getPortfolio().getPortfolioId(), txn.getAmount());
			break;
		case 4: // withdraw
			txn = txnFactory.createWithdrawTransaction(userParam);
			updateCashBal(txn.getPortfolio().getPortfolioId(), txn.getAmount() * -1);
			break;
		case 5: // dividend
			txn = txnFactory.createDividendTranction(userParam);
			updateCashBal(txn.getPortfolio().getPortfolioId(), txn.getAmount());
			break;
		}
		
	}
	
	private void updateCashBal(Integer portfolioId, Double amount) {
		CashBalance cb = cashBalanceRepo.findByPortfolioId(portfolioId);
		if (cb == null) {
			cb = new CashBalance();
			cb.setPortfolioId(portfolioId);
			cb.setBalance(amount);
		} else {
			cb.setBalance(cb.getBalance() + amount);
		}
		cashBalanceRepo.saveAndFlush(cb);
	}


	private Cost updateCost(Transaction txn) throws Exception {
		final Cost cost = costMgr.createCost(txn);
		return cost;
	}

	private void updateHolding(Cost cost) {
		holdingFactory.updateHolding(cost.getPortfolio(), cost.getTransaction().getSecurity(), cost.getBalance(), cost.getCost());
	}

	@Override
	public List<Holding> getHolding(Portfolio portfolio) {
		return holdingRepo.findByPortfolioOrderBySecurityAsc(portfolio);
	}

	@Override
	public List<Transaction> getDividend(Portfolio portfolio) {
		return txnRepo.findDividendByPortfolio(portfolio);
	}

}
