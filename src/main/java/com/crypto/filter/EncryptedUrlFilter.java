package com.crypto.filter;

import com.crypto.config.DecryptedUrlRequestWrapper;
import com.crypto.encryption.AES;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EncryptedUrlFilter implements Filter {

    @Value("${security.encryption.url.enabled}")
    private boolean urlEncryptionEnabled;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    private static final String SLASH = "/";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!urlEncryptionEnabled) {
            chain.doFilter(request, response);
        } else {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String encryptedPath = httpRequest.getRequestURI();
            try {
                String[] parts = encryptedPath.split(contextPath + SLASH);
                if (parts.length > 1) {
                    String encryptedSegment = parts[1];
                    String decryptedPath = AES.decrypt(encryptedSegment);
                    HttpServletRequest wrappedRequest = new DecryptedUrlRequestWrapper(httpRequest, contextPath + SLASH + decryptedPath);
                    chain.doFilter(wrappedRequest, response);
                    return;
                }
            } catch (Exception e) {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid encrypted URL");
                return;
            }
            chain.doFilter(request, response);
        }
    }
}
