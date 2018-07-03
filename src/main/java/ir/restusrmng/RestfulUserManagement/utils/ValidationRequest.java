package ir.restusrmng.RestfulUserManagement.utils;

public class ValidationRequest {

    private String token;

    public ValidationRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
