package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

public class TemplateOptionalFieldsJdbcTemplateRetriever {

	private static final String SELECT_HEADER_SQL = "SELECT FILE_NAME, FIELD_VALUE FROM DH_OPTIONAL_FIELDS WHERE LOADER_ID = ? AND TEMPLATE_NAME =? AND TEMPLATE_HEADER = 'H1000' AND ROW_NUMBER = '0' AND COLUMN_NUMBER = 0";
	
	private static final String SELECT_SQL = "SELECT LOADER_ID, FILE_NAME, TEMPLATE_NAME, TEMPLATE_HEADER, ROW_NUMBER, COLUMN_NUMBER, FIELD_VALUE FROM DH_OPTIONAL_FIELDS WHERE LOADER_ID = ? AND TEMPLATE_NAME = ?";
	
	private final JdbcTemplate jdbcTemplate;
	
	public TemplateOptionalFieldsJdbcTemplateRetriever(JdbcTemplate jdbcTemplate) {
		if (null == jdbcTemplate) {
			throw new IllegalArgumentException("TemplateOptionalFieldsJdbcTemplateRetriever:jdbcTemplate cannot be null!");
		}
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Map<String, Object>> getHeaders(String template, long batchId) {
		List<Map<String, Object>> result = jdbcTemplate.queryForList(SELECT_HEADER_SQL,
				new Object[] {batchId, template});
		return result;
	}
	
	public List<Map<String, Object>> getData(String template, long batchId) {
		List<Map<String, Object>> result = jdbcTemplate.queryForList(SELECT_SQL, 
				new Object[] {batchId, template});
		return result;
	}
}
