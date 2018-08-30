package au.gov.vic.ecodev.mrt.rest.service.template.retriever.vgphydro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import au.gov.vic.ecodev.mrt.rest.service.template.helper.TemplateHeaderExtractor;
import au.gov.vic.ecodev.mrt.rest.service.template.helper.TemplateHeaderFileNameHelper;
import au.gov.vic.ecodev.mrt.rest.service.template.helper.TemplateMandatoryFieldsExtractionHelper;
import au.gov.vic.ecodev.mrt.rest.service.template.retriever.TemplateDataRetriever;

public class VgpHydroTemplateDataRetriever implements TemplateDataRetriever {

	private JdbcTemplate jdbcTemplate;
	private Map<String, List<Map<String, Object>>> resultMap;
	private Map<String, Object> templateFieldMap;
	@SuppressWarnings("unused")
	private Map<String, Boolean> templateHeadersMap;
	private String templateName;
	private long sessionId;
	
	@Override
	public void doDataRetrieve() {
		if (!"LOC".equalsIgnoreCase(templateName)) {
			Map<String, String> templateTableMap = new HashMap<>();
			new TemplateHeaderExtractor(templateFieldMap, templateName)
				.doHeadersExtraction(resultMap, templateTableMap);
			new TemplateHeaderFileNameHelper(jdbcTemplate, templateTableMap,
					templateName, sessionId)
				.doFileNameUpdate(resultMap);
			new TemplateMandatoryFieldsExtractionHelper(jdbcTemplate)
				.extractMandatoryFields(resultMap, templateFieldMap, sessionId, templateName);
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
