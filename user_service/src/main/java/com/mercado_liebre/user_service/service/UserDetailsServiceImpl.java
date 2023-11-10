package com.mercado_liebre.user_service.service;

import com.mercado_liebre.user_service.error.ResponseException;
import com.mercado_liebre.user_service.model.user.User;
import com.mercado_liebre.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            Optional<User> authUser = userRepository.findByEmail(email);
            if(authUser.isPresent()) {
                return UserDetailsImpl.build(authUser.get());
            } else {
                throw new ResponseException("Token invalid", null, HttpStatus.BAD_REQUEST);
            }
        } catch (ResponseException ex) {
            return (UserDetails) ex;
        }


    }
}
