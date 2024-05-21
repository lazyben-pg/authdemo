package com.lazyben.demo.controller;

import com.lazyben.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/auth/login")
    public Map<String, String> getSomething(@RequestBody Map<String, String> loginInfo) {
        return userService.login(loginInfo.get("username"), loginInfo.get("password"));
    }
}
