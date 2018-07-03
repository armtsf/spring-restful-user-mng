package ir.restusrmng.RestfulUserManagement.services;

import ir.restusrmng.RestfulUserManagement.models.User;
import ir.restusrmng.RestfulUserManagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repo;

    public List<User> findAll() {
        return repo.findAll();
    }

    public Optional<User> findByUsername(String username) {
        User found = this.repo.findByUsername(username);
        return Optional.ofNullable(found);

    }

    @Transactional
    public User createUser(User user) {
        if (repo.findByUsername(user.getUsername()) != null) {
            return null;
        }
        repo.save(user);
        return user;
    }

    @Transactional
    public boolean deleteByUsername(String username) {
        User user = repo.findByUsername(username);
        if (user == null) {
            return false;
        }
        repo.delete(user);
        return true;
    }

    @Transactional
    public User updateUser(String username, User user) {
        User tmp = repo.findByUsername(username);
        if (tmp == null) {
            return null;
        }
        user.setId(tmp.getId());
        repo.save(user);
        return user;
    }

    public User login(String username, String password) {
        User user = this.repo.findByUsername(username);
        if ((user == null) || !password.equals(user.getPassword())) {
            return null;
        }
        return user;
    }
}
