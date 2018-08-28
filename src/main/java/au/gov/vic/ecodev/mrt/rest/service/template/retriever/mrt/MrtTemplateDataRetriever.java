package au.gov.vic.ecodev.mrt.rest.service.template.retriever.mrt;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import au.gov.vic.ecodev.mrt.rest.service.template.helper.TemplateHeaderRetriever;
import au.gov.vic.ecodev.mrt.rest.service.template.helper.TemplateMandatoryFieldsExtractionHelper;
import au.gov.vic.ecodev.mrt.rest.service.template.helper.TemplateMandatoryHeaderColumnCounter;
import au.gov.vic.ecodev.mrt.rest.service.template.helper.TemplateMandatoryHeaderFieldsExtractionHelper;
import au.gov.vic.ecodev.mrt.rest.service.template.helper.TemplateOptionalFieldsRetriever;
import au.gov.vic.ecodev.mrt.rest.service.template.retriever.TemplateDataRetriever;

public class MrtTemplateDataRetriever implements TemplateDataRetriever {

	private static final Logger LOGGER = Logger.getLogger(MrtTemplateDataRetriever.class);
	
	private JdbcTemplate jdbcTemplate;
	private Map<String, List<Map<String, Object>>> resultMap;
	private Map<String, Object> templateFieldMap;
	private Map<String, Boolean> templateHeadersMap;
	private String templateName;
	private long sessionId;

	@Override
	public void doDataRetrieve() {
		try {
			new TemplateHeaderRetriever(jdbcTemplate)
				.extractTemplateHeaders(resultMap, sessionId, templateName);
			new TemplateMandatoryHeaderFieldsExtractionHelper(jdbcTemplate)
				.extractTemplateHeaderFields(resultMap, sessionId, templateName);
			int mandatoryColumnCount = new TemplateMandatoryHeaderColumnCounter(resultMap, templateName)
					.getColumnCount();
			new TemplateMandatoryFieldsExtractionHelper(jdbcTemplate)
				.extractMandatoryFields(resultMap, templateFieldMap, sessionId, templateName);
			
			new TemplateOptionalFieldsRetriever(jdbcTemplate).extractOptionalFields(resultMap, 
					templateHeadersMap, mandatoryColumnCount,
					sessionId, templateName);
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			resultMap.put(templateName, null);
		}

	}

	@Override
	public void setResultMap(Map<String, List<Map<String, Object>>> resultMap) {
		this.resultMap = resultMap;
	}

	@Override
	public void setTemplateFieldMap(Map<String, Object> templateFieldMap) {
		this.templateFieldMap = templateFieldMap;
	}

	@Override
	public void setTemplateHeaderMap(Map<String, Boolean> templateHeadersMap) {
		this.templateHeadersMap = templateHeadersMap;
	}

	@Override
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	@Override
	public void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}

	@Override
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
