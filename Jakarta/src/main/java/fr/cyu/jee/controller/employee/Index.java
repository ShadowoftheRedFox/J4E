package fr.cyu.jee.controller.employee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.cyu.jee.HibernateUtil;
import fr.cyu.jee.beans.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;

@WebServlet("/employee")
public class Index extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = new ArrayList<>();
//        Optional<List<User>> usersList = HibernateUtil.list("Users", User.class);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            users = session.createQuery("from User", User.class).list();
        } catch (Exception e) {
            throw new ServletException(e);
        }
        
//        if (usersList.isPresent()) {
//            users.addAll(usersList.get());
//        } else {
//            users = new ArrayList<>();
//        }
        req.setAttribute("users", users);

        req.getRequestDispatcher("/employee/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String password = req.getParameter("password");

        if (firstName == null || lastName == null || password == null ||
                firstName.isEmpty() || lastName.isEmpty() || password.isEmpty()) {
            req.setAttribute("error", "Des champs sont vides");
        } else {
            User user = new User(firstName, lastName, password);
            HibernateUtil.save(user);
        }

        doGet(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        int id;
        
        try {
            id =  Integer.parseInt(idStr);
        } catch (Exception e) {
            req.setAttribute("delete_error", "ID invalide ou inconnu");
            doGet(req, resp);
            return;
        }
    }
}
