package com.mercado_liebre.transaction_service.service;

import com.mercado_liebre.transaction_service.error.ResponseException;
import com.mercado_liebre.transaction_service.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            Optional<User> authUser = Optional.ofNullable(restTemplate.getForObject("http://user-service/user/email/" + email, User.class));
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
