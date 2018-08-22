package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.jdbc.core.JdbcTemplate;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TemplateMandatoryHeaderFieldsExtractionHelper.class)
public class TemplateMandatoryHeaderFieldsExtractionHelperTest {

	private TemplateMandatoryHeaderFieldsExtractionHelper testInstance;
	private JdbcTemplate mockJdbcTemplate;
	
	@Test
	public void shouldExtractMandatoryFields() throws Exception {
		//Given
		givenTestInstance();
		Map<String, List<Map<String, Object>>> resultMap = new HashMap<>();
		String cls = "SL4";
		long batchId = 100l;
		List<Map<String, Object>> locSites = new ArrayList<>();
		Map<String, Object> locMap = new HashMap<>();
//		locMap.put("TEMPLATE_NAME", "SL4");
		locMap.put("COLUMN_HEADER", "Hole_id");
		locMap.put("FILE_NAME", "myTest.txt");
		locMap.put("ROW_NUMBER", "H1001");
		locMap.put("FIELD_VALUE", "NA");
		locSites.add(locMap);
		TemplateMandatoryHeadersJdbcTemplateHelper mockTemplateMandatoryHeadersJdbcTemplateHelper = 
				Mockito.mock(TemplateMandatoryHeadersJdbcTemplateHelper.class);
		when(mockTemplateMandatoryHeadersJdbcTemplateHelper.getList(Matchers.anyString(), 
				Matchers.anyLong()))
			.thenReturn(locSites);
		PowerMockito.whenNew(TemplateMandatoryHeadersJdbcTemplateHelper.class)
			.withArguments(mockJdbcTemplate)
			.thenReturn(mockTemplateMandatoryHeadersJdbcTemplateHelper);
		//When
		testInstance.extractTemplateHeaderFields(resultMap, batchId, cls);
		//Then
		assertThat(MapUtils.isNotEmpty(resultMap), is(true));
		assertThat(resultMap.size(), is(equalTo(1)));
		assertThat(resultMap.keySet().iterator().next(), is(equalTo("SL4_myTest.txt_H1001")));
		List<Map<String, Object>> dataRecord = resultMap.get("SL4_myTest.txt_H1001");
		assertThat(dataRecord, is(notNullValue()));
		assertThat(dataRecord.size(), is(equalTo(1)));
		Map<String, Object> record1 = dataRecord.get(0);
		Iterator<String> iterator = record1.keySet().iterator();
		List<String> resultList = new ArrayList<>();
		while(iterator.hasNext()) {
			resultList.add(iterator.next());
		}
		assertThat(resultList.contains("Hole_id"), is(true));
		assertThat(record1.get("Hole_id"), is(equalTo("NA")));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		givenTestInstance();
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenJdbcTemplateIsNull() {
		//Given
		JdbcTemplate jdbcTemplate = null;
		//When
		new TemplateMandatoryHeaderFieldsExtractionHelper(jdbcTemplate);
		fail("Program reached unexpected point!");
	}

	private void givenTestInstance() {
		mockJdbcTemplate = Mockito.mock(JdbcTemplate.class);
		testInstance = new TemplateMandatoryHeaderFieldsExtractionHelper(mockJdbcTemplate);
	}
}
