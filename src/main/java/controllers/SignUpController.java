package controllers;

import services.AccountService;
import services.AccountServiceException;
import services.UserService;
import services.UserServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/signup")
public class SignUpController extends HttpServlet {

    public static final String VIEW_JSP = "/views/signup.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("UTF-8");

        // уже зарегистрирован и залогинен
        if (req.getSession().getAttribute("loggedUser") != null) {
            req.getSession().setAttribute("errorMessage", "Вы уже зарегистрированы");
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }
        // отрисовка вьюхи
        req.getRequestDispatcher(VIEW_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("UTF-8");

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        try {
            if (AccountService.getInstance().register(login, password, email)) {
                req.getSession().setAttribute("user", UserService.getInstance().getUserByLogin(login));
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }
        } catch (AccountServiceException | UserServiceException e) {
            e.printStackTrace();
        }

        // что-то пошло не так
        req.getSession().setAttribute("errorMessage", "Не удалось зарегистрироваться");
        req.getRequestDispatcher(VIEW_JSP).forward(req, resp);
    }
}
