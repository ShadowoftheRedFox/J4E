package fr.cyu.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.cyu.controller.dto.NewDepartmentDTO;
import fr.cyu.data.department.Department;
import fr.cyu.data.department.DepartmentService;
import fr.cyu.utils.JSONUtil;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService ds;

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
        return ds.add(dto.getName()).isPresent() ? JSONUtil.OK : JSONUtil.BAD_REQUEST_ERROR;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<String> editDepartment(@PathVariable("id") Integer id,
            @Valid @RequestBody NewDepartmentDTO dto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JSONUtil.BAD_REQUEST_ERROR;
        }

        Department d = ds.getById(id).orElse(null);
        if (d == null) {
            return JSONUtil.NOT_FOUND_ERROR;
        }

        d.setName(dto.getName());
        // d.setEmployees(); // TODO employees

        return JSONUtil.OK;
    }
}
