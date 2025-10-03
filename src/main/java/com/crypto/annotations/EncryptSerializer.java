package com.crypto.annotations;

import com.crypto.encryption.AES;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

//public class EncryptSerializer extends JsonSerializer<String> {
//    @Override
//    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//        try {
//            gen.writeString(value != null ? AES.encrypt(value) : null);
//        } catch (Exception e) {
//            throw new RuntimeException("Encryption failed", e);
//        }
//    }
//}

public class EncryptSerializer extends JsonSerializer<Object> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        try {
            if (value == null) {
                gen.writeNull();
                return;
            }
            String jsonValue = mapper.writeValueAsString(value);
            String encrypted = AES.encrypt(jsonValue);
            gen.writeString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }
}

