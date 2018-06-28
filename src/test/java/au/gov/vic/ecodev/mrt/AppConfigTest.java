package au.gov.vic.ecodev.mrt;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import au.gov.vic.ecodev.mrt.config.TemplateWebPropertiesConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AppConfigTest {

	@Autowired
	private AppConfig testInstance;
	
	@Test
	public void shouldAutowiredConfig() {
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test
	public void shouldReturnTemplateWebPropertiesConfig() {
		//Given
		//When
		TemplateWebPropertiesConfig bean = testInstance.templateWebPropertiesConfig();
		//Then
		assertThat(bean, is(notNullValue()));
	}
	
	@Test
	public void shouldReturnFilterRegistrationBean() {
		//Given
		//When
		FilterRegistrationBean bean = testInstance.invalidFileLogSessionIdFilter();
		//Then
		assertThat(bean, is(notNullValue()));
	}
}
