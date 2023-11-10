package com.mercado_liebre.user_service.service;

import com.mercado_liebre.user_service.error.ResponseException;
import com.mercado_liebre.user_service.model.userRol.UserRol;
import com.mercado_liebre.user_service.model.userRol.UserRolDTO;
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
public class UserRolServiceTest {
    @Mock
    private UserRolRepository userRolRepository;
    @InjectMocks
    private UserRolServiceImpl userRolService;
    @Mock
    private UserRol rol;

    private UserRolDTO userRolDTO;

    @Test
    public void givenUserRoles_whenGetAll_thenListShouldNotBeEmpty() {
        UserRol rol_2 = new UserRol(2L,"Test 2 Type");

        when(userRolRepository.findAll()).thenReturn(Arrays.asList(rol,rol_2));

        assertTrue(userRolService.getAll().size() == 2,"The lists should contain the role 'Type Test' and 'Type 2 Test");
        assertFalse(userRolService.getAll().isEmpty(),"The lists should contain two roles");
    }

    @Test
    public void givenIdUserRol_whenGetById_thenUserRolNotBeNull() {
        when(userRolRepository.findById(rol.getIdRol())).thenReturn(Optional.ofNullable(rol));

        assertNotNull(userRolService.getById(rol.getIdRol()),"The method should return rol");
    }

    @Test
    public void givenExistingUserRol_whenCreate_thenReturnThrowException() {
        userRolDTO = new UserRolDTO(1L,"Test Type");
        ResponseException responseException = new ResponseException("User rol already exist", null, HttpStatus.BAD_REQUEST);

        when(userRolRepository.findByType(userRolDTO.getType())).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                userRolService.createUserRol(userRolDTO), "The method should return ResponseException");

        assertEquals("User rol already exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenInvalidUserRolId_whenUpdate_thenThrowException() {
        Long idInvalid = 2L;
        ResponseException responseException = new ResponseException("That user rol does not exist", null, HttpStatus.NOT_FOUND);

        when(userRolRepository.findById(idInvalid)).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                userRolService.updateUserRol(idInvalid,userRolDTO),"The method should return ResponseException");

        assertEquals("That user rol does not exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenUserRol_whenUpdate_thenReturnOk() {
        UserRol rol_2 = new UserRol(2L,"Test 2 Type");
        userRolDTO = new UserRolDTO(1L,"Test Type");

        when(userRolRepository.save(rol_2)).thenReturn(rol_2);
        when(userRolRepository.findById(rol_2.getIdRol())).thenReturn(Optional.ofNullable(rol_2));

        UserRolDTO userRolDtoUpdated = userRolService.updateUserRol(rol_2.getIdRol(),userRolDTO);

        assertEquals(userRolDtoUpdated.getType(),rol_2.getType(), "The rol type should be 'Test Type' in both cases");
    }
}
