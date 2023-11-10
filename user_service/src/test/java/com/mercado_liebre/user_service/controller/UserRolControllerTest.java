package com.mercado_liebre.user_service.controller;

import com.mercado_liebre.user_service.error.ResponseException;
import com.mercado_liebre.user_service.model.userRol.UserRol;
import com.mercado_liebre.user_service.model.userRol.UserRolDTO;
import com.mercado_liebre.user_service.repository.UserRolRepository;
import com.mercado_liebre.user_service.service.UserRolServiceImpl;
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
public class UserRolControllerTest {
    @Mock
    private UserRolRepository userRolRepository;
    @Mock
    private UserRolServiceImpl userRolService;
    @InjectMocks
    private UserRolController userRolController;
    @Mock
    private UserRol rol;
    private UserRolDTO userRolDTO;

    @Test
    public void givenUserRoles_whenGetAll_thenListShouldNotBeEmpty() {
        UserRolDTO userRolDTO_2 = new UserRolDTO();

        when(userRolService.getAll()).thenReturn(Arrays.asList(userRolDTO,userRolDTO_2));

        assertTrue(userRolController.getAll().size() == 2,"The lists should contain the role 'Type Test' and 'Type 2 Test");
        assertFalse(userRolController.getAll().isEmpty(),"The lists should contain two roles");
    }

    @Test
    public void givenIdUserRol_whenGetById_thenUserRolNotBeNull() {
        when(userRolService.getById(rol.getIdRol())).thenReturn(Optional.ofNullable(userRolDTO));

        assertNotNull(userRolController.getById(rol.getIdRol()),"The method should return rol");
    }

    @Test
    public void givenExistingUserRol_whenCreate_thenReturnThrowException() {
        userRolDTO = new UserRolDTO();
        ResponseException responseException = new ResponseException("User rol already exist", null, HttpStatus.BAD_REQUEST);

        when(userRolService.createUserRol(userRolDTO)).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                userRolController.createUserRol(userRolDTO), "The method should return ResponseException");

        assertEquals("User rol already exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenUserRol_whenAddToDatabase_thenItIsPersisted() {
        userRolDTO = new UserRolDTO(1L,"Test Type");

        when(userRolService.createUserRol(userRolDTO)).thenReturn(userRolDTO);

        UserRolDTO savedUserRolDTO = userRolController.createUserRol(userRolDTO);

        assertNotNull(savedUserRolDTO, "The saved object should not be null");
        assertEquals(userRolDTO.getType(), savedUserRolDTO.getType(), "The object type should 'Type Test'");

        verify(userRolService, times(1)).createUserRol(userRolDTO);
    }

    @Test
    public void givenInvalidUserRolId_whenUpdate_thenThrowException() {
        Long idInvalid = 2L;
        userRolDTO = new UserRolDTO();
        ResponseException responseException = new ResponseException("That user rol does not exist", null, HttpStatus.NOT_FOUND);

        when(userRolService.updateUserRol(idInvalid,userRolDTO)).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                userRolController.updateUserRol(idInvalid,userRolDTO),"The method should return ResponseException");

        assertEquals("That user rol does not exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenUserRol_whenUpdate_thenReturnOk() {
        UserRol userRol_2 = new UserRol(2L, "Test 2 Type");
        userRolDTO = new UserRolDTO(1L,"Test Type");

        when(userRolService.updateUserRol(userRol_2.getIdRol(),userRolDTO)).thenReturn(
                new UserRolDTO(userRolDTO.getIdRol(),userRolDTO.getType()));

        UserRolDTO userRolDtoUpdated = userRolController.updateUserRol(userRol_2.getIdRol(),userRolDTO);

        assertEquals(userRolDtoUpdated.getType(),userRolDTO.getType(), "The rol type should be 'Test Type' in both cases");

    }

}
