package ir.restusrmng.RestfulUserManagement.models;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {

    @Id
    private String id;

    @NotNull
    @Size(min = 2, message = "Username should have at least 2 characters.")
    private String username;

    @NotNull
    @Size(min = 2, message = "Password should have at least 2 characters.")
    private String password;

    private boolean lock;

    public String getId() {
        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {

        this.lock = lock;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.lock = false;
    }

    public User() {};

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
