package controllers.admin;

import services.UserServiceException;
import services.UserService;
import database.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/admin/edit")
public class EditController extends HttpServlet {
    public static final String EDIT_PAGE_PATH = "/views/admin/edit.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String ERROR_PAGE = "/admin";

        String forward = EDIT_PAGE_PATH;
        try {
            long id = Long.parseLong(req.getParameter("id"));
            User user = UserService.getInstance().getUser(id);
            if (user == null) {
                forward = ERROR_PAGE;
            } else {
                req.setAttribute("user", user);
            }
        } catch (NumberFormatException | UserServiceException e) {
            e.printStackTrace();
            forward = ERROR_PAGE;
        }
        req.getServletContext().getRequestDispatcher(forward).forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
                done = UserService.getInstance().updateUser(user);
            } catch (UserServiceException e) {
                e.printStackTrace();
            }
        }

        if (done) {
            req.getSession().setAttribute("successMessage", "Пользователь успешно изенен");
            resp.sendRedirect(req.getContextPath() + "/admin");
        } else {
            req.setAttribute("user", user);
            req.setAttribute("errorMessage", "Не удалось изменить пользователя");
            req.getRequestDispatcher(EDIT_PAGE_PATH).forward(req, resp);
        }
    }
}
