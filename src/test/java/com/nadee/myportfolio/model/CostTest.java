package com.nadee.myportfolio.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.nadee.myportfolio.config.ApplicationConfig;
import com.nadee.myportfolio.repositories.CostRepository;

@ContextConfiguration(classes = { ApplicationConfig.class })
// @ActiveProfiles("test")
@Transactional
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
public class CostTest {

	@Autowired
	private CostRepository costRepo;

	private Portfolio portfolio;
	private Security security;

	@Before
	public void setUp() {
		portfolio = new Portfolio();
		portfolio.setPortfolioId(1);

	}

	@Test
	public void testFindByPortfolioAndMatched() throws Exception {
		security = new Security();
		security.setSecurityId(1);
		security.setSymbol("AOT");
		List<Cost> costList = costRepo.findByPortfolioAndMatchedOrderByCostIdAsc(portfolio, (short) 1, security);

		for (Cost cost : costList) {
			assertEquals(cost.getMatched().shortValue(), (short) 1);
		}

	}

	@Test
	public void testFindByPortfolioAndLatestCostId() throws Exception {
		//Cost cost = costRepo.findByPortfolioAndLatestCostId(portfolio);
		//System.out.println(cost.getRunningTotal());
	}

	@Test
	public void testFindByPortfolioOrderByCostId() throws Exception{
		List<Cost> costList = costRepo.findByPortfolioAndTxnIdOrderByCostIdAsc(portfolio, 1);
		
		for (Cost cost : costList) {
			System.out.println("CostId=" + cost.getCostId());
		}
	}
}
