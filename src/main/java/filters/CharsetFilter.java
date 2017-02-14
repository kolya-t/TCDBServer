package filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter("/*")
public class CharsetFilter extends AbstractFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        resp.setContentType("text/html; charset=utf-8");
        req.setCharacterEncoding("UTF-8");
        chain.doFilter(req, resp);
    }
}
