package fr.cyu.jee;

import fr.cyu.jee.beans.User;
import fr.cyu.jee.dao.UserDAO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {
    public static void setSession(HttpServletRequest req, HttpServletResponse resp, int user_id) {
        HttpSession session = req.getSession(true);
        session.setAttribute("user", user_id);
        session.setAttribute("id", "8");
        
        Cookie cookie = new Cookie("account", String.valueOf(user_id));
        cookie.setPath("/");
        cookie.setMaxAge(3600 * 24 * 7); // 7 days
        cookie.setHttpOnly(true);
        cookie.setAttribute("SameSite", "strict");
        
        resp.addCookie(cookie);
    }

    public static User getSession(HttpServletRequest req, HttpServletResponse resp) {
        Object strId = req.getSession().getAttribute("user");
        UserDAO udao = new UserDAO();
        if (strId != null) {
            return udao.get(Integer.parseInt(strId.toString()));
        }

        return null;
    }

    public static void removeSession(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        if (session != null) {
            session.invalidate();
        }
    }
}
