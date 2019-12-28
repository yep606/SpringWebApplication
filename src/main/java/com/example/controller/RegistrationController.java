package com.example.controller;

import com.example.domain.Role;
import com.example.domain.User;
import com.example.repos.UserRepo;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegistrationController {

    @Autowired
    UserRepo userRepo;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model){
      User userFromDB = userRepo.findByUsername(user.getUsername());

      if(userFromDB != null){
          model.addAttribute("message", "User exists!");
          return "registration";
      }
      user.setActive(true);
      user.setRoles(Collections.singleton(Role.USER));
      userRepo.save(user);

      return "redirect:/login";


    }

}
