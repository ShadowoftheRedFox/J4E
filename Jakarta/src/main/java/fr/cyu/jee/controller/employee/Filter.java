package fr.cyu.jee.controller.employee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.cyu.jee.beans.Department;
import fr.cyu.jee.beans.User;
import fr.cyu.jee.dao.DepartmentDAO;
import fr.cyu.jee.dao.ProjectDAO;
import fr.cyu.jee.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/employee/filter")
public class Filter extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO udao = new UserDAO();
        DepartmentDAO ddao = new DepartmentDAO();
        ProjectDAO pdao = new ProjectDAO();

        List<User> users = udao.getAll();
        List<Department> deps = ddao.getAll();

        HashMap<Integer, Integer> user_count = new HashMap<>();
        pdao.getAll().forEach(p -> {
            p.getUsers().forEach(user -> {
                if (user_count.containsKey(user.getId())) {
                    user_count.put(user.getId(), user_count.get(user.getId()) + 1);
                } else {
                    user_count.put(user.getId(), 1);
                }
            });
        });

        ArrayList<User> filteredUsers = new ArrayList<>();

        users.forEach(user -> {
            if (user_count.containsKey(user.getId()) && user_count.get(user.getId()) >= 4) {
                filteredUsers.add(user);
            }
        });

        req.setAttribute("users", filteredUsers);
        req.setAttribute("departments", deps);

        req.getRequestDispatcher("/employee/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filter_four = req.getParameter("filter_four");
        
        if (filter_four == null || filter_four.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/employee");
        } else {
            doGet(req, resp);
        }
    }
}
