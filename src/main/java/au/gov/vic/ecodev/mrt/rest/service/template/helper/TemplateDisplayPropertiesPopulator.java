package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class TemplateDisplayPropertiesPopulator {

	private static final Logger LOGGER = Logger.getLogger(TemplateDisplayPropertiesPopulator.class);
	
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public TemplateDisplayPropertiesPopulator(JdbcTemplate jdbcTemplate) {
		if (null == jdbcTemplate) {
			throw new IllegalArgumentException("TemplateDisplayPropertiesPopulator:jdbcTemplate cannot be null!");
		}
		this.jdbcTemplate = jdbcTemplate;
	}

	public void doPopulation(Map<String, List<Map<String, Object>>> resultMap, 
			List<String> classesList, Map<String, Object> templateFieldMap, 
			Map<String, Object> templateHeadersMap, long batchId) {
		if ((!CollectionUtils.isEmpty(classesList)) && (MapUtils.isNotEmpty(templateFieldMap))) {
			classesList.stream().forEach(cls -> {
				doSingleTemplatePopulation(resultMap, templateFieldMap, templateHeadersMap,
						cls, batchId);
			});
		}

	}

	protected final void doSingleTemplatePopulation(Map<String, 
			List<Map<String, Object>>> resultMap, Map<String, Object> templateFieldMap, 
			Map<String, Object> templateHeadersMap,
			final String templateName, final long batchId) {
		try {
			new TemplateHeaderRetriever(jdbcTemplate)
				.extractTemplateHeaders(resultMap, batchId, templateName);
			new TemplateMandatoryHeaderFieldsExtractionHelper(jdbcTemplate)
				.extractTemplateHeaderFields(resultMap, batchId, templateName);
			int mandatoryColumnCount = new TemplateMandatoryHeaderColumnCounter(resultMap, templateName)
					.getColumnCount();
			new TemplateMandatoryFieldsExtractionHelper(jdbcTemplate)
				.extractMandatoryFields(resultMap, templateFieldMap, batchId, templateName);
			
			new TemplateOptionalFieldsRetriever(jdbcTemplate).extractOptionalFields(resultMap, 
					templateHeadersMap, mandatoryColumnCount,
					batchId, templateName);
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			resultMap.put(templateName, null);
		}
	}
}
