package au.gov.vic.ecodev.mrt.web.repository.rest.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

public class XcontentTypeOptionFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) response;
		Object xContentTypeOptions = resp.getHeader("X-Content-Type-Options");
		if ((null == xContentTypeOptions) 
				|| (!"nosniff".equals(xContentTypeOptions))) {
			resp.setHeader("X-Content-Type-Options", "nosniff"); 
		}
		chain.doFilter(request, response);

	}

}
