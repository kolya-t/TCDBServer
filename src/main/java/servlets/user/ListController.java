package servlets.user;

import database.DBException;
import database.DBService;
import database.helper.HibernateSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/user/list")
public class ListController extends HttpServlet {
    public static final String VIEW_JSP = "/views/user/list.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("UTF-8");

        try {
            req.setAttribute("userList", DBService.getInstance().getAllUsers());
            req.getServletContext().getRequestDispatcher(VIEW_JSP).forward(req, resp);
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        HibernateSessionFactory.shutdown();
    }
}
