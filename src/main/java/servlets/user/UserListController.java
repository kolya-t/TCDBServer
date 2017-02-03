package servlets.user;

import database.DBException;
import database.DBService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/userList")
public class UserListController extends HttpServlet {

    private static final String SUCCESS_PAGE = "/views/userListView.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("UTF-8");

        try {
            req.setAttribute("userList", DBService.getInstance().getAllUsers());
            req.getRequestDispatcher(SUCCESS_PAGE).forward(req, resp);
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
