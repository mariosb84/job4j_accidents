package ru.job4j;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Main2 {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pwd = encoder.encode("secret");
        System.out.println(pwd);
    }

}
