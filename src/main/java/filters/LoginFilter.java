package filters;

import services.AccountService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@WebFilter("/*")
public class LoginFilter implements Filter {
//    public static final String COOKIE_CHECKED = "COOKIE_CHECKED";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        AccountService.getInstance().checkLogged(req);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
