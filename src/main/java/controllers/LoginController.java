package controllers;

import services.AccountService;
import services.AccountServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/login")
public class LoginController extends HttpServlet {
    public static final String LOGIN_PAGE_PATH = "/views/login.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // уже залогинен
        if (new AccountService(req, resp).isLoggedIn()) {
            req.getSession().setAttribute("errorMessage", "Вы уже залогинены");
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        req.getRequestDispatcher(LOGIN_PAGE_PATH).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        boolean remember = req.getParameter("remember") != null;

        if ((login != null) && (password != null) && !login.isEmpty() && !password.isEmpty()) {
            AccountService accountService = new AccountService(req, resp);
            try {
                if (accountService.login(login, password, remember)) {
                    // if ok -> redirect to homepage
                    resp.sendRedirect(req.getContextPath() + "/");
                    return;
                }
            } catch (AccountServiceException e) {
                e.printStackTrace();
            }
        }

        // if error -> back to /login page
        req.setAttribute("errorMessage", "Некорректные данные");
        getServletContext().getRequestDispatcher(LoginController.LOGIN_PAGE_PATH).forward(req, resp);
    }
}
