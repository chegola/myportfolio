package com.nadee.myportfolio.factory;

import org.springframework.beans.factory.annotation.Autowired;

import com.nadee.myportfolio.repositories.SecurityRepository;
import com.nadee.myportfolio.model.Security;

public class SecurityFactory {

	@Autowired private SecurityRepository securityRepo;
	//private JpaService jpaService;

	public Security createSecurity(String symbol) {
		Security security = new Security(symbol);
		securityRepo.saveAndFlush(security);
		//jpaService.save(security);
		return security;
	}
}
