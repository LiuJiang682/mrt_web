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
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections4.MapUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import au.gov.vic.ecodev.mrt.test.fixture.TestFixture;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TemplateMandatoryFieldsExtractionHelper.class)
public class TemplateMandatoryFieldsExtractionHelperTest {

	private JdbcTemplate mockJdbcTemplate;
	private TemplateMandatoryFieldsExtractionHelper testInstance;
	
	@Test
	public void shouldExtractMandatoryFields() throws Exception {
		//Given
		givenTestInstance();
		Map<String, List<Map<String, Object>>> resultMap = new HashMap<>();
		Map<String, Object> templateFieldMap = TestFixture.getTemplateFieldMap();
		String cls = "SL4";
		long batchId = 100l;
		List<Map<String, Object>> locSites = new ArrayList<>();
		Map<String, Object> locMap = new HashMap<>();
		locMap.put("LOADER_ID", "123456");
		locMap.put("SITE_ID", "ARD001");
		locSites.add(locMap);
		TemplateDisplayPropertiesJdbcTemplateHelper mockTemplateDisplayPropertiesJdbcTemplateHelper = 
				Mockito.mock(TemplateDisplayPropertiesJdbcTemplateHelper.class);
		when(mockTemplateDisplayPropertiesJdbcTemplateHelper.getList(Matchers.anyString(), Matchers.anyString(), Matchers.anyLong()))
			.thenReturn(locSites);
		PowerMockito.whenNew(TemplateDisplayPropertiesJdbcTemplateHelper.class).withArguments(mockJdbcTemplate)
			.thenReturn(mockTemplateDisplayPropertiesJdbcTemplateHelper);
		//When
		testInstance.extractMandatoryFields(resultMap, templateFieldMap, batchId, cls);
		//Then
		assertThat(MapUtils.isNotEmpty(resultMap), is(true));
		assertThat(resultMap.size(), is(equalTo(1)));
		assertThat(resultMap.keySet().iterator().next(), is(equalTo("SL4_D1")));
		List<Map<String, Object>> dataRecord = resultMap.get("SL4_D1");
		assertThat(dataRecord, is(notNullValue()));
		assertThat(dataRecord.size(), is(equalTo(1)));
		Map<String, Object> record1 = dataRecord.get(0);
		Iterator<String> iterator = record1.keySet().iterator();
		assertThat(iterator.next(), is(equalTo("SITE_ID")));
		assertThat(iterator.next(), is(equalTo("LOADER_ID")));
		assertThat(record1.get("LOADER_ID"), is(equalTo("123456")));
		assertThat(record1.get("SITE_ID"), is(equalTo("ARD001")));
		assertThat(iterator.hasNext(), is(false));
	}
	
	@Test 
	public void shouldAddedNewEntryInResultMapWhenDoValuePopulationCalledWithNoRecord() {
		//Given
		givenTestInstance();
		Map<String, List<Map<String, Object>>> resultMap = new HashMap<>();
		assertThat(resultMap.size(), is(equalTo(0)));
		String cls = "SL4";
		AtomicInteger counter = new AtomicInteger();
		Map<String, Object> result = new HashMap<>();
		result.put("LOADER_ID", "123456");
		result.put("SITE_ID", "ARD001");
		//When
		testInstance.doValuePopulation(resultMap, cls, counter, result);
		//Then
		assertThat(resultMap.size(), is(equalTo(1)));
		List<Map<String, Object>> dataList = resultMap.get("SL4_D1");
		Map<String, Object> dataMap = dataList.get(0);
		assertThat(dataMap.size(), is(equalTo(2)));
	}
	
	@Test 
	public void shouldExpendExistingEntryInResultMapWhenDoValuePopulationCalledWithExistingRecord() {
		//Given
		givenTestInstance();
		Map<String, List<Map<String, Object>>> resultMap = new HashMap<>();
		assertThat(resultMap.size(), is(equalTo(0)));
		String cls = "SL4";
		AtomicInteger counter = new AtomicInteger();
		Map<String, Object> result = new HashMap<>();
		result.put("LOADER_ID", "123456");
		result.put("SITE_ID", "ARD001");
		List<Map<String, Object>> dataList = new ArrayList<>();
		dataList.add(result);
		resultMap.put("SL4_D1", dataList);
		assertThat(resultMap.size(), is(equalTo(1)));
		Map<String, Object> result1 = new HashMap<>();
		result1.put("EASTING", "123456");
		result1.put("NORTHING", "789012");
		//When
		testInstance.doValuePopulation(resultMap, cls, counter, result1);
		//Then
		assertThat(resultMap.size(), is(equalTo(1)));
		List<Map<String, Object>> retrievedDataList = resultMap.get("SL4_D1");
		Map<String, Object> dataMap = retrievedDataList.get(0);
		assertThat(dataMap.size(), is(equalTo(4)));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		givenTestInstance();
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenJdbcTemplateIsNull() {
		//Given
		JdbcTemplate nullJdbcTemplate = null;
		//When
		new TemplateMandatoryFieldsExtractionHelper(nullJdbcTemplate);
		fail("Program reached unexpected point!");
	}
	
	private void givenTestInstance() {
		mockJdbcTemplate = Mockito.mock(JdbcTemplate.class);
		
		testInstance = new TemplateMandatoryFieldsExtractionHelper(mockJdbcTemplate);
	}
}
