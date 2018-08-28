package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TemplateHeaderConfigRetrieverHelperTest {

	@Autowired
	private TemplateHeaderConfigRetrieverHelper testInstance;
	
	@Test
	public void shouldReturnTemplateHeaderConfigMap() {
		//Given
		//When
		Map<String, Map<String, Map<String, Boolean>>> map = 
				testInstance.getTemplateHeaderConfigMap();
		//Then
		assertThat(map, is(notNullValue()));
		assertThat(map.size(), is(equalTo(2)));
		Map<String, Map<String, Boolean>> mrtMap = map.get("MRT");
		assertThat(mrtMap, is(notNullValue()));
		assertThat(mrtMap.size(), is(equalTo(5)));
		Map<String, Boolean> sl4Map = mrtMap.get("SL4");
		assertThat(sl4Map, is(notNullValue()));
		assertThat(sl4Map.size(), is(equalTo(3)));
		assertThat(sl4Map.get("H1000"), is(false));
		assertThat(sl4Map.get("H1001"), is(true));
		assertThat(sl4Map.get("H1004"), is(true));
		Map<String, Map<String, Boolean>> vgpHydroMap = map.get("VGPHYDRO");
		assertThat(vgpHydroMap, is(notNullValue()));
		assertThat(vgpHydroMap.isEmpty(), is(true));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenJdbcTemplateIsNull() {
		//Given
		JdbcTemplate jdbcTemplate = null;
		//When
		new TemplateHeaderConfigRetrieverHelper(jdbcTemplate);
		fail("Program reached unexpected point!");
	}
}
