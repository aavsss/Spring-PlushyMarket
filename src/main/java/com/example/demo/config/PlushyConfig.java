package com.example.demo.config;

import com.example.demo.appUser.repository.AppUserRepository;
import com.example.demo.appUser.repository.models.AppUserInDB;
import com.example.demo.appUser.user.models.AppUserRole;
import com.example.demo.cart.repository.CartRepository;
import com.example.demo.cart.repository.models.CartInDB;
import com.example.demo.crud.repository.PlushyRepository;
import com.example.demo.crud.repository.models.PlushyInDB;
import com.example.demo.globalService.FileService.FileServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
public class PlushyConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            PlushyRepository plushyRepository,
            CartRepository cartRepository,
            FileServiceImpl fileService,
            AppUserRepository appUserRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder
    ) {
        return args -> {
            // user
            AppUserInDB appUserInDB = new AppUserInDB(
                    "Aavash",
                    "Sthapit",
                    "aavashsthapit@gmail.com",
                    bCryptPasswordEncoder.encode("password"),
                    AppUserRole.ADMIN
            );
            AppUserInDB seller = new AppUserInDB(
                    "Peter",
                    "Parker",
                    "peterparker@gmail.com",
                    bCryptPasswordEncoder.encode("password"),
                    AppUserRole.SELLER
            );
            appUserRepository.saveAll(List.of(appUserInDB, seller));

            PlushyInDB naruto = new PlushyInDB(
                    "Naruto",
                    20,
                    5,
                    "naruto is the next hokage",
                    fileService.findByName("plushy/naruto.png"),
                    "aavashsthapit@gmail.com"
            );

            PlushyInDB sasuke = new PlushyInDB(
                    "Sasuke",
                    19,
                    6,
                    "sasuke unlocks mangekyou sharingan",
                    fileService.findByName("plushy/sasuke.jpg"),
                    "peterparker@gmail.com"
            );

            plushyRepository.saveAll(
                    List.of(naruto, sasuke)
            );

            // Cart
            CartInDB narutoInCartInDB = new CartInDB(1L, 1L, 1);
            cartRepository.save(narutoInCartInDB);


        };
    }
}
