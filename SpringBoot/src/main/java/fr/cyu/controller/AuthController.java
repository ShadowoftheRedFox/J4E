package fr.cyu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.cyu.controller.dto.AuthDTO;
import fr.cyu.data.employee.Employee;
import fr.cyu.data.employee.EmployeeRepository;
import fr.cyu.utils.JSONUtil;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private EmployeeRepository er;

    @PostMapping("")
    public ResponseEntity<String> connect(@Valid @RequestBody AuthDTO dto, BindingResult biding) {
        if (biding.hasErrors()) {
            return JSONUtil.BAD_REQUEST_ERROR;
        }

        Employee e = er.findByUsername(dto.getUsername()).orElse(null);
        if (e == null || !e.getPassword().equals(dto.getPassword()) || dto.getSession_id() > 0) {
            return JSONUtil.UNAUTHORIZED_ERROR;
        }
        return ResponseEntity.ok(JSONUtil.stringify(e));
    }
}
