package au.gov.vic.ecodev.mrt.web.repository.rest.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import au.gov.vic.ecodev.mrt.common.Constants.Strings;

public class InvalidSessionIdFilter extends GenericFilterBean {

	private static final String SESSION_ID = "sessionId";

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		Object sessionId = req.getParameter(SESSION_ID);
		
		if (StringUtils.isBlank((CharSequence) sessionId)) {
			HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.setContentType("application/json");
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad request");
            return;
		} else {
			String[] sessionIds = ((String)sessionId).split(Strings.COMMA);
			try {
				for (String id : sessionIds) {
					Long.parseLong(id);
				}
			} catch (Exception e) {
				HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
	            httpResponse.setContentType("application/json");
	            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad request");
	            return;
			}
		} 
		
		filterChain.doFilter(servletRequest, servletResponse);
	}

}
