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
    public static final String VIEW_JSP = "/views/login.jsp";
    public static final String USERNAME_IN_COOKIE = "ATTRIBUTE_FOR_STORE_USER_NAME_IN_COOKIE";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("UTF-8");

        // уже залогинен
        if (req.getSession().getAttribute("loggedUser") != null) {
            req.getSession().setAttribute("errorMessage", "Вы уже залогинены");
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        req.getRequestDispatcher(VIEW_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("UTF-8");

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        boolean remember = req.getParameter("remember") != null;

        // if ok -> redirect to homepage
        try {
            if (AccountService.getInstance().login(req.getSession(), resp, login, password, remember)) {
                resp.sendRedirect(req.getContextPath() + "/");
                return;
            }
        } catch (AccountServiceException e) {
            e.printStackTrace();
        }

        // if error -> back to /login page
        req.setAttribute("errorMessage", "Некорректные данные");
        getServletContext().getRequestDispatcher(LoginController.VIEW_JSP).forward(req, resp);
    }
}
