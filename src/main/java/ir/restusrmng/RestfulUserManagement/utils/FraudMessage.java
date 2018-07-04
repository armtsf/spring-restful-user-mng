package ir.restusrmng.RestfulUserManagement.utils;

public class FraudMessage {

    private String username;

    public FraudMessage() {}

    public FraudMessage(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
