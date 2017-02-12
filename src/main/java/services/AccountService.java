package services;

import database.pojo.User;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


/**
 * Сервис для выполнения операций, связаных с авторизацией пользователей
 */
public class AccountService {

    /**
     * Имя аттрибут пользователя в сессии
     */
    public static final String LOGGED_USER_ATTR = "loggedUser";

    /**
     * Имя cookie, в которой хранится идентификатор сессии сохраненного пользователя
     */
    private static final String SESSION_ID_COOKIE = "session_id";

    /**
     * Время жизни cookie хранящей идентификатор сессии сохраненного пользователя
     */
    private static final int COOKIE_MAX_AGE = 60 * 60 * 24 * 365;

    /**
     * Здесь только пользователи, отметившие "запомнить меня"
     */
    private static Map<String, User> sessionIdToUserMap = new HashMap<>();

    /**
     * Пара Request-Response
     */
    private HttpServletRequest req;
    private HttpServletResponse resp;

    /**
     * Конструктор с параметрами парой Request-Response.
     * Новый объект нужно создавать каждый раз для выполнения операции,
     * связанной с системой авторизации пользователей
     *
     * @param req  Request
     * @param resp Response
     */
    public AccountService(HttpServletRequest req, HttpServletResponse resp) {
        this.req = req;
        this.resp = resp;
    }

    /**
     * Выполняет регистрацию пользователя в системе без авторизации.
     *
     * @param login    введенный логин
     * @param password введенный пароль
     * @param email    введенный email
     * @return удалось ли зарегистрировать пользователя с такими данными
     */
    public boolean register(@NotNull String login, @NotNull String email, @NotNull String password)
            throws AccountServiceException {
        User userToRegister = new User(login, password, email, "user");
        try {
            long id = UserService.getInstance().addUser(userToRegister);
            return id != -1;
        } catch (UserServiceException e) {
            throw new AccountServiceException(e);
        }
    }

    /**
     * Сравнивает пару логин-пароль с записями из базы.
     * Если данные совпали, сохраняет пользователя как аттрибут сессии.
     * Если стоит флаг remember, то также сохраняет пару sessionID-User в словарь sessionIdToUserMap,
     * а sessionId в куках.
     *
     * @param login    введенный логин
     * @param password введенный пароль
     * @return удалось ли авторизоваться с такими данными
     */
    public boolean login(@NotNull String login, @NotNull String password, boolean remember) throws AccountServiceException {
        boolean logged = false;
        try {
            User user = UserService.getInstance().getUserByLogin(login);
            if ((user != null) && (user.getPassword().equals(password))) {
                HttpSession session = req.getSession();
                session.setAttribute(LOGGED_USER_ATTR, user);

                if (remember) {
                    Cookie sessionIdCookie = new Cookie(SESSION_ID_COOKIE, session.getId());
                    sessionIdCookie.setMaxAge(COOKIE_MAX_AGE);
                    resp.addCookie(sessionIdCookie);

                    sessionIdToUserMap.put(session.getId(), user);
                }

                logged = true;
            }
        } catch (UserServiceException e) {
            throw new AccountServiceException(e);
        }
        return logged;
    }

    /**
     * Проверка, залогинен ли пользователь. Сначала идет поиск в аттрибутах сессии.
     * Еси в сессии пользователь не найден, то, если пользователь выбирал "запомнить меня",
     * из cookies достается идентификатор сессии, в которой он выполнял вход.
     * Выполняется поиск пользователя по идентификатору сессии в sessionIdToUserMap,
     * и в случае совпадения пользователь сохраняется в текущую сессию.
     * Если хоть где-то (в сессии или в sessionIdToUserMap по sessionId) пользователь был найден,
     * то возвращает {@code true}.
     *
     * @return залогинен ли пользователь
     */
    public boolean isLoggedIn() {
        boolean logged = false;
        // 1. Поиск пользователя в сессии
        User user = (User) req.getSession().getAttribute(LOGGED_USER_ATTR);
        if (user != null) {
            logged = true;
        } else {
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    // 2.1. Проверка, есть ли запись session_id в куках
                    if (cookie.getName().equals(SESSION_ID_COOKIE)) {
                        // 2.2. Поиск пользователя с таким session_id в мапе
                        user = sessionIdToUserMap.get(cookie.getValue());
                        break;
                    }
                }
            }
            if (user != null) {
                // 2.3. Если такой пользователь найден - записать его в сессию
                req.getSession().setAttribute(LOGGED_USER_ATTR, user);
                logged = true;
            }
        }
        return logged;
    }

    /**
     * Выход пользователя из системы. Удаление сохраненного пользователя из сессии,
     * удаление cookie с идентификатором сессии, удаление сохраненного пользователя из sessionIdToUserMap.
     */
    public void logout() {
        Cookie sessionIdCookie = new Cookie(SESSION_ID_COOKIE, null);
        sessionIdCookie.setMaxAge(0);
        resp.addCookie(sessionIdCookie);

        HttpSession session = req.getSession();
        sessionIdToUserMap.remove(session.getId());

        session.invalidate();
    }

    /**
     * @return пользователь, сохраненный в сессии или {@code null}
     */
    public User getLoggedUser() {
        return (User) req.getSession().getAttribute(LOGGED_USER_ATTR);
    }
}
