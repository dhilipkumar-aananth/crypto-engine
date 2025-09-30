package com.crypto.service;

import com.crypto.dto.BaseDTO;
import com.crypto.encryption.EncryptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EncryptionService {

    private final EncryptionUtil encryptionUtil;

    public EncryptionService(EncryptionUtil encryptionUtil) {
        this.encryptionUtil = encryptionUtil;
    }

    public ResponseEntity<String> encrypt(String input, String algorithm) {
        try {
            log.info("[{}] Encryption request received. Payload size: {} bytes", algorithm, input != null ? input.length() : 0);
            String encrypted = "AES-GCM".equalsIgnoreCase(algorithm) ? encryptionUtil.encryptGCMObject(input) : encryptionUtil.encryptObject(input);
            log.debug("[{}] Encryption successful. Output length: {}", algorithm, encrypted.length());
            return ResponseEntity.ok(encrypted);
        } catch (Exception e) {
            log.error("[{}] Encryption failed. Input: [{}], Error: {}", algorithm, input, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Encryption failed due to internal error. Please contact support.");
        }
    }

    public ResponseEntity<BaseDTO> decrypt(String input, String algorithm) {
        try {
            log.info("[{}] Decryption request received. Input length: {} characters", algorithm, input != null ? input.length() : 0);
            if (input == null || input.trim().isEmpty()) {
                log.warn("[{}] Empty or null input received for decryption", algorithm);
                return ResponseEntity.badRequest().body(null);
            }
            String sanitized = input.trim().replaceAll("\\s+", "");
            log.debug("[{}] Sanitized Base64 input: {}", algorithm, sanitized);
            BaseDTO result = "AES-GCM".equalsIgnoreCase(algorithm) ? encryptionUtil.decryptGCMString(sanitized) : encryptionUtil.decryptString(sanitized);
            log.debug("[{}] Decryption successful. Output object: {}", algorithm, result);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("[{}] Decryption failed. Input: [{}], Error: {}", algorithm, input, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}