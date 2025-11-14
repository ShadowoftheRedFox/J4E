package fr.cyu.jee.controller.auth;

import java.io.IOException;

import fr.cyu.jee.SessionUtil;
import fr.cyu.jee.beans.User;
import fr.cyu.jee.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/auth/login")
public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            req.setAttribute("error", "Champs manquants");
            doGet(req, resp);
            return;
        }

        try {
            int id = Integer.parseInt(login);
            UserDAO userDao = new UserDAO();
            User user = userDao.get(id);

            if (user != null && password.equals(user.getPassword())) {
                SessionUtil.setSession(req, resp, id);
                resp.sendRedirect(req.getContextPath() + "/");
                return;
            }

            throw new Exception("Invalid login or password");
        } catch (Exception e) {
            req.setAttribute("error", "Identifiant ou mot de passe invalide");
        }
        doGet(req, resp);
    }
}
