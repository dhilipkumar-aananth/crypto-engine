package com.crypto.annotations;

import com.crypto.encryption.AES;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

//public class DecryptDeserializer extends JsonDeserializer<String> {
//    @Override
//    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
//        try {
//            String value = p.getValueAsString();
//            return value != null ? AES.decrypt(value) : null;
//        } catch (Exception e) {
//            throw new RuntimeException("Decryption failed", e);
//        }
//    }
//}

public class DecryptDeserializer extends JsonDeserializer<Object> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        try {
            if (p.currentToken() == JsonToken.VALUE_STRING) {
                String encrypted = p.getValueAsString();
                if (encrypted == null || encrypted.isBlank()) {
                    return null;
                }

                // 🔓 Decrypt the value
                String decryptedJson = AES.decrypt(encrypted).trim();

                // ✅ If decrypted value looks like JSON
                if (decryptedJson.startsWith("{")) {
                    // Object or Map
                    return mapper.readValue(decryptedJson, Map.class);
                } else if (decryptedJson.startsWith("[")) {
                    // List / Array
                    return mapper.readValue(decryptedJson, List.class);
                } else {
                    // ✅ Otherwise plain primitive (String, number, boolean, etc.)
                    return decryptedJson;
                }
            }

            // Case: Already JSON → delegate to Jackson
            return mapper.readValue(p, ctxt.getContextualType() != null
                    ? ctxt.getContextualType()
                    : mapper.constructType(Object.class));

        } catch (Exception e) {
            throw new RuntimeException("Decryption failed: " + e.getMessage(), e);
        }
    }
}
