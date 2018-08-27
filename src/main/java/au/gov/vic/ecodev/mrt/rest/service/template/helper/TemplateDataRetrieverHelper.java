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

import au.gov.vic.ecodev.mrt.common.Constants.Strings;

@Component
public class TemplateDataRetrieverHelper {
	
	private static final Logger LOGGER = Logger.getLogger(TemplateDataRetrieverHelper.class);

	private static final String TEMPLATE = "TEMPLATE";

	private static final String TEMPLATE_RETRIEVER = "TEMPLATE_RETRIEVER";

	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public TemplateDataRetrieverHelper(final JdbcTemplate jdbcTemplate) {
		if (null == jdbcTemplate) {
			throw new IllegalArgumentException("TemplateDataRetrieverHelper:jdbcTemplate parameter cannot be null!");
		}
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public Map<String, String> getDataRetrieverClassMap() {
		TemplateDisplayPropertiesJdbcTemplateHelper templateDisplayPropertiesJdbcTemplateHelper = 
				new TemplateDisplayPropertiesJdbcTemplateHelper(jdbcTemplate);
		List<Map<String, Object>> templateRetrieverList = 
				templateDisplayPropertiesJdbcTemplateHelper.getTemplateRetrieverClasses();
		Map<String, String> dataRetrieverClassMap = new HashMap<>();
		templateRetrieverList.forEach(map -> {
			String template = (String) map.get(TEMPLATE);
			String classList = (String) map.get(TEMPLATE_RETRIEVER);
			try {
				@SuppressWarnings("unchecked")
				Map<String, Object> response = new ObjectMapper().readValue(classList, 
						HashMap.class);
				response.forEach((key, value) -> {
					String mapKey = new StringBuilder(template)
							.append(Strings.HYPEN)
							.append(key)
							.toString();
					dataRetrieverClassMap.put(mapKey, (String) value);
				});
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
				throw new RuntimeException(e.getMessage(), e);
			}
		});
		return dataRetrieverClassMap;
	}
}
