package servlets.filters;

import database.pojo.User;
import servlets.LoginController;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/user/*")
public class AdminFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        User user = LoginController.getLoggedUser(session);
        // пользователь не залогинен
        if (user == null) {
            req.getSession().setAttribute("errorMessage", "Нужно войти");
            resp.sendRedirect("/login");
        }
        // залогинен, НЕ админ
        else if (!user.getRole().equals("admin")) {
            req.getSession().setAttribute("errorMessage", "Вы не администратор");
            resp.sendRedirect("/");
        }
        // залогинен, админ
        else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}
