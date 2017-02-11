package controllers.admin;

import services.UserServiceException;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/admin/delete")
public class DeleteController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("UTF-8");

        boolean done = false;
        try {
            long id = Long.parseLong(req.getParameter("id"));
            done = UserService.getInstance().deleteUser(id);
        } catch (NumberFormatException | UserServiceException e) {
            e.printStackTrace();
        }

        if (done) {
            // TODO: добавить сообщения
            resp.sendRedirect(req.getContextPath() + "/admin");
        }
    }
}
