package com.crypto.config;

import com.crypto.annotations.DecryptDeserializer;
import com.crypto.annotations.EncryptSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonEncryptionConfig {
//    @Bean
//    public Module encryptionModule() {
//        return new SimpleModule()
//                .addSerializer(String.class, new EncryptSerializer())
//                .addDeserializer(String.class, new DecryptDeserializer());
//    }

    @Bean
    public Module encryptionModule() {
        return new SimpleModule(); // no global binding
    }
}


