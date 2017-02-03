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


@WebServlet("/user/edit")
public class EditUserController extends HttpServlet {

    private static final String SUCCESS_PAGE = "/views/user/edit.jsp";
    private static final String ERROR_PAGE = "/user/list";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("UTF-8");

        String forward = SUCCESS_PAGE;
        try {
            long id = Long.parseLong(req.getParameter("id"));
            User user = DBService.getInstance().getUser(id);
            if (user == null) {
                forward = ERROR_PAGE;
            } else {
                req.setAttribute("user", user);
            }
        } catch (NumberFormatException | DBException e) {
            e.printStackTrace();
            forward = ERROR_PAGE;
        }
        req.getServletContext().getRequestDispatcher(forward).forward(req, resp);
    }
}
