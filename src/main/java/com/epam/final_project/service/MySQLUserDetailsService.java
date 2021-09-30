package com.epam.final_project.service;

import com.epam.final_project.entity.UserEntity;
import com.epam.final_project.repository.UserRepository;
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
        System.out.println(userRepository.findByLogin(login));
        UserEntity user = userRepository.findByLogin(login).
                orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.out.println("USER");
        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(user.getAccessLevel()));
        System.out.println("HERE");
        return new User(user.getLogin(), user.getPassword(), authorities);
    }
}
