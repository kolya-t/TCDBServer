package servlets.user;

import database.DBException;
import database.DBService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/user/list")
public class ListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("UTF-8");

        try {
            req.setAttribute("userList", DBService.getInstance().getAllUsers());
            req.getServletContext().getRequestDispatcher("/views/user/list.jsp").forward(req, resp);
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
