package com.epam.final_project.service;

import com.epam.final_project.entity.UserEntity;
import com.epam.final_project.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class MySQLUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MySQLUserDetailsService(UserRepository repository) {
        this.userRepository = repository;
    }


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByLogin(login).
                orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(user.getAccessLevel()));
        return new User(user.getLogin(), user.getPassword(), authorities);
    }
}
