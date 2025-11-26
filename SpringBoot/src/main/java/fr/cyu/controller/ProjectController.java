package fr.cyu.controller;

import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.cyu.controller.dto.NewProjectDTO;
import fr.cyu.controller.dto.SessionDTO;
import fr.cyu.data.SessionService;
import fr.cyu.data.employee.Employee;
import fr.cyu.data.employee.EmployeeService;
import fr.cyu.data.employee.Permission;
import fr.cyu.data.project.Project;
import fr.cyu.data.project.ProjectService;
import fr.cyu.data.project.Status;
import fr.cyu.utils.JSONUtil;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService ps;

    @Autowired
    private EmployeeService es;

    @Autowired
    private SessionService ss;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAll() {
        return JSONUtil.stringify(ps.getAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> get(@PathVariable("id") Integer id) {
        if (id <= 0) {
            return JSONUtil.NOT_FOUND_ERROR;
        }
        Optional<Project> e = ps.getById(id);
        if (e.isEmpty()) {
            return JSONUtil.NOT_FOUND_ERROR;
        }
        return ResponseEntity.ok(JSONUtil.stringify(e.get()));
    }

    @PostMapping(value = "")
    public ResponseEntity<String> newProject(@Valid @RequestBody NewProjectDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JSONUtil.BAD_REQUEST_ERROR;
        }

        if (!ss.isAuthorized(dto, Permission.CREATE_PROJECT)) {
            return JSONUtil.UNAUTHORIZED_ERROR;
        }

        boolean res = ps.add(dto.getName(), Status.fromValue(dto.getStatus()),
                new HashSet<Employee>(es.employeeFromIds(dto.getEmployees())))
                .isPresent();
        return res ? JSONUtil.OK : JSONUtil.BAD_REQUEST_ERROR;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<String> editProject(@PathVariable("id") Integer id, @Valid @RequestBody NewProjectDTO dto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JSONUtil.BAD_REQUEST_ERROR;
        }

        if (!ss.isAuthorized(dto, Permission.EDIT_PROJECT)) {
            return JSONUtil.UNAUTHORIZED_ERROR;
        }

        Project p = ps.getById(id).orElse(null);
        if (p == null) {
            return JSONUtil.NOT_FOUND_ERROR;
        }

        p.setName(dto.getName());
        p.setStatus(Status.fromValue(dto.getStatus()));
        p.setEmployees(new HashSet<Employee>(es.employeeFromIds(dto.getEmployees())));

        return ps.update(p) ? JSONUtil.OK : JSONUtil.SERVER_ERROR;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable("id") Integer id, @Valid @RequestBody SessionDTO dto,
            BindingResult bindingResult) {
        if (id == null || id <= 0 || bindingResult.hasErrors()) {
            return JSONUtil.BAD_REQUEST_ERROR;
        }

        if (!ss.isAuthorized(dto, Permission.DELETE_PROJECT)) {
            return JSONUtil.UNAUTHORIZED_ERROR;
        }

        boolean res = ps.deleteById(id);

        return res ? JSONUtil.OK : JSONUtil.NOT_FOUND_ERROR;
    }
}
