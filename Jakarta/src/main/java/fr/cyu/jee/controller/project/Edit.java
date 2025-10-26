package fr.cyu.jee.controller.project;

import java.io.IOException;
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

@WebServlet("/project/edit/*")
public class Edit extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // get the id
        String[] paths = req.getPathInfo().split("/");
        String idStr = "x";
        if (paths.length > 1) {
            idStr = paths[1];
        }

        req.setAttribute("id", idStr);

        try {
            ProjectDAO pdao = new ProjectDAO();
            UserDAO udao = new UserDAO();
            
            Project project = pdao.get(Integer.parseInt(idStr));
            List<User> users = udao.getAll();

            req.setAttribute("project", project);
            req.setAttribute("users", users);
        } catch (Exception e) {
        }

        req.getRequestDispatcher("/project/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // get the id
        String[] paths = req.getPathInfo().split("/");
        String idStr = "x";
        if (paths.length > 1) {
            idStr = paths[1];
        }

        final String name = req.getParameter("name");
        final String status = req.getParameter("status");
        final String[] userIds = req.getParameterValues("users");

        if (name == null || status == null || name.isEmpty() || status.isEmpty()) {
            req.setAttribute("error", "Des champs sont vides");
            doGet(req, resp);
            return;
        }

        ProjectDAO pdao = new ProjectDAO();
        int id;

        try {
            id = Integer.parseInt(idStr);
        } catch (Exception e) {
            req.setAttribute("error", "Projet inconnu");
            doGet(req, resp);
            return;
        }

        Project project = pdao.get(id);
        if (project == null) {
            req.setAttribute("error", "Projet inconnu");
            doGet(req, resp);
            return;
        }

        try {
            final int projectId = id;
           
            HibernateUtil.useSession(new HibernateUtil.SessionWrapper<Boolean>() {
                @Override
                public Boolean use(Transaction trs, Session session) {
                    Project proj = session.find(Project.class, projectId);
                    if (proj != null) {
                        proj.setName(name);
                        proj.setStatus(Status.valueOf(status));

                 
                        Set<User> users = new HashSet<>();
                        if (userIds != null) {
                            for (String userId : userIds) {
                                try {
                                    int uid = Integer.parseInt(userId);
                                    User user = session.find(User.class, uid);
                                    if (user != null) {
                                        users.add(user);
                                    }
                                } catch (NumberFormatException e) {
                                    // Skip invalid user IDs
                                }
                            }
                        }
                        proj.setUsers(users);
                        
                        session.merge(proj);
                    }
                    return true;
                }
            });

            resp.sendRedirect(req.getContextPath() + "/project");
        } catch (Exception e) {
            req.setAttribute("error", "Erreur lors de la modification: " + e.getMessage());
            doGet(req, resp);
        }
    }
}
