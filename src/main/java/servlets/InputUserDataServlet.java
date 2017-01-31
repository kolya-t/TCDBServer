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

@WebServlet("/inputUserData")
public class InputUserDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String action = req.getParameter("action");
            User user;
            switch (action) {
                case "edit":
                    int id = Integer.parseInt(req.getParameter("id"));
                    user = DBUserService.instance().getUser(id);
                    if (user == null) {
                        user = new User();
                    }
                    req.setAttribute("user", user);
                    req.setAttribute("action", req.getParameter("action"));
                    break;
                case "add":
                    user = new User();
                    req.setAttribute("user", user);
                    req.setAttribute("action", req.getParameter("action"));
                    break;
                default:
                    UserDBServlet.forwardToUsersTable(req, resp);
                    return;
            }
            forwardToInputPage(req, resp);

        } catch (NumberFormatException | DBException | NullPointerException e) {
            UserDBServlet.forwardToUsersTable(req, resp);
        }
    }

    private void forwardToInputPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(UserDBServlet.contentType);
        req.getRequestDispatcher("input_user_data.jsp").forward(req, resp);
        resp.setStatus(HttpServletResponse.SC_OK);

    }
}
