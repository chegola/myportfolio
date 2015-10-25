package com.nadee.myportfolio.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.nadee.myportfolio.config.ApplicationConfig;
import com.nadee.myportfolio.factory.PortfolioFactory;
import com.nadee.myportfolio.factory.SecurityFactory;
import com.nadee.myportfolio.factory.TransactionFactory;
import com.nadee.myportfolio.model.Cost;
import com.nadee.myportfolio.model.Holding;
import com.nadee.myportfolio.model.Portfolio;
import com.nadee.myportfolio.model.Security;
import com.nadee.myportfolio.model.Transaction;
import com.nadee.myportfolio.model.TransactionType;
import com.nadee.myportfolio.repositories.CostRepository;
import com.nadee.myportfolio.repositories.HoldingRepository;
import com.nadee.myportfolio.repositories.PortfolioRepository;
import com.nadee.myportfolio.repositories.SecurityRepository;
import com.nadee.myportfolio.repositories.TxnRepository;
import com.nadee.myportfolio.exception.*;

@ContextConfiguration(classes = { ApplicationConfig.class })
// @ActiveProfiles("test")
@Transactional
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
public class TradeManagerIT {
	@Autowired private TradeManagerImpl testSubject;
	@Autowired private HoldingRepository holdingRepo;
	
	@Before
	public void setUp() {
	}
/*
	@After
	public void tearDown() {
		portfolioRepo.delete(portfolio);
		secRepo.delete(security);
	}*/

/*	@Test(expected=NotEnoughUnitException.class)
	public void testNotEnoughUnit() throws Exception {
	    Transaction dummyTxn;

		// when: 1st day Sell without Holding
		Calendar calendar = new GregorianCalendar(2015,01,01);
		dummyTxn = createSellTxn(calendar.getTime(), 12.0, 2500.0);
		Cost newCost = costMgr.createCost(dummyTxn);
	}*/
	
	@Test
	public void testGetHolding() throws Exception {
		Holding holding = new Holding();
		Portfolio portfolio = new Portfolio();
		portfolio.setPortfolioId(1);
		holding.setPortfolio(portfolio);
		
		Security sec = new Security();
		sec.setSecurityId(1);
		holding.setSecurity(sec);
		holding.setBalance(100.0);
		holdingRepo.save(holding);

		sec = new Security();
		sec.setSecurityId(2);
		holding = new Holding();
		holding.setPortfolio(portfolio);
		holding.setSecurity(sec);
		holding.setBalance(430.0);
		holdingRepo.save(holding);
		
		holding = new Holding();
		holding.setPortfolio(portfolio);
		sec = new Security();
		sec.setSecurityId(4);
		holding.setSecurity(sec);
		holding.setBalance(1000.0);
		holdingRepo.save(holding);

		holding = new Holding();
		holding.setPortfolio(portfolio);
		sec = new Security();
		sec.setSecurityId(3);
		holding.setSecurity(sec);
		holding.setBalance(50.0);
		holdingRepo.save(holding);

		List<Holding> holdings = testSubject.getHolding(portfolio);
		
		for(Holding h : holdings) {
			System.out.println(h.getPortfolio().getPortfolioId() + ":" + h.getSecurity().getSecurityId()+ ":" + h.getBalance());
		}
	}

}
