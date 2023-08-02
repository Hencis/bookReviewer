package com.siit.bookReviewer.service;

import com.siit.bookReviewer.model.User;
import com.siit.bookReviewer.model.dto.UserLoginDTO;
import com.siit.bookReviewer.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.security.InvalidParameterException;
import java.util.List;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public UserLoginDTO checkIfExists(String email, String password) {
        try {
            User user = userRepository.checkIfExists(email, password);
            return new UserLoginDTO(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName());
        } catch (InvalidParameterException e) {
            throw new InvalidParameterException("User is not found in the database");
        }
    }

    public User checkIfExists2(String email, String password) {
        try {
            User user = userRepository.checkIfExists(email, password);
            return new User( user.getEmail(), user.getPassword());
        } catch (InvalidParameterException e) {
            throw new InvalidParameterException("User is not found in the database");
        }
    }

    public void add(User user) {
        userRepository.addUser(user);
    }

    public User getUserByEmail(String email) {
        try {
            return userRepository.getUserByEmail(email);
        } catch (EntityNotFoundException e) {
            throw e;
        }
    }

    public int updateUserInfo(String newFirstName, String newLastName, String email) {
        return userRepository.updateUserInfo(newFirstName, newLastName, email);
    }

    public void deleteAccount(String email) {
        userRepository.deleteAccount(email);
    }
}