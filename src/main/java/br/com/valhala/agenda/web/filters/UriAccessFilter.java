/**
 *
 */
package br.com.valhala.agenda.web.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.INCLUDE,
        DispatcherType.REQUEST, DispatcherType.ERROR})
public class UriAccessFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(UriAccessFilter.class);

    public UriAccessFilter() {
    }

    @Override
    public void destroy() {
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

        if (!isRecursoEstatico) {
            LOGGER.info(
                    "Uri: " + url + (queryString != null ? ("/" + queryString) : "") + " realizada dentro da Sessao "
                            + req.getSession(true).getId() + " com Metodo: " + req.getMethod());
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

}
