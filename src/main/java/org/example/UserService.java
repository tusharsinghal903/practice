package org.example;

import java.util.List;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int getTotalUserCount() {
        List<User> users = userRepository.getAllUsers();
        return users.size();
    }

    public User getUserById(int userId) {
        return userRepository.getUserById(userId);
    }

    public void saveUser(User user) {
        userRepository.saveUser(user);
    }

    public void deleteUser(int userId) {
        userRepository.deleteUser(userId);
    }
}

