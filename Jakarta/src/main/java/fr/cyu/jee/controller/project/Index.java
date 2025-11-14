package fr.cyu.jee.controller.project;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

import fr.cyu.jee.HibernateUtil;
import fr.cyu.jee.beans.Project;
import fr.cyu.jee.beans.User;
import fr.cyu.jee.beans.enums.Status;
import fr.cyu.jee.dao.ProjectDAO;
import fr.cyu.jee.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/project")
public class Index extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProjectDAO pdao = new ProjectDAO();
        UserDAO udao = new UserDAO();
        
        Collection<Project> projects = pdao.getAll();
        List<User> users = udao.getAll();

        req.setAttribute("projects", projects);
        req.setAttribute("users", users);

        req.getRequestDispatcher("/project/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String status = req.getParameter("status");
        String[] userIds = req.getParameterValues("users");

        if (name == null || status == null || name.isEmpty() || status.isEmpty()) {
            req.setAttribute("error", "Des champs sont vides");
        } else {
            try {
                HibernateUtil.useSession(new HibernateUtil.SessionWrapper<Boolean>() {
                    @Override
                    public Boolean use(Transaction trs, Session session) {
                        Project project = new Project();
                        project.setName(name);
                        project.setStatus(Status.valueOf(status));
                        
                        
                        Set<User> users = new HashSet<>();
                        if (userIds != null) {
                            for (String userId : userIds) {
                                try {
                                    int id = Integer.parseInt(userId);
                                    User user = session.find(User.class, id);
                                    if (user != null) {
                                        users.add(user);
                                    }
                                } catch (NumberFormatException e) {
                                    // Skip invalid user IDs
                                }
                            }
                        }
                        project.setUsers(users);
                        
                        session.persist(project);
                        return true;
                    }
                });
            } catch (Exception e) {
                req.setAttribute("error", "Erreur lors de la création du projet: " + e.getMessage());
            }
        }

        doGet(req, resp);
    }
}
