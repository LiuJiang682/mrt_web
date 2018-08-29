package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.common.Constants.Strings;
import au.gov.vic.ecodev.mrt.rest.service.template.retriever.TemplateDataRetriever;

@Component
public class TemplateDisplayPropertiesPopulator {

	private static final Logger LOGGER = Logger.getLogger(TemplateDisplayPropertiesPopulator.class);
	
	private final JdbcTemplate jdbcTemplate;
	private final Map<String, String> dataRetrieverClassMap;
	private final Map<String, Map<String, Map<String, Boolean>>> templateHeaderConfigMap;

	@Autowired
	public TemplateDisplayPropertiesPopulator(final JdbcTemplate jdbcTemplate, 
			final Map<String, String> dataRetrieverClassMap, 
			final Map<String, Map<String, Map<String, Boolean>>> templateHeaderConfigMap,
			final TemplateDisplayPropertiesHelper templateDisplayPropertiesHelper) {
		if (null == jdbcTemplate) {
			throw new IllegalArgumentException("TemplateDisplayPropertiesPopulator:jdbcTemplate cannot be null!");
		}
		this.jdbcTemplate = jdbcTemplate;
		this.dataRetrieverClassMap = dataRetrieverClassMap;
		this.templateHeaderConfigMap = templateHeaderConfigMap;
	}

	public void doPopulation(String parentTemplate,
			Map<String, List<Map<String, Object>>> resultMap, 
			List<String> classesList, Map<String, Object> templateFieldMap,  
			long batchId) {
		if ((!CollectionUtils.isEmpty(classesList)) && (MapUtils.isNotEmpty(templateFieldMap))) {
			classesList.stream().forEach(cls -> {
				try {
					doSingleTemplatePopulation(resultMap, templateFieldMap, 
							parentTemplate, cls, batchId);
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					throw new RuntimeException(e.getMessage(), e);
				}
			});
		}

	}

	protected final void doSingleTemplatePopulation(Map<String, 
			List<Map<String, Object>>> resultMap, Map<String, Object> templateFieldMap, 
			final String parentTemplate,
			final String templateName, final long batchId) throws Exception {
		TemplateDataRetriever templateDataRetriever =
				getTemplateDataRetriever(parentTemplate, templateName, dataRetrieverClassMap);
		templateDataRetriever.setJdbcTemplate(jdbcTemplate);
		templateDataRetriever.setResultMap(resultMap);
		templateDataRetriever.setTemplateFieldMap(templateFieldMap);
		Map<String, Map<String, Boolean>> map = templateHeaderConfigMap.get(parentTemplate.toUpperCase());
		 Map<String, Boolean> templateHeadersMap = map.get(templateName.toUpperCase());
		templateDataRetriever.setTemplateHeaderMap(templateHeadersMap);
		templateDataRetriever.setTemplateName(templateName);
		templateDataRetriever.setSessionId(batchId);
		templateDataRetriever.doDataRetrieve();
	}

	protected final TemplateDataRetriever getTemplateDataRetriever(final String parentTemplate,
			final String templateName, 
			Map<String, String> dataRetrieverClassMap) throws Exception {
		String key = new StringBuilder(parentTemplate)
				.append(Strings.HYPEN)
				.append(templateName)
				.toString();
		// LOGGER.info(key);
		String dataRetrieverClass = dataRetrieverClassMap.get(key.toUpperCase());
		Class<?> clazz = Class.forName(dataRetrieverClass);
		return (TemplateDataRetriever) clazz.newInstance();
	}
	
}
