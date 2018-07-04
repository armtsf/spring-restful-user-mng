package ir.restusrmng.RestfulUserManagement.services;

import ir.restusrmng.RestfulUserManagement.models.User;

import java.util.List;

public interface UserService {
    public List<User> findAll();
    public User findByUsername(String username);
    public User createUser(User user);
    public boolean deleteByUsername(String username);
    public User updateUser(String username, User user);
    public User login(String username, String password);
    public void lock(String username);
}
