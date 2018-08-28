package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import au.gov.vic.ecodev.mrt.common.Constants.Numeral;
import au.gov.vic.ecodev.mrt.common.Constants.Strings;

@Component
public class TemplateHeaderConfigRetrieverHelper {

	private static final Logger LOGGER = Logger.getLogger(TemplateHeaderConfigRetrieverHelper.class);
	
	private static final String HEADER_FIELDS = "HEADER_FIELDS";

	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public TemplateHeaderConfigRetrieverHelper(final JdbcTemplate jdbcTemplate) {
		if (null == jdbcTemplate) {
			throw new IllegalArgumentException("TemplateHeaderConfigRetrieverHelper:jdbcTemplate cannot be null!");
		}
		this.jdbcTemplate = jdbcTemplate;
	}

	public Map<String, Map<String, Map<String, Boolean>>> getTemplateHeaderConfigMap() {
		TemplateDisplayPropertiesJdbcTemplateHelper templateDisplayPropertiesJdbcTemplateHelper
			= new TemplateDisplayPropertiesJdbcTemplateHelper(jdbcTemplate);
		List<Map<String, Object>> configList = 
				templateDisplayPropertiesJdbcTemplateHelper.getTemplateHeaders();
		Map<String, Map<String, Map<String, Boolean>>> templateHeaderConfigMap = 
				new HashMap<>();
		configList.stream()
			.forEach(map -> {
				String template = (String) map.get(Strings.TEMPLATE);
				String headerConfig = (String) map.get(HEADER_FIELDS);
				try {
					@SuppressWarnings("unchecked")
					Map<String, Object> resultMap = new ObjectMapper().readValue(headerConfig, 
							HashMap.class);
					 Map<String, Map<String, Boolean>> headerConfigMap = getHeaderConfigMap(resultMap);
					 templateHeaderConfigMap.put(template, headerConfigMap);
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
					throw new RuntimeException(e.getMessage(), e);
				}
			});
		return templateHeaderConfigMap;
	}

	protected final Map<String, Map<String, Boolean>> getHeaderConfigMap(Map<String, Object> resultMap) {
		Map<String, Map<String, Boolean>> templateHeaderConfigMap = new HashMap<>();
		resultMap.forEach((key, value) -> {
			String headerString = (String) value;
			String[] headers = headerString.split(Strings.COMMA);
			Map<String, Boolean> headerConfigMap = new HashMap<>();
			for (String header : headers) {
				String[] headerConfig = header.split(Strings.HYPEN);
				if (Numeral.TWO == headerConfig.length) {
					headerConfigMap.put(headerConfig[Numeral.ZERO], 
						Boolean.valueOf(headerConfig[Numeral.ONE]));
				} else {
					headerConfigMap.put(headerConfig[Numeral.ZERO], Boolean.FALSE);
				}
			}
			templateHeaderConfigMap.put(key, headerConfigMap);
		});
		return templateHeaderConfigMap;
	}
	
	
}
