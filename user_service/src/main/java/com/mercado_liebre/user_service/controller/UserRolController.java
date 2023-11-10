package com.mercado_liebre.user_service.controller;

import com.mercado_liebre.user_service.model.userRol.UserRol;
import com.mercado_liebre.user_service.model.userRol.UserRolDTO;
import com.mercado_liebre.user_service.service.UserRolServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/user-rol")
public class UserRolController {
    @Autowired
    private UserRolServiceImpl userRolService;
    @GetMapping
    public List<UserRolDTO> getAll() {
        return userRolService.getAll();
    }
    @GetMapping("/{idUserRol}")
    public Optional<UserRolDTO> getById(@PathVariable("idUserRol") Long idRol) {
        return userRolService.getById(idRol);
    }
    @GetMapping("/type/{rolType}")
    public  Optional<UserRol> getByType(@PathVariable("rolType") String rolType) {
        return userRolService.getByType(rolType);
    }
    @PostMapping
    public UserRolDTO createUserRol(@RequestBody UserRolDTO userRolDTO) {
        return userRolService.createUserRol(userRolDTO);
    }
    @PutMapping("/{idUserRol}")
    public UserRolDTO updateUserRol(@PathVariable("idUserRol")Long idRol, @RequestBody UserRolDTO userRolDTO) {
        return userRolService.updateUserRol(idRol, userRolDTO);
    }
    @DeleteMapping("/{idUserRol}")
    public UserRolDTO deleteUserRol(@PathVariable("idUserRol") Long idRol) {
        return userRolService.deleteUserRol(idRol);
    }
}
