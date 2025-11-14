package fr.cyu.jee.controller.department;

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

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@WebServlet("/department/edit/*")
public class Edit extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO check if we can edit department
        // req.getCookies();

        // get the id
        String[] paths = req.getPathInfo().split("/");
        String idStr = "x";
        if (paths.length > 1) {
            idStr = paths[1];
        }

        req.setAttribute("id", idStr);

        try {
            DepartmentDAO ddao = new DepartmentDAO();

            Department dep = ddao.get(Integer.parseInt(idStr));

            req.setAttribute("department", dep);
        } catch (Exception e) {
        }

        req.getRequestDispatcher("/department/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // get the id
        String[] paths = req.getPathInfo().split("/");
        String idStr = "x";
        if (paths.length > 1) {
            idStr = paths[1];
        }

        String name = req.getParameter("name");

        if (name == null || name.isEmpty()) {
            req.setAttribute("error", "Des champs sont vides");
            doGet(req, resp);
            return;
        }

        DepartmentDAO ddao = new DepartmentDAO();
        Department d;

        try {
            d = ddao.get(Integer.parseInt(idStr));
        } catch (Exception e) {
            req.setAttribute("error", "Département inconnu");
            doGet(req, resp);
            return;
        }

        d.setName(name);
        HibernateUtil.update(d);
        
        resp.sendRedirect(req.getContextPath() + "/department");
    }
}
