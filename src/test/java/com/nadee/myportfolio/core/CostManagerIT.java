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
import com.nadee.myportfolio.model.Portfolio;
import com.nadee.myportfolio.model.Security;
import com.nadee.myportfolio.model.Transaction;
import com.nadee.myportfolio.repositories.CostRepository;
import com.nadee.myportfolio.repositories.PortfolioRepository;
import com.nadee.myportfolio.repositories.SecurityRepository;
import com.nadee.myportfolio.repositories.TxnRepository;
import com.nadee.myportfolio.exception.*;

@ContextConfiguration(classes = { ApplicationConfig.class })
// @ActiveProfiles("test")
@Transactional
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
public class CostManagerIT {

	private final String SEC_NAME = "AOT";
	
	@Autowired private TransactionFactory txnFactory;
	@Autowired private PortfolioFactory portfolioFactory;
	@Autowired private SecurityFactory secFactory;
	@Autowired private UserParameter userParam;
	@Autowired private TxnRepository txnRepo;
	@Autowired private PortfolioRepository portfolioRepo;
	@Autowired private SecurityRepository secRepo;
	@Autowired private CostManager costMgr;
	
	Portfolio portfolio;
	Security  security;
	
	Integer portfolioId;
	
	//@InjectMocks
   //	@Mock private Portfolio portfolio;
	@Before
	public void setUp() {
		//MockitoAnnotations.initMocks(this);
		portfolio = portfolioFactory.createPortfolio("test");
		portfolioId = portfolio.getPortfolioId();
		security = secFactory.createSecurity(SEC_NAME);
	}

	@After
	public void tearDown() {
/*		portfolioRepo.delete(portfolio);
		secRepo.delete(security);*/
	}

	@Test(expected=NotEnoughUnitException.class)
	public void testNotEnoughUnit() throws Exception {
	    Transaction dummyTxn;

		// when: 1st day Sell without Holding
		Calendar calendar = new GregorianCalendar(2015,01,01);
		dummyTxn = createSellTxn(calendar.getTime(), 12.0, 2500.0);
		Cost newCost = costMgr.createCost(dummyTxn);
	}
	
	@Test
	public void testScenario1() throws Exception {
		// setup
	    Transaction dummyTxn;

		// when: 1st day Buy
		Calendar calendar = new GregorianCalendar(2015,01,01);
		dummyTxn = createBuyTxn(calendar.getTime(), 10.0, 10000.0);
		
		// then
		Cost newCost = costMgr.createCost(dummyTxn);

		assertEquals(10.0, newCost.getCost(), 0.1);
		assertEquals(10000.0, newCost.getBalance() , 0.1);
		assertEquals(100000.0, newCost.getRunningTotal(), 0.1);
		assertTrue(newCost.getMatched() == 0);
		
		// when: 2nd day Buy
		calendar = new GregorianCalendar(2015,01,02);
		dummyTxn = createBuyTxn(calendar.getTime(), 9.5, 10000.0);
	
		// then
		newCost = costMgr.createCost(dummyTxn);

		assertEquals(9.75, newCost.getCost(), 0.1);
		assertEquals(20000.0, newCost.getBalance(), 0.1);
		assertEquals(195000.0, newCost.getRunningTotal(), 0.1);
		assertTrue(newCost.getMatched() == 0);
		
		// when: 3rd day Sell
		calendar = new GregorianCalendar(2015, 01, 03);
		dummyTxn = createSellTxn(calendar.getTime(), 12.0, 2500.0);

		// then
		newCost = costMgr.createCost(dummyTxn);	
		assertEquals(9.714, newCost.getCost(), 0.1);
		assertEquals(17500.0, newCost.getBalance(), 0.1);
		assertEquals(170000.0, newCost.getRunningTotal(), 0.1);
		assertEquals(25000.0, newCost.getSaleCost(), 0.1);
		assertTrue(newCost.getMatched() == 0);
	
		// when: 4th day Sell
		calendar = new GregorianCalendar(2015, 01, 04);
		dummyTxn = createSellTxn(calendar.getTime(), 13.0, 5500.0);

		// then
		newCost = costMgr.createCost(dummyTxn);	
		assertEquals(9.583, newCost.getCost(), 0.1);
		assertEquals(12000.0, newCost.getBalance(), 0.1);
		assertEquals(115000.0, newCost.getRunningTotal(), 0.1);
		assertEquals(55000.0, newCost.getSaleCost(), 0.1);
		assertTrue(newCost.getMatched() == 0);

		// when: 5th day Sell
		calendar = new GregorianCalendar(2015, 01, 05);
		dummyTxn = createSellTxn(calendar.getTime(), 11.0, 5000.0);

		// then
		newCost = costMgr.createCost(dummyTxn);	
		assertEquals(9.393, newCost.getCost(), 0.1);
		assertEquals(7000.0,newCost.getBalance(), 0.1);
		assertEquals(65750.0, newCost.getRunningTotal(), 0.1);
		assertEquals(49250.0, newCost.getSaleCost(), 0.1);
		assertTrue(newCost.getMatched() == 0);

		// when: 6th day Sell
		calendar = new GregorianCalendar(2015, 01, 06);
		dummyTxn = createSellTxn(calendar.getTime(), 11.0, 7000.0);

		// then
		newCost = costMgr.createCost(dummyTxn);	
		assertEquals(0.0, newCost.getCost(), 0.1);
		assertEquals(0.0, newCost.getBalance(), 0.1);
	}
	
	private Transaction createSellTxn(Date time, Double price, Double share) throws Exception {
		userParam.setPortfolioId(portfolioId);;
		userParam.setSecuritySymbol("AOT");
		userParam.setPrice(price);
		userParam.setShare(share);
		final Transaction txn = txnFactory.createSellTransaction(userParam);
		return txn;
	}

	private Transaction createBuyTxn(Date date, Double price, Double share) throws Exception{
		userParam.setPortfolioId(portfolioId);
		userParam.setSecuritySymbol("AOT");
		userParam.setPrice(price);
		userParam.setShare(share);
		final Transaction txn = txnFactory.createBuyTransaction(userParam);
		return txn;
		
	}

}
