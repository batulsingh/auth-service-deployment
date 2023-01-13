package com.app.controller;


import com.app.model.SuccessBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin()
public class DashAuthController {

    @RequestMapping({ "/login" })
    public ResponseEntity<SuccessBody> firstPage(){
        SuccessBody sb = new SuccessBody();
        sb.setMesssge("Successfully logged in!");
        return ResponseEntity.ok(sb);
    }
}
