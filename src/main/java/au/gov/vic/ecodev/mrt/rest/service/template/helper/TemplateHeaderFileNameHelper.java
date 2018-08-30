package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import au.gov.vic.ecodev.mrt.common.Constants.Strings;
import au.gov.vic.ecodev.utils.sql.helper.DbTableNameSqlInjectionFilter;

public class TemplateHeaderFileNameHelper {

	private static final String FILE_NAME_SQL_PREFIX = "SELECT DISTINCT FILE_NAME FROM ";
	private static final String	FILE_NAME_SQL_SUFFIX = " WHERE LOADER_ID = ?";
	
	private final JdbcTemplate jdbcTemplate;
	private final Map<String, String> templateTableMap;
	private final String templateName;
	private final long sessionId;
	
	public TemplateHeaderFileNameHelper(JdbcTemplate jdbcTemplate, 
			Map<String, String> templateTableMap, 
			String templateName, long sessionId) {
		if (null == jdbcTemplate) {
			throw new IllegalArgumentException("TemplateHeaderFileNameHelper:jdbcTemplate parameter cannot be null!");
		}
		this.jdbcTemplate = jdbcTemplate;
		if (null == templateTableMap) {
			throw new IllegalArgumentException("TemplateHeaderFileNameHelper:templateTableMap parameter cannot be null!");
		}
		this.templateTableMap = templateTableMap;
		this.templateName = templateName;
		this.sessionId = sessionId;
	}

	public void doFileNameUpdate(Map<String, List<Map<String, Object>>> resultMap) {
		String resultKey = new StringBuilder(templateName)
				.append(Strings.HEADERS_SUFFIX)
				.toString();
		List<Map<String, Object>> headers = resultMap.remove(resultKey);
		if (CollectionUtils.isNotEmpty(headers)) {
			String templateTable = templateTableMap.get(templateName);
			List<Map<String, Object>> fileNames = 
					getFileName(jdbcTemplate, templateTable, sessionId);
			List<String> fileNameList = buildFileNameList(fileNames);
			fileNameList.stream()
				.forEach(fileName -> {
					String fileHeaderKey = new StringBuilder(templateName)
							.append(Strings.UNDER_LINE)
							.append(fileName)
							.append(Strings.HEADERS_SUFFIX)
							.toString();
					resultMap.put(fileHeaderKey, headers);
				});
		}
	}

	private List<String> buildFileNameList(List<Map<String, Object>> fileNames) {
		List<String> fileNameList = new ArrayList<>();
		fileNames.stream()
			.forEach(entry -> {
				fileNameList.add((String) entry.get(Strings.FILE_NAME));
			});
		return fileNameList;
	}

	protected List<Map<String, Object>> getFileName(JdbcTemplate jdbcTemplate, 
			String templateTable, long sessionId) {
		if (DbTableNameSqlInjectionFilter.foundSqlInjectedTableName(templateTable)) {
			throw new RuntimeException("template name has been SQL inject attacked! It contains: " + templateTable);
		}
		String sql = new StringBuilder(FILE_NAME_SQL_PREFIX)
				.append(templateTable.trim())
				.append(FILE_NAME_SQL_SUFFIX)
				.toString();
		return jdbcTemplate.queryForList(sql, 
				new Object[] { 
						 sessionId 
				});
	}

}
