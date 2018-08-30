package au.gov.vic.ecodev.mrt.rest.service.template.helper.utils;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.junit.Test;

public class StringToListIndexHeaderConvertorTest {

	private StringToListIndexHeaderConvertor testInstance;
	
	@Test
	public void shouldReturnListOfIndexHeaderedTitle() {
		//Given
		String header = "field1,field2,field3";
		givenTestInstance(header);
		//When
		List<Map<String, Object>> titleList = testInstance.getIndexedHeaderList();
		//Then
		assertThat(titleList, is(notNullValue()));
		assertThat(titleList.size(), is(equalTo(1)));
		Map<String, Object> titleMap = titleList.get(0);
		assertThat(titleMap.size(), is(equalTo(3)));
		assertThat(titleMap.get("1"), is(equalTo("field1")));
		assertThat(titleMap.get("2"), is(equalTo("field2")));
		assertThat(titleMap.get("3"), is(equalTo("field3")));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		givenTestInstance("abc");
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenStringIsNull() {
		//Given
		String string = null;
		//When
		new StringToListIndexHeaderConvertor(string);
		fail("Program reached unexpected point!");
	}

	private void givenTestInstance(final String string) {
		testInstance = new StringToListIndexHeaderConvertor(string);
	}
}
