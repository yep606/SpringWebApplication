package com.example.controller;

import com.example.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepo repo;

    @GetMapping
    public String userList(Model model){

        model.addAttribute("users", repo.findAll());
        return "userlist";

    }

}
