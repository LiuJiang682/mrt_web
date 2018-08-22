package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

public class TemplateMandatoryHeadersJdbcTemplateHelper {

	private static final String SELECT_SQL = "SELECT FILE_NAME, ROW_NUMBER, COLUMN_HEADER, FIELD_VALUE FROM DH_MANDATORY_HEADERS WHERE TEMPLATE_NAME = ? AND LOADER_ID = ?";
	
	private final JdbcTemplate jdbcTemplate;
	
	public TemplateMandatoryHeadersJdbcTemplateHelper(JdbcTemplate jdbcTemplate) {
		if (null == jdbcTemplate) {
			throw new IllegalArgumentException("TemplateMandatoryHeadersJdbcTemplateHelper:jdbcTemplate parameter cannot be null!");
		}
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Map<String, Object>> getList(final String templateName, long batchId) {
		List<Map<String, Object>> results = jdbcTemplate.queryForList(SELECT_SQL, 
				new Object[] { templateName, batchId });
		return results;
	}

}
