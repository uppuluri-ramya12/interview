package com.example.interview.controller;

import com.example.interview.model.User;
import com.example.interview.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register() {
        return "register"; // Registration page
    }

    @PostMapping("/register")
    public String registerSubmit(@RequestParam String username, @RequestParam String password, Model model) {
        // Assuming a simple user creation method
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userService.save(user);
        return "redirect:/login"; // Redirect to login page after registration
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Login page
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String username, @RequestParam String password, Model model) {
        // Logic to authenticate the user
        User user = userService.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return "redirect:/posts"; // Redirect to home page on successful login
        }
        model.addAttribute("error", "Invalid credentials");
        return "login"; // Stay on login page if authentication fails
    }
}
