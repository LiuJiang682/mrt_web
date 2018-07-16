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

public class InvalidSessionIdFilterTest {
	private HttpServletRequest mockRequest;
	private HttpServletResponse mockResponse;
	private FilterChain mockFilterChain;
	private InvalidSessionIdFilter testInstance;

	@Test
	public void shouldAcceptTheRequestWhenSessionIdIsNumber() throws Exception {
		//Given
		givenTestInstance();
		when(mockRequest.getParameter(eq("sessionId"))).thenReturn("1");
		//When
		testInstance.doFilter(mockRequest, mockResponse, mockFilterChain);
		//Then
		verify(mockResponse, times(0)).sendError(Matchers.anyInt(), 
				Matchers.anyString());
		verify(mockFilterChain, times(1)).doFilter(eq(mockRequest), 
				eq(mockResponse));
	}
	
	@Test
	public void shouldAcceptTheRequestWhenSessionIdIsMultiplyNumber() throws Exception {
		//Given
		givenTestInstance();
		when(mockRequest.getParameter(eq("sessionId"))).thenReturn("1,2");
		//When
		testInstance.doFilter(mockRequest, mockResponse, mockFilterChain);
		//Then
		verify(mockResponse, times(0)).sendError(Matchers.anyInt(), 
				Matchers.anyString());
		verify(mockFilterChain, times(1)).doFilter(eq(mockRequest), 
				eq(mockResponse));
	}
	
	@Test
	public void shouldRejectTheRequestWhenSessionIdIsNull() throws Exception {
		//Given
		givenTestInstance();
		//When
		testInstance.doFilter(mockRequest, mockResponse, mockFilterChain);
		//Then
		ArgumentCaptor<Integer> errorCodeCaptor = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<String> errorMsgCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockResponse).sendError(errorCodeCaptor.capture(), errorMsgCaptor.capture());
		assertThat(errorCodeCaptor.getValue(), is(equalTo(HttpServletResponse.SC_BAD_REQUEST)));
		assertThat(errorMsgCaptor.getValue(), is(equalTo("Bad request")));
		verify(mockFilterChain, times(0)).doFilter(Matchers.any(HttpServletRequest.class), 
				Matchers.any(HttpServletResponse.class));
	}
	
	@Test
	public void shouldRejectTheRequestWhenSessionIdIsXSS() throws Exception {
		//Given
		givenTestInstance();
		when(mockRequest.getParameter(eq("sessionId"))).thenReturn("<script>alert(1)</script>");
		//When
		testInstance.doFilter(mockRequest, mockResponse, mockFilterChain);
		//Then
		ArgumentCaptor<Integer> errorCodeCaptor = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<String> errorMsgCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockResponse).sendError(errorCodeCaptor.capture(), errorMsgCaptor.capture());
		assertThat(errorCodeCaptor.getValue(), is(equalTo(HttpServletResponse.SC_BAD_REQUEST)));
		assertThat(errorMsgCaptor.getValue(), is(equalTo("Bad request")));
		verify(mockFilterChain, times(0)).doFilter(Matchers.any(HttpServletRequest.class), 
				Matchers.any(HttpServletResponse.class));
	}

	private void givenTestInstance() {
		mockRequest = Mockito.mock(HttpServletRequest.class);
		mockResponse = Mockito.mock(HttpServletResponse.class);
		mockFilterChain = Mockito.mock(FilterChain.class);
		testInstance = new InvalidSessionIdFilter();
	}
}
