package com.siit.bookReviewer.repository;
import com.siit.bookReviewer.model.User;
import com.siit.bookReviewer.model.dto.UserLoginDTO;
import com.siit.bookReviewer.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.management.Query;
import java.util.ArrayList;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    private List<User> testingUsers;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        testingUsers = new ArrayList<>();
        User user1 = new User(1, "FirstName1", "LastName1", "fn1@yahoo.com", "pass1");
        User user2 = new User(2, "FirstName2", "LastName2", "fn2@yahoo.com", "pass2");
        testingUsers.add(user1);
        testingUsers.add(user2);
    }

    @Test
    public void testFindAllUsers() {
        when(userRepository.findAll()).thenReturn(testingUsers);

        List<User> users = new ArrayList<>(userService.findAll());

        assertEquals(testingUsers.size(), users.size());
    }

    @Test
    public void CheckIfExistsUserFound() {
        when(userRepository.checkIfExists("fn1@yahoo.com", "pass1")).thenReturn(testingUsers.get(0));

        UserLoginDTO result = userService.checkIfExists("fn1@yahoo.com", "pass1");

        UserLoginDTO expectedUserDTO = new UserLoginDTO(
                testingUsers.get(0).getId(),
                testingUsers.get(0).getEmail(),
                testingUsers.get(0).getFirstName(),
                testingUsers.get(0).getLastName()
        );

        assertNotNull(result);
        assertEquals(expectedUserDTO, result);
    }

    @Test
    public void getUserByEmail() {
        when(userRepository.getUserByEmail("fn1@yahoo.com")).thenReturn(testingUsers.get(0));

        User result = userService.getUserByEmail("fn1@yahoo.com");

        assertEquals(testingUsers.get(0), result);

    }

}