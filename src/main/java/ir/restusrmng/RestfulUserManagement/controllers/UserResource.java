package ir.restusrmng.RestfulUserManagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.restusrmng.RestfulUserManagement.models.User;
import ir.restusrmng.RestfulUserManagement.services.AuthenticationService;
import ir.restusrmng.RestfulUserManagement.services.UserService;
import ir.restusrmng.RestfulUserManagement.utils.LoginResponse;
import ir.restusrmng.RestfulUserManagement.utils.UserList;
import ir.restusrmng.RestfulUserManagement.utils.UserNotFoundException;
import ir.restusrmng.RestfulUserManagement.utils.ValidationRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/users")
@RestController
public class UserResource {

    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationService authenticationService;

    private ObjectMapper jckson = new ObjectMapper();

    private ModelMapper mapper = new ModelMapper();

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserList> getUsers() {
        List<User> users = userService.findAll();
        if (users == null || users.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        UserList usersDto =  new UserList(users.stream()
                .map(user -> convertToDto(user))
                .collect(Collectors.toList()));
        return new ResponseEntity<UserList>(usersDto, HttpStatus.OK);
    }

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable("username") String username) {
        Optional<User> user = userService.findByUsername(username);
        if (!user.isPresent()) {
            throw new UserNotFoundException(username);
        }
        UserDTO userDto = convertToDto(user.get());
        return new ResponseEntity<UserDTO>(userDto, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity createUser(@Valid @RequestBody UserDTO userDto) throws ParseException {
        User user = convertToEntity(userDto);
        User newUser = userService.createUser(user);
        if (newUser == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteUser(@PathVariable("username") String username) {
        boolean success = userService.deleteByUsername(username);
        if (!success) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value = "/{username}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDTO> updateUser(@PathVariable("username") String username, @RequestBody UserDTO userDto) throws ParseException {
        User user = convertToEntity(userDto);
        User updated = userService.updateUser(username, user);
        if (updated == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        UserDTO updatedDto = convertToDto(user);

        return new ResponseEntity(updatedDto, HttpStatus.OK);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LoginResponse> loginUser(@RequestBody UserDTO userDto) throws ParseException {
        User loginUser = userService.login(userDto.getUsername(), userDto.getPassword());
        if (loginUser == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        LoginResponse response = new LoginResponse(authenticationService.getToken(loginUser));
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/validation" , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,  produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity validateUser(@RequestBody ValidationRequest reqJson) {
        String token = reqJson.getToken();
        boolean check = authenticationService.checkToken(token);
        if (!check) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok().build();
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
