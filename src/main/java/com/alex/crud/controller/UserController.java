package com.alex.crud.controller;

import com.alex.crud.model.User;
import com.alex.crud.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/users")
    public String users(Model model) {
        model.addAttribute("users", userService.listAllUsers());

        return "users";
    }

    @GetMapping(path = "users/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model, HttpServletResponse response) throws IOException {
        model.addAttribute("user", userService.findUserById(id, response));

        if (response.isCommitted()) {
            return null;
        }

        return "users_form";
    }

    @PostMapping("users/save")
    public String saveUser(User user, HttpServletResponse response) throws IOException {
        userService.saveUser(user, response);

        if (response.isCommitted()) {
            return null;
        }

        return "redirect:/users";
    }

    @GetMapping("users/new")
    public String showNewForm(Model model) {
        model.addAttribute("user", new User());

        return "users_form";
    }

    @GetMapping(path = "users/delete")
    public String deleteUser(@RequestParam long id, HttpServletResponse response) throws IOException {
        userService.deleteById(id, response);

        if (response.isCommitted()) {
            return null;
        }

        return "redirect:/users";
    }
}
