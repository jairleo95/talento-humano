package com.app.recruitment.util;

import java.net.URI;

public final class UrlValidator {

    private static final String HTTP_SCHEME = "http";
    private static final String HTTPS_SCHEME = "https";

    private UrlValidator() {
    }

    public static boolean isSafeUrl(String url) {
        if (url == null || url.isBlank()) {
            return false;
        }
        try {
            URI uri = URI.create(url);
            String scheme = uri.getScheme();
            return (HTTP_SCHEME.equalsIgnoreCase(scheme) || HTTPS_SCHEME.equalsIgnoreCase(scheme))
                    && uri.getHost() != null;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}