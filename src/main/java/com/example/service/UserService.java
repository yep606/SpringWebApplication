package com.example.service;

import com.example.domain.Role;
import com.example.domain.User;
import com.example.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private MailSender senderService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo.findByUsername(username);
    }

    public boolean addUser(User user) {
        User userFromDB = repo.findByUsername(user.getUsername());

        if (userFromDB != null)
            return false;

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        repo.save(user);

        sendMessage(user);

        return true;


    }

    private void sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s \n" +
                            "Welcome to my test project." +
                            "Check out this link: http://localhost:8080/activate/%s",
                    user.getUsername(), user.getActivationCode());

            senderService.send(user.getEmail(), "Activation code", message);
        }
    }

    public boolean activateUser(String code) {

        User user = repo.findByActivationCode(code);

        if (user == null)
            return false;

        user.setActivationCode(null);
        repo.save(user);

        return true;
    }

    public List<User> findAll() {

        return repo.findAll();

    }

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key))
                user.getRoles().add(Role.valueOf(key));
        }


        repo.save(user);
    }

    public void updateProfile(User user, String password, String email) {
        String userEmail = user.getEmail();

        boolean isChanged = ((email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email)));

        if (isChanged) {
            user.setEmail(email);

            user.setActivationCode(UUID.randomUUID().toString());
        }

        if(!StringUtils.isEmpty(password))
            user.setPassword(password);

        repo.save(user);

        if(isChanged)
            sendMessage(user);
    }
}
