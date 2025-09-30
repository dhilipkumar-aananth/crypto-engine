package com.crypto.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class DecryptedUrlRequestWrapper extends HttpServletRequestWrapper {
    private final String newUri;

    public DecryptedUrlRequestWrapper(HttpServletRequest request, String newUri) {
        super(request);
        this.newUri = newUri;
    }

    @Override
    public String getRequestURI() {
        return newUri;
    }

    @Override
    public String getServletPath() {
        return newUri;
    }
}

