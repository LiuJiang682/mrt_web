package au.gov.vic.ecodev.mrt;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

//import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = {"au.gov.vic.ecodev"})
public class DatabaseConfig {
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public JdbcTemplate getJdbcTemplate() {
		return new JdbcTemplate(dataSource);
	}
	
	@Configuration
	static class OracleDatabaseConfig {
		@Primary
		@Bean
		public DataSource dataSource(final Environment env) {
			DriverManagerDataSource datasource = new DriverManagerDataSource(); 
			datasource.setDriverClassName(env.getRequiredProperty("spring.datasource.driver-class-name"));
			datasource.setUrl(env.getRequiredProperty("spring.datasource.url"));
			datasource.setUsername(env.getRequiredProperty("spring.datasource.username"));
			datasource.setPassword(env.getRequiredProperty("spring.datasource.password"));
			return datasource;
		}
	}
	
	
	@Configuration
	static class H2DatabaseConfig {
		@Bean
		@Profile(StarterProfiles.TEST)
		public DataSource dataSource_test() {
			// no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
			EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
			EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2) // .H2 or .DERBY
					.addScript("db/sql/create-db.sql")
					.addScript("db/sql/insert-data.sql").build();
			return db;
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
