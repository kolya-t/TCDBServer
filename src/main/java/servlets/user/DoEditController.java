package servlets.user;

import database.DBException;
import database.DBService;
import database.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/user/doEdit")
public class DoEditController extends HttpServlet {

    private static final String SUCCESS_PAGE = "/user/list";
    private static final String ERROR_PAGE = "/user/edit";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("UTF-8");

        String forward = SUCCESS_PAGE;
        try {
            User user = new User();
            user.setId(Long.valueOf(req.getParameter("id")));
            user.setLogin(req.getParameter("login"));
            user.setPassword(req.getParameter("password"));
            user.setEmail(req.getParameter("email"));
            user.setRole(req.getParameter("role"));

            DBService.getInstance().updateUser(user);
        } catch (NumberFormatException | DBException e) {
            e.printStackTrace();
            forward = ERROR_PAGE;
        }
        resp.sendRedirect(forward);
    }
}
