package com.nadee.myportfolio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nadee.myportfolio.model.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {
	Portfolio findByName(String name);

}
