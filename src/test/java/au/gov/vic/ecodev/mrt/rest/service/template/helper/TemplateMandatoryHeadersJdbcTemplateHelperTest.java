package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;
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
public class TemplateMandatoryHeadersJdbcTemplateHelperTest {

	private TemplateMandatoryHeadersJdbcTemplateHelper testInstance;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	public void shouldReturnMandatoryHeaders() {
		//Given
		givenTestInstance();
		long batchId = 100l;
		//When
		List<Map<String, Object>> results = testInstance.getList("SL4", batchId);
		//Then
		assertThat(results, is(notNullValue()));
		assertThat(results.size(), is(equalTo(7)));
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
		new TemplateMandatoryHeadersJdbcTemplateHelper(jdbcTemplate);
		fail("Program reached unexpected point!");
	}

	private void givenTestInstance() {
		testInstance = new TemplateMandatoryHeadersJdbcTemplateHelper(jdbcTemplate);
	}
}
