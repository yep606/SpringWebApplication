package com.example.controller;

import com.example.domain.User;
import com.example.domain.dto.CaptchaResponseDto;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Autowired
    private UserService service;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${recaptcha.secret}")
    private String captchaKey;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam("passwordConfirm") String passwordConfirm,
                          @RequestParam("g-recaptcha-response") String capthcaResponce,
                          @Valid User user,
                          BindingResult bindingResult,
                          Model model) {

        String url = String.format(CAPTCHA_URL, captchaKey, capthcaResponce);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);

        if(!response.isSuccess())
            model.addAttribute("captchaError", "Fill captcha");

        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirm);
        if (isConfirmEmpty)
            model.addAttribute("passwordConfirmError", "Password confirmation cannot be null");

        if (user.getPassword() != null && !user.getPassword().equals(passwordConfirm))
            model.addAttribute("passwordError", "Different passwords");

        if (isConfirmEmpty || bindingResult.hasErrors() || !response.isSuccess()) {

            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);

            return "registration";

        }

        if (!service.addUser(user)) {
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = service.activateUser(code);

        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Activated!");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Authentication code expired");
        }

        return "loginPage";
    }

}
