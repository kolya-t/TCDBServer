package servlets;

import database.DBException;
import database.DBUserService;
import database.dataset.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/database")
public class UserDBServlet extends HttpServlet {
    public static final String contentType = "text/html;charset=UTF-8";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String action = req.getParameter("action");
            if (action == null) {
                forwardToUsersTable(req, resp);
                return;
            }
            int id = Integer.parseInt(req.getParameter("id"));

            switch (action) {
                case "delete":
                    DBUserService.instance().deleteUser(id);
                    forwardToUsersTable(req, resp);
                    break;
                case "edit":
                    DBUserService.instance().updateLogin(id, req.getParameter("login"));
                    DBUserService.instance().updateName(id, req.getParameter("name"));
                    DBUserService.instance().updatePassword(id, req.getParameter("password"));
                    DBUserService.instance().updateEmail(id, req.getParameter("email"));
                    forwardToUsersTable(req, resp);
                    break;
                case "add":
                    DBUserService.instance().addUser(new User(
                            id,
                            req.getParameter("login"),
                            req.getParameter("name"),
                            req.getParameter("password"),
                            req.getParameter("email")
                    ));
                    forwardToUsersTable(req, resp);
                    break;
                default:
                    forwardToUsersTable(req, resp);
            }
        } catch (NumberFormatException | DBException e) {
            e.printStackTrace();
            forwardToUsersTable(req, resp);
        }
    }

    public static void forwardToUsersTable(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(contentType);
        try {
            List<User> allUsers = DBUserService.instance().getAllUsers();
            req.setAttribute("users", allUsers);
            req.getRequestDispatcher("database.jsp").forward(req, resp);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (DBException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
