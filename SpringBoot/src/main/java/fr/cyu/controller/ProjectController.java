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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.cyu.data.project.Project;
import fr.cyu.data.project.ProjectService;
import fr.cyu.data.project.Status;
import fr.cyu.utils.JSONUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@RestController
@CrossOrigin
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService ps;

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

    public class NewProjectDTO {
        @NotNull
        @NotEmpty
        @Size(min = 3, max = 50)
        private String name;

        private String status;

        public String getName() {
            return name;
        }

        public String getStatus() {
            return status;
        }
    }

    @PostMapping(value = "/add")
    public ResponseEntity<String> newProject(@Valid NewProjectDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JSONUtil.BAD_REQUEST_ERROR;
        }
        boolean res = ps.add(dto.getName(), Status.fromValue(dto.getStatus())).isPresent();
        return res ? JSONUtil.OK : JSONUtil.BAD_REQUEST_ERROR;
    }

    @PutMapping(value = "/{id}/edit")
    public ResponseEntity<String> editProject(@PathVariable("id") Integer id, @Valid NewProjectDTO dto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JSONUtil.BAD_REQUEST_ERROR;
        }
        return JSONUtil.SERVER_ERROR;
    }
}
