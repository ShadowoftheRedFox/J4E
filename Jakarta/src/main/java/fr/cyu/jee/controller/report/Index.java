package fr.cyu.jee.controller.report;

import java.io.IOException;

import fr.cyu.jee.beans.Department;
import fr.cyu.jee.beans.User;
import fr.cyu.jee.dao.DepartmentDAO;
import fr.cyu.jee.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/report")
public class Index extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("report/index.jsp").forward(req, resp);
        User user=new User();
        UserDAO udao=new UserDAO();
        Department  department=new Department();
        DepartmentDAO ddao=new DepartmentDAO();
        
        
      
        
        
    }
}
