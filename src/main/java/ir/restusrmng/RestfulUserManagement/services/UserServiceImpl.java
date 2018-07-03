package ir.restusrmng.RestfulUserManagement.services;

import ir.restusrmng.RestfulUserManagement.models.User;
import ir.restusrmng.RestfulUserManagement.repositories.UserRepository;
import ir.restusrmng.RestfulUserManagement.utils.AuthenticationException;
import ir.restusrmng.RestfulUserManagement.utils.UserNotFoundException;
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

    public User findByUsername(String username) {
        User found = this.repo.findByUsername(username);
        Optional<User> t = Optional.ofNullable(found);
        return t.orElseThrow(() -> new UserNotFoundException(username));

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
        Optional<User> t = Optional.ofNullable(user);
        if (!t.isPresent()) {
            throw new UserNotFoundException(username);
        }
        repo.delete(user);
        return true;
    }

    @Transactional
    public User updateUser(String username, User user) {
        User tmp = repo.findByUsername(username);
        Optional<User> t = Optional.ofNullable(tmp);
        if (!t.isPresent()) {
            throw new UserNotFoundException(username);
        }
        user.setId(tmp.getId());
        repo.save(user);
        return user;
    }

    public User login(String username, String password) {
        User user = this.repo.findByUsername(username);
        if ((user == null) || !password.equals(user.getPassword())) {
            throw new AuthenticationException("Username or password invalid.");
        }
        return user;
    }
}
