package com.crypto.controller;

import com.crypto.dto.BaseDTO;
import com.crypto.encryption.EncryptionUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/encryption")
@Slf4j
public class EncryptionController {

	private final EncryptionUtil encryptionUtil;

	public EncryptionController(EncryptionUtil encryptionUtil) {
		this.encryptionUtil = encryptionUtil;
	}

	@PostMapping("/encrypt")
	public ResponseEntity<String> encrypt(@RequestBody String obj) {
		try {
			log.info("Received encryption request. Payload size: {} bytes", obj != null ? obj.length() : 0);
			String encryptedString = encryptionUtil.encryptObject(obj);
			log.debug("Encryption successful. Encrypted output length: {}", encryptedString.length());
			return ResponseEntity.ok(encryptedString);
		} catch (Exception e) {
			log.error("Encryption failed. Input: [{}], Error: {}", obj, e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Encryption failed due to internal error. Please contact support.");
		}
	}

	@PostMapping("/decrypt")
	public ResponseEntity<BaseDTO> decrypt(@RequestBody String encryptedString) {
		try {
			log.info("Received decryption request. Encrypted input length: {} characters",
					encryptedString != null ? encryptedString.length() : 0);
            assert encryptedString != null;
            String sanitizedBase64 = encryptedString.trim().replaceAll("\\s+", "");
			log.debug("Sanitized Base64 input: {}", sanitizedBase64);

			BaseDTO decryptedObject = encryptionUtil.decryptString(sanitizedBase64);
			log.debug("Decryption successful. Output object: {}", decryptedObject);

			return ResponseEntity.ok(decryptedObject);
		} catch (Exception e) {
			log.error("Decryption failed. Input: [{}], Error: {}", encryptedString, e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
