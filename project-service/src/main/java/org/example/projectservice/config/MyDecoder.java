package org.example.projectservice.config;

import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import org.example.projectservice.exception.FeignClientException;
import org.example.projectservice.exception.NotFoundException;

public class MyDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        FeignException exception = FeignException.errorStatus(s, response);
        int status = response.status();
        String[] responseMessageSplit = exception.getMessage().split("\"message\"");
        String[] exMessageSplit = responseMessageSplit[responseMessageSplit.length - 1].split("\"");
        String exMessage = exMessageSplit[exMessageSplit.length - 2];
        if (status == 400) {
            throw new FeignClientException(exMessage);
        }
        if (status == 404) {
            throw new NotFoundException(exMessage);
        }
        return new RetryableException(
                response.status(),
                exception.getMessage(),
                response.request().httpMethod(),
                (Throwable) exception,
                (Long) null,
                response.request());
    }
}
