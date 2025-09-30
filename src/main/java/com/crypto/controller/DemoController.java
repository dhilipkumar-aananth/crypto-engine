package com.crypto.controller;

import com.crypto.dto.BaseDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DemoController {

    @PostMapping("/save")
    public BaseDTO save(@RequestBody BaseDTO dto) {
        return new BaseDTO("Status Code","Message","Status");
    }
}