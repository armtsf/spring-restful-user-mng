package ir.restusrmng.RestfulUserManagement.utils;

import ir.restusrmng.RestfulUserManagement.services.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FraudReceiver {

    @Autowired
    private UserService userService;

    @RabbitListener(queues = "fraud")
    public void receive(FraudMessage in) {
        try {
            userService.lock(in.getUsername());
        }
        catch (Exception e){}
    }

}
