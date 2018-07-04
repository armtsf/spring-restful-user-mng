package ir.restusrmng.RestfulUserManagement;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.restusrmng.RestfulUserManagement.models.User;
import ir.restusrmng.RestfulUserManagement.repositories.TokenRepository;
import ir.restusrmng.RestfulUserManagement.repositories.UserRepository;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
public class UserServiceIntegrationTest {

    ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TokenRepository tokenRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        User alex = new User("alex", "123");
        when(userRepository.findByUsername(alex.getUsername())).thenReturn(alex);
        List<User> users = new ArrayList<User>();
        users.add(alex);
        when(userRepository.findAll()).thenReturn(users);
    }

    @Test
    public void createUser() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        String body = "{\"username\":\"jake\",\"password\":\"123\"}";
        JsonNode actualObj = mapper.readTree(body);
        HttpEntity<?> entity = new HttpEntity(actualObj, headers);
        ResponseEntity<?> response = restTemplate.exchange("http://localhost:8080/users", HttpMethod.POST, entity, JsonNode.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void getAllUsers() throws JSONException, IOException {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<?> response = restTemplate.exchange("http://localhost:8080/users", HttpMethod.GET, entity, JsonNode.class);
        String expected = "{\"userList\":[{\"username\":\"alex\",\"password\":\"123\"}]}";
        JsonNode actualObj = mapper.readTree(expected);
        assertEquals(actualObj, response.getBody());
    }

    @Test
    public void fetchExistingUser() throws JSONException, IOException {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<?> response = restTemplate.exchange("http://localhost:8080/users/alex", HttpMethod.GET, entity, JsonNode.class);
        String expected = "{\"username\":\"alex\",\"password\":\"123\"}";
        JsonNode actualObj = mapper.readTree(expected);
        assertEquals(actualObj, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void fetchNonExistingUser() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/users/jack", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
