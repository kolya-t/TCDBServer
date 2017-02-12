package controllers;

import database.pojo.User;
import services.AccountService;
import services.AccountServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/signup")
public class SignUpController extends HttpServlet {

    public static final String SIGNUP_PAGE_PATH = "/views/signup.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("UTF-8");

        // уже зарегистрирован и залогинен
        if (new AccountService(req, resp).isLoggedIn()) {
            req.getSession().setAttribute("errorMessage", "Вы уже зарегистрированы");
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }
        // отрисовка вьюхи
        req.getRequestDispatcher(SIGNUP_PAGE_PATH).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("UTF-8");

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        if ((login != null) && (password != null) && (email != null)
                && !login.isEmpty() && !password.isEmpty() && !email.isEmpty()) {
            AccountService accountService = new AccountService(req, resp);
            try {
                if (accountService.register(login, email, password)) {
                    // В случае успешной регистрации - редирект на /login
                    req.getSession().setAttribute("user", new User(login, password, null, null));
                    resp.sendRedirect(req.getContextPath() + "/login");
                    return;
                }
            } catch (AccountServiceException e) {
                e.printStackTrace();
            }
        }

        // что-то пошло не так
        req.getSession().setAttribute("errorMessage", "Не удалось зарегистрироваться");
        req.getRequestDispatcher(SIGNUP_PAGE_PATH).forward(req, resp);
    }
}
