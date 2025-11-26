package fr.cyu.data;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.cyu.controller.dto.SessionDTO;
import fr.cyu.data.employee.Employee;
import fr.cyu.data.employee.EmployeeService;
import fr.cyu.data.employee.Permission;

@Service
public class SessionService {
    @Autowired
    private EmployeeService es;

    public boolean isAuthorized(SessionDTO dto, Permission p) {
        if (dto == null || p == null) {
            return false;
        }

        Employee e = es.getById(dto.getSession_id()).orElse(null);

        return e != null && e.getPermissions().contains(p);
    }

    public boolean isAuthorized(SessionDTO dto, Collection<Permission> p) {
        if (dto == null || p == null || p.size() == 0) {
            return false;
        }

        Employee e = es.getById(dto.getSession_id()).orElse(null);

        return e != null && e.getPermissions().containsAll(p);
    }
}
