package controllers;

import services.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


@WebServlet("/logout")
public class LogoutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        deleteLoggedUser(req.getSession());
//        deleteUserCookie(resp);
//        req.getSession().removeAttribute(COOKIE_CHECKED);
        AccountService.getInstance().logout(req, resp);
        resp.sendRedirect(req.getContextPath() + "/");
    }

//    public static void deleteUserCookie(HttpServletResponse resp) {
//        Cookie cookieUserName = new Cookie(USERNAME_IN_COOKIE, null);
//        cookieUserName.setMaxAge(0);
//        resp.addCookie(cookieUserName);
//    }
//
//    public void deleteLoggedUser(HttpSession session) {
//        session.removeAttribute("loggedUser");
//    }
}
