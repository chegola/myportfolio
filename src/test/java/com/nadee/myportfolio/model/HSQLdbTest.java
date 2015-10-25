package com.nadee.myportfolio.model;

import java.util.ArrayList;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfig.class})
@Transactional
@TransactionConfiguration(defaultRollback=true)
//@ActiveProfiles("dev")
public class HSQLdbTest {

	@Autowired private PortfolioRepository portfolioRepo;
	@Autowired private PortfolioFactory portfolioFactory;


	@Test
	public void testInsertData(){
		Portfolio portf = new Portfolio();
		portf.setPortfolioId(1);
		portf.setName("test hsql");
		portfolioRepo.save(portf);
		
		 List<Portfolio> portList = portfolioRepo.findAll();
		 for (Portfolio port : portList) {
			 System.out.println(port.getPortfolioId() + ":" + port.getName());
		 }

		
	}
}
