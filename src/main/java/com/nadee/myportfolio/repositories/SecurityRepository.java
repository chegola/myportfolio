package com.nadee.myportfolio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nadee.myportfolio.model.Security;

public interface SecurityRepository extends JpaRepository<Security, Integer> {
	Security findBySymbol(String symbol);
	
}
