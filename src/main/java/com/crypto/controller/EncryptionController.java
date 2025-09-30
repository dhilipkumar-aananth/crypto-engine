package com.crypto.controller;

import com.crypto.dto.BaseDTO;
import com.crypto.service.EncryptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/encryption")
public class EncryptionController {

    private final EncryptionService encryptionService;

    public EncryptionController(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @PostMapping("/encrypt")
    public ResponseEntity<String> encryptWithAES(@RequestBody String obj) {
        return encryptionService.encrypt(obj, "AES");
    }

    @PostMapping("/encrypt-gcm")
    public ResponseEntity<String> encryptWithGcm(@RequestBody String obj) {
        return encryptionService.encrypt(obj, "AES-GCM");
    }

    @PostMapping("/decrypt")
    public ResponseEntity<BaseDTO> decryptWithAES(@RequestBody String encryptedString) {
        return encryptionService.decrypt(encryptedString, "AES");
    }

    @PostMapping("/decrypt-gcm")
    public ResponseEntity<BaseDTO> decryptWithGCM(@RequestBody String encryptedString) {
        return encryptionService.decrypt(encryptedString, "AES-GCM");
    }
}