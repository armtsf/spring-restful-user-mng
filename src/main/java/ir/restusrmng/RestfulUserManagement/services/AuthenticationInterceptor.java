package ir.restusrmng.RestfulUserManagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if ((request.getMethod().equals("DELETE")) || (request.getMethod().equals("PUT"))) {
            if ((token == null) || (!authenticationService.checkToken(token))) {
                response.setStatus(401);
                return false;
            }
        }
        return true;
    }
}
