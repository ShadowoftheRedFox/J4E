package fr.cyu.jee.controller.department;

import java.io.IOException;

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

@WebServlet("/department/delete")
public class Delete extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/department");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        int id;

        try {
            id = Integer.parseInt(idStr);
        } catch (Exception e) {
            doGet(req, resp);
            return;
        }
        
        // TODO move all users in the department to another department, or fail if can't

        DepartmentDAO ddao = new DepartmentDAO();
        ddao.delete(id);
        UserDAO udao = new UserDAO();
        User user= new User();
        Department departement = new Department();
        for(int i=0; i<udao.getAll().size();i++){
            
            if(udao.getAll().isEmpty()){
                continue;
            }
            if (ddao.get(id)== user.getDepartment() && udao.getAll().get(i).getDepartment().getId() == id){
                user.setDepartment(ddao.get(1));
                
            }
            else{
                req.setAttribute("error","Requête échoué");
                resp.sendRedirect(req.getContextPath() +"/department");
            }
        }
        
        doGet(req, resp);
    }
}