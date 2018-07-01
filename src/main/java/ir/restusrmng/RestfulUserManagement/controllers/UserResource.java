package ir.restusrmng.RestfulUserManagement.controllers;

import ir.restusrmng.RestfulUserManagement.models.User;
import ir.restusrmng.RestfulUserManagement.repositories.UserRepository;
import ir.restusrmng.RestfulUserManagement.services.UserService;
import ir.restusrmng.RestfulUserManagement.utils.CustomError;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/users")
@RestController
public class UserResource {

    @Autowired
    private UserRepository repo;

    @Autowired
    private UserService userService;

    private ModelMapper mapper = new ModelMapper();

    @GetMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        List<UserDTO> usersDto =  users.stream()
                .map(user -> convertToDto(user))
                .collect(Collectors.toList());
        return new ResponseEntity<List<UserDTO>>(usersDto, HttpStatus.OK);
    }

    @GetMapping(value = "/{username}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getUserByUsername(@PathVariable("username") String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return new ResponseEntity(new CustomError("User with username " + username + " not found."), HttpStatus.NOT_FOUND);
        }
        UserDTO userDto = convertToDto(user);
        return new ResponseEntity<UserDTO>(userDto, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity createUser(@RequestBody UserDTO userDto) throws ParseException {
        User user = convertToEntity(userDto);
        User newUser = userService.createUser(user);
        if (newUser == null) {
            return new ResponseEntity(new CustomError("Unable to create. A User with name " +
                    user.getUsername() + " already exist."),HttpStatus.CONFLICT);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{username}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteUser(@PathVariable("username") String username) {
        boolean success = userService.deleteByUsername(username);
        if (!success) {
            return new ResponseEntity(new CustomError("Unable to delete. User with username " + username + " does not exist."),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value = "/{username}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateUser(@PathVariable("username") String username, @RequestBody UserDTO userDto) throws ParseException {
        User user = convertToEntity(userDto);
        User updated = userService.updateUser(username, user);
        if (updated == null) {
            return new ResponseEntity(new CustomError("Unable to Update. User with username " + username + " does not exist."),
                    HttpStatus.NOT_FOUND);
        }
        UserDTO updatedDto = convertToDto(user);
        return new ResponseEntity(updatedDto, HttpStatus.OK);
    }

    private UserDTO convertToDto(User user) {
        UserDTO userDto = mapper.map(user, UserDTO.class);
        return userDto;
    }

    private User convertToEntity(UserDTO userDto) throws ParseException {
        User user = mapper.map(userDto, User.class);
        return user;
    }

}
