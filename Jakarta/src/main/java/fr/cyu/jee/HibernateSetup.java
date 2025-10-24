package fr.cyu.jee;

import fr.cyu.jee.beans.Department;
import fr.cyu.jee.beans.User;
import fr.cyu.jee.beans.enums.Permission;
import fr.cyu.jee.dao.DepartmentDAO;
import fr.cyu.jee.dao.UserDAO;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class HibernateSetup implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);

        try {
            // setup admin account
            DepartmentDAO ddao = new DepartmentDAO();
            Department administratif = ddao.get(1);
            if (administratif == null) {
                administratif = new Department("Administratif");
                HibernateUtil.save(administratif);
            }

            UserDAO udao = new UserDAO();
            User admin = udao.get(1);

            if (admin == null) {
                admin = new User("admin", "admin", "admin", administratif);
                HibernateUtil.save(admin);
            }

            // add all permission
            for (Permission p : Permission.values()) {
                admin.getPermissions().add(p.name());
            }

            HibernateUtil.update(admin);
        } catch (Throwable t) {
        }
    }
}
