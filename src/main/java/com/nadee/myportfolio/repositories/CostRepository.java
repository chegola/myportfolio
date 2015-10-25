package com.nadee.myportfolio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nadee.myportfolio.model.Cost;
import com.nadee.myportfolio.model.Portfolio;
import com.nadee.myportfolio.model.Security;

public interface CostRepository extends JpaRepository<Cost, Integer> {

	@Query("SELECT c FROM Cost c JOIN c.transaction t JOIN t.security s where  c.portfolio = :portfolio AND c.matched = :matched AND t.security = :security")
	List<Cost> findByPortfolioAndMatchedOrderByCostIdAsc(@Param("portfolio") Portfolio portfolio, @Param("matched") Short matched,  @Param("security") Security security);
	
	@Query("SELECT c FROM Cost c where c.costId = (SELECT MAX(co.costId) FROM Cost co JOIN co.transaction t JOIN t.security s WHERE co.matched = 0 and co.portfolio = :portfolio AND t.security = :security)")
	public Cost findByPortfolioAndLatestCostIdAndSecurity(@Param("portfolio") Portfolio portfolio, @Param("security") Security security);

	@Query("SELECT c FROM Cost c JOIN c.transaction t WHERE c.portfolio = :portfolio AND t.transactionId = :transactionId ORDER BY c.costId ASC ")
	public List<Cost> findByPortfolioAndTxnIdOrderByCostIdAsc(@Param("portfolio") Portfolio portfolio, @Param("transactionId") Integer transactionId);


	@Query("SELECT c FROM Cost c JOIN c.transaction t JOIN t.security s WHERE c.portfolio = :portfolio AND t.security= :security ORDER BY c.costId ASC ")
	public List<Cost> findByPortfolioAndSecurituOrderByCostIdAsc(@Param("portfolio") Portfolio portfolio, @Param("security") Security security);

	
};
