package controllers.admin;

import database.DBException;
import database.DBService;
import database.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/admin/edit")
public class EditController extends HttpServlet {
    public static final String VIEW_JSP = "/views/admin/edit.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String ERROR_PAGE = "/admin";

        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("UTF-8");

        String forward = VIEW_JSP;
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


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("UTF-8");

        String idStr = req.getParameter("id");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String role = req.getParameter("role");

        boolean done = false;
        User user = new User();
        if ((idStr != null) && (login != null) && (password != null) && (email != null) && (role != null)) {
            user.setId(Long.valueOf(idStr));
            user.setLogin(login);
            user.setPassword(password);
            user.setEmail(email);
            user.setRole(role);

            try {
                done = DBService.getInstance().updateUser(user);
            } catch (DBException e) {
                e.printStackTrace();
            }
        }

        if (done) {
            req.getSession().setAttribute("successMessage", "Пользователь успешно изенен");
            resp.sendRedirect("/admin");
        } else {
            req.setAttribute("user", user);
            req.setAttribute("errorMessage", "Не удалось изменить пользователя");
            req.getRequestDispatcher(VIEW_JSP).forward(req, resp);
        }
    }
}
