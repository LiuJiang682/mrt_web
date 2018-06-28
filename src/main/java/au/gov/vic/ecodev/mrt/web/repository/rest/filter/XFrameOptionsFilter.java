package au.gov.vic.ecodev.mrt.web.repository.rest.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

public class XFrameOptionsFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) response;
		Object xFrameOption = resp.getHeader("X-Frame-Options");
		if ((null == xFrameOption) 
				|| (!"DENY".equals(xFrameOption))) {
			resp.setHeader("X-Frame-Options", "DENY"); 
		}
		chain.doFilter(request, response);
	}

}
