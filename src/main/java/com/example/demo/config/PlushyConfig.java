package com.example.demo.config;

import com.example.demo.authorization.appuser.models.AppUser;
import com.example.demo.authorization.appuser.models.AppUserRole;
import com.example.demo.authorization.appuser.repository.AppUserRepository;
import com.example.demo.cart.model.Cart;
import com.example.demo.cart.repository.CartRepository;
import com.example.demo.crud.model.Plushy;
import com.example.demo.crud.repository.PlushyRepository;
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
            Plushy naruto = new Plushy(
                    "Naruto",
                    20,
                    5,
                    "naruto is the next hokage",
                    fileService.findByName("plushy/naruto.png")
            );

            Plushy sasuke = new Plushy(
                    "Sasuke",
                    19,
                    6,
                    "sasuke unlocks mangekyou sharingan",
                    fileService.findByName("plushy/sasuke.jpg")
            );

            plushyRepository.saveAll(
                    List.of(naruto, sasuke)
            );

            // Cart
            Cart narutoInCart = new Cart(1L, 1L, 1);
            cartRepository.save(narutoInCart);

            // user
            AppUser appUser = new AppUser(
                    "Aavash",
                    "Sthapit",
                    "aavashsthapit@gmail.com",
                    bCryptPasswordEncoder.encode("password"),
                    AppUserRole.ADMIN
            );
            appUserRepository.save(appUser);
        };
    }
}
