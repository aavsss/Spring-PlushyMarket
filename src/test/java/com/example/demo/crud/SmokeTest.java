package com.example.demo.crud;

import com.example.demo.crud.controller.PlushyController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class SmokeTest {

    private final PlushyController plushyController;

    @Autowired
    public SmokeTest(PlushyController plushyController) {
        this.plushyController = plushyController;
    }

    @Test
    void contextLoads() throws Exception {
        assert(plushyController != null);
    }
}
