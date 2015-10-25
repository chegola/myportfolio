package com.nadee.external;

import org.springframework.beans.factory.annotation.Autowired;

import com.nadee.myportfolio.factory.SecurityFactory;

public class SecurityCreator {
	@Autowired SecurityFactory secFactory;
	public void createSecurity() {
		secFactory.createSecurity("CPALL");
		secFactory.createSecurity("EPCO");
		secFactory.createSecurity("KGI");
		secFactory.createSecurity("AOT");
		secFactory.createSecurity("CENTEL");
		secFactory.createSecurity("UTP");
		secFactory.createSecurity("PYLON");
		secFactory.createSecurity("HFT");
		secFactory.createSecurity("PS");
		secFactory.createSecurity("SCP");
		secFactory.createSecurity("ARROW");
		secFactory.createSecurity("SVI");
		secFactory.createSecurity("ASK");
		secFactory.createSecurity("KBANK");
		secFactory.createSecurity("AMATA");
		secFactory.createSecurity("KIAT");
		secFactory.createSecurity("TCCC");
		
	}
}
