package au.gov.vic.ecodev.mrt.web.repository.rest.filter;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class XFrameOptionsFilterTest {
	private HttpServletRequest mockRequest;
	private HttpServletResponse mockResponse;
	private FilterChain mockFilterChain;
	private XFrameOptionsFilter testInstance;
	
	@Test
	public void shouldNoModifyTheResponeWhenXXSSProtectionIsBlock() throws Exception {
		//Given
		givenTestInstance();
		when(mockResponse.getHeader(eq("X-Frame-Options"))).thenReturn("DENY");
		//When
		testInstance.doFilter(mockRequest, mockResponse, mockFilterChain);
		//Then
		verify(mockResponse, times(0)).setHeader(Matchers.anyString(), Matchers.anyString());
		verify(mockFilterChain).doFilter(eq(mockRequest), 
				eq(mockResponse));
	}
	
	@Test
	public void shouldInjectTheResponeWhenXXSSProtectionIsNull() throws Exception {
		//Given
		givenTestInstance();
		//When
		testInstance.doFilter(mockRequest, mockResponse, mockFilterChain);
		//Then
		ArgumentCaptor<String> headerCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockResponse).setHeader(headerCaptor.capture(), valueCaptor.capture());
		assertThat(headerCaptor.getValue(), is(equalTo("X-Frame-Options")));
		assertThat(valueCaptor.getValue(), is(equalTo("DENY")));
		verify(mockFilterChain).doFilter(eq(mockRequest), 
				eq(mockResponse));
	}
	
	@Test
	public void shouldInjectTheResponeWhenXXSSProtectionIsOther() throws Exception {
		//Given
		givenTestInstance();
		when(mockResponse.getHeader(eq("X-Frame-Options"))).thenReturn("allow");
		//When
		testInstance.doFilter(mockRequest, mockResponse, mockFilterChain);
		//Then
		ArgumentCaptor<String> headerCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockResponse).setHeader(headerCaptor.capture(), valueCaptor.capture());
		assertThat(headerCaptor.getValue(), is(equalTo("X-Frame-Options")));
		assertThat(valueCaptor.getValue(), is(equalTo("DENY")));
		verify(mockFilterChain).doFilter(eq(mockRequest), 
				eq(mockResponse));
	}

	private void givenTestInstance() {
		mockRequest = Mockito.mock(HttpServletRequest.class);
		mockResponse = Mockito.mock(HttpServletResponse.class);
		mockFilterChain = Mockito.mock(FilterChain.class);
		testInstance = new XFrameOptionsFilter();
	}
}
