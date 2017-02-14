package filters;

import services.AccountService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Филтр, проверяющий, авторизовался ли пользователь как администратор
 */
@WebFilter("/admin/*")
public class AdminFilter extends AbstractFilter {

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        AccountService accountService = new AccountService(req, resp);
        if (!accountService.isLoggedIn()) {
            req.getSession().setAttribute("errorMessage", "Нужно войти");
            resp.sendRedirect(req.getContextPath() + "/login");
        } else if (!accountService.getLoggedUser().getRole().equals("admin")) {
            req.getSession().setAttribute("errorMessage", "Вы не администратор");
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            chain.doFilter(req, resp);
        }

    }
}
