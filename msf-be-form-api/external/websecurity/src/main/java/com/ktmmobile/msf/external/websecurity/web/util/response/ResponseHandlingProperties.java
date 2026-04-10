package com.ktmmobile.msf.external.websecurity.web.util.response;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "response-handling")
public record ResponseHandlingProperties(
    ExceptionResponse exception
) {

    public ResponseHandlingProperties {
        if (exception == null) {
            exception = ExceptionResponse.empty();
        }
    }

    public boolean exceptionEnabled() {
        return exceptionClassName() || exceptionMessage();
    }

    public boolean exceptionClassName() {
        return exception.className();
    }

    public boolean exceptionMessage() {
        return exception.message();
    }

    record ExceptionResponse(
        @DefaultValue("false") boolean className,
        @DefaultValue("false") boolean message
    ) {

        static ExceptionResponse empty() {
            return new ExceptionResponse(false, false);
        }
    }
}
