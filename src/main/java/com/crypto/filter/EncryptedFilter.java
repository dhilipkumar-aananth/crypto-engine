package com.crypto.filter;

import com.crypto.config.BufferedResponseWrapper;
import com.crypto.config.DecryptedRequestWrapper;
import com.crypto.encryption.AES;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.servlet.Filter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Component
public class EncryptedFilter implements Filter {

    @Value("${security.encryption.request.enabled}")
    private boolean requestEncryptionEnabled;

    @Value("${security.encryption.response.enabled}")
    private boolean responseEncryptionEnabled;

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        if (requestEncryptionEnabled && responseEncryptionEnabled) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            String encryptedBody = new BufferedReader(new InputStreamReader(httpRequest.getInputStream(), StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
            String contentType = httpRequest.getContentType();
            String decryptedBody = AES.decrypt(encryptedBody);
            HttpServletRequest wrappedRequest = new DecryptedRequestWrapper(httpRequest, decryptedBody, contentType);
            BufferedResponseWrapper responseWrapper = new BufferedResponseWrapper(httpResponse);
            chain.doFilter(wrappedRequest, responseWrapper);
            String originalResponse = new String(responseWrapper.getCapturedBody(), StandardCharsets.UTF_8);
            String encryptedResponse = AES.encrypt(originalResponse);
            httpResponse.setContentType("text/plain;charset=UTF-8");
            byte[] encryptedBytes = encryptedResponse.getBytes(StandardCharsets.UTF_8);
            httpResponse.setContentLength(encryptedBytes.length);
            httpResponse.getOutputStream().write(encryptedBytes);
            httpResponse.getOutputStream().flush();
        } else if (requestEncryptionEnabled) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String encryptedBody = new BufferedReader(new InputStreamReader(httpRequest.getInputStream(), StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
            String contentType = httpRequest.getContentType();
            String decryptedBody = AES.decrypt(encryptedBody);
            HttpServletRequest wrappedRequest = new DecryptedRequestWrapper(httpRequest, decryptedBody, contentType);
            chain.doFilter(wrappedRequest, response);
        } else if (responseEncryptionEnabled) {
            BufferedResponseWrapper responseWrapper = new BufferedResponseWrapper((HttpServletResponse) response);
            chain.doFilter(request, responseWrapper);
            String originalResponse = new String(responseWrapper.getCapturedBody(), StandardCharsets.UTF_8);
            String encryptedResponse = AES.encrypt(originalResponse);
            byte[] encryptedBytes = encryptedResponse.getBytes(StandardCharsets.UTF_8);
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setContentType("text/plain;charset=UTF-8");
            httpResponse.setContentLength(encryptedResponse.getBytes().length);
            httpResponse.getOutputStream().write(encryptedBytes);
            httpResponse.getOutputStream().flush();
        } else {
            chain.doFilter(request, response);
        }
    }
}


