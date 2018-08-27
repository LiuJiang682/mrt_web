package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TemplateHeaderConfigMapHelperTest {

	private TemplateHeaderConfigMapHelper testInstance;
	private Map<String, Object> templateHeadersMap;
	private String templateName;
	
	@Test
	public void shouldReturnSL4MapWhenTemplateIsSL4() {
		//Given
		givenTestInstance("SL4");
		templateHeadersMap.put("SL4", "H1000-false,H1001-true,H1004-true");
		//When
		Map<String, Boolean> headerConfigMap = testInstance.getHeaderConfig();
		//Then
		assertThat(headerConfigMap, is(notNullValue()));
		assertThat(headerConfigMap.size(), is(equalTo(3)));
	}
	
	@Test
	public void shouldReturnSL4MapWhenTemplateIsSL4With2ValidOptions() {
		//Given
		givenTestInstance("SL4");
		templateHeadersMap.put("SL4", "H1000-false,H1001-true,H1004");
		//When
		Map<String, Boolean> headerConfigMap = testInstance.getHeaderConfig();
		//Then
		assertThat(headerConfigMap, is(notNullValue()));
		assertThat(headerConfigMap.size(), is(equalTo(3)));
		assertThat(headerConfigMap.get("H1000"), is(false));
		assertThat(headerConfigMap.get("H1001"), is(true));
		assertThat(headerConfigMap.get("H1004"), is(false));
	}
	
	@Test
	public void shouldReturnVgpHydroSampMapWhenTemplateIsSamp() {
		//Given
		givenTestInstance("SAMP");
		//When
		Map<String, Boolean> headerConfigMap = testInstance.getHeaderConfig();
		//Then
		assertThat(headerConfigMap, is(notNullValue()));
		assertThat(headerConfigMap.size(), is(equalTo(0)));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		givenTestInstance("MRT");
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenMapIsNull() {
		//Given
		templateHeadersMap = null;
		templateName = null;
		//When
		new TemplateHeaderConfigMapHelper(templateHeadersMap, templateName);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTemplateNameIsNull() {
		//Given
		templateHeadersMap = new HashMap<>();
		templateName = null;
		//When
		new TemplateHeaderConfigMapHelper(templateHeadersMap, templateName);
		fail("Program reached unexpected point!");
	}

	private void givenTestInstance(final String templateName) {
		templateHeadersMap = new HashMap<>();
		testInstance = new TemplateHeaderConfigMapHelper(templateHeadersMap, templateName);
	}
}
