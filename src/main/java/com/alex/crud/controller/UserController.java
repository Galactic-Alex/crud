package com.alex.crud.controller;

import com.alex.crud.model.User;
import com.alex.crud.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/")
    public ModelAndView indexPage() {
        logger.info("Some viewed the index page");
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
    public ResponseEntity<User> getCurrUser(Authentication authentication) {
        return new ResponseEntity<>(userService.getUserByName(authentication.getName()), HttpStatus.OK);
    }

    @GetMapping(path = "user/page")
    public ModelAndView getUserPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("parts/user_page");
        return modelAndView;
    }

    @GetMapping("admin/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.listAllUsers(), HttpStatus.OK);
    }

    @DeleteMapping("admin/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id, HttpServletResponse response) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "admin/user/{id}")
    public ResponseEntity<User> editUserPage(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    @GetMapping(path = "admin/page")
    public ModelAndView getAdminPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("parts/admin_page");
        return modelAndView;
    }

    @PostMapping(path = "admin/user")
    public ResponseEntity<?> registerNewUserPage(@ModelAttribute User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
