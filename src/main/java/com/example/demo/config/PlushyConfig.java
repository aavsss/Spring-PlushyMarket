package com.example.demo.config;

import com.example.demo.cart.model.Cart;
import com.example.demo.cart.repository.CartRepository;
import com.example.demo.crud.model.Plushy;
import com.example.demo.crud.repository.PlushyRepository;
import com.example.demo.globalService.FileServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PlushyConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            PlushyRepository plushyRepository,
            CartRepository cartRepository,
            FileServiceImpl fileService
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
        };
    }
}
