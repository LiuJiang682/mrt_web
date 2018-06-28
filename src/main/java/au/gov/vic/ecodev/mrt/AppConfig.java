package au.gov.vic.ecodev.mrt;

import org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import au.gov.vic.ecodev.mrt.web.repository.rest.filter.InvalidSessionIdFilter;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class })
@ComponentScan(basePackages = {"au.gov.vic.ecodev"})
public class AppConfig {
	@Bean
	public FilterRegistrationBean invalidFileLogSessionIdFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new InvalidSessionIdFilter());
		registration.addUrlPatterns("/fileLogs/search/get");
		return registration;
	}
}
