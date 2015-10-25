package com.nadee.myportfolio.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.nadee.myportfolio.config.ApplicationConfig;
import com.nadee.myportfolio.model.Cost;
import com.nadee.myportfolio.model.Portfolio;
import com.nadee.myportfolio.model.Security;
import com.nadee.myportfolio.model.Transaction;
import com.nadee.myportfolio.model.TransactionType;
import com.nadee.myportfolio.repositories.CostRepository;

@ContextConfiguration(classes = { ApplicationConfig.class })
// @ActiveProfiles("test")
@Transactional
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
public class CostManagerTest {

	@Mock private CostRepository costRepo;
//	@Mock private Portfolio portfolio;
	@Mock private Security security;
	private Transaction dummyTxn;
	private Cost stubCost;
	
	@InjectMocks
	private CostManager costMgr = new CostManager();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() {
		dummyTxn = null;
		stubCost = null;
	}
	@Test
	public void testSimpleBuy() throws Exception {
		// setup
		dummyTxn = new Transaction();
		dummyTxn.setTxnType(TransactionType.BUY.getDatabaseId());
		dummyTxn.setShare(20000.0);
		dummyTxn.setPrice(6.3);
		dummyTxn.setAmount(126000.0);
		
		// given
		given(costRepo.findByPortfolioAndLatestCostIdAndSecurity(any(Portfolio.class), any(Security.class))).willReturn(stubCost);
		
		// when
		Cost newCost = costMgr.createCost(dummyTxn);
		
		// then
		assertEquals(newCost.getCost(), 6.3, 0.1);
		assertEquals(newCost.getBalance(), 20000.0, 0.1);
		assertEquals(newCost.getRunningTotal(), 126000.0, 0.1);
		assertTrue(newCost.getMatched() == 0);
	}
	
	@Test
	public void testBuy2ndTxn() throws Exception {
		// setup
		dummyTxn = new Transaction();
		dummyTxn.setTxnType(TransactionType.BUY.getDatabaseId());
		dummyTxn.setShare(2000.0);
		dummyTxn.setPrice(6.2);
		dummyTxn.setAmount(12400.0);
		
		stubCost = new Cost();
		stubCost.setRunningTotal(126000.0);

		// given
		given(costRepo.findByPortfolioAndLatestCostIdAndSecurity(any(Portfolio.class),any(Security.class))).willReturn(stubCost);
		given(costRepo.findByPortfolioAndTxnIdOrderByCostIdAsc(any(Portfolio.class), any())).willReturn(createCostList(20000.0));
		
		// when
		Cost newCost = costMgr.createCost(dummyTxn);
		
		// then
		assertEquals(newCost.getCost(), 6.291, 0.1);
		assertEquals(newCost.getBalance(), 22000.0, 0.1);
		assertEquals(newCost.getRunningTotal(), 138400.0, 0.1);
		assertTrue(newCost.getMatched() == 0);

	}

	
	@Test
	public void testBuy3ndTxn() throws Exception {
		// setup
		dummyTxn = new Transaction();
		dummyTxn.setTxnType(TransactionType.BUY.getDatabaseId());
		dummyTxn.setShare(5000.0);
		dummyTxn.setPrice(6.15);
		dummyTxn.setAmount(30750.0);
		
		stubCost = new Cost();
		stubCost.setRunningTotal(138400.0);

		// given
		given(costRepo.findByPortfolioAndLatestCostIdAndSecurity(any(Portfolio.class), any(Security.class))).willReturn(stubCost);
		given(costRepo.findByPortfolioAndTxnIdOrderByCostIdAsc(any(Portfolio.class), any())).willReturn(createCostList(22000.0));
		
		// when
		Cost newCost = costMgr.createCost(dummyTxn);
		
		// then
		assertEquals(newCost.getCost(), 6.265, 0.1);
		assertEquals(newCost.getBalance(), 27000.0, 0.1);
		assertEquals(newCost.getRunningTotal(), 169150.0, 0.1);
		assertTrue(newCost.getMatched() == 0);

	}

	@Test
	public void testSimpleSell() throws Exception {
		// setup
		dummyTxn = new Transaction();
		dummyTxn.setTxnType(TransactionType.SELL.getDatabaseId());
		dummyTxn.setShare(2500.0);
		dummyTxn.setPrice(12.0);
		dummyTxn.setAmount(30000.0);
		
		stubCost = new Cost();
		stubCost.setRunningTotal(195000.0);

		// given
		given(costRepo.findByPortfolioAndTxnIdOrderByCostIdAsc(any(Portfolio.class),any())).willReturn(createCostListSimpleSell());
		given(costRepo.findByPortfolioAndMatchedOrderByCostIdAsc(any(Portfolio.class), any(Short.class), any(Security.class))).willReturn(createCostListSimpleSell());
		given(costRepo.findByPortfolioAndLatestCostIdAndSecurity(any(Portfolio.class), any(Security.class))).willReturn(stubCost);
		
		// when
		Cost newCost = costMgr.createCost(dummyTxn);
		
		// then
		assertEquals(newCost.getCost(), 9.714, 0.1);
		assertEquals(newCost.getBalance(), 17500.0, 0.1);
		assertEquals(newCost.getRunningTotal(), 170000.0, 0.1);
		assertTrue(newCost.getMatched() == 0);
		assertEquals(newCost.getSaleCost(), 25000.0, 0.1);
		assertEquals(newCost.getRemainingCost(), 170000.0, 0.1);
	}


	
	private List<Cost> createCostListSimpleSell() {
		List<Cost> costList = new ArrayList<Cost>();
		Cost cost = new Cost();
		cost.setBalance(10000.0);
		cost.setRemainingshare(0.0);
		cost.setCost(10.0);
		cost.setMatched((short) 0);
		Transaction tmpTxn = new Transaction();
		tmpTxn.setShare(10000.0);
		cost.setTransaction(tmpTxn);
		costList.add(cost);
		
		cost = new Cost();
		cost.setBalance(20000.0);
		cost.setRemainingshare(0.0);
		cost.setMatched((short) 0) ;
		cost.setCost(9.750);
		tmpTxn = new Transaction();
		tmpTxn.setShare(10000.0);
		cost.setTransaction(tmpTxn);
		costList.add(cost);
		
		return costList;
	}

	
	
	private List<Cost> createCostList(Double balance) {
		List<Cost> costList = new ArrayList<Cost>();
		Cost cost = new Cost();
		cost.setBalance(balance);
		costList.add(cost);
		return costList;
	}

}
