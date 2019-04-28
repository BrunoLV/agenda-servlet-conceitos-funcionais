/**
 *
 */
package br.com.valhala.agenda.web.filters;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebFilter(urlPatterns = { "/*" }, dispatcherTypes = { DispatcherType.FORWARD, DispatcherType.INCLUDE,
		DispatcherType.REQUEST, DispatcherType.ERROR })
public class UriAccessFilter implements Filter {

	private static final Logger LOGGER = LogManager.getLogger(UriAccessFilter.class);

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (request.getCharacterEncoding() == null) {
			request.setCharacterEncoding("UTF-8");
		}

		HttpServletRequest req = (HttpServletRequest) request;

		String url = req.getRequestURL().toString();
		String queryString = req.getQueryString();

		boolean isRecursoEstatico = url.contains("/resources/");

		/*
		if (!isRecursoEstatico) {
			LOGGER.info(
					"Uri: " + url + (queryString != null ? ("/" + queryString) : "") + " realizada dentro da Sessao "
							+ req.getSession(true).getId() + " com Metodo: " + req.getMethod());
		}
		*/

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

}
