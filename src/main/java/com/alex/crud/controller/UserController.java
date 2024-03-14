package com.alex.crud.controller;

import com.alex.crud.model.User;
import com.alex.crud.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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

    @GetMapping(path = "/")
    public String indexPage() {
        return "index";
    }

    @GetMapping(path = "/register")
    public String registerFormPage(Model model) {
        model.addAttribute("user", new User());

        return "registration";
    }

    @PostMapping(path = "/register/new")
    public String registerNewUserPage(User user, HttpServletResponse response) throws IOException {
        userService.saveUser(user, response);

        if (response.isCommitted()) {
            return null;
        }

        return "login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage(SecurityContextLogoutHandler logoutHandler, HttpServletResponse response, HttpServletRequest request, Authentication authentication) {
        logoutHandler.logout(request, response, authentication);
        return "redirect:login";
    }

    @GetMapping(path = "/admin")
    public String admin(Model model) {
        model.addAttribute("users", userService.listAllUsers());

        return "admin";
    }

    @GetMapping(path = "admin/edit/{id}")
    public String editUserPage(@PathVariable("id") Long id, Model model, HttpServletResponse response) throws IOException {
        model.addAttribute("user", userService.findUserById(id, response));

        if (response.isCommitted()) {
            return null;
        }

        return "users_form";
    }

    @PostMapping("admin/save")
    public String saveUserPage(User user, HttpServletResponse response) throws IOException {
        userService.saveUser(user, response);

        if (response.isCommitted()) {
            return null;
        }

        return "redirect:/admin";
    }

    @GetMapping("admin/new")
    public String newForm(Model model) {
        model.addAttribute("user", new User());

        return "users_form";
    }

    @GetMapping(path = "admin/delete")
    public String deleteUserPage(@RequestParam Long id, HttpServletResponse response) throws IOException {
        userService.deleteById(id, response);

        if (response.isCommitted()) {
            return null;
        }

        return "redirect:/admin";
    }

    @GetMapping(path = "user")
    public String userPage(Authentication authentication, HttpServletResponse response, Model model) throws IOException {
        model.addAttribute("user", userService.loadUserByUsername(authentication.getName()));

        if (response.isCommitted()) {
            return null;
        }

        return "user";
    }
}
