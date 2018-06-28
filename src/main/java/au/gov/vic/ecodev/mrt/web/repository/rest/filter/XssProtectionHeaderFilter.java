package au.gov.vic.ecodev.mrt.web.repository.rest.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

public class XssProtectionHeaderFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) response;
		Object xssProtection = resp.getHeader("X-XSS-Protection");
		if ((null == xssProtection) 
				|| (!"1; mode=block".equals(xssProtection))) {
			resp.setHeader("X-XSS-Protection", "1; mode=block"); 
		}
		chain.doFilter(request, response);

	}

}
