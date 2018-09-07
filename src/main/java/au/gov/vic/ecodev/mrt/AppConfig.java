package au.gov.vic.ecodev.mrt;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import au.gov.vic.ecodev.mrt.config.TemplateWebPropertiesConfig;
import au.gov.vic.ecodev.mrt.rest.service.template.helper.TemplateDataRetrieverHelper;
import au.gov.vic.ecodev.mrt.rest.service.template.helper.TemplateDisplayPropertiesHelper;
import au.gov.vic.ecodev.mrt.rest.service.template.helper.TemplateDisplayPropertiesPopulator;
import au.gov.vic.ecodev.mrt.rest.service.template.helper.TemplateHeaderConfigRetrieverHelper;
import au.gov.vic.ecodev.mrt.rest.service.template.helper.headers.HeaderMappingHelper;
import au.gov.vic.ecodev.mrt.rest.service.template.helper.headers.PropertyHeaderMappingHelper;
import au.gov.vic.ecodev.mrt.web.repository.rest.filter.InvalidSessionIdFilter;
import au.gov.vic.ecodev.mrt.web.repository.rest.filter.XContentTypeOptionFilter;
import au.gov.vic.ecodev.mrt.web.repository.rest.filter.XFrameOptionsFilter;
import au.gov.vic.ecodev.mrt.web.repository.rest.filter.XssProtectionHeaderFilter;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class })
@ComponentScan(basePackages = {"au.gov.vic.ecodev"})
public class AppConfig {
	
	@Autowired
	private DatabaseConfig databaseConfig;
	
	@Autowired
	private TemplateDataRetrieverHelper templateDataRetrieverHelper;
	
	@Autowired
	private TemplateDisplayPropertiesHelper templateDisplayPropertiesHelper;
	
	@Autowired
	private TemplateHeaderConfigRetrieverHelper templateHeaderConfigRetrieverHelper;
	
	@Bean
	public TemplateWebPropertiesConfig templateWebPropertiesConfig() {
		return new TemplateWebPropertiesConfig();
	}
	
	@Bean
	public EmbeddedServletContainerCustomizer
	  embeddedServletContainerCustomizer() {
		String contextPath = templateWebPropertiesConfig().getContextPath();
	    return container -> container.setContextPath(contextPath);
	}
	
	@Bean
	public TemplateDisplayPropertiesPopulator templateDisplayPropertiesPopulator() {
		Map<String, String> dataRetrieverMap = templateDataRetrieverHelper.getDataRetrieverClassMap();
		Map<String, Map<String, Map<String, Boolean>>> templateHeaderConfigMap = 
				templateHeaderConfigRetrieverHelper.getTemplateHeaderConfigMap();
		TemplateDisplayPropertiesPopulator templateDisplayPropertiesPopulator = 
				new TemplateDisplayPropertiesPopulator(databaseConfig.getJdbcTemplate(),
						dataRetrieverMap,
						templateHeaderConfigMap,
						templateDisplayPropertiesHelper);
		return templateDisplayPropertiesPopulator;
	}
	
	@Bean
	public FilterRegistrationBean invalidFileLogSessionIdFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new InvalidSessionIdFilter());
		registration.addUrlPatterns(templateWebPropertiesConfig().getSessionIdFilterUrls());
		return registration;
	}
	
	@Bean
	public FilterRegistrationBean xFrmeOptionFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new XFrameOptionsFilter());
		return registration;
	}
	
	@Bean
	public FilterRegistrationBean xssProtectionFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new XssProtectionHeaderFilter());
		return registration;
	}
	
	@Bean
	public FilterRegistrationBean xcontentTypeOptionFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new XContentTypeOptionFilter());
		return registration;
	}
	
	@Bean
	public HeaderMappingHelper propertyHeaderMappingHelper() throws Exception {
		String propertyString = templateWebPropertiesConfig().getTemplateHeaderMappingString();
		return new PropertyHeaderMappingHelper(propertyString);
	}
}
