package au.gov.vic.ecodev.mrt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import au.gov.vic.ecodev.mrt.common.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.properties.TemplateProperties;

@Component
public class TemplateWebPropertiesConfig implements TemplateProperties {

	@Value("${session.id.filters.urls}")
	private String sessionIDFilterUrls;
	
	@Value("${template.header.mapping}")
	private String templateHeaderMappingString;

	public String getTemplateHeaderMappingString() {
		return templateHeaderMappingString;
	}

	public String[] getSessionIdFilterUrls() {
		return sessionIDFilterUrls.split(Strings.COMMA);
	}
}
