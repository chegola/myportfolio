package com.nadee.myportfolio.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.nadee.myportfolio.config.ApplicationConfig;
import com.nadee.myportfolio.core.UserParameter;
import com.nadee.myportfolio.factory.TransactionFactory;
import com.nadee.myportfolio.repositories.TxnRepository;

@ContextConfiguration(classes = { ApplicationConfig.class })
//@ActiveProfiles("test")
@Transactional
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
public class TransactionTest {

	@Autowired private TxnRepository txnRepo;
	@Autowired private TransactionFactory txnFactory;
	
	private UserParameter userParam;

	@Before
	public void setUp(){
		userParam  = new UserParameter();
		
	}
	@Test
	public void testCreateBuyTransaction() throws Exception {
		//assertNull();
		//Transaction txn = txnFactory.createBuyTransaction(userParam);
		//assertEquals(txn.getTxnType().intValue(), TransactionType.BUY.getDatabaseId());
	}
}
