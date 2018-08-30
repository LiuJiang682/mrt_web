package au.gov.vic.ecodev.mrt.rest.service.template.helper.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import au.gov.vic.ecodev.mrt.common.Constants.Numeral;
import au.gov.vic.ecodev.mrt.common.Constants.Strings;

public class StringToListIndexHeaderConvertor {

	private final String string;
	
	public StringToListIndexHeaderConvertor(final String string) {
		if (StringUtils.isEmpty(string)) {
			throw new IllegalArgumentException("StringToListIndexHeaderConvertor:string parameter cannot be null or empty!");
		}
		this.string = string;
	}

	public List<Map<String, Object>> getIndexedHeaderList() {
		String[] headersArray = string.split(Strings.COMMA);
		Map<String, Object> newHeaders = new HashMap<>(headersArray.length);
		for (int i = Numeral.ZERO; i < headersArray.length; i++) {
			int index = i;
			String key = String.valueOf(++index);
			newHeaders.put(key, headersArray[i]);
		}
		List<Map<String, Object>> list = new ArrayList<>(Numeral.ONE);
		list.add(newHeaders);
		return list;
	}
}
