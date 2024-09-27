package com.farmstory.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    //main page
    @GetMapping(value = {"/","/index","/category"})
    public String index() {
        return "index";
    }
}
