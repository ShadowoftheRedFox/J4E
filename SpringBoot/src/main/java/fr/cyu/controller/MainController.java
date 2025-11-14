package fr.cyu.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.cyu.utils.JSONUtil;

/**
 * Main controller. Manage the main routes.
 */
@RestController
public class MainController {

    @CrossOrigin
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> get() {
        return JSONUtil.NOT_FOUND_ERROR;
    }
}
