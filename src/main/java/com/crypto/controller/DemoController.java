package com.crypto.controller;

import com.crypto.dto.BaseDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DemoController {

    @PostMapping("/save")
    public String save(@RequestBody BaseDTO dto) {
        return "Decrypted object received: name=" + dto.getMessage() + ", age=" + dto.getStatus();
    }
}