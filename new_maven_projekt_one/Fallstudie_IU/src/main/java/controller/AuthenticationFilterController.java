package controller;

import java.io.IOException;
import jakarta.inject.Inject;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import util.UserSessionBean;

@WebFilter(filterName = "AuthenticationFilterController", urlPatterns = { "/requirementsengineer.xhtml",
		"/testfallersteller.xhtml", "/testmanager.xhtml", "/tester.xhtml" })
public class AuthenticationFilterController implements Filter {

	@Inject
	private UserSessionBean userSessionBean;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String requestURI = httpRequest.getRequestURI();
		String contextPath = httpRequest.getContextPath();

		boolean isLoggedIn = (userSessionBean != null && userSessionBean.getAuthenticatedUser() != null);

		boolean isLoginPage = requestURI.contains("/login.xhtml");

		boolean isResource = requestURI.startsWith(contextPath + "/jakarta.faces.resource/")
				|| requestURI.startsWith(contextPath + "/resources/");

		if (!isLoggedIn && !isLoginPage && !isResource) {
			httpResponse.sendRedirect(contextPath + "/login.xhtml");
		} else {
			chain.doFilter(request, response);
		}
	}
}