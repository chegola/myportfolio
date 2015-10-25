package com.nadee.myportfolio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nadee.myportfolio.model.Holding;
import com.nadee.myportfolio.model.Portfolio;
import com.nadee.myportfolio.model.Security;

public interface HoldingRepository extends JpaRepository<Holding, Integer> {
	
	public Holding findByPortfolioAndSecurityOrderBySecurityAsc(Portfolio portfolio, Security security);

	@Query("SELECT h FROM Holding h JOIN h.security s WHERE h.balance > 0  and h.portfolio = :portfolio ORDER BY s.symbol ASC")
	public List<Holding> findByPortfolioOrderBySecurityAsc(@Param("portfolio") Portfolio portfolio);
}
