package org.atilla.cytraining.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Index controller.
 */
@Controller
public class IndexController {

    /**
     * Index/root page.
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return "index";
    }
}