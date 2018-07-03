package ir.restusrmng.RestfulUserManagement.services;

import ir.restusrmng.RestfulUserManagement.models.User;

public interface AuthenticationService {
    public String generateToken();
    public String getToken(User user);
    public boolean checkToken(String token);
}
