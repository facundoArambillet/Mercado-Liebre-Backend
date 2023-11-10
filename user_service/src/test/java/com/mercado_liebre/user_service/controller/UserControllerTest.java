//package com.mercado_liebre.user_service.controller;
//
//
//import com.mercado_liebre.user_service.error.ResponseException;
//import com.mercado_liebre.user_service.model.user.*;
//import com.mercado_liebre.user_service.model.userRol.UserRol;
//import com.mercado_liebre.user_service.service.UserServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//
//import java.sql.Date;
//import java.util.Arrays;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class UserControllerTest {
//    @Mock
//    private UserServiceImpl userService;
//    @InjectMocks
//    private UserController userController;
//
//    @Mock
//    private User user;
//    @Mock
//    private UserRol userRol;
//    private UserDTO userDTO;
//
//    @Test
//    public void givenUsers_whenGetAll_thenListShouldNotBeEmpty() {
//        UserDTO userDTO_2 = new UserDTO();
//
//        when(userService.getAll()).thenReturn(Arrays.asList(userDTO,userDTO_2));
//
//        assertTrue(userController.getAll().size() == 2,"The lists should contain the users 'Test@Test.com' and 'Test2@Test.com");
//        assertFalse(userController.getAll().isEmpty(),"The lists should contain two users DTOs");
//    }
//
//    @Test
//    public void givenIdUser_whenGetById_thenUserNotBeNull() {
//        UserDetailDTO userDetailDTO = new UserDetailDTO();
//
//        when(userService.getById(user.getIdUser())).thenReturn(Optional.ofNullable(userDetailDTO));
//
//        assertNotNull(userController.getById(user.getIdUser()),"The method should return user detail DTO");
//    }
//
//    @Test
//    public void givenExistingUser_whenCreate_thenReturnThrowException() {
//        UserCreateDTO userCreateDTO = new UserCreateDTO();
//        ResponseException responseException = new ResponseException("User already exist", null, HttpStatus.BAD_REQUEST);
//
//        when(userService.registerUser(userCreateDTO)).thenThrow(responseException);
//        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
//                userController.registerUser(userCreateDTO), "The method should return ResponseException");
//
//        assertEquals("User already exist", exceptionCaptured.getMessage());
//        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
//    }
//
//    @Test
//    public void givenUser_whenAddToDatabase_thenItIsPersisted() {
//        UserCreateDTO userCreateDTO = new UserCreateDTO("Test@Test.com","Test Password","Test Name","Test LastName");
//        when(userService.registerUser(userCreateDTO)).thenReturn(userCreateDTO);
//
//        UserCreateDTO savedUser = userController.registerUser(userCreateDTO);
//
//        verify(userService, times(1)).registerUser(userCreateDTO);
//
//        assertNotNull(savedUser, "The saved object should not be null");
//        assertEquals("Test@Test.com", savedUser.getEmail(), "The object email should 'Test@Test.com'");
//    }
//
//    @Test
//    public void givenInvalidUserId_whenUpdate_thenThrowException() {
//        Long idInvalid = 2L;
//        ResponseException responseException = new ResponseException("That user does not exist", null, HttpStatus.NOT_FOUND);
//
//        when(userService.updateUser(idInvalid,user)).thenThrow(responseException);
//        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
//                userController.updateUser(idInvalid,user),"The method should return ResponseException");
//
//        assertEquals("That user does not exist", exceptionCaptured.getMessage());
//        assertEquals(HttpStatus.NOT_FOUND, exceptionCaptured.getHttpStatus());
//    }
//
//    @Test
//    public void givenUser_whenUpdate_thenReturnOk() {
//        User user_3 = new User();
//        User user_2 = new User(2L, "Test2@Test.com","Test 2 password","Test 2 name","Test 2 last name",
//                new Date(2023,10,21),0L,userRol);
//        user_3.setIdUser(3L);
//
//        when(userService.updateUser(user_3.getIdUser(),user_2)).thenReturn(
//                new User(user_3.getIdUser(), user_2.getEmail(), user_2.getPassword(), user_2.getName(),user_2.getLastName(),
//                        user_2.getCreationDate(),user_2.getSalesMade(),user_2.getUserRol())
//        );
//
//        User userUpdated = userController.updateUser(user_3.getIdUser(),user_2);
//
//        assertEquals(userUpdated.getEmail(),user_2.getEmail(), "The email should be 'Test2@Test.com' in both cases");
//
//    }
//}
