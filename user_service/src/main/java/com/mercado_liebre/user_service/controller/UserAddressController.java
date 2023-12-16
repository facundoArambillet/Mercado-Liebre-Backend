package com.mercado_liebre.user_service.controller;

import com.mercado_liebre.user_service.model.userAddress.UserAddressCreateDTO;
import com.mercado_liebre.user_service.model.userAddress.UserAddressDTO;
import com.mercado_liebre.user_service.model.userAddress.UserAddressDetailDTO;
import com.mercado_liebre.user_service.service.UserAddressServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/user-address")
public class UserAddressController {
    @Autowired
    private UserAddressServiceImpl userAddressService;
    @GetMapping
    public List<UserAddressDTO> getAll() {
        return userAddressService.getAll();
    }
    @GetMapping("/{idAddress}")
    public Optional<UserAddressDetailDTO> getById(@PathVariable("idAddress") Long idAddress) {
        return userAddressService.getById(idAddress);
    }
    @GetMapping("/by-user/{idUser}")
    public List<UserAddressDetailDTO> getByUser(@PathVariable("idUser") Long idUser) {
        return userAddressService.getByUser(idUser);
    }
    @PostMapping
    public UserAddressCreateDTO createUserAddress(@RequestBody UserAddressCreateDTO userAddressCreateDTO) {
        return userAddressService.createUserAddress(userAddressCreateDTO);
    }
    @PutMapping("/{idAddress}")
    public UserAddressDetailDTO updateUserAddress(@PathVariable("idAddress")Long idAddress, @RequestBody UserAddressDetailDTO userAddressDetailDTO) {
        return userAddressService.updateUserAddress(idAddress, userAddressDetailDTO);
    }
    @PutMapping("/principal/change")
    public UserAddressDetailDTO toogleAddressPrincipal(@RequestBody UserAddressDetailDTO userAddressDetailDTO) {
        return userAddressService.toogleAddressPrincipal(userAddressDetailDTO);
    }
    @DeleteMapping("/{idAddress}")
    public UserAddressDTO deleteUserAddress(@PathVariable("idAddress") Long idAddress) {
        return userAddressService.deleteUserAddress(idAddress);
    }
}
