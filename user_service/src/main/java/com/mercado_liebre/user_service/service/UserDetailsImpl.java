package com.mercado_liebre.user_service.service;

import com.mercado_liebre.user_service.model.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {
    private Long idUser;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long idUser, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.idUser = idUser;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User authUser) {
        Collection<GrantedAuthority> authorities = Collections.singleton(
                new SimpleGrantedAuthority(authUser.getUserRol().getType())
        );
//        Collection<GrantedAuthority> authorities =
//            Stream.of(authUser.getRol()).map(rol -> new SimpleGrantedAuthority(rol)).collect(Collectors.toList());
        return new UserDetailsImpl(
                authUser.getIdUser(),
                authUser.getEmail(),
                authUser.getPassword(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
