package au.gov.vic.ecodev.mrt.rest.service.template;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import au.gov.vic.ecodev.mrt.common.Constants.Numeral;
import au.gov.vic.ecodev.mrt.common.Constants.Strings;
import au.gov.vic.ecodev.mrt.dao.SessionHeaderDao;
import au.gov.vic.ecodev.mrt.model.SessionHeader;
import au.gov.vic.ecodev.mrt.rest.service.template.helper.TemplateClassesListHelper;
import au.gov.vic.ecodev.mrt.rest.service.template.helper.TemplateDisplayPropertiesHelper;
import au.gov.vic.ecodev.mrt.rest.service.template.helper.TemplateDisplayPropertiesPopulator;
import au.gov.vic.ecodev.mrt.rest.service.template.helper.headers.HeaderMappingHelper;

@Service
public class TemplateDataServicesImpl implements TemplateDataServices {

	private static final Logger LOGGER = Logger.getLogger(TemplateDataServicesImpl.class);
	
	private static final String REGEX_VERTICAL_DELIM = "\\|";

	private static final String VERTICAL_DELIM = "|";

	private static final Pattern DATA_RECORD_HEADER_PATTERN = Pattern.compile("^D\\d{1,}$");
	
	@Autowired
	private SessionHeaderDao sessionHeaderDao;
	@Autowired
	private TemplateDisplayPropertiesHelper templateDisplayPropertiesHelper;
	@Autowired
	private TemplateClassesListHelper templateClassesListHelper;
	@Autowired
	private TemplateDisplayPropertiesPopulator templateDisplayPropertiesPopulator;
	@Autowired
	private HeaderMappingHelper headerMappingHeader;
			
	@Override
	public Map<String, Map<String, List<Map<String, Object>>>> getAllTemplateData(String batchId) throws Exception {
		LOGGER.info("TemplateDataServicesImpl.getAllTemplateData -- batchId: " + batchId);
		Map<String, List<Map<String, Object>>> resultMap = this.retrieveDisplayData(batchId);
		return groupRecordsByTemplate(resultMap);
	}

	/**
	 * This method formats the records by grouping the records into template and sorting
	 * the records in the headers, optional headers and data order.
	 * 
	 * @param resultMap the data records from database
	 * @return grouped and sorted data records.
	 */
	protected final Map<String, Map<String, List<Map<String, Object>>>> groupRecordsByTemplate(
			Map<String, List<Map<String, Object>>> resultMap) {
		Map<String, Map<String, List<Map<String, Object>>>> groupedMap = new HashMap<>();
		List<String> templates = new ArrayList<>();
		Map<String, Map<String, Map<String, Map<String, Object>>>> cache = new HashMap<>();
		doDataGroupping(resultMap, groupedMap, templates, cache);
		
		doTemplateRecordOrderSorting(groupedMap, cache);
		// LOGGER.info(groupedMap);
		return groupedMap;
	}

	/**
	 * This method sorts the records in headers, optional headers and data record order.
	 * @param groupedMap the final data map
	 * @param cache the grouped data map
	 */
	protected final void doTemplateRecordOrderSorting(Map<String, Map<String, List<Map<String, Object>>>> groupedMap,
			Map<String, Map<String, Map<String, Map<String, Object>>>> cache) {
		cache.forEach((template, templateFileValue) -> {
			// LOGGER.info(template + " " + templateFileValue);
			Map<String, List<Map<String, Object>>> templateFiles = new HashMap<>();
			templateFileValue.forEach((templateFile, templateFileValues) -> {
				// LOGGER.info(templateFile + " " + templateFileValues);
				List<Map<String, Object>> dataList = templateFiles.get(templateFile);
				if (null == dataList) {
					dataList = new ArrayList<>();
					templateFiles.put(templateFile, dataList);
				}
				Map<String, Object> headers = new HashMap<>();
				TreeMap<String, Map<String, Object>> dataMap = new TreeMap<>();
				TreeMap<String, Map<String, Object>> headersMap = new TreeMap<>();
				templateFileValues.forEach((templateFileEntryKey, 
						templateFielEntryValue) -> {
					// LOGGER.info(templateFileEntryKey + " " + templateFielEntryValue);
					if (Strings.HEADERS.equalsIgnoreCase(templateFileEntryKey)) {
						headers.putAll(templateFielEntryValue);
					} else if (DATA_RECORD_HEADER_PATTERN.matcher(templateFileEntryKey)
							.matches()) {
						dataMap.put(templateFileEntryKey, templateFielEntryValue);
					} else {
						headersMap.put(templateFileEntryKey, templateFielEntryValue);
					}
				});
				TreeMap<String, Map<String, Object>> correctTitleMap = 
						doDataMapHeaderMatching(template, headers, dataMap);
				dataList.add(headers);
				dataList.addAll(headersMap.values());
				dataList.addAll(correctTitleMap.values());
			});
			groupedMap.put(template, templateFiles);
		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected final TreeMap<String, Map<String, Object>> doDataMapHeaderMatching(String templateName, 
			Map<String, Object> headers, 
			TreeMap<String, Map<String, Object>> dataMap) {
		TreeMap<String, Map<String, Object>> displayMap = new TreeMap<>();
		
		List<String> displayHeaders = new ArrayList(headers.values());
		
		BiConsumer<? super String, ? super Map<String, Object>> titleConsumer = (dataKey, dataValue) -> {
			Map<String, Object> correctTitleMap = new HashMap<>();
			BiConsumer<? super String, ? super Object> dataTitleConsumer = (title, value) -> {
				if (!displayHeaders.contains(title)) {
					String newTitle = headerMappingHeader.lookUp(templateName, title);
					if (!StringUtils.isEmpty(newTitle)) {
						if (newTitle.contains(VERTICAL_DELIM)) {
							String[] titles = newTitle.split(REGEX_VERTICAL_DELIM);
							for (String myTitle : titles) {
								if (displayHeaders.contains(myTitle)) {
									title = myTitle;
									break;
								}
							}
						} else {
							if (displayHeaders.contains(newTitle)) {
								title = newTitle;
							}
						}
					}
				}
				correctTitleMap.put(title, value);
			};
			dataValue.forEach(dataTitleConsumer);
			displayMap.put(dataKey, correctTitleMap);
		};
		dataMap.forEach(titleConsumer);

		return displayMap;
	}

	/**
	 * This method groups the data from database into the cache map by the template whose records belong.
	 * 
	 * @param resultMap the map from database.
	 * @param groupedMap the map contains data groups by template they belong.
	 * @param templates the template list associated with the result Map.
	 * @param cache the map for grouped data.
	 */
	protected final void doDataGroupping(Map<String, List<Map<String, Object>>> resultMap,
			Map<String, Map<String, List<Map<String, Object>>>> groupedMap, 
			List<String> templates,
			Map<String, Map<String, Map<String, Map<String, Object>>>> cache) {
		// LOGGER.info(resultMap);
		resultMap.forEach((key, value) -> {
			 // LOGGER.info("k: " + key);
			 // LOGGER.info("v:" + value);
			String[] templateAndRows = getTemplateAndRows(key); 
			String template = templateAndRows[Numeral.ZERO];
			if (Numeral.ONE == templateAndRows.length) {
				//No data case
				LOGGER.error("Got WRONG templateAndRow: " + templateAndRows);
				//groupedMap.put(k, v);
			} else {
				String fileName = templateAndRows[Numeral.ONE];
				String rowLabel = templateAndRows[Numeral.TWO];
				Map<String, Map<String, Map<String, Object>>> files;
				Map<String, Map<String, Object>> rows;
				if (templates.contains(template)) {
					files = cache.get(template);
				} else {
					files = new HashMap<>();
					templates.add(template);
					cache.put(template, files);
				}
				// LOGGER.info(value);
				rows = files.get(fileName);
				if (null == rows) {
					rows = new HashMap<>();
					files.put(fileName, rows);
				}
				rows.put(rowLabel, value.get(Numeral.ZERO));
				// LOGGER.warn(rows);
			}
		});
	}
	
	protected final String[] getTemplateAndRows(String k) {
		String[] templateAndRows = k.split(Strings.UNDER_LINE);
		if (3 < templateAndRows.length) {
			String template = templateAndRows[Numeral.ZERO];
			String header = templateAndRows[templateAndRows.length - Numeral.ONE];
			int len = templateAndRows.length - Numeral.ONE;
			String[] newDatas = Arrays.copyOfRange(templateAndRows, Numeral.ONE, len);
			String fileName = String.join(Strings.UNDER_LINE, newDatas);
			templateAndRows = new String[3];
			templateAndRows[Numeral.ZERO] = template;
			templateAndRows[Numeral.ONE] = fileName;
			templateAndRows[Numeral.TWO] = header;
		}
		return templateAndRows;
	}

	protected final Map<String, List<Map<String, Object>>> retrieveDisplayData(String batchId) throws Exception {
		SessionHeader sessionHeader = (SessionHeader) sessionHeaderDao
				.get(Long.parseLong(batchId));
		Map<String, List<Map<String, Object>>> resultMap = new HashMap<>();
		if (null != sessionHeader) {
			String templates = sessionHeader.getTemplate().toUpperCase();
			List<String> templateList = extractUniqueTemplate(templates);
			for(String template : templateList) {
				Map<String, Object> templateFieldMap = templateDisplayPropertiesHelper
						.getTemplateDisplayProperties(template);
				List<String> classesList = templateClassesListHelper
						.getTemplateClassesList(template);
				templateDisplayPropertiesPopulator
					.doPopulation(resultMap, classesList, templateFieldMap, Long.parseLong(batchId));
			}
		}
		return resultMap;
	}

	protected final List<String> extractUniqueTemplate(String templates) {
		if (StringUtils.isEmpty(templates)) {
			throw new IllegalArgumentException("TemplateDataServicesImpl.extractUniqueTemplate -- templates parameter cannot be null!");
		}
		String[] templateArray = templates.split(Strings.COMMA);
		String[] distinctTemplates = Arrays.stream(templateArray)
				.distinct()
				.toArray(String[]::new);
		return Arrays.asList(distinctTemplates);
	}

	@Override
	public List<String> getSingleTemplateData(String templateName, String batchId) {
		LOGGER.info("TemplateDataServicesImpl.getSingleTemplateData -- templateName: " 
				+ templateName + ", batchId: " + batchId);
		return new ArrayList<>();
	}

}
