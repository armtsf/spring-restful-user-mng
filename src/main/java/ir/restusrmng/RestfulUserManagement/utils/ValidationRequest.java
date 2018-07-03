package ir.restusrmng.RestfulUserManagement.utils;

public class ValidationRequest {

    private String token;

    public ValidationRequest(String token) {
        this.token = token;
    }

    public ValidationRequest() {}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
