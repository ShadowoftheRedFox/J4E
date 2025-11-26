package fr.cyu.controller;

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

import fr.cyu.controller.dto.NewDepartmentDTO;
import fr.cyu.controller.dto.SessionDTO;
import fr.cyu.data.SessionService;
import fr.cyu.data.department.Department;
import fr.cyu.data.department.DepartmentService;
import fr.cyu.data.employee.EmployeeService;
import fr.cyu.data.employee.Permission;
import fr.cyu.utils.JSONUtil;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService ds;

    @Autowired
    private EmployeeService es;

    @Autowired
    private SessionService ss;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAll() {
        return JSONUtil.stringify(ds.getAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> get(@PathVariable("id") Integer id) {
        if (id <= 0) {
            return JSONUtil.NOT_FOUND_ERROR;
        }
        Optional<Department> e = ds.getById(id);
        if (e.isEmpty()) {
            return JSONUtil.NOT_FOUND_ERROR;
        }
        return ResponseEntity.ok(JSONUtil.stringify(e.get()));
    }

    @PostMapping(value = "")
    public ResponseEntity<String> newDepartment(@Valid @RequestBody NewDepartmentDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JSONUtil.BAD_REQUEST_ERROR;
        }

        if (!ss.isAuthorized(dto, Permission.CREATE_DEPARTMENT)) {
            return JSONUtil.UNAUTHORIZED_ERROR;
        }

        return ds.add(dto.getName(), es.employeeFromIds(dto.getEmployees())).isPresent() ? JSONUtil.OK
                : JSONUtil.BAD_REQUEST_ERROR;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<String> editDepartment(@PathVariable("id") Integer id,
            @Valid @RequestBody NewDepartmentDTO dto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JSONUtil.BAD_REQUEST_ERROR;
        }

        if (!ss.isAuthorized(dto, Permission.EDIT_DEPARTMENT)) {
            return JSONUtil.UNAUTHORIZED_ERROR;
        }

        Department d = ds.getById(id).orElse(null);
        if (d == null) {
            return JSONUtil.NOT_FOUND_ERROR;
        }

        d.setName(dto.getName());
        d.setEmployees(es.employeeFromIds(dto.getEmployees()));

        return ds.update(d) ? JSONUtil.OK : JSONUtil.SERVER_ERROR;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable("id") Integer id,
            @Valid @RequestBody SessionDTO dto, BindingResult bindingResult) {
        if (id == null || id <= 0) {
            return JSONUtil.BAD_REQUEST_ERROR;
        }

        if (!ss.isAuthorized(dto, Permission.DELETE_DEPARTMENT)) {
            return JSONUtil.UNAUTHORIZED_ERROR;
        }

        return ds.deleteById(id) ? JSONUtil.OK : JSONUtil.NOT_FOUND_ERROR;
    }
}
