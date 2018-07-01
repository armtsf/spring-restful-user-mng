package ir.restusrmng.RestfulUserManagement.services;

import ir.restusrmng.RestfulUserManagement.controllers.UserDTO;
import ir.restusrmng.RestfulUserManagement.models.User;
import ir.restusrmng.RestfulUserManagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    public List<User> findAll() {
        System.out.println("Find All Users");
        return repo.findAll();
    }

    public User findByUsername(String username) {
        System.out.println("Find by Username");
        return repo.findByUsername(username);
    }

    public User createUser(User user) {
        System.out.println("Create User");
        if (repo.findByUsername(user.getUsername()) != null) {
            return null;
        }
        repo.save(user);
        return user;
    }

    public boolean deleteByUsername(String username) {
        System.out.println("Delete User");
        User user = repo.findByUsername(username);
        if (user == null) {
            return false;
        }
        repo.delete(user);
        return true;
    }

    public User updateUser(String username, User user) {
        if (repo.findByUsername(username) == null) {
            return null;
        }
        repo.
    }
}
