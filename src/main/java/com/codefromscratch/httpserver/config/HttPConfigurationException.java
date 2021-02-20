package com.codefromscratch.httpserver.config;

public class HttPConfigurationException extends RuntimeException {

    public HttPConfigurationException() {
    }

    public HttPConfigurationException(String message) {
        super(message);
    }

    public HttPConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttPConfigurationException(Throwable cause) {
        super(cause);
    }
}
