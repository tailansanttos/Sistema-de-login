package com.tailan.auth.application.service.impl;

import com.tailan.auth.application.handle.exceptions.UserNotFoundException;
import com.tailan.auth.application.infra.security.UserDetailsImpl;
import com.tailan.auth.application.model.domain.User;
import com.tailan.auth.application.model.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UserNotFoundException("User with this " + username + " not found!"));
        return new UserDetailsImpl(user);
    }
}
