package servlets;

import dbservice.DBException;
import dbservice.DBService;
import dbservice.datasets.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/database")
public class HelloWorldServlet extends HttpServlet {
    private static final String contentType = "text/html;charset=UTF-8";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(contentType);
        try {
            List<User> allUsers = DBService.instance().getAllUsers();
            req.setAttribute("users", allUsers);
            req.getRequestDispatcher("database.jsp").forward(req, resp);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (DBException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}