package ir.restusrmng.RestfulUserManagement.utils;

import java.util.Date;

public class ErrorDetails {

    private Date date;
    private String message;

    public ErrorDetails(String message, Date date) {
        this.message = message;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }
}
