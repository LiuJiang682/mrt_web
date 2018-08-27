package au.gov.vic.ecodev.mrt.rest.service.template.retriever;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

public interface TemplateDataRetriever {

	void doDataRetrieve();
	
	void setResultMap(final Map<String, List<Map<String, Object>>> resultMap);
	void setTemplateFieldMap(final Map<String, Object> templateFieldMap);
	void setTemplateHeaderMap(final Map<String, Object> templateHeadersMap);
	void setTemplateName(final String templateName);
	void setSessionId(final long sessionId);
	void setJdbcTemplate(final JdbcTemplate jdbcTemplate);
}
