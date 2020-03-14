package com.example.controller;

import com.example.domain.Message;
import com.example.domain.User;
import com.example.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @Autowired
    private MessageRepo repo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/main")
    public String mainPage(@RequestParam(required = false, defaultValue = "") String filter, Model model) {

        Iterable<Message> messages;

        if (filter != null && !filter.isEmpty())
            messages = repo.findByTag(filter);
        else
            messages = repo.findAll();

        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);

        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @Valid Message message,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file) throws IOException {

            message.setAuthor(user);

            if(bindingResult.hasErrors()){
                Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

                model.mergeAttributes(errorsMap);
                model.addAttribute("message", message);
            }
            else {

                if (file != null && !file.getOriginalFilename().isEmpty()) {

                    File uploadDir = new File(uploadPath);

                    if (!uploadDir.exists())
                        uploadDir.mkdir();

                    String uuIdFile = UUID.randomUUID().toString();
                    String resultFile = uuIdFile + "." + file.getOriginalFilename();

                    file.transferTo(new File(uploadPath + "/" + resultFile));

                    message.setFilename(resultFile);


                }

                repo.save(message);
            }

        Iterable<Message> messages = repo.findAll();

        model.addAttribute("messages", messages);

        return "main";
    }


}