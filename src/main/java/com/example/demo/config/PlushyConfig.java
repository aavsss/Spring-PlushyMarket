package com.example.demo.config;

import com.example.demo.appUser.repository.AppUserRepository;
import com.example.demo.appUser.repository.models.AppUserInDB;
import com.example.demo.appUser.userContext.models.AppUserRole;
import com.example.demo.cart.repository.CartRepository;
import com.example.demo.cart.repository.models.CartInDB;
import com.example.demo.crud.repository.PlushyRepository;
import com.example.demo.crud.repository.models.PlushyInDB;
import com.example.demo.globalService.fileService.FileServiceImpl;
import com.example.demo.recommendation.repository.RecommendationRepository;
import com.example.demo.recommendation.repository.models.HitCountInDB;
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
            RecommendationRepository recommendationRepository,
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
                    AppUserRole.USER
            );
            appUserRepository.saveAll(List.of(appUserInDB, seller));

            PlushyInDB naruto = new PlushyInDB(
                    "Naruto",
                    20,
                    5,
                    "naruto is the next hokage",
                    fileService.findByName("plushy/naruto.png"),
                    "aavashsthapit@gmail.com",
                    0
            );

            PlushyInDB sasuke = new PlushyInDB(
                    "Sasuke",
                    19,
                    6,
                    "sasuke unlocks mangekyou sharingan",
                    fileService.findByName("plushy/sasuke.jpg"),
                    "peterparker@gmail.com",
                    0
            );

            PlushyInDB luffy = new PlushyInDB(
                    "Luffy",
                    22,
                    10,
                    "King of the pirates",
                    null,
                    "aavashsthapit@gmail.com",
                    0
            );

            PlushyInDB zoro = new PlushyInDB(
                    "Zoro",
                    21,
                    10,
                    "World's greatest swordsman",
                    null,
                    "aavashsthapit@gmail.com",
                    0
            );

            PlushyInDB nami = new PlushyInDB(
                    "Nami",
                    21,
                    10,
                    "World's greatest navigator",
                    null,
                    "aavashsthapit@gmail.com",
                    0
            );

            plushyRepository.saveAll(
                    List.of(naruto, sasuke, luffy, zoro, nami)
            );

            // Cart
            CartInDB narutoInCartInDB = new CartInDB(1L, 1L, 1);
            cartRepository.save(narutoInCartInDB);

            // Recommendation
            HitCountInDB luffyHitCount = new HitCountInDB(3L, 15);
            HitCountInDB namiHitCount = new HitCountInDB(5L, 4);
            HitCountInDB zoroHitCount = new HitCountInDB(4L, 10);
            recommendationRepository.saveAll(
                    List.of(luffyHitCount, namiHitCount, zoroHitCount)
            );
        };
    }
}
