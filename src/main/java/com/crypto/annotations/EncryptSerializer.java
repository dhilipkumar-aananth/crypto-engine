package com.crypto.annotations;

import com.crypto.encryption.AES;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class EncryptSerializer extends JsonSerializer<String> {
    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        try {
            gen.writeString(value != null ? AES.encrypt(value) : null);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }
}
