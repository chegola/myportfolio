package com.nadee.myportfolio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nadee.myportfolio.model.CashBalance;

public interface CashBalanceRepository extends JpaRepository<CashBalance, Integer> {
	CashBalance findByPortfolioId(Integer portfolioId);
}
