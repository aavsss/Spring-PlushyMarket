package com.example.demo.admin.controller;

import com.example.demo.admin.model.Revenue;
import com.example.demo.admin.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin")
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/revenues")
    public List<Revenue> getRevenues() {
        return adminService.getRevenues();
    }
}
