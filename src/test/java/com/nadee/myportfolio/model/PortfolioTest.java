package com.nadee.myportfolio.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.nadee.myportfolio.config.ApplicationConfig;
import com.nadee.myportfolio.config.DevConfiguration;
import com.nadee.myportfolio.factory.PortfolioFactory;
import com.nadee.myportfolio.repositories.PortfolioRepository;

@ContextConfiguration(classes = { ApplicationConfig.class, DevConfiguration.class })
//@ActiveProfiles("dev")
@Transactional
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
public class PortfolioTest {

	@Autowired
	private PortfolioRepository portfolioRepo;
	@Autowired private PortfolioFactory portfolioFactory;

	@Test
	public void testInsert() {
		assertNull(portfolioRepo.findByName("another"));
    	portfolioFactory.createPortfolio("another");
     	
    	Portfolio another = portfolioRepo.findByName("another");
    	assertEquals(another.getName(), "another");
	}

	@Test
	public void testFindAll() {
		 List<Portfolio> portList = portfolioRepo.findAll();
		 for (Portfolio port : portList) {
			 System.out.println(port.getPortfolioId() + ":" + port.getName());
		 }

	 }

	@Test
	public void testDelete() {
		assertNull(portfolioRepo.findByName("anotherOne"));
    	portfolioFactory.createPortfolio("anotherOne");
     	
    	Portfolio another = portfolioRepo.findByName("anotherOne");
    	portfolioRepo.delete(another);
		assertNull(portfolioRepo.findByName("anotherOne"));
    	
	}

}
