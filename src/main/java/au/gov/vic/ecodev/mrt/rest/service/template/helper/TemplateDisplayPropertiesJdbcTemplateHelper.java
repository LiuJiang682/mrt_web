package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import static au.gov.vic.ecodev.mrt.common.Constants.Strings;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import au.gov.vic.ecodev.utils.sql.helper.DbTableNameSqlInjectionFilter;

public class TemplateDisplayPropertiesJdbcTemplateHelper {

	private static final String SELECT_HEADERS_SQL = "SELECT TEMPLATE, HEADER_FIELDS FROM TEMPLATE_DISPLAY_PROPERTIES";
	private static final String SELECT_TEMPLATE_RETRIEVER_SQL = "SELECT TEMPLATE, TEMPLATE_RETRIEVER FROM TEMPLATE_DISPLAY_PROPERTIES";
	private static final String SQL_WHERE_CAUSE = " WHERE LOADER_ID = ?";
	private static final String SQL_FROM = " FROM ";
	private static final String SQL_SELECT = "SELECT ";
	
	private final JdbcTemplate jdbcTemplate;
	
	public TemplateDisplayPropertiesJdbcTemplateHelper(final JdbcTemplate jdbcTemplate) {
		if (null == jdbcTemplate) {
			throw new IllegalArgumentException("TemplateDisplayPropertiesJdbcTemplateHelper:jdbcTemplate cannot be null!");
		}
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Map<String, Object>> getList(final String template, final String fields, final long batchId) {
		String sql = null;
		
		if (DbTableNameSqlInjectionFilter.foundSqlInjectedTableName(template)) {
			throw new RuntimeException("template name has been SQL inject attacked! It contains: " + template);
		}
		
		if (fields.contains(Strings.JSON_SQL_TAG)) {
			sql = fields.substring(fields.lastIndexOf(Strings.JSON_SQL_TAG) + Strings.JSON_SQL_TAG.length(), 
					fields.length());
			if (DbTableNameSqlInjectionFilter.foundSqlInjectedSqlTag(sql)) {
				throw new RuntimeException("SQL Tag has been SQL inject attacked! It contains: " + template);
			}
		} else {
			if (DbTableNameSqlInjectionFilter.foundSqlInjectedColumnName(fields)) {
				throw new RuntimeException("field name has been SQL inject attacked! It contains: " + fields);
			}
			sql = new StringBuilder(SQL_SELECT)
				.append(fields)
				.append(SQL_FROM)
				.append(template)
				.append(SQL_WHERE_CAUSE)
				.toString();
		}
		List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, 
				new Object[] { batchId });
		return results;
	}
	
	public List<Map<String, Object>> getTemplateRetrieverClasses() {
		return jdbcTemplate.queryForList(SELECT_TEMPLATE_RETRIEVER_SQL);
	}
	
	public List<Map<String, Object>> getTemplateHeaders() {
		return jdbcTemplate.queryForList(SELECT_HEADERS_SQL);
	}
}
