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

import fr.cyu.controller.dto.EditEmployeeDTO;
import fr.cyu.controller.dto.NewEmployeeDTO;
import fr.cyu.controller.dto.SessionDTO;
import fr.cyu.data.SessionService;
import fr.cyu.data.department.DepartmentService;
import fr.cyu.data.employee.Employee;
import fr.cyu.data.employee.EmployeeService;
import fr.cyu.data.employee.Permission;
import fr.cyu.data.employee.Rank;
import fr.cyu.utils.JSONUtil;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService es;

    @Autowired
    private DepartmentService ds;

    @Autowired
    private SessionService ss;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAll() {
        return JSONUtil.stringify(es.getAll());
    }

    @PostMapping(value = "")
    public ResponseEntity<String> newEmployee(@Valid @RequestBody NewEmployeeDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JSONUtil.BAD_REQUEST_ERROR;
        }

        if (!ss.isAuthorized(dto, Permission.CREATE_USER)) {
            return JSONUtil.UNAUTHORIZED_ERROR;
        }

        HashSet<Permission> permissions = new HashSet<>();
        dto.getPermissions().forEach(s -> {
            permissions.add(Permission.fromValue(s));
        });
        HashSet<Rank> ranks = new HashSet<>();
        dto.getRanks().forEach(s -> {
            ranks.add(Rank.fromValue(s));
        });

        boolean res = es.add(dto.getUsername(), dto.getPassword(), dto.getFirstName(), dto.getLastName(),
                ds.getById(dto.getDepartment()).orElse(null), ranks, permissions).isPresent();
        return res ? JSONUtil.OK : JSONUtil.BAD_REQUEST_ERROR;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> get(@PathVariable("id") Integer id) {
        if (id <= 0) {
            return JSONUtil.NOT_FOUND_ERROR;
        }
        Optional<Employee> e = es.getById(id);
        if (e.isEmpty()) {
            return JSONUtil.NOT_FOUND_ERROR;
        }
        return ResponseEntity.ok(JSONUtil.stringify(e.get()));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<String> editEmployee(@PathVariable("id") Integer id, @Valid @RequestBody EditEmployeeDTO dto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JSONUtil.BAD_REQUEST_ERROR;
        }

        if (!ss.isAuthorized(dto, Permission.EDIT_USER)) {
            return JSONUtil.UNAUTHORIZED_ERROR;
        }

        Employee e = es.getById(id).orElse(null);
        if (e == null) {
            return JSONUtil.NOT_FOUND_ERROR;
        }

        if (!dto.getPassword().isBlank()) {
            e.setPassword(dto.getPassword());
        }

        e.setDepartment(ds.getById(dto.getDepartment()).orElse(null));
        e.setFirstName(dto.getFirstName());
        e.setLastName(dto.getLastName());
        e.setUsername(dto.getUsername());
        HashSet<Permission> permissions = new HashSet<>();
        dto.getPermissions().forEach(s -> {
            permissions.add(Permission.fromValue(s));
        });
        e.setPermissions(permissions);
        HashSet<Rank> ranks = new HashSet<>();
        dto.getRanks().forEach(s -> {
            ranks.add(Rank.fromValue(s));
        });
        e.setRanks(ranks);

        return es.update(e) ? JSONUtil.OK : JSONUtil.SERVER_ERROR;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Integer id,
            @Valid @RequestBody SessionDTO dto, BindingResult bindingResult) {
        if (id == null || id <= 0) {
            return JSONUtil.BAD_REQUEST_ERROR;
        }

        if (!ss.isAuthorized(dto, Permission.DELETE_USER)) {
            return JSONUtil.UNAUTHORIZED_ERROR;
        }

        boolean res = es.deleteById(id);

        return res ? JSONUtil.OK : JSONUtil.NOT_FOUND_ERROR;
    }
}
