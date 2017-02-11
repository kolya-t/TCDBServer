package services;

import database.pojo.User;
import org.jetbrains.annotations.Nullable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


public class AccountService {
    public static final String LOGGED_USER = "loggedUser";
    private static final String SESSION_ID = "session_id";
    private int cookieMaxAge = 365 * 24 * 60 * 60; // неделя по-умолчанию
    private static AccountService instance;
    private Map<String, User> sessionIdToUserMap;

    private AccountService() {
        sessionIdToUserMap = new HashMap<>();
    }

    public static AccountService getInstance() {
        if (instance == null) {
            instance = new AccountService();
        }
        return instance;
    }

    /**
     * Выполняет регистрацию пользователя в системе без авторизации.
     *
     * @param login    введенный логин
     * @param password введенный пароль
     * @param email    введенный email
     * @return удалось ли зарегистрировать пользователя с такими данными
     */
    public boolean register(String login, String password, String email)
            throws AccountServiceException {
        boolean result = false;
        try {
            if ((login != null) && (password != null) && (email != null)) {
                User user = new User(null, login, password, email, "user");
                long id = UserService.getInstance().addUser(user);
                result = id != -1;
            }
        } catch (UserServiceException e) {
            e.printStackTrace();
            throw new AccountServiceException(e);
        }
        return result;
    }

    /**
     * Сравнивает пару логин-пароль с записями из базы.
     * Если данные совпали, сохраняет пользователя как аттрибут сессии.
     * Сохраняет sessionId в куках.
     * Если стоит флаг remember, то также сохраняет пару sessionID-User в словарь sessionIdToUserMap.
     *
     * @param session  сессия пользователя
     * @param login    введенный логин
     * @param password введенный логин
     * @param remember нужно ли запомнить данные пользователя в куках
     * @return удалось ли авторизоваться с такими данными
     */
    public boolean login(HttpSession session, HttpServletResponse resp, String login, String password, boolean remember)
            throws AccountServiceException {
        boolean result = false;
        try {
            User user = UserService.getInstance().getUserByLogin(login);

            if (user != null && user.getPassword().equals(password)) {
                session.setAttribute(LOGGED_USER, user);

                String sessionId = session.getId();
                sessionIdToUserMap.put(sessionId, user);

                Cookie cookie = new Cookie(SESSION_ID, sessionId);
                cookie.setMaxAge(remember ? cookieMaxAge : -1);
                resp.addCookie(cookie);

                result = true;
            }
        } catch (UserServiceException e) {
            e.printStackTrace();
            throw new AccountServiceException(e);
        }

        return result;
    }

    /**
     * Ищет в карте залогиненых пользователей пользователя с sessionId таким же,
     * как сохранен в куках (не обязательно id текущей сессии).
     * Если в куках сохранен sessionId и пользователь по этому sessionId найден,
     * сохраняет пользователя как аттрибут сессии и возвращает {@code true}
     *
     * @param req запрос
     * @return был ли пользователь залогинен
     */
    public boolean checkLogged(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(SESSION_ID)) {

                    String sessionId = cookie.getValue();
                    if (sessionIdToUserMap.containsKey(sessionId)) {

                        User user = sessionIdToUserMap.get(sessionId);
                        req.getSession().setAttribute(LOGGED_USER, user);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public @Nullable User getLoggedUser(HttpSession session) {
        return (User) session.getAttribute(LOGGED_USER);
    }


    /**
     * Выход из системы
     *
     * @param req  запрос
     * @param resp ответ
     */
    public void logout(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(SESSION_ID)) {

                    String sessionId = cookie.getValue();
                    if (sessionIdToUserMap.containsKey(sessionId)) {
                        sessionIdToUserMap.remove(sessionId);
                    }

                    // протестить, мб можно заменить на cookie.setMaxAge(0)
                    Cookie sessionIdCookie = new Cookie(SESSION_ID, null);
                    sessionIdCookie.setMaxAge(0);
                    resp.addCookie(sessionIdCookie);
                    break;
                }
            }
        }

        req.getSession().invalidate();
    }

    public int getCookieMaxAge() {
        return cookieMaxAge;
    }

    public void setCookieMaxAge(int cookieMaxAge) {
        this.cookieMaxAge = cookieMaxAge;
    }
}
