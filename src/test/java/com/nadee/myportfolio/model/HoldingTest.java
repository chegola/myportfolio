package com.nadee.myportfolio.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.nadee.myportfolio.config.ApplicationConfig;
import com.nadee.myportfolio.config.DevConfiguration;
import com.nadee.myportfolio.factory.HoldingFactory;
import com.nadee.myportfolio.repositories.HoldingRepository;

@ContextConfiguration(classes = { ApplicationConfig.class, DevConfiguration.class })
//@ActiveProfiles("dev")
@Transactional
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
public class HoldingTest {

	@Autowired private HoldingRepository holdingRepo;
	@Autowired private HoldingFactory holdingFactory;

	@Test
	public void testInsertAndUpdate() {
		
		Portfolio p = new Portfolio();
		p.setPortfolioId(1);
		p.setName("test");
		
		Security s = new Security();
		s.setSecurityId(1);
		s.setSymbol("AOT");
		
		Integer portfolioId = 1;
		Integer securityId = 1;
		Double balance = 1000.0;
		Double cost = 1000.0;
		assertNull(holdingRepo.findByPortfolioAndSecurityOrderBySecurityAsc(p, s));
    	
		holdingFactory.updateHolding(p, s, balance, cost);
    	Holding holding = holdingRepo.findByPortfolioAndSecurityOrderBySecurityAsc(p, s);

    	assertNotNull(holding);
    	assertEquals(1000.0, holding.getBalance(), 0.1);
    	
    	holding.setBalance(1500.0);
    	
    	holdingRepo.save(holding);
    	assertEquals(1500.0, holding.getBalance(), 0.1);
    	
    	
	}

	@Test
	public void testFindByPortfolio() {
		Portfolio p = new Portfolio();
		p.setPortfolioId(1);
		p.setName("test");
		
		Security s = new Security();
		s.setSecurityId(1);
		s.setSymbol("AOT");
		
		//Integer p = 1;
		//Integer securityId = 1;
		Double balance = 1000.0;
		Double cost = 1000.0;
		holdingFactory.updateHolding(p, s, balance, cost);
		holdingFactory.updateHolding(p, s, balance+100, cost);
		holdingFactory.updateHolding(p, s, balance+200, cost);
		holdingFactory.updateHolding(p, s, balance+300, cost);
		
		s = new Security();
		s.setSecurityId(2);
		s.setSymbol("xxx");
		
		balance = 1000.0;
		holdingFactory.updateHolding(p, s, balance, cost);
		holdingFactory.updateHolding(p, s, balance+100, cost);
		
		s = new Security();
		s.setSecurityId(3);
		s.setSymbol("yyy");
		
		balance = 1000.0;
		holdingFactory.updateHolding(p, s, balance, cost);
		holdingFactory.updateHolding(p, s, balance+500, cost);
		
		
		List<Holding> holdingList = holdingRepo.findByPortfolioOrderBySecurityAsc(p);
		assertEquals(3, holdingList.size());
		assertEquals(1300.0, holdingList.get(0).getBalance(), 0.1);
		assertEquals(1100.0, holdingList.get(1).getBalance(), 0.1);
		assertEquals(1500.0, holdingList.get(2).getBalance(), 0.1);	
	 }
}
