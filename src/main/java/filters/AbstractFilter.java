package filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Абстрактный класс фильтра, преобразующий ServletRequest к HttpServletRequest
 * и ServletResponse к HttpServletResponse в методе doFilter. А методы init и destroy, которые редко используются,
 * реализуются в этом классе, чтобы не пришлось реализовывать их потом
 */
public abstract class AbstractFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    public abstract void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
            throws IOException, ServletException;

    @Override
    public void destroy() {
    }
}
