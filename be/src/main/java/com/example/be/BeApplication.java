package com.example.be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;

@SpringBootApplication
public class BeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeApplication.class, args);
        String a = "12345";
        String b = BCrypt.hashpw(a, BCrypt.gensalt(12));
        System.out.println(b);
        boolean c = BCrypt.checkpw(a,b);
        System.out.println(c);
    }
}
