package com.epam.final_project;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Mian {

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("adminqwer"));
    }

}
