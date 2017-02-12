package controllers.admin;

import services.UserServiceException;
import services.UserService;
import database.helper.HibernateSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;


@WebServlet("/admin")
public class ListController extends HttpServlet {
    public static final String USER_LIST_PAGE_PATH = "/views/admin/list.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("UTF-8");

        try {
            req.setAttribute("userList", UserService.getInstance().getUserList());
            req.getServletContext().getRequestDispatcher(USER_LIST_PAGE_PATH).forward(req, resp);
        } catch (UserServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        HibernateSessionFactory.shutdown();
    }
}
