package ir.restusrmng.RestfulUserManagement.utils;

import ir.restusrmng.RestfulUserManagement.controllers.UserDTO;

import java.util.List;

public class UserList {

    private List<UserDTO> userList;

    public UserList(List<UserDTO> userList) {
        this.userList = userList;
    }

    public List<UserDTO> getUserList() {
        return userList;
    }

}
