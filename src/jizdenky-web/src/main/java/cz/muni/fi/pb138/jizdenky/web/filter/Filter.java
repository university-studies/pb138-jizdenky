package cz.muni.fi.pb138.jizdenky.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by pavol on 25.5.2014.
 */
@WebFilter("/*")
public class Filter implements javax.servlet.Filter {
    private static final Logger log = LoggerFactory.getLogger(Filter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("Filter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("request on filter :{}", ((HttpServletRequest) servletRequest).getRequestURI());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("filter end");
    }
}
