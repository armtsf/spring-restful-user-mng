package ir.restusrmng.RestfulUserManagement.controllers;

import ir.restusrmng.RestfulUserManagement.utils.ErrorDetails;
import ir.restusrmng.RestfulUserManagement.utils.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorDetails errorDetails = new ErrorDetails("Username [" + ex.getMessage() + "] not found.", new Date());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


}
