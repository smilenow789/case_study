package controller;

import java.io.IOException;
import jakarta.inject.Inject;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
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

	/*
	 * @Override public void init(FilterConfig filterConfig) throws ServletException
	 * { // Initialisierungen, falls erforderlich
	 * System.out.println("AuthenticationFilter initialized."); }
	 */

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

		// Log-Ausgaben zur Fehlersuche (können später entfernt werden)
		System.out.println("Request URI: " + requestURI);
		System.out.println("Is Login Page: " + isLoginPage);
		System.out.println("Is Resource: " + isResource);
		System.out.println("Is Logged In: " + isLoggedIn);

		if (!isLoggedIn && !isLoginPage && !isResource) {
			System.out.println("Unauthorized access to " + requestURI + ". Redirecting to login.xhtml");
			httpResponse.sendRedirect(contextPath + "/login.xhtml");
		} else {
			chain.doFilter(request, response);
		}
	}

	/*
	 * @Override public void destroy() { // Aufräumarbeiten, falls erforderlich
	 * System.out.println("AuthenticationFilter destroyed."); }
	 */
}