package ir.restusrmng.RestfulUserManagement;

import ir.restusrmng.RestfulUserManagement.models.User;
import ir.restusrmng.RestfulUserManagement.repositories.UserRepository;
import ir.restusrmng.RestfulUserManagement.services.UserService;
import ir.restusrmng.RestfulUserManagement.services.UserServiceImpl;
import ir.restusrmng.RestfulUserManagement.utils.AuthenticationException;
import ir.restusrmng.RestfulUserManagement.utils.UserNotFoundException;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userServiceImpl;

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration {

        @Bean
        public UserServiceImpl employeeService() {
            return new UserServiceImpl();
        }
    }

    @Before
    public void setUp() {
       // MockitoAnnotations.initMocks(this);
        User alex = new User("alex", "123");
        Mockito.when(userRepository.findByUsername(alex.getUsername())).thenReturn(alex);
    }

    @Test
    public void whenUserInRepository_thenUserShouldBeFound() {
        String name = "alex";
        User found = userServiceImpl.findByUsername(name);
        assertEquals(found.getUsername(), name);
    }

    @Test(expected = UserNotFoundException.class)
    public void  whenUserNotInRepository_ifUserNotFound_thenShouldThrowUserNotFoundException() {
        String name = RandomStringUtils.randomAlphabetic( 8 );
        userServiceImpl.findByUsername(name);
    }

    @Test
    public void whenCreatingUser_ifDoesNotExist_thenShouldCreateUser() {
        String name = RandomStringUtils.randomAlphabetic( 3 );
        String password = RandomStringUtils.randomAlphabetic( 8 );
        User user = new User(name, password);
        User createdUser = userServiceImpl.createUser(user);
        assertNotNull(createdUser);
    }

    @Test
    public void whenCreatingUser_ifAlreadyExists_thenShouldReturnNull() {
        String name = "alex";
        String password = RandomStringUtils.randomAlphabetic( 8 );
        User user = new User(name, password);
        User createdUser = userServiceImpl.createUser(user);
        assertNull(createdUser);
    }

    @Test
    public void whenDeletingUser_ifUserExist_thenShouldDeleteUser() {
        boolean deleted = userServiceImpl.deleteByUsername("alex");
        assertTrue(deleted);
    }

    @Test(expected = UserNotFoundException.class)
    public void whenDeletingUser_ifUserDoesNotExist_thenShouldThrowUserNotFoundException() {
        String name = RandomStringUtils.randomAlphabetic( 8 );
        boolean deleted = userServiceImpl.deleteByUsername(name);
    }

    @Test
    public void whenUpdatingUser_ifUserExists_thenShouldUpdate() {
        User user = userServiceImpl.updateUser("alex", new User("ali", "123"));
        assertNotNull(user);
    }

    @Test(expected = UserNotFoundException.class)
    public void whenUpdatingUser_ifDoesNotExist_thenShouldThrowUserNotFoundException() {
        String name = RandomStringUtils.randomAlphabetic( 8 );
        User user = userServiceImpl.updateUser(name, new User("ali", "123"));
    }

    @Test
    public void whenUserCredentialsAreCorrect_thenShouldLogin() {
        User user = userServiceImpl.login("alex", "123");
        assertNotNull(user);
    }

    @Test(expected = AuthenticationException.class)
    public void whenUsernameNotValid_thenShouldThrowAuthenticationException() {
        String name = RandomStringUtils.randomAlphabetic( 8 );
        User user = userServiceImpl.login(name, "123");
    }

    @Test(expected = AuthenticationException.class)
    public void whenPasswordNotValid_thenShouldThrowAuthenticationException() {
        String password = RandomStringUtils.randomAlphabetic( 8 );
        User user = userServiceImpl.login("alex", password);
    }

}
