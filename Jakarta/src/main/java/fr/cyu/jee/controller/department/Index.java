package fr.cyu.jee.controller.department;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.cyu.jee.HibernateUtil;
import fr.cyu.jee.beans.Department;
import fr.cyu.jee.dao.DepartmentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/department")
public class Index extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DepartmentDAO ddao = new DepartmentDAO();
        
        List<Department> departments = ddao.getAll();

        req.setAttribute("departments", departments);

        req.getRequestDispatcher("/department/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");

        if (name == null || name.isEmpty()) {
            req.setAttribute("error", "Des champs sont vides");
        } else {
            Department department = new Department(name);
            HibernateUtil.save(department);
        }

        doGet(req, resp);
    }
}
