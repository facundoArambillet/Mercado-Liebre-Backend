package com.mercado_liebre.user_service.controller;

import com.mercado_liebre.user_service.model.user.*;
import com.mercado_liebre.user_service.security.JwtProvider;
import com.mercado_liebre.user_service.security.Token;
import com.mercado_liebre.user_service.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping
    public List<UserDTO> getAll() {
        return userService.getAll();
    }
    @GetMapping("/{idUser}")
    public Optional<UserDetailDTO> getById(@PathVariable("idUser") Long idUser) {
        return userService.getById(idUser);
    }
    @GetMapping("/by-user/{idUser}")
    public Optional<User> getByIdUser(@PathVariable("idUser") Long idUser) {
        return userService.getByIdUser(idUser);
    }
    @GetMapping("/email/{userEmail}")
    public Optional<UserDetailDTO> getByEmail(@PathVariable("userEmail") String userEmail) {
        return userService.getByEmail(userEmail);
    }

//    @GetMapping("/history/{idUser}")
//    public List<CategoryFamilyDTO> getLatestCategoryFamilyInHistoryById (@PathVariable("idUser") Long idUser) {
//        return userService.getLatestCategoryFamilyInHistoryById(idUser);
//    }
    @GetMapping("/email/validate/{userEmail}")
    public boolean validateEmail(@PathVariable("userEmail") String userEmail) {
        return userService.validateEmail(userEmail);
    }
    @GetMapping("/auth/validate")
    public boolean validateToken(@RequestParam("token") String token) {
        return jwtProvider.validateToken(token);
    }
    @PostMapping("/auth/register")
    public UserCreateDTO registerUser(@RequestBody UserCreateDTO userCreateDTO) {
        return userService.registerUser(userCreateDTO);
    }
    @PostMapping("/auth/login")
    public Token loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        return userService.login(userLoginDTO);
    }
    @PutMapping("/{idUser}")
    public User updateUser(@PathVariable("idUser") Long idUser, @RequestBody User user) {
        return userService.updateUser(idUser, user);
    }
    @PutMapping("/salesMade/{idUser}")
    public UserDTO incrementSalesMade(@PathVariable("idUser") Long idUser) {
        return userService.incrementSalesMade(idUser);
    }
    @DeleteMapping("/{idUser}")
    public UserDTO deleteUser(@PathVariable("idUser") Long idUser) {
        return userService.deleteUser(idUser);
    }
}
