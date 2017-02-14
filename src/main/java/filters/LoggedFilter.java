package filters;

import services.AccountService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Фильтр проверяет, есть ли данные об авторизации пользователя в сессии и,
 * если нет, но пользователь просил его запомнить, то загружает эти данные в сессию
 */
@WebFilter("/*")
public class LoggedFilter extends AbstractFilter {

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        new AccountService(req, resp).isLoggedIn();
        chain.doFilter(req, resp);
    }
}
