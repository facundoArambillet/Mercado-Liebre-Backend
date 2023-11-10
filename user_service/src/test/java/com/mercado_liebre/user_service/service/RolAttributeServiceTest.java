package com.mercado_liebre.user_service.service;

import com.mercado_liebre.user_service.error.ResponseException;
import com.mercado_liebre.user_service.model.rolAttribute.RolAttribute;
import com.mercado_liebre.user_service.model.rolAttribute.RolAttributeDTO;
import com.mercado_liebre.user_service.model.userRol.UserRol;
import com.mercado_liebre.user_service.model.userRol.UserRolDTO;
import com.mercado_liebre.user_service.repository.RolAttributeRepository;
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
public class RolAttributeServiceTest {
    @Mock
    private UserRolRepository userRolRepository;
    @Mock
    private RolAttributeRepository rolAttributeRepository;
    @InjectMocks
    private RolAttributeServiceImpl rolAttributeService;
    @Mock
    private RolAttribute rolAttribute;
    private RolAttributeDTO rolAttributeDTO;
    private UserRolDTO userRolDTO;

    @Test
    public void givenRolAttributes_whenGetAll_thenListShouldNotBeEmpty() {
        RolAttribute rolAttribute_2 = new RolAttribute();

        when(rolAttributeRepository.findAll()).thenReturn(Arrays.asList(rolAttribute,rolAttribute_2));

        assertTrue(rolAttributeService.getAll().size() == 2,"The lists should contain the rolAttribute and rolAttribute_2");
        assertFalse(rolAttributeService.getAll().isEmpty(),"The lists should contain two rolAttributes");
    }

    @Test
    public void givenIdRolAttribute_whenGetById_thenRolAttributeNotBeNull() {
        when(rolAttributeRepository.findById(rolAttribute.getIdAttribute())).thenReturn(Optional.ofNullable(rolAttribute));

        assertNotNull(rolAttributeService.getById(rolAttribute.getIdAttribute()),"The method should return rolAttribute");
    }

    @Test
    public void givenExistingRolAttribute_whenCreate_thenReturnThrowException() {
        rolAttributeDTO = new RolAttributeDTO("Test Name", "Test Value", userRolDTO);
        ResponseException responseException = new ResponseException("Rol attribute already exist", null, HttpStatus.BAD_REQUEST);

        when(rolAttributeRepository.findByName(rolAttributeDTO.getName())).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                rolAttributeService.createRolAttribute(rolAttributeDTO), "The method should return ResponseException");

        assertEquals("Rol attribute already exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenInvalidRolAttributeId_whenUpdate_thenThrowException() {
        Long idInvalid = 2L;
        ResponseException responseException = new ResponseException("That rol attribute does not exist", null, HttpStatus.NOT_FOUND);

        when(rolAttributeRepository.findById(idInvalid)).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                rolAttributeService.updateRolAttribute(idInvalid,rolAttributeDTO),"The method should return ResponseException");

        assertEquals("That rol attribute does not exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenRolAttribute_whenUpdate_thenReturnOk() {
        UserRol userRol_2 = new UserRol(1L, "Type Name");
        RolAttribute rolAttribute_2 = new RolAttribute(2L,"Test 2 Name","Test 2 Value",userRol_2);
        userRolDTO = new UserRolDTO(1L, "Type Name");
        rolAttributeDTO = new RolAttributeDTO("Test Name","Test Value", userRolDTO);

        when(rolAttributeRepository.save(rolAttribute_2)).thenReturn(rolAttribute_2);
        when(rolAttributeRepository.findById(rolAttribute_2.getIdAttribute())).thenReturn(Optional.ofNullable(rolAttribute_2));
        when(userRolRepository.findByType(rolAttribute_2.getUserRol().getType())).thenReturn(Optional.ofNullable(userRol_2));

        RolAttributeDTO rolAttributeDTOUpdated = rolAttributeService.updateRolAttribute(rolAttribute_2.getIdAttribute(),rolAttributeDTO);

        assertEquals(rolAttributeDTOUpdated.getName(),rolAttributeDTO.getName(), "The name should be 'Test Name' in both cases");
    }

    @Test
    public void givenUserRolWithNotExist_whenCreate_thenReturnThrowException() {
        userRolDTO = new UserRolDTO(1L, "Test Type");
        rolAttributeDTO = new RolAttributeDTO("Test Name","Test Value", userRolDTO);
        ResponseException responseException = new ResponseException("User rol does not exist", null, HttpStatus.BAD_REQUEST);

        when(userRolRepository.findByType(rolAttributeDTO.getUserRol().getType())).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                rolAttributeService.createRolAttribute(rolAttributeDTO), "The method should return ResponseException");

        verify(rolAttributeRepository, never()).save(any());

        assertEquals("User rol does not exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
    }
}
