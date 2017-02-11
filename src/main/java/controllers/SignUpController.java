package controllers;

import services.UserServiceException;
import services.UserService;
import database.pojo.User;

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
            resp.sendRedirect("/");
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

        boolean done = false;
        User user = new User();
        if ((login != null) && (password != null) && (email != null)) {
            user.setLogin(login);
            user.setPassword(password);
            user.setEmail(email);
            // регистрация только как пользователя, доп. права может назначить администратор
            user.setRole("user");

            try {
                long id = UserService.getInstance().addUser(user);
                done = id != -1;
            } catch (UserServiceException e) {
                e.printStackTrace();
            }
        }

        if (done) {
            req.getRequestDispatcher("/login").forward(req, resp);
        } else {
            req.getSession().setAttribute("errorMessage", "Не удалось зарегистрироваться");
            resp.sendRedirect("/");
        }
    }
}
