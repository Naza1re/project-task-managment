package org.example.taskservice.config;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.example.taskservice.exception.FeignClientException;
import org.example.taskservice.exception.NotFoundException;

@Slf4j
public class MyDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        FeignException exception = FeignException.errorStatus(s, response);
        int status = response.status();
        log.info("Status of response from external service from decoder : {}", status);

        if (status == 503) {
            return exception;
        }
        String[] responseMessageSplit = exception.getMessage().split("\"message\"");
        String[] exMessageSplit = responseMessageSplit[responseMessageSplit.length - 1].split("\"");
        String exMessage = exMessageSplit[exMessageSplit.length - 2];

        log.info("Exception message from external service inf decoder: {}", exMessage);

        return switch (status) {
            case 400 -> throw  new FeignClientException(exMessage);
            case 404 -> throw  new NotFoundException(exMessage);
            default -> exception;
        };
    }
}
