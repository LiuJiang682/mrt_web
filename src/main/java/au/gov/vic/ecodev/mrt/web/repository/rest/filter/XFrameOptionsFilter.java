package au.gov.vic.ecodev.mrt.web.repository.rest.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

public class XFrameOptionsFilter extends GenericFilterBean {

	private static final String X_FRAME_OPTION_DENY = "DENY";
	private static final String X_FRAME_OPTIONS = "X-Frame-Options";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) response;
		Object xFrameOption = resp.getHeader(X_FRAME_OPTIONS);
		if ((null == xFrameOption) 
				|| (!X_FRAME_OPTION_DENY.equals(xFrameOption))) {
			resp.setHeader(X_FRAME_OPTIONS, X_FRAME_OPTION_DENY); 
		}
		chain.doFilter(request, response);
	}

}
