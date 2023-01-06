package com.app.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin()
public class DashAuthController {

    @RequestMapping({ "/login" })
    public String firstPage(){
        return "Successfully logged in!";
    }
}
