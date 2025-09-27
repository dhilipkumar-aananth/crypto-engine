package com.crypto.filter;

import com.crypto.config.DecryptedRequestWrapper;
import com.crypto.encryption.AES;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.servlet.Filter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Component
public class DecryptionFilter implements Filter {

    @Value("${security.encryption.enabled}")
    private boolean encryptionEnabled;

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        if(encryptionEnabled){
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String encryptedBody = new BufferedReader(
                    new InputStreamReader(httpRequest.getInputStream(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            String contentType = httpRequest.getContentType();
            String decryptedBody = AES.decrypt(encryptedBody);
            HttpServletRequest wrappedRequest = new DecryptedRequestWrapper(httpRequest, decryptedBody, contentType);
            chain.doFilter(wrappedRequest, response);
        }else{
            chain.doFilter(request, response);
        }
    }
}
