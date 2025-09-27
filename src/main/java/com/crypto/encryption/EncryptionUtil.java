package com.crypto.encryption;

import com.crypto.dto.BaseDTO;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class EncryptionUtil {

	private final ObjectMapper objectMapper;

	public EncryptionUtil(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public String encryptObject(Object obj) throws Exception {
		String jsonString = convertObjectToJson(obj);
		return AES.encrypt(jsonString);
	}
	public String encryptGCMObject(Object obj) throws Exception {
		String jsonString = convertObjectToJson(obj);
		return GCM.encrypt(jsonString);
	}

	public <T> T decryptString(String encryptedString, Class<T> valueType) throws Exception {
		String jsonString = AES.decrypt(encryptedString);
		return convertJsonToObject(jsonString, valueType);
	}
	public <T> T decryptGCMString(String encryptedString, Class<T> valueType) throws Exception {
		String jsonString = GCM.decrypt(encryptedString);
		return convertJsonToObject(jsonString, valueType);
	}

	public BaseDTO decryptString(String encryptedString) throws Exception {
		String jsonString = AES.decrypt(encryptedString);
		return convertJsonToObject(jsonString, BaseDTO.class);
	}

	public BaseDTO decryptGCMString(String encryptedString) throws Exception {
		String jsonString = GCM.decrypt(encryptedString);
		return convertJsonToObject(jsonString, BaseDTO.class);
	}

	public String convertObjectToJson(Object obj) throws JsonProcessingException {
		return objectMapper.writeValueAsString(obj);
	}

	public <T> T convertJsonToObject(String jsonString, Class<T> valueType) throws JsonProcessingException {
		return objectMapper.readValue(jsonString, valueType);
	}
}
