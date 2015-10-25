package com.nadee.myportfolio.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.nadee.external.SecurityCreator;
import com.nadee.myportfolio.config.ApplicationConfig;

public class MyPortfolio {
	
	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		SecurityCreator sc = ctx.getBean(SecurityCreator.class);
		sc.createSecurity();
		
		SomeClient someClient = ctx.getBean(SomeClient.class);
	   
	      try {
	    	  someClient.doImport();
	      } catch (Exception e) {
	    	  e.printStackTrace();
	      }
		
		//someClient.doList();
		}

}
