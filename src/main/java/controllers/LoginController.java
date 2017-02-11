package controllers;

import database.pojo.User;
import services.AccountService;
import services.AccountServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
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
//        String login = req.getParameter("login");
//        String password = req.getParameter("password");
//        boolean remember = req.getParameter("remember") != null;
//
//        boolean hasError = false;
//        String errorMessage = null;
////        String successMessage = "Вы успешно вошли в систему";
//        User user = null;
//
//        // данные не введены
//        if (login == null || password == null || login.trim().length() == 0 || password.trim().length() == 0) {
//            hasError = true;
//            req.setAttribute("errorMessage", "Вы ввели не все данные");
//
//        } else {
//            try {
//                user = UserService.getInstance().getUserByLogin(login);
//                if (user == null || !Objects.equals(password, user.getPassword())) {
//                    hasError = true;
//                    errorMessage = "Некорректный логин или пароль";
//                }
//            } catch (UserServiceException e) {
//                e.printStackTrace();
//            }
//        }
//
//        // if error -> back to /login page
//        if (hasError) {
//            user = new User();
//            user.setLogin(login);
//            user.setPassword(password);
//
//            // сохранить информацию об ошибке в запросе перед возвратом
//            req.setAttribute("errorMessage", errorMessage);
//            req.setAttribute("user", user);
//
//            getServletContext().getRequestDispatcher(LoginController.VIEW_JSP).forward(req, resp);
//        }
//
//        // if no error
//        // store information in session
//        // and redirect to homepage
//        else {
//            HttpSession session = req.getSession();
//            storeLoggedUser(session, user);
//
//            // if checked 'remember me'
//            if (remember) {
//                storeUserCookie(resp, user);
//            }
//
//            // redirect to homepage
//            resp.sendRedirect(req.getContextPath() + "/");
//        }
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

//    public static void storeLoggedUser(HttpSession session, User user) {
//        session.setAttribute("loggedUser", user);
//    }
//
//    public static User getLoggedUser(HttpSession session) {
//        return (User) session.getAttribute("loggedUser");
//    }
//
//    public static void storeUserCookie(HttpServletResponse resp, User user) {
//        System.out.println("Store user in cookie");
//        Cookie cookieUserName = new Cookie(USERNAME_IN_COOKIE, user.getLogin());
//
//        cookieUserName.setMaxAge(24 * 60 * 60); // 1 день
//        resp.addCookie(cookieUserName);
//    }
//
//    public static String getLoginFromCookie(HttpServletRequest req) {
//        Cookie[] cookies = req.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (USERNAME_IN_COOKIE.equals(cookie.getName())) {
//                    return cookie.getValue();
//                }
//            }
//        }
//        return null;
//    }
}
