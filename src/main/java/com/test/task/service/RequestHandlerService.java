package com.test.task.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.task.domein.GetInfoRequest;
import com.test.task.domein.InfoResponse;
import com.test.task.exception.ConversionException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.test.task.exception.DataNotFoundException;
import com.test.task.exception.RequestNotValidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class RequestHandlerService {

    private final Aes256Class aes;

    private final ObjectMapper mapper;

    private final Logger log = LogManager.getLogger(this.getClass());

    public RequestHandlerService(Aes256Class aes, ObjectMapper mapper) {
        this.aes = aes;
        this.mapper = mapper;
    }

    public InfoResponse handleRequest(GetInfoRequest request) {
        logObject(getJsonFromObject(request), "Request");

        if (request == null) {
            logObject(getJsonFromObject("Request not valid"), "Response");
            throw new RequestNotValidException("Request not valid");
        } else if (request.getId() == 1) {
            var response = new InfoResponse();
            logObject(getJsonFromObject(response), "Response");
            return response;
        } else {
            logObject(getJsonFromObject("Data not found"), "Response");
            throw new DataNotFoundException("Data not found");
        }
    }

    private String getJsonFromObject(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new ConversionException("Object to JSON conversion failed");
        }
    }

    private void logObject(String jsonFromObject, String type) {
        log.info("{} body: {}, time : {}", type, jsonFromObject, LocalDateTime.now().toString());
        var encrypt = aes.getAesOutput(jsonFromObject.getBytes(StandardCharsets.UTF_8), Cipher.ENCRYPT_MODE);
        log.info("=== {} encryption: {}", type, Base64.getEncoder().encodeToString(encrypt));
        var decrypt = aes.getAesOutput(encrypt, Cipher.DECRYPT_MODE);
        log.info("=== {} decryption: {}", type, new String(decrypt));
    }
}
