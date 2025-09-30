package com.crypto.annotations;

import com.crypto.encryption.AES;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class DecryptDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        try {
            String value = p.getValueAsString();
            return value != null ? AES.decrypt(value) : null;
        } catch (Exception e) {
            throw new RuntimeException("Decryption failed", e);
        }
    }
}


