package ir.restusrmng.RestfulUserManagement.services;

import ir.restusrmng.RestfulUserManagement.models.User;
import ir.restusrmng.RestfulUserManagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repo;

    public List<User> findAll() {
        return repo.findAll();
    }

    public User findByUsername(String username) {
        return repo.findByUsername(username);
    }

    public User createUser(User user) {
        if (repo.findByUsername(user.getUsername()) != null) {
            return null;
        }
        repo.save(user);
        return user;
    }

    public boolean deleteByUsername(String username) {
        User user = repo.findByUsername(username);
        if (user == null) {
            return false;
        }
        repo.delete(user);
        return true;
    }

    public User updateUser(String username, User user) {
        User tmp = repo.findByUsername(username);
        if (tmp == null) {
            return null;
        }
        user.setId(tmp.getId());
        repo.save(user);
        return user;
    }

    public boolean login(User user) {
        String name = user.getUsername();
        String password = user.getPassword();
        if ((repo.findByUsername(name) == null) || !password.equals(repo.findByUsername(name).getPassword())) {
            return false;
        }
        return true;
    }
}
