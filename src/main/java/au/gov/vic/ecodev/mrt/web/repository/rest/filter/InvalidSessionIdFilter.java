package au.gov.vic.ecodev.mrt.web.repository.rest.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

public class InvalidSessionIdFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) arg0;
		Object sessionId = req.getParameter("sessionId");
		try {
			Long.parseLong((String) sessionId);
		} catch (Exception e) {
			HttpServletResponse httpResponse = (HttpServletResponse) arg1;
            httpResponse.setContentType("application/json");
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad request");
            return;
		}
		arg2.doFilter(arg0, arg1);
	}

}
