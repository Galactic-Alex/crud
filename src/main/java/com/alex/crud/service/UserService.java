package com.alex.crud.service;

import com.alex.crud.model.User;
import com.alex.crud.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

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
        if (!isUserValid(user)) {
            response.sendError(400, "User params not valid");
            return;
        }
        Optional<User> optionalUser = userRepository.findFirstByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            response.sendError(409, "User with this email already exists");
            return;
        }

        userRepository.save(user);
    }

    private boolean isUserValid(User user) {
        if (user == null) {
            return false;
        }
        if (userHasNulls(user)) {
            return false;
        }
        return user.getAge() <= 130 && user.getAge() >= 18;
    }
    private boolean userHasNulls(User user) {
        if (user.getEmail() == null) {
            return true;
        }
        return user.getName() == null;
    }
}
