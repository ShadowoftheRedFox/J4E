package fr.cyu.jee.controller.employee;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import fr.cyu.jee.HibernateUtil;
import fr.cyu.jee.beans.Department;
import fr.cyu.jee.beans.User;
import fr.cyu.jee.beans.enums.Permission;
import fr.cyu.jee.beans.enums.Rank;
import fr.cyu.jee.dao.DepartmentDAO;
import fr.cyu.jee.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/employee/edit/*")
public class Edit extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO check if we can edit user
        // req.getCookies();

        // get the id
        String[] paths = req.getPathInfo().split("/");
        String idStr = "x";
        if (paths.length > 1) {
            idStr = paths[1];
        }

        req.setAttribute("id", idStr);

        try {
            UserDAO udao = new UserDAO();
            DepartmentDAO ddao = new DepartmentDAO();

            User user = udao.get(Integer.parseInt(idStr));
            List<Department> deps = ddao.getAll();

            req.setAttribute("user", user);
            req.setAttribute("departments", deps);
        } catch (Exception e) {
        }

        req.getRequestDispatcher("/employee/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // get the id
        String[] paths = req.getPathInfo().split("/");
        String idStr = "x";
        if (paths.length > 1) {
            idStr = paths[1];
        }

        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String password = req.getParameter("password");
        String department = req.getParameter("department");

        if (firstName == null || lastName == null || password == null || department == null ||
                firstName.isEmpty() || lastName.isEmpty() || password.isEmpty() || department.isEmpty()) {
            req.setAttribute("error", "Des champs sont vides");
            doGet(req, resp);
            return;
        }
        DepartmentDAO ddao = new DepartmentDAO();
        Department d;

        try {
            d = ddao.get(Integer.parseInt(department));
        } catch (Exception e) {
            req.setAttribute("error", "Département inconnu");
            doGet(req, resp);
            return;
        }

        UserDAO udao = new UserDAO();
        int id;

        try {
            id = Integer.parseInt(idStr);
        } catch (Exception e) {
            req.setAttribute("error", "Utilisateur inconnu");
            doGet(req, resp);
            return;
        }

        User user = udao.get(id);
        if (user == null) {
            req.setAttribute("error", "Utilisateur inconnu");
            doGet(req, resp);
            return;
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setDepartment(d);

        // update each permission
        Collection<String> up = user.getPermissions();
        for (Permission p : Permission.values()) {
            if ("on".equals(req.getParameter(p.name()))) {
                up.add(p.name());
            } else {
                up.remove(p.name());
            }
        }

        // update each rank
        Collection<String> ur = user.getRanks();
        for (Rank r : Rank.values()) {
            if ("on".equals(req.getParameter(r.name()))) {
                ur.add(r.name());
            } else {
                ur.remove(r.name());
            }
        }

        HibernateUtil.update(user);

        resp.sendRedirect(req.getContextPath() + "/employee");
    }
}
