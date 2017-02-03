package servlets.user;

import database.DBException;
import database.DBService;
import database.pojo.UserBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/doAdd")
public class DoAddUserController extends HttpServlet {

    private static final String SUCCESS_PAGE = "/user/list";
    private static final String ERROR_PAGE = "/user/add";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("UTF-8");

        String forward = SUCCESS_PAGE;
        try {
            UserBuilder userBuilder = new UserBuilder()
                    .setLogin(req.getParameter("login"))
                    .setName(req.getParameter("name"))
                    .setPassword(req.getParameter("password"))
                    .setEmail(req.getParameter("email"));

            DBService.getInstance().addUser(userBuilder.build());
        } catch (DBException e) {
            e.printStackTrace();
            forward = ERROR_PAGE;
        }
        resp.sendRedirect(forward);
    }
}
