package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import au.gov.vic.ecodev.mrt.common.Constants.Numeral;
import au.gov.vic.ecodev.mrt.common.Constants.Strings;

public class TemplateHeaderConfigMapHelper {

	private final Map<String, Object> templateHeadersMap;
	private final String templateName;
	
	public TemplateHeaderConfigMapHelper(final Map<String, Object> templateHeadersMap,
		final String templateName) {
		if (null == templateHeadersMap) {
			throw new IllegalArgumentException("TemplateHeaderConfigMapHelper:templateHeadersMap parameter cannot be null!");
		}
		this.templateHeadersMap = templateHeadersMap;
		if (StringUtils.isEmpty(templateName)) {
			throw new IllegalArgumentException("TemplateHeaderConfigMapHelper:templateName parameter cannot be null or empty!");
		}
		this.templateName = templateName;
	}

	public Map<String, Boolean> getHeaderConfig() {
		Map<String, Boolean> headerConfigMap = new HashMap<>();
		Object object = templateHeadersMap.get(templateName);
		if (null != object) {
			String headerString = (String) object;
			String[] headers = headerString.split(Strings.COMMA);
			
			for (String header : headers) {
				String[] headerConfig = header.split(Strings.HYPEN);
				if (Numeral.TWO == headerConfig.length) {
					headerConfigMap.put(headerConfig[Numeral.ZERO], 
						Boolean.valueOf(headerConfig[Numeral.ONE]));
				} else {
					headerConfigMap.put(headerConfig[Numeral.ZERO], Boolean.FALSE);
				}
			}
		}
		
		return headerConfigMap;
	}
}
