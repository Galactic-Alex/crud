package com.alex.crud.service;

import com.alex.crud.model.User;
import com.alex.crud.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    public void deleteById(Long id, HttpServletResponse response) throws IOException {
        User user = findUserById(id, response);
        userRepository.delete(user);
    }

    public User findUserById(Long id, HttpServletResponse response) throws IOException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            response.sendError(400, "User doesn't exist");
            return null;
        }
        return optionalUser.get();
    }

    public void saveUser(User user, HttpServletResponse response) throws IOException {
        if (!isUserValid(user, response)) {
            return;
        }

        if (user.getId() == 0) {
            if (!isNewUserValid(user, response)) {
                return;
            }
        }

        userRepository.save(user);
    }

    private boolean isUserValid(User user, HttpServletResponse response) throws IOException {
        if (user == null) {
            response.sendError(400, "User is null");
            return false;
        }
        if (userHasNulls(user)) {
            response.sendError(400, "Some user fields are empty");
            return false;
        }
        if (user.getAge() > 130 || user.getAge() < 18) {
            response.sendError(400, "Invalid user age");
            return false;
        }

        return true;
    }

    private boolean userHasNulls(User user) {
        if (user.getEmail() == null) {
            return true;
        }
        return user.getName() == null;
    }

    private boolean isNewUserValid(User user, HttpServletResponse response) throws IOException {
        Optional<User> optionalUser = userRepository.findFirstByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            response.sendError(409, "User with this email already exists");
            return false;
        }
        optionalUser = userRepository.findFirstByName(user.getName());
        if (optionalUser.isPresent()) {
            response.sendError(409, "User with this name already exists");
            return false;
        }
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findFirstByName(username).get();
    }
}
