package com.mercado_liebre.user_service.controller;

import com.mercado_liebre.user_service.error.ResponseException;
import com.mercado_liebre.user_service.model.user.UserDTO;
import com.mercado_liebre.user_service.model.userAddress.*;
import com.mercado_liebre.user_service.service.UserAddressServiceImpl;
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
public class UserAddressControllerTest {
    @Mock
    private UserAddressServiceImpl userAddressService;
    @InjectMocks
    private UserAddressController userAddressController;
    @Mock
    private UserAddress userAddress;
    private UserDTO userDTO;
    private UserAddressDTO userAddressDTO;


    @Test
    public void givenUserAddress_whenGetAll_thenListShouldNotBeEmpty() {
        UserAddressDTO userAddressDTO_2 = new UserAddressDTO();

        when(userAddressService.getAll()).thenReturn(Arrays.asList(userAddressDTO,userAddressDTO_2));

        assertTrue(userAddressController.getAll().size() == 2,"The lists should contain the userAddress and userAddress_2");
        assertFalse(userAddressController.getAll().isEmpty(),"The lists should contain two userAddress");
    }

    @Test
    public void givenIdUserAddress_whenGetById_thenUserNotBeNull() {
        UserAddressDetailDTO userAddressDetailDTO = new UserAddressDetailDTO();

        when(userAddressService.getById(userAddress.getIdAddress())).thenReturn(Optional.ofNullable(userAddressDetailDTO));

        assertNotNull(userAddressService.getById(userAddress.getIdAddress()),"The method should return userAddress");
    }

    @Test
    public void givenExistingUserAddress_whenCreate_thenReturnThrowException() {
        UserAddressCreateDTO userAddressCreateDTO = new UserAddressCreateDTO();
        ResponseException responseException = new ResponseException("User Address already exist", null, HttpStatus.BAD_REQUEST);

        when(userAddressService.createUserAddress(userAddressCreateDTO)).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                userAddressController.createUserAddress(userAddressCreateDTO), "The method should return ResponseException");

        assertEquals("User Address already exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenUserAddress_whenAddToDatabase_thenItIsPersisted() {
        UserAddressCreateDTO userAddressCreateDTO = new UserAddressCreateDTO();
        userAddressCreateDTO.setAddress("Test Address");

        when(userAddressService.createUserAddress(userAddressCreateDTO)).thenReturn(userAddressCreateDTO);

        UserAddressCreateDTO savedUserAddressCreateDTO = userAddressController.createUserAddress(userAddressCreateDTO);

        verify(userAddressService, times(1)).createUserAddress(userAddressCreateDTO);

        assertNotNull(savedUserAddressCreateDTO, "The saved object should not be null");
        assertEquals(savedUserAddressCreateDTO.getAddress(), userAddressCreateDTO.getAddress(),"The object address should 'Test Address'");
    }

    @Test
    public void givenInvalidUserAddressId_whenUpdate_thenThrowException() {
        Long idInvalid = 2L;
        UserAddressDetailDTO userAddressDetailDTO = new UserAddressDetailDTO();
        ResponseException responseException = new ResponseException("That user address does not exist", null, HttpStatus.NOT_FOUND);

        when(userAddressService.updateUserAddress(idInvalid,userAddressDetailDTO)).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                userAddressController.updateUserAddress(idInvalid,userAddressDetailDTO),"The method should return ResponseException");

        assertEquals("That user address does not exist",exceptionCaptured.getMessage());
        assertEquals(HttpStatus.NOT_FOUND,exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenUserAddress_whenUpdate_thenReturnOk() {
        UserAddress userAddress_2 = new UserAddress();
        UserAddressDetailDTO userAddressDetailDTO = new UserAddressDetailDTO(1L,"Test Address",1234,"Test Province","Test City",
                7513,123456,false,userDTO);
        userAddress_2.setIdAddress(2L);

        when(userAddressService.updateUserAddress(userAddress_2.getIdAddress(),userAddressDetailDTO)).thenReturn(
                new UserAddressDetailDTO(userAddressDetailDTO.getIdAddress(), userAddressDetailDTO.getAddress(), userAddressDetailDTO.getAddressNumber(),
                        userAddressDetailDTO.getProvince(), userAddressDetailDTO.getCity(), userAddressDetailDTO.getPostalCode(),
                        userAddressDetailDTO.getContactPhone(), userAddressDetailDTO.isPrincipal(), userAddressDetailDTO.getUser()
                )
        );

        UserAddressDetailDTO userAddressDetailDTOUpdated = userAddressController.updateUserAddress(userAddress_2.getIdAddress(),userAddressDetailDTO);

        assertEquals(userAddressDetailDTOUpdated.getAddress(),userAddressDetailDTO.getAddress(), "The address should be 'Test Address' in both cases");

    }
}
