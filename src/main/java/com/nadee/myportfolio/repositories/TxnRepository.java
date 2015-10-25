package com.nadee.myportfolio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nadee.myportfolio.model.Cost;
import com.nadee.myportfolio.model.Portfolio;
import com.nadee.myportfolio.model.Security;
import com.nadee.myportfolio.model.Transaction;

public interface TxnRepository extends JpaRepository<Transaction, Integer> {
	
	@Query("SELECT t FROM Transaction t JOIN t.security s where t.portfolio = :portfolio AND t.txnType = 5 ORDER BY t.txnDate, s.symbol")
	List<Transaction> findDividendByPortfolio(@Param("portfolio") Portfolio portfolio);

	@Query("SELECT t FROM Transaction t where t.portfolio = :portfolio AND t.txnType = 3 ORDER BY t.txnDate")
	List<Transaction> findDepositByPortfolio(@Param("portfolio") Portfolio portfolio);

}
