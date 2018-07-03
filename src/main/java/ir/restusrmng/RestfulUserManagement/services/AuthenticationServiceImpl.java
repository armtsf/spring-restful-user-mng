package ir.restusrmng.RestfulUserManagement.services;

import ir.restusrmng.RestfulUserManagement.models.User;
import ir.restusrmng.RestfulUserManagement.repositories.TokenRepository;
import ir.restusrmng.RestfulUserManagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    public String generateToken() {
        int minLimit = 65;
        int maxLimit = 122;
        Random rand = new Random();
        String token = "";
        ArrayList<Integer> ban = new ArrayList<Integer>() {{
            add(91); add(92); add(93); add(94); add(95); add(96);
        }};
        for (int i = 0; i < 15; i++) {
            int tmp = rand.nextInt(maxLimit - minLimit) + minLimit;
            if (ban.contains(new Integer(tmp))) {
               i--;
               continue;
            }
            token += (char)(tmp);
        }
        return token;
    }

    public String getToken(User user) {
        String token = this.generateToken();
        String id = userRepository.findByUsername(user.getUsername()).getId();
        tokenRepository.save(token, id);
        return token;
    }

    public boolean checkToken(String token) {
        if (tokenRepository.find(token) != null) {
            return true;
        }
        return false;
    }

}
