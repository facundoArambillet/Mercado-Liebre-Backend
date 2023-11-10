package com.mercado_liebre.user_service.service;


import com.mercado_liebre.user_service.error.ResponseException;
import com.mercado_liebre.user_service.model.user.User;
import com.mercado_liebre.user_service.model.user.UserDTO;
import com.mercado_liebre.user_service.model.userAddress.*;
import com.mercado_liebre.user_service.model.userRol.UserRol;
import com.mercado_liebre.user_service.repository.UserAddressRepository;
import com.mercado_liebre.user_service.repository.UserRepository;
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
public class UserAddressServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserAddressRepository userAddressRepository;
    @InjectMocks
    private UserAddressServiceImpl userAddressService;
    @Mock
    private User user;
    @Mock
    private UserAddress userAddress;
    private UserDTO userDTO;

    @Test
    public void givenUserAddress_whenGetAll_thenListShouldNotBeEmpty() {
        UserAddress userAddress_2 = new UserAddress();

        when(userAddressRepository.findAll()).thenReturn(Arrays.asList(userAddress,userAddress_2));

        assertTrue(userAddressService.getAll().size() == 2,"The lists should contain the userAddress and userAddress_2");
        assertFalse(userAddressService.getAll().isEmpty(),"The lists should contain two userAddress");
    }

    @Test
    public void givenIdUserAddress_whenGetById_thenUserAddressNotBeNull() {
        when(userAddressRepository.findById(userAddress.getIdAddress())).thenReturn(Optional.ofNullable(userAddress));

        assertNotNull(userAddressService.getById(userAddress.getIdAddress()),"The method should return userAddress");
    }

    @Test
    public void givenExistingUserAddress_whenCreate_thenReturnThrowException() {
        UserAddressCreateDTO userAddressCreateDTO = new UserAddressCreateDTO("Test Address",1234,"Test Province",
                "Test City",7513,123456,userDTO);
        ResponseException responseException = new ResponseException("User Address already exist", null, HttpStatus.BAD_REQUEST);

        when(userAddressRepository.findByDetails(userAddressCreateDTO.getAddress(),userAddressCreateDTO.getAddressNumber(),
                userAddressCreateDTO.getProvince(),userAddressCreateDTO.getCity())).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                userAddressService.createUserAddress(userAddressCreateDTO), "The method should return ResponseException");

        assertEquals("User Address already exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenInvalidUserAddressId_whenUpdate_thenThrowException() {
        Long idInvalid = 2L;
        UserAddressDetailDTO userAddressDetailDTO = new UserAddressDetailDTO();
        ResponseException responseException = new ResponseException("That user address does not exist", null, HttpStatus.NOT_FOUND);

        when(userAddressRepository.findById(idInvalid)).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                userAddressService.updateUserAddress(idInvalid,userAddressDetailDTO),"The method should return ResponseException");

        assertEquals("That user address does not exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenUserAddress_whenUpdate_thenReturnOk() {
        UserAddress userAddress_2 = new UserAddress();
        UserAddressDetailDTO userAddressDetailDTO = new UserAddressDetailDTO();
        userDTO = new UserDTO();
        userAddress_2.setIdAddress(2L);
        userDTO.setIdUser(1L);
        userAddressDetailDTO.setIdAddress(1L);
        userAddressDetailDTO.setAddress("Test Address");
        userAddressDetailDTO.setUser(userDTO);

        when(userAddressRepository.save(userAddress_2)).thenReturn(userAddress_2);
        when(userAddressRepository.findById(userAddress_2.getIdAddress())).thenReturn(Optional.ofNullable(userAddress_2));
        when(userRepository.findById(userAddressDetailDTO.getUser().getIdUser())).thenReturn(Optional.ofNullable(user));

        UserAddressDetailDTO userAddressDetailDTOUpdated = userAddressService.updateUserAddress(userAddress_2.getIdAddress(),userAddressDetailDTO);

        assertEquals(userAddressDetailDTOUpdated.getAddress(),userAddressDetailDTO.getAddress(), "The address should be 'Test Address' in both cases");
    }

    @Test
    public void givenUserWithNotExist_whenCreateUserAddress_thenReturnThrowException() {
        userDTO = new UserDTO();
        userDTO.setIdUser(1L);
        UserAddressCreateDTO userAddressCreateDTO = new UserAddressCreateDTO("Test Address",1234,"Test Province",
                "Test City",7513,123456,userDTO);

        ResponseException responseException = new ResponseException("User does not exist", null, HttpStatus.BAD_REQUEST);

        when(userAddressRepository.findByDetails(userAddressCreateDTO.getAddress(),userAddressCreateDTO.getAddressNumber(),
                userAddressCreateDTO.getProvince(),userAddressCreateDTO.getCity())).thenReturn(Optional.empty());
        when(userRepository.findById(userAddressCreateDTO.getUser().getIdUser())).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                userAddressService.createUserAddress(userAddressCreateDTO), "The method should return ResponseException");

        verify(userAddressRepository, never()).save(any());

        assertEquals("User does not exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
    }
}
