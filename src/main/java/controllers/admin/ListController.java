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
    public static final String VIEW_JSP = "/views/admin/list.jsp";
    public static final int PAGE_ROWS_LIMIT = 10; // макс. кол-во записей на страницу

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("UTF-8");

        try {
            // создание списка пользователей
            int offset = 0;
            String offsetStr = req.getParameter("offset");
            if (offsetStr != null) {
                offset = Integer.parseInt(offsetStr);
            }
            req.setAttribute("userList", UserService.getInstance().getUserList(offset, PAGE_ROWS_LIMIT));

            // создание списка номеров страниц и списка смещений
            int rowCount = UserService.getInstance().getUserCount();
            int pageCount = (int) Math.ceil((double) rowCount / PAGE_ROWS_LIMIT);
            Map<Integer, Integer> offsets = new LinkedHashMap<>();
            for (int i = 0; i < pageCount; i++) {
                offsets.put(i + 1, i * PAGE_ROWS_LIMIT);   // смещения которые будут вставляться в ссылку
            }
            req.setAttribute("offsets", offsets);

            // указание, какая страница активная
            req.setAttribute("activePage", offset / PAGE_ROWS_LIMIT + 1);

            // отрисовка страницы
            req.getServletContext().getRequestDispatcher(VIEW_JSP).forward(req, resp);
        } catch (UserServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        HibernateSessionFactory.shutdown();
    }
}
