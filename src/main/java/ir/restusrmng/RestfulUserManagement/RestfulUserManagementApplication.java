package ir.restusrmng.RestfulUserManagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestfulUserManagementApplication {

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger(RestfulUserManagementApplication.class);
    }

	public static void main(String[] args) {
		SpringApplication.run(RestfulUserManagementApplication.class, args);
	}
}
