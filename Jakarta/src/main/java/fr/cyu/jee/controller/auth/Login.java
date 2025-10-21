package fr.cyu.jee.controller.auth;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fr.cyu.jee.beans.User;
import fr.cyu.jee.dao.UserDAO;

@WebServlet("/auth/login")
public class Login extends HttpServlet {

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
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
            // Redirect to application root (adjust destination as needed)
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            // Authentication failed: forward back to login with an error message
            req.setAttribute("error", "Invalid username or password.");
            req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
        }
    }
}
