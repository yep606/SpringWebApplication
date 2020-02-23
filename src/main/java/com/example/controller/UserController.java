package com.example.controller;

import com.example.domain.Role;
import com.example.domain.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", service.findAll());

        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    ) {

        service.saveUser(user, username, form);

        return "redirect:/user";

    }

    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user){

        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "profile";

    }

    @PostMapping("profile")
    public String updateProfile(@AuthenticationPrincipal User user,
                                @RequestParam String password,
                                @RequestParam String email){

        service.updateProfile(user, password, email);

        return "redirect:/user/profile";
    }
























}
