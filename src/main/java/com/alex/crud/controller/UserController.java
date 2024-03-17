package com.alex.crud.controller;

import com.alex.crud.model.User;
import com.alex.crud.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/")
    public ModelAndView indexPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView loginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView logoutPage(SecurityContextLogoutHandler logoutHandler, HttpServletResponse response, HttpServletRequest request, Authentication authentication) {
        logoutHandler.logout(request, response, authentication);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping(path = "user")
    public User getCurrUser(Authentication authentication) {
        return userService.getUserByName(authentication.getName());
    }

    @GetMapping(path = "user/page")
    public ModelAndView getUserPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("parts/user_page");
        return modelAndView;
    }

    @GetMapping("admin/users")
    public List<User> getAllUsers() {
        return userService.listAllUsers();
    }

    @DeleteMapping("admin/user/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "deleted";
    }

    @GetMapping(path = "admin/user/{id}")
    public User editUserPage(@PathVariable("id") Long id) {
        return userService.findUserById(id);
    }

    @GetMapping(path = "admin/page")
    public ModelAndView getAdminPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("parts/admin_page");
        return modelAndView;
    }

    @PostMapping(path = "admin/user")
    public String registerNewUserPage(@ModelAttribute User user) {
        userService.saveUser(user);
        return "saved";
    }
}
