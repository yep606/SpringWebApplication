package com.example.controller;

import com.example.domain.Message;
import com.example.domain.User;
import com.example.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private MessageRepo repo;

    @GetMapping("/")
    public String greeting(String name, Model model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String mainPage(Model model) {

        Iterable<Message> messages = repo.findAll();
        model.addAttribute("messages", messages);

        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String name,
            @RequestParam String tag, Model model) {

        Message message = new Message(name, tag, user);
        repo.save(message);

        Iterable<Message> messages = repo.findAll();

        model.addAttribute("messages", messages);

        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Model model) {
        Iterable<Message> messages;
        if (filter != null && !filter.isEmpty())
            messages = repo.findByTag(filter);
        else
            messages = repo.findAll();

        model.addAttribute("messages", messages);

        return "main";

    }

}