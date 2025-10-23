package fr.cyu.jee.controller.employee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.cyu.jee.HibernateUtil;
import fr.cyu.jee.beans.Department;
import fr.cyu.jee.beans.User;
import fr.cyu.jee.dao.DepartmentDAO;
import fr.cyu.jee.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/employee")
public class Index extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO udao = new UserDAO();
        DepartmentDAO ddao = new DepartmentDAO();
        
        List<User> users = udao.getAll();
        List<Department> deps = ddao.getAll();

        req.setAttribute("users", users);
        req.setAttribute("departments", deps);

        req.getRequestDispatcher("/employee/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String password = req.getParameter("password");
        String department = req.getParameter("department");

        if (firstName == null || lastName == null || password == null || department == null ||
            firstName.isEmpty() || lastName.isEmpty() || password.isEmpty() || department.isEmpty()) {
            req.setAttribute("error", "Des champs sont vides");
        } else {
            DepartmentDAO ddao = new DepartmentDAO();
            Department d;

            try {
                d = ddao.get(Integer.parseInt(department));
            } catch (Exception e) {
                req.setAttribute("error", "Département inconnu");
                doGet(req, resp);
                return;
            }

            User user = new User(firstName, lastName, password, d);
            HibernateUtil.save(user);
        }

        doGet(req, resp);
    }
}
