package au.gov.vic.ecodev.mrt;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

//import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DatabaseConfig {
	@Configuration
	static class OracleDatabaseConfig {
		@Bean
		public DataSource dataSource(final Environment env) {
			DriverManagerDataSource datasource = new DriverManagerDataSource(); System.err.println("ds_props" + env.getRequiredProperty("spring.datasource.driver-class-name"));
			datasource.setDriverClassName(env.getRequiredProperty("spring.datasource.driver-class-name"));
			datasource.setUrl(env.getRequiredProperty("spring.datasource.url"));
			datasource.setUsername(env.getRequiredProperty("spring.datasource.username"));
			datasource.setPassword(env.getRequiredProperty("spring.datasource.password"));
			return datasource;
		}
	}
//
//	@Profile({ StarterProfiles.STANDALONE, StarterProfiles.TEST })
//	@PropertySource("classpath:application-default.properties") // Not loaded by naming convention
//	@Configuration
//	static class StandaloneDatabaseConfig {
//		@Bean
//		public DataSource dataSource(final Environment env) {
//			final HikariDataSource ds = new HikariDataSource();
//			ds.setJdbcUrl(env.getRequiredProperty("h2.jdbcurl"));
//			ds.setUsername(env.getRequiredProperty("h2.username"));
//			return ds;
//		}
//	}
//
//	@Profile(StarterProfiles.STAGING)
//	@Configuration
//	static class StagingDatabaseConfig {
//		@Bean
//		public DataSource dataSource(final Environment env) {
//			final HikariDataSource ds = new HikariDataSource();
//			ds.setJdbcUrl(env.getRequiredProperty("psql.jdbcurl"));
//			ds.setUsername(env.getRequiredProperty("psql.username"));
//			return ds;
//		}
//	}
//
//	@Profile(StarterProfiles.PRODUCTION)
//	@Configuration
//	static class ProuctionDatabaseConfig {
//		@Bean
//		public DataSource dataSource(final Environment env) {
//			final HikariDataSource ds = new HikariDataSource();
//			ds.setJdbcUrl(env.getRequiredProperty("psql.jdbcurl"));
//			ds.setUsername(env.getRequiredProperty("psql.username"));
//			return ds;
//		}
//	}

}
