package au.gov.vic.ecodev.mrt.web.repository.rest.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

public class XContentTypeOptionFilter extends GenericFilterBean {

	private static final String X_CONTENT_TYPE_OPTION_NO_SNIFF = "nosniff";
	private static final String X_CONTENT_TYPE_OPTIONS = "X-Content-Type-Options";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) response;
		Object xContentTypeOptions = resp.getHeader(X_CONTENT_TYPE_OPTIONS);
		if ((null == xContentTypeOptions) 
				|| (!X_CONTENT_TYPE_OPTION_NO_SNIFF.equals(xContentTypeOptions))) {
			resp.setHeader(X_CONTENT_TYPE_OPTIONS, X_CONTENT_TYPE_OPTION_NO_SNIFF); 
		}
		chain.doFilter(request, response);

	}

}
