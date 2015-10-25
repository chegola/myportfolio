package com.nadee.myportfolio.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.nadee.external.ReadTxnFromCsv;
import com.nadee.external.SecurityCreator;
import com.nadee.myportfolio.core.BuyTransaction;
import com.nadee.myportfolio.core.CostManager;
import com.nadee.myportfolio.core.DepositTransaction;
import com.nadee.myportfolio.core.DividendTransaction;
import com.nadee.myportfolio.core.SellTransaction;
import com.nadee.myportfolio.core.SomeClient;
import com.nadee.myportfolio.core.TradeManagerImpl;
import com.nadee.myportfolio.core.UserParameter;
import com.nadee.myportfolio.core.WithdrawTransaction;
import com.nadee.myportfolio.factory.HoldingFactory;
import com.nadee.myportfolio.factory.PortfolioFactory;
import com.nadee.myportfolio.factory.SecurityFactory;
import com.nadee.myportfolio.factory.TransactionFactory;
import com.nadee.myportfolio.factory.UserParameterFactory;

@Configuration
//@Profile("dev")
@EnableJpaRepositories("com.nadee.myportfolio.repositories")
@EnableTransactionManagement
public class ApplicationConfig {
	/*Basic DataSource*/
	
/*	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
				.setName("db")
				.continueOnError(true)
				//.addScript("db/sql/create-db.sql")
				.build();
	}
*/

	/*	@Bean
	public BasicDataSource dataSource() {
		BasicDataSource bds = new BasicDataSource();
		bds.setDriverClassName("com.mysql.jdbc.Driver");
		bds.setUrl("jdbc:mysql://localhost:3306/test");
		bds.setUsername("root");
		bds.setPassword("123456");
		bds.setInitialSize(3);
		return bds;
	}*/
	
		@Bean
	public BasicDataSource dataSourceHSQL() {
		BasicDataSource bds = new BasicDataSource();
		bds.setDriverClassName("org.hsqldb.jdbcDriver");
		bds.setUrl("jdbc:hsqldb:file:C:\\Development\\HSQL\\hsqldb;shutdown=true");
		bds.setUsername("SA");
		bds.setPassword("");
		bds.setInitialSize(1);
		bds.setMaxActive(5);
		return bds;
	}
	

	
/*	  @Bean
		public JdbcTemplate getJdbcTemplate() {
			return new JdbcTemplate(dataSource());
		}
*/	
 	/*Define EclipseLink JPA Vendor Adapter*/
	@Bean
	public EclipseLinkJpaVendorAdapter jpaVendorAdapter() {
		EclipseLinkJpaVendorAdapter jpaAdapter = new EclipseLinkJpaVendorAdapter();
		jpaAdapter.setDatabasePlatform("org.eclipse.persistence.platform.database.MySQLPlatform");
		jpaAdapter.setGenerateDdl(false);
		jpaAdapter.setShowSql(true);
		//jpaAdapter.setDatabase(Database.HSQL);
		
		return jpaAdapter;
	}

	/*Entity Manager Factory */
	@Bean
	public LocalEntityManagerFactoryBean entityManagerFactory() {
		LocalEntityManagerFactoryBean localEm = new LocalEntityManagerFactoryBean();
		//localEm.setPersistenceUnitName("hsql");
		localEm.setPersistenceUnitName("myportfolio");
		localEm.setJpaDialect(jpaDialect()); 
		localEm.setJpaVendorAdapter(jpaVendorAdapter());
		return localEm;
	}
	
	@Bean
	public EclipseLinkJpaDialect jpaDialect() {
		return new EclipseLinkJpaDialect();
	}
/*
	@Bean 
	public FactoryBean<EntityManagerFactory> localEntityManagerFactoryBean() { 
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean(); 
		entityManagerFactoryBean.setPersistenceUnitName("newportfolio"); 
		entityManagerFactoryBean.setDataSource(dataSourceHSQL()); 
		entityManagerFactoryBean.setJpaDialect(jpaDialect()); 
		entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter()); 
		entityManagerFactoryBean.setJpaPropertyMap(Collections.singletonMap("eclipselink.weaving", "false")); // TODO use load time weaving 
		// entityManagerFactoryBean.setLoadTimeWeaver(loadTimeWeaver); // TODO use load time weaving 
		return entityManagerFactoryBean; 
	} 
*/	

	/*Transaction Manager */
	@Bean
	public JpaTransactionManager transactionManager() throws Exception {
		JpaTransactionManager txMgr = new JpaTransactionManager();
		txMgr.setEntityManagerFactory(entityManagerFactory().getNativeEntityManagerFactory());
//		txMgr.setEntityManagerFactory(localEntityManagerFactoryBean().getObject());
		return txMgr;
	}
	/*JPA Service*/
	/*@Bean
	public JpaService jpaService() {
		return new JpaService();
	}
*/
	/*Portfolio factory*/
	@Bean
	public PortfolioFactory portfolioFactory() {
		return new PortfolioFactory();
	}

/*	@Bean
	public SomeClient someClient() {
		return new SomeClient();
	}*/
	/*Security factory*/
	@Bean
	public SecurityFactory securityFactory() {
		return new SecurityFactory();
	}
	
	@Bean
	public UserParameterFactory userParameterFactory() {
		return new UserParameterFactory();
	}
	
	@Bean
	public UserParameter userParameter() {
		return new UserParameter();
	}

	/*Transaction factory*/
	@Bean
	public TransactionFactory transactionFactory() {
		return new TransactionFactory();
	}
	
	@Bean
	public BuyTransaction buyTransaction() {
		return new BuyTransaction();
	}
	
	@Bean
	public SellTransaction sellTransaction() {
		return new SellTransaction();
	}
	
	@Bean
	public CostManager costManager() {
		return new CostManager();
	}
	
	@Bean
	public HoldingFactory holdingFactory() {
		return new HoldingFactory();
	}
	
	@Bean
	public TradeManagerImpl tradeManagerImpl() {
		return new TradeManagerImpl();
	}
	
	@Bean
	public DepositTransaction depositTransaction() {
		return new DepositTransaction();
	}
	
	@Bean
	public WithdrawTransaction withdrawTransaction() {
		return new WithdrawTransaction();
	}
	
	@Bean
	public DividendTransaction dividendTransaction() {
		return new DividendTransaction();
	}
	
	@Bean
	public ReadTxnFromCsv readTxnFromCsv() {
		return new ReadTxnFromCsv();
	}
	
	@Bean
	public SomeClient someClient() {
		return new SomeClient();
	}
	
	@Bean
	public SecurityCreator securityCreator() {
		return new SecurityCreator();
	}
	
}
