package com.mercado_liebre.user_service.controller;

import com.mercado_liebre.user_service.error.ResponseException;
import com.mercado_liebre.user_service.model.rolAttribute.RolAttribute;
import com.mercado_liebre.user_service.model.rolAttribute.RolAttributeDTO;
import com.mercado_liebre.user_service.model.userRol.UserRol;
import com.mercado_liebre.user_service.model.userRol.UserRolDTO;
import com.mercado_liebre.user_service.service.RolAttributeServiceImpl;
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
public class RolAttributeControllerTest {
    @Mock
    private RolAttributeServiceImpl rolAttributeService;
    @InjectMocks
    private RolAttributeController rolAttributeController;
    @Mock
    private RolAttribute rolAttribute;
    @Mock
    private UserRol userRol;
    private RolAttributeDTO rolAttributeDTO;
    private UserRolDTO userRolDTO;

    @Test
    public void givenRolAttributes_whenGetAll_thenListShouldNotBeEmpty() {
        RolAttributeDTO rolAttributeDTO_2 = new RolAttributeDTO();

        when(rolAttributeService.getAll()).thenReturn(Arrays.asList(rolAttributeDTO,rolAttributeDTO_2));

        assertTrue(rolAttributeController.getAll().size() == 2,"The lists should contain the roles attributes 'Test Name' and 'Test 2 Name");
        assertFalse(rolAttributeController.getAll().isEmpty(),"The lists should contain two rol attributes");
    }

    @Test
    public void givenIdRolAttribute_whenGetById_thenRolAttributeNotBeNull() {
        when(rolAttributeService.getById(rolAttribute.getIdAttribute())).thenReturn(Optional.ofNullable(rolAttribute));

        assertNotNull(rolAttributeController.getById(rolAttribute.getIdAttribute()),"The method should return rol attribute");
    }

    @Test
    public void givenExistingRolAttribute_whenCreate_thenReturnThrowException() {
        rolAttributeDTO = new RolAttributeDTO();
        ResponseException responseException = new ResponseException("Rol attribute already exist", null, HttpStatus.BAD_REQUEST);

        when(rolAttributeService.createRolAttribute(rolAttributeDTO)).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                rolAttributeController.createRolAttribute(rolAttributeDTO), "The method should return ResponseException");

        assertEquals("Rol attribute already exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenRolAttribute_whenAddToDatabase_thenItIsPersisted() {
        rolAttributeDTO = new RolAttributeDTO("Test Name","Test Value", userRolDTO);
        when(rolAttributeService.createRolAttribute(rolAttributeDTO)).thenReturn(rolAttributeDTO);

        RolAttributeDTO savedRolDTOAttribute = rolAttributeController.createRolAttribute(rolAttributeDTO);

        assertNotNull(savedRolDTOAttribute, "The saved object should not be null");
        assertEquals("Test Name", savedRolDTOAttribute.getName(), "The object name should 'Test Name'");

        // Verifica que el método save del repositorio se haya llamado exactamente una vez con el objeto
        verify(rolAttributeService, times(1)).createRolAttribute(rolAttributeDTO);
    }

    @Test
    public void givenInvalidRolAttributeId_whenUpdate_thenThrowException() {
        Long idInvalid = 2L;
        rolAttributeDTO = new RolAttributeDTO();
        ResponseException responseException = new ResponseException("That rol attribute does not exist", null, HttpStatus.NOT_FOUND);

        when(rolAttributeService.updateRolAttribute(idInvalid,rolAttributeDTO)).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                rolAttributeController.updateRolAttribute(idInvalid,rolAttributeDTO),"The method should return ResponseException");

        assertEquals("That rol attribute does not exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenRolAttribute_whenUpdate_thenReturnOk() {
        userRolDTO = new UserRolDTO();
        rolAttributeDTO = new RolAttributeDTO("Test Name", "Test Value", userRolDTO);

        when(rolAttributeService.updateRolAttribute(rolAttribute.getIdAttribute(),rolAttributeDTO)).thenReturn(
                new RolAttributeDTO(rolAttributeDTO.getName(),rolAttributeDTO.getValue(),rolAttributeDTO.getUserRol())
        );

        RolAttributeDTO rolAttributeDTOUpdated = rolAttributeController.updateRolAttribute(rolAttribute.getIdAttribute(),rolAttributeDTO);

        assertEquals(rolAttributeDTOUpdated.getName(),rolAttributeDTO.getName(), "The name should be 'Test 2 type' in both cases");

    }
}
