package com.mercado_liebre.user_service.service;

import com.mercado_liebre.user_service.error.ResponseException;
import com.mercado_liebre.user_service.model.user.*;
import com.mercado_liebre.user_service.model.userRol.UserRol;
import com.mercado_liebre.user_service.model.userRol.UserRolDTO;
import com.mercado_liebre.user_service.repository.UserRepository;
import com.mercado_liebre.user_service.repository.UserRolRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;


import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRolRepository userRolRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private User user;


    @Test
    public void givenUsers_whenGetAll_thenListShouldNotBeEmpty() {
        User user_2  = new User();

        when(userRepository.findAll()).thenReturn(Arrays.asList(user,user_2));

        assertTrue(userService.getAll().size() == 2,"The lists should contain the user and user_2");
        assertFalse(userService.getAll().isEmpty(),"The lists should contain two users");
    }

    @Test
    public void givenIdUser_whenGetById_thenUserNotBeNull() {
        when(userRepository.findById(user.getIdUser())).thenReturn(Optional.ofNullable(user));

        assertNotNull(userService.getById(user.getIdUser()),"The method should return user");
    }

    @Test
    public void givenExistingUser_whenCreate_thenReturnThrowException() {
        User user_2 = new User();
        user_2.setEmail("Test Email");
        UserCreateDTO userCreateDTO = new UserCreateDTO("Test Email", "Test Password","Test Name", "Test LastName");
        ResponseException responseException = new ResponseException("User already exist", null, HttpStatus.BAD_REQUEST);

        when(userRepository.findByEmail(userCreateDTO.getEmail())).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                userService.registerUser(userCreateDTO), "The method should return ResponseException");

        assertEquals("User already exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenInvalidUserId_whenUpdate_thenThrowException() {
        Long idInvalid = 2L;
        ResponseException responseException = new ResponseException("That user does not exist", null, HttpStatus.NOT_FOUND);

        when(userRepository.findById(idInvalid)).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                userService.updateUser(idInvalid,user),"The method should return ResponseException");

        assertEquals("That user does not exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenUser_whenUpdate_thenReturnOk() {
        User user_2 = new User();
        User user_3 = new User();
        UserRol userRol_2 = new UserRol(1L, "Test Type");
        user_2.setIdUser(2L);
        user_2.setUserRol(userRol_2);
        user_2.setEmail("Test2@Test.com");
        user_3.setIdUser(3L);

        when(userRepository.save(user_3)).thenReturn(user_3);
        when(userRepository.findById(user_3.getIdUser())).thenReturn(Optional.ofNullable(user_3));
        when(userRolRepository.findById(user_2.getUserRol().getIdRol())).thenReturn(Optional.ofNullable(userRol_2));

        User userUpdated = userService.updateUser(user_3.getIdUser(),user_2);

        assertEquals(userUpdated.getEmail(),user_2.getEmail(), "The email should be 'Test2@Test.com' in both cases");
    }

}
