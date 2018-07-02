package ir.restusrmng.RestfulUserManagement.controllers;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDTO {

    @NotNull
    @Size(min = 2, message = "Username should have at least 2 characters.")
    private String username;

    @NotNull
    @Size(min = 2, message = "Password should have at least 2 characters.")
    private String password;

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserDTO() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
