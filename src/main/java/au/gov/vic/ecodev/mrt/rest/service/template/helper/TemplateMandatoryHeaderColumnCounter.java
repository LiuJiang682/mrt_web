package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import au.gov.vic.ecodev.mrt.common.Constants.Numeral;
import au.gov.vic.ecodev.mrt.common.Constants.Strings;

public class TemplateMandatoryHeaderColumnCounter {

	private static final String REGEX_HEADER = "^[H|h]\\d{4}$";
	private static final Pattern PATTERN = Pattern.compile(REGEX_HEADER);
	
	private final Map<String, List<Map<String, Object>>> resultMap;
	private final String templateName;
	
	public TemplateMandatoryHeaderColumnCounter(Map<String, List<Map<String, Object>>> resultMap, String templateName) {
		this.resultMap = resultMap;
		this.templateName = templateName;
	}

	public int getColumnCount() {
		int columnCount = Numeral.NEGATIVE_ONE;
		for(String key : resultMap.keySet()) {
			if (key.startsWith(templateName)) {
				String[] names = key.split(Strings.UNDER_LINE);
				String last = names[names.length - Numeral.ONE];
				if (PATTERN.matcher(last).matches()) {
					columnCount = resultMap.get(key).get(Numeral.ZERO).size();
					break;
				}
			}
		}
		return columnCount;
	}

}
