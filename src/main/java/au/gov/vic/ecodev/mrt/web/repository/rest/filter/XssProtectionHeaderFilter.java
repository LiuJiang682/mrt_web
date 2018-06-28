package au.gov.vic.ecodev.mrt.web.repository.rest.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

public class XssProtectionHeaderFilter extends GenericFilterBean {

	private static final String XSS_PROTECTION_MODE_BLOCK = "1; mode=block";
	private static final String X_XSS_PROTECTION = "X-XSS-Protection";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) response;
		Object xssProtection = resp.getHeader(X_XSS_PROTECTION);
		if ((null == xssProtection) 
				|| (!XSS_PROTECTION_MODE_BLOCK.equals(xssProtection))) {
			resp.setHeader(X_XSS_PROTECTION, XSS_PROTECTION_MODE_BLOCK); 
		}
		chain.doFilter(request, response);

	}

}
