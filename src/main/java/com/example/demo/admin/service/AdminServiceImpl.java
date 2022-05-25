package com.example.demo.admin.service;

import com.example.demo.admin.model.Revenue;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService{

    @Override
    public List<Revenue> getRevenues() {
        return List.of(
                new Revenue("January", 10),
                new Revenue("February", 15),
                new Revenue("March", 20),
                new Revenue("April", 10),
                new Revenue("May", 5),
                new Revenue("June", 7),
                new Revenue("July", 10),
                new Revenue("August", 20),
                new Revenue("September", 15),
                new Revenue("October", 8),
                new Revenue("November", 10),
                new Revenue("December", 13)
        );
    }
}
