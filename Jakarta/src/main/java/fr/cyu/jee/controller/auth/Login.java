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
    private static final long serialVersionUID = 1L;

    public Login() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer loginUsername = Integer.parseInt(req.getParameter("username")); // le username est l'id
        String loginPassword = req.getParameter("password");
        UserDAO userDao = new UserDAO();

        Map<String, Object> usersInDb = new HashMap<>();
        Collection<User> users = userDao.find(usersInDb);
        User userInDb = isUsernameInDatabase(loginUsername, users);

        if (validatePassword(loginPassword, userInDb.getPassword())) {
            // Successful login: store user info in session and redirect to home/dashboard
            HttpSession session = req.getSession(true);
            session.setAttribute("user", loginUsername);
            // Redirect to application root (adjust destination as needed)
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            // Authentication failed: forward back to login with an error message
            req.setAttribute("error", "Invalid username or password.");
            RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp");
            rd.forward(req, resp);
        }
    }

    // Function that searches for a username in a collection by comparing the
    // username entered during login with those stored in the database.
    private User isUsernameInDatabase(int LoginUsername, Collection<User> users) {
        for (User u : users) {
            if (u.getId() == LoginUsername) {
                return u;
            }
        }
        return null;
    }

    // Compare the passwords entered during login with those stored in the
    // database
    private boolean validatePassword(String password, String passwordInDb) {
        return passwordInDb.equals(password);
    }
}
