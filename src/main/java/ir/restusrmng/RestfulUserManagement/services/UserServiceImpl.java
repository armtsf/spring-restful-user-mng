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
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByUsername(String username) {
        User found = this.userRepository.findByUsername(username);
        Optional<User> t = Optional.ofNullable(found);
        return t.orElseThrow(() -> new UserNotFoundException(username));

    }

    @Transactional
    public User createUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return null;
        }
        userRepository.save(user);
        return user;
    }

    @Transactional
    public boolean deleteByUsername(String username) {
        User user = userRepository.findByUsername(username);
        Optional<User> t = Optional.ofNullable(user);
        if (!t.isPresent()) {
            throw new UserNotFoundException(username);
        }
        userRepository.delete(user);
        return true;
    }

    @Transactional
    public User updateUser(String username, User user) {
        User tmp = userRepository.findByUsername(username);
        Optional<User> t = Optional.ofNullable(tmp);
        if (!t.isPresent()) {
            throw new UserNotFoundException(username);
        }
        user.setId(tmp.getId());
        userRepository.save(user);
        return user;
    }

    public User login(String username, String password) {
        User user = this.userRepository.findByUsername(username);
        if ((user == null) || !password.equals(user.getPassword())) {
            throw new AuthenticationException("Username or password invalid.");
        }
        return user;
    }
}
