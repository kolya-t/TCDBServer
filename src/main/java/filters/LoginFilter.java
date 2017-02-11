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
//        HttpServletResponse resp = (HttpServletResponse) response;
//        HttpSession session = req.getSession();
//        User userFromSession = LoginController.getLoggedUser(session);
//        // if logged in -> check cookie
//        if (userFromSession != null) {
//            session.setAttribute(COOKIE_CHECKED, "CHECKED");
//            chain.doFilter(request, response);
//            return;
//        }
//
//        // if not logged but cookie checked
//        if (session.getAttribute(COOKIE_CHECKED) != null) {
//            String login = LoginController.getLoginFromCookie(req);
//            try {
//                User user = UserService.getInstance().getUserByLogin(login);
//                LoginController.storeUserCookie(resp, user);
//            } catch (UserServiceException e) {
//                e.printStackTrace();
//            }
//
//            session.setAttribute(COOKIE_CHECKED, "CHECKED");
//        }
//
//        chain.doFilter(request, response);
        AccountService.getInstance().checkLogged(req);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
