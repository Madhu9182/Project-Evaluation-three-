package io.capgemini.com.service;

import io.capgemini.com.exception.UserNameNotFoundException;
import io.capgemini.com.exception.WrongPasswordException;
import io.capgemini.com.model.PasswordUpdate;
import io.capgemini.com.model.Role;
import io.capgemini.com.model.UserRegistration;
import io.capgemini.com.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        UserRegistration userRegistration = new UserRegistration(1L,"madhu123","Madhu","Pavan","madhu@gmail.com", passwordEncoder.encode("madhu9182"), Role.USER);
        Mockito.when(userRepository.save(Mockito.any(UserRegistration.class))).thenReturn(userRegistration);
        Mockito.when(userRepository.findByUsername("madhu123")).thenReturn(Optional.of(userRegistration));
    }

    @Test
    void saveRegistration(){
        UserRegistration userRegistration = new UserRegistration(1L,"madhu123","Madhu","Pavan","madhu@gmail.com","madhu9182",Role.USER);
        assertEquals("User Registration Successfully",userService.saveRegistration(userRegistration));
    }

    @Test
    void getRegistration() throws Exception{
        assertInstanceOf(UserRegistration.class, userService.getRegistration("madhu123"));
    }

    @Test
    void updateRegistration() throws UserNameNotFoundException {
        UserRegistration userRegistration = new UserRegistration(1L,"madhu123","Madhu","Pavan","madhu@gmail.com","madhu9182",Role.USER);
        assertEquals("Updated successfully",userService.updateRegistration(userRegistration));
    }

    @Test
    void deleteRegistration() throws UserNameNotFoundException{
        assertEquals("Deleted Successfully",userService.deleteRegistration("madhu123"));
    }

    @Test
    void updatePassword() throws UserNameNotFoundException, WrongPasswordException {
        PasswordUpdate passwordUpdate = new PasswordUpdate("madhu9182","pavan91827");
        assertEquals( userService.updatePassword("madhu123",passwordUpdate),"Password updated Successfully");
    }

    @Test
    void verifyLoginUser() {

    }
}