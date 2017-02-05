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

@WebServlet("/user/add")
public class AddController extends HttpServlet {
    public static final String VIEW_JSP = "/views/user/add.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("UTF-8");

        req.getRequestDispatcher(VIEW_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("UTF-8");

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String role = req.getParameter("role");

        boolean done = false;
        User user = new User();
        if ((login != null) && (password != null) && (email != null) && (role != null)) {
            user.setLogin(login);
            user.setPassword(password);
            user.setEmail(email);
            user.setRole(role);

            try {
                long id = DBService.getInstance().addUser(user);
                done = id != -1;
            } catch (DBException e) {
                e.printStackTrace();
            }
        }

        if (done) {
            req.getSession().setAttribute("successMessage", "Пользователь добавлен");
            resp.sendRedirect("/user/list");
        } else {
            req.setAttribute("errorMessage", "Не удалось добавить пользователя");
            req.setAttribute("user", user);
            req.getRequestDispatcher(VIEW_JSP).forward(req, resp);
        }
    }
}
