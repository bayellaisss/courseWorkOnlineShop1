package org.example.service;

import org.example.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private Map<String, User> users = new HashMap<>();

    public boolean register(String username, String password, String email) {
        if (users.containsKey(username)) {
            return false; // Пользователь уже существует
        }
        User newUser = new User(users.size() + 1, username, password, email, "", "", new ArrayList<>());
        users.put(username, newUser);
        return true;
    }

    public User login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null; // Неверное имя пользователя или пароль
    }

    public boolean userExists(String username) {
        return users.containsKey(username);
    }
}
