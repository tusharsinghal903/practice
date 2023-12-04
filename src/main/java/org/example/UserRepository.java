package org.example;

import java.util.List;

public interface UserRepository {
    List<User> getAllUsers();
    User getUserById(int userId);
    void saveUser(User user);
    void deleteUser(int userId);
}