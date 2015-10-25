package com.nadee.myportfolio.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
@Profile("dev")
public class DevConfiguration {

	
	/*@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
				.setName("db")
				.continueOnError(true)
				//.addScript("db/sql/create-db.sql")
				.build();
	}

	@Bean
	public ResourceDatabasePopulator databasePopulator() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.setSqlScriptEncoding("UTF-8");
		//populator.addScript(new ClassPathResource("db/sql/create-db1.sql"));
		return populator;
	}

	@Bean
	public InitializingBean populatorExecutor() {
		return new InitializingBean() {
			@Override
			public void afterPropertiesSet() throws Exception {
				DatabasePopulatorUtils.execute(databasePopulator(), dataSource());
			}
		};
	}*/
}