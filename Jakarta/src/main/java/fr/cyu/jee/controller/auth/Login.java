package fr.cyu.jee.controller.auth;

import java.io.IOException;

import fr.cyu.jee.beans.User;
import fr.cyu.jee.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/auth/login")
public class Login extends HttpServlet {

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO try catch parseint
        Integer loginId = Integer.parseInt(req.getParameter("identifier")); // le username est l'id
        String loginPassword = req.getParameter("password");

        UserDAO userDao = new UserDAO();
        User user = userDao.get(loginId);

        if (user != null && user.getPassword().equals(loginPassword)) {
            // Successful login: store user info in session and redirect to home/dashboard
            HttpSession session = req.getSession(true);
            session.setAttribute("user", loginId);
            Cookie cookie = new Cookie("account", loginId.toString());
            cookie.setPath("/");
            cookie.setMaxAge(3600 * 24 * 7);
            resp.addCookie(cookie);
            // Redirect to application root (adjust destination as needed)
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            // Authentication failed: forward back to login with an error message
            req.setAttribute("error", "Invalid username or password.");
            req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
        }
    }
}
