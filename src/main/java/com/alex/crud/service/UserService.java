package com.alex.crud.service;

import com.alex.crud.model.User;
import com.alex.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    public void deleteById(Long id) {
        User user = findUserById(id);
        userRepository.delete(user);
    }

    public User findUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserServiceException("User doesn't exist");
        }
        return optionalUser.get();
    }

    public void saveUser(User user) {
        isUserValid(user);

        if (user.getId() == 0) {
            if (!isNewUserValid(user)) {
                return;
            }
        } else {
            changeUser(user);
            return;
        }

        userRepository.save(user);
    }

    public void changeUser(User user) {
        isUserValid(user);
        Optional<User> optionalUser = userRepository.findById(user.getId());
        if (optionalUser.isEmpty()) {
            throw new UserServiceException("User you were trying to change doesn't exist");
        }
        User previousUser = optionalUser.get();

        if (!previousUser.getEmail().equalsIgnoreCase(user.getEmail())) {
            optionalUser = userRepository.findFirstByEmail(user.getEmail());
            if (optionalUser.isPresent()) {
                throw new UserServiceException("User with this email already exists");
            }
        }
        if (!previousUser.getName().equals(user.getName())) {
            optionalUser = userRepository.findFirstByName(user.getName());
            if (optionalUser.isPresent()) {
                throw new UserServiceException("User with this name already exists");
            }
        }

        userRepository.save(user);
    }

    private void isUserValid(User user) {
        if (user == null) {
            throw new UserServiceException("User is null");
        }
        if (userHasNulls(user)) {
            throw new UserServiceException("Some user fields are empty");
        }
        if (user.getAge() > 130 || user.getAge() < 18) {
            throw new UserServiceException("Invalid user age");
        }
    }

    private boolean userHasNulls(User user) {
        if (user.getEmail() == null) {
            return true;
        }
        return user.getName() == null;
    }

    private boolean isNewUserValid(User user) {
        Optional<User> optionalUser = userRepository.findFirstByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            throw new UserServiceException("User with this email already exists");
        }
        optionalUser = userRepository.findFirstByName(user.getName());
        if (optionalUser.isPresent()) {
            throw new UserServiceException("User with this name already exists");
        }
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findFirstByName(username).get();
    }
}
