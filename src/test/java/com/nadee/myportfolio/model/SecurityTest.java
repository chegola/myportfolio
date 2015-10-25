package com.nadee.myportfolio.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
import com.nadee.myportfolio.factory.SecurityFactory;
import com.nadee.myportfolio.repositories.SecurityRepository;


@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes={com.nadee.myportfolio.config.ApplicationConfig.class})
@ContextConfiguration(classes={com.nadee.myportfolio.config.ApplicationConfig.class, com.nadee.myportfolio.config.DevConfiguration.class})
@ActiveProfiles("dev")
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class SecurityTest {

	@Autowired private SecurityRepository portfolioRepo;
	@Autowired private SecurityFactory portfolioFactory;
    
    @Test
    public void testInsert() {
    	
    	assertNull(portfolioRepo.findBySymbol("symb"));
    	portfolioFactory.createSecurity("symb");
     	
    	Security another = portfolioRepo.findBySymbol("symb");
    	assertEquals(another.getSymbol(), "symb");
   }
}
